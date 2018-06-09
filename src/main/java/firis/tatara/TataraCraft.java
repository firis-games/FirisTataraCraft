package firis.tatara;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;

import firis.tatara.biome.YuzukiBiome;
import firis.tatara.enchantment.EnchantmentMobSpawner;
import firis.tatara.entity.EntityCarbonArrow;
import firis.tatara.entity.EntityRendererItem;
import firis.tatara.entity.EntityTest;
import firis.tatara.entity.EntityTestModel;
import firis.tatara.entity.EntityTestRenderer;
import firis.tatara.entity.EntityTorchBall;
import firis.tatara.entity.living.EntityChickenCustom;
import firis.tatara.entity.living.RenderChickenCustom;
import firis.tatara.event.EventBlockMobSpawner;
import firis.tatara.event.EventBlockTest;
import firis.tatara.event.EventOreGen;
import firis.tatara.event.EventPlayerTest;
import firis.tatara.event.TataraDestoryAllEvent;
import firis.tatara.event.TataraEntityDropEvent;
import firis.tatara.gui.TataraGuiHandler;
import firis.tatara.item.ItemCarbonBow;
import firis.tatara.item.ItemToolBag;
import firis.tatara.item.ItemTorchBall;
import firis.tatara.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Modの情報を定義する */
@Mod(
		modid = TataraCraft.MOD_ID, 
		name = TataraCraft.MOD_NAME, 
		version = TataraCraft.MOD_VERSION, 
		dependencies = TataraCraft.MOD_DEPENDENCIES, 
		acceptedMinecraftVersions = TataraCraft.MOD_ACCEPTED_MINECRAFT_VERSIONS
	)
public class TataraCraft { 
	
	/**
	 * **************************************************
	 * Mod Infomation
	 * MODの情報をセット、固定値についてはここを参照するようにする
	 * **************************************************
	 */
	/** Mod Id */
    public static final String MOD_ID = "TataraCraft";
    //public static final String MOD_ID2 = "YukariCraft";
    /** MOD名 */
    public static final String MOD_NAME = "TataraCraft";
    //public static final String MOD_NAME2 = "YukariCraft";
    /** MOD Version */
    public static final String MOD_VERSION = "0.1";
    /** 前提mod(Forgeをとりあえず前提にする。Forgeだと設定しなくてもいいのかは不明) 
     * 参考URL
     * http://minecraftjp.info/modding/index.php/MOD%E3%81%8C%E8%AA%AD%E3%81%BF%E8%BE%BC%E3%81%BE%E3%82%8C%E3%82%8B%E9%A0%86%E7%95%AA%E3%82%92%E6%8C%87%E5%AE%9A%E3%81%99%E3%82%8B
     * */
    public static final String MOD_DEPENDENCIES = "required-after:Forge@[1.8-11.15.1.1722,)";
    /** 起動出来るMinecraft本体のバージョン。記法はMavenのVersion Range Specificationを検索すること。 */
    public static final String MOD_ACCEPTED_MINECRAFT_VERSIONS = "[1.8.9]";
    
    
    /**
     * **************************************************
     * loggerのインスタンス設定
     * **************************************************
     */
	public static Logger logger = LogManager.getLogger(TataraCraft.MOD_ID);

	
	/**
     * **************************************************
     * Modのインスタンス設定
     * **************************************************
     */
    @Instance(TataraCraft.MOD_ID)
    public static TataraCraft INSTANCE;
    
    
    /**
     * **************************************************
     * Server/Client用Proxyのインスタンス設定
     * **************************************************
     */
    @SidedProxy(clientSide = "firis.tatara.proxy.ClientProxy", serverSide = "firis.tatara.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    
    /**
     * **************************************************
     * クリエイティブタブ
     * CreativeTabsを継承したクラスからインスタンスを生成する
     * 引数はローカライズ用の内部名になる
     * （itemGroup.argc, すなわちこの場合はitemGroup.tataraTab=LocalizedName）
     * 
     *　別クラス定義でもいいが単純なタブのため無名クラスで定義する
     * 
     * **************************************************
     */
    public static final CreativeTabs tabTatara = new CreativeTabs("tabTatara") {
    	@Override
    	public Item getTabIconItem()
    	{
    		//アイテムのアイコンでタブのアイコンを設定してる
    		return TataraItems.StoneReinforcedSuperDrill;
    	}
    };
    
    /** ************************************************** */
    
    /**
     * EventHandlerアノテーション関連での参考URL
     * 概要
     * http://hevohevo.hatenablog.com/entry/2014/08/15/185204
     * メカニズムの初期化
     * https://github.com/aidancbrady/Mekanism/blob/master/src/main/java/mekanism/tools/common/MekanismTools.java
     */
    
    
    public static Enchantment enchantmentMobSpawner;
    
    /**
     * FMLPreInitializationEventイベント
     * @EventHandlerアノテーションでイベント定義を行っている
     * メソッド名は可変にできるがmodding的にはpreInitを使うっぽい
     * 事前の初期化処理を行う
     * 設定ファイル・ブロックの登録等
     * 
     * とりあえずブロックとかアイテムの登録を行う
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	
    	
    	
    	/** アイテムの初期化 */
    	/********************************************************************************/
    	TataraItems.registerItems();
    	
    	
    	/** ブロックの初期化 */
    	/********************************************************************************/
    	TataraBlocks.registerBlocks();
    	
    	
		//Blocks.mob_spawner.setCreativeTab(TataraCraft.tabTatara);

    	/** エンチャントを生成？ */
    	TataraCraft.enchantmentMobSpawner = new EnchantmentMobSpawner();
    	Enchantment.addToBookList(TataraCraft.enchantmentMobSpawner);
    	
    	
	
		
    	
    	/** Entity */
    	/********************************************************************************/
    	//矢を設定
        int EntityId = 0;
        EntityRegistry.registerModEntity(EntityCarbonArrow.class, "EntityCarbonArrow", EntityId, this, 64, 5, true);
        
        
        EntityId = 1;
        EntityRegistry.registerModEntity(EntityTest.class, "EntityTest", EntityId, this, 64, 5, true);

        //投げ松明
        EntityId = 2;
        EntityRegistry.registerModEntity(EntityTorchBall.class, "EntityTorchBall", EntityId, this, 64, 5, true);
    	

        //中身鳥
        EntityId = 3;
        EntityRegistry.registerModEntity(EntityChickenCustom.class, "EntityChickenCustom", EntityId, this, 64, 5, true);

        
        
    	//テクスチャ・モデル指定JSONファイル名の登録。
        if (event.getSide() == Side.CLIENT) {
        	
        	
        	//※※レンダーの設定はpreinitのタイミングでやらないと正常に設定されないから注意
        	RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new IRenderFactory<EntityTest>() {
    			@Override
    			public Render<EntityTest> createRenderFor(RenderManager manager)
    			{
    				RenderItem rmi = Minecraft.getMinecraft().getRenderItem();
    				return new EntityTestRenderer(manager, rmi);
    			}
        	});
        	
        	
        	//投げ松明のrenderer
        	RenderingRegistry.registerEntityRenderingHandler(EntityTorchBall.class, new IRenderFactory<EntityTorchBall>() {
				@Override
    			public Render<EntityTorchBall> createRenderFor(RenderManager manager)
    			{
    				RenderItem rmi = Minecraft.getMinecraft().getRenderItem();
    				return new EntityRendererItem<EntityTorchBall>(manager, TataraItems.TorchBall, rmi);
    			}
        	});
        	
        	
        	
        	//にわとりのrenderer
        	RenderingRegistry.registerEntityRenderingHandler(EntityChickenCustom.class, new IRenderFactory<EntityChickenCustom>() {
				@Override
    			public Render<? super EntityChickenCustom> createRenderFor(RenderManager manager)
    			{
					//Render<? super T>
    				//return new RenderChicken(manager, new ModelChicken(), 0.3F);
    				return new RenderChickenCustom(manager);
    			}
        	});
        	
	
        }

    }
 
    @SideOnly(Side.CLIENT)
    public class ToolBagItemMeshDefinition implements ItemMeshDefinition {
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			// TODO 自動生成されたメソッド・スタブ
			return new ModelResourceLocation("tataracraft:ToolBag_default", "inventory");
		}
    }
    
	/**
	 * FMLInitializationEventイベント
	 * @EventHandlerアノテーションでイベント定義を行っている
	 * メソッド名は可変にできるがmodding的にはinitかloadを使うっぽい
	 * 初期化処理を行う
	 * レシピの追加とかを行う（メカニズムは一括でやってるから微妙に参考にならないかも）
	 */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){

    	
    	/*
    	int min = 1;
    	int max = 1;
    	int rarity = 10000000;
    	ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).addItem(
    			new WeightedRandomChestContent(new ItemStack(TataraItems.DiamondReinforcedDrill), min, max, rarity));
    	*/
    	
    	//バイオームの設定
    	
    	
    	
    	/*
    	//バイオームの設定
    	BiomeGenBase biome1 = (new YuzukiBiome(65))
    		    .setColor(0xff0000)
    		    .setBiomeName("YuzukiBiome");
    	//2つ目の引数はバイオーム発生頻度の重み付け。大きいほど頻繁に出現する。
    	BiomeManager.addBiome(BiomeType.DESERT, new BiomeManager.BiomeEntry(biome1, 10));
    	*/
    	
    	//ハンマー用の割り込み（ブロック破壊）
    	//テスト用
		//初期化の時点でForge Eventが書かれているインスタンス(今回は同一クラス内なのでthis)を登録しておく
		//MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new TataraDestoryAllEvent());
        
        
        MinecraftForge.EVENT_BUS.register(new EventBlockMobSpawner());
        
        
        //落下のイベントとかテスト用
        MinecraftForge.EVENT_BUS.register(new EventPlayerTest());

        //アイテムを拾うイベント
        MinecraftForge.EVENT_BUS.register(new ItemToolBag());
        
        
        MinecraftForge.EVENT_BUS.register(new TataraEntityDropEvent());
        
        
        //鉱石生成のイベント
        MinecraftForge.ORE_GEN_BUS.register(new EventOreGen());
        
        //収穫イベントのテスト
        MinecraftForge.EVENT_BUS.register(new EventBlockTest());
        
        
        

        
        //EntityRegistry.registerModEntity(EntityTest.class, "EntityTest", 0, this, 64, 5, true);
    	

        /*

        */
        
        //FML Eventの場合はこちらを使用
        //FMLCommonHandler.instance().bus().register(this);
        
        
        
        //NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHopper());
        
        //エンティティレンダーの設定はエンティティ登録後？
        if (event.getSide().isClient()) {
        	//ダミーの定義を設定？
        	clientLoad();
        	
        }
        
        //ツールボックスのGUIを開く
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new TataraGuiHandler());
    	
    	
        
        //レシピの登録
        TataraRecipes.addRecipes();
        
        //精錬レシピの登録
        TataraRecipes.addSmeltingRecipes();
 
    }
    
    @SideOnly(Side.CLIENT)
    public void clientLoad() {
    	//FMLClientHandlerが分離しとかないとマルチでおちる
    	FMLClientHandler.instance().getClient().getRenderItem().getItemModelMesher().register(TataraItems.ToolBag, new ToolBagItemMeshDefinition());
    	//ツールバックの制御関連
    	MinecraftForge.EVENT_BUS.register(new ItemToolBag());
    	
    	
    	//弓の視野関連
    	MinecraftForge.EVENT_BUS.register(new ItemCarbonBow());
    	
    	
    	//Entityのテストレンダーとか
    	//mekanismからもってきた
    	//ここが動いてないせいでテクスチャの貼り付けができてない？
    	/*
    	RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new IRenderFactory<EntityTest>() {
			@Override
			public Render<EntityTest> createRenderFor(RenderManager manager)
			{
				return new EntityTestRenderer(manager);
			}
    	});
    	*/
    	

    	/*
    	Map<Class<? extends Entity>, Render<? extends Entity>> entityRenderMap = Maps.<Class<? extends Entity>, Render<? extends Entity>>newHashMap();
    	RenderManager rm = Minecraft.getMinecraft().getRenderManager();
    	RenderItem rmi = Minecraft.getMinecraft().getRenderItem();
    	
    	entityRenderMap.put(EntityTest.class, new EntityTestRenderer(rm, rmi));
    	net.minecraftforge.fml.client.registry.RenderingRegistry.loadEntityRenderers(rm, entityRenderMap);
    	*/
    	
    	//net.minecraftforge.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new RenderFatory1());
    	
    	
    	//Entityのテスト
    	//RenderingRegistry.registerEntityRenderingHandler(EntityCarbonArrow.class, new EntityRenderer(new EntityArrow()));

    }
 
    /**
     * FMLPostInitializationEventイベント
     * @EventHandlerアノテーションでイベント定義を行っている
     * メソッド名は可変にできるがmodding的にはpostInitを使うっぽい
     * 初期化処理後の処理を行う
     * 全ロード前提の処理とか別のModとの連携処理とかはここで記載するらしい
     * 序盤は使うことないけど定義だけ行っておく
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	
    	TataraRecipesConvert.convertRecipes();
    	
    }
}