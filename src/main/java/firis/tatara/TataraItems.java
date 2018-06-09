package firis.tatara;

import firis.tatara.item.ItemBakedRottenFlesh;
import firis.tatara.item.ItemCarbonBow;
import firis.tatara.item.ItemExpBook;
import firis.tatara.item.ItemGlassTube;
import firis.tatara.item.ItemGlassTubeEmpty;
import firis.tatara.item.ItemGlassTubePotion;
import firis.tatara.item.ItemMobSpawner;
import firis.tatara.item.ItemStoneBreakHammer;
import firis.tatara.item.ItemTataraBasic;
import firis.tatara.item.ItemTataraFoodBasic;
import firis.tatara.item.ItemToolBag;
import firis.tatara.item.ItemTorchBall;
import firis.tatara.item.TestItemEgg;
import firis.tatara.item.armor.ItemTataraArmor;
import firis.tatara.item.armor.ItemYukariArmor;
import firis.tatara.item.fluid.ItemBucketTank;
import firis.tatara.item.fluid.ItemWaterTank;
import firis.tatara.item.tool.ItemTataraDrill;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * たたらクラフトのアイテム定義
 * アイテムのインスタンス等を書く
 */
public class TataraItems {

	//マグネタイト
	public static Item Magnetite = new ItemTataraBasic();
	
	//砂鉄
	public static Item IronSand = new ItemTataraBasic();
	
	//銑鉄
	public static Item PigIron = new ItemTataraBasic();
	
	//鉄のナゲット
	public static Item IronNugget = new ItemTataraBasic();
	
	//土粘土
	public static Item DirtClay = new ItemTataraBasic();
	
	//石割ハンマー
	public static Item StoneBreakHammer = new ItemStoneBreakHammer();
	
	//経験値本
	public static Item ExpBook = new ItemExpBook();
	
	//バケツタンク
	public static Item BucketTank = new ItemBucketTank();
	
	//水タンク
	public static Item WaterTank = new ItemWaterTank();
	
	//木製ドリル
	public static Item WoodDrill = new ItemTataraDrill(Item.ToolMaterial.WOOD);
	
	//石製ドリル
	public static Item StoneDrill = new ItemTataraDrill(Item.ToolMaterial.STONE);

	//鉄製ドリル
	public static Item IronDrill = new ItemTataraDrill(Item.ToolMaterial.IRON);
		
	//金製ドリル
	public static Item GoldDrill = new ItemTataraDrill(Item.ToolMaterial.GOLD);

	//ダイヤ製ドリル
	public static Item DiamondDrill = new ItemTataraDrill(Item.ToolMaterial.EMERALD);
	
	//石製強化ドリル
	public static Item StoneReinforcedDrill = new ItemTataraDrill(ItemTataraDrill.STONE_2TIMES);
	
	//鉄製強化ドリル
	public static Item IronReinforcedDrill = new ItemTataraDrill(ItemTataraDrill.IRON_BLOCK);

	//ダイヤ製強化ドリル
	public static Item DiamondReinforcedDrill = new ItemTataraDrill(ItemTataraDrill.DIAMOND_BLOCK);

	//石製超強化ドリル
	public static Item StoneReinforcedSuperDrill = new ItemTataraDrill(ItemTataraDrill.STONE_3TIMES);

	
	//目玉焼き
	public static ItemFood FriedEgg = new ItemTataraFoodBasic(3, 2.5F);
	
	//焼きかぼちゃ
	public static ItemFood BakedPumpkin = new ItemTataraFoodBasic(4, 4.0F);
	
	//焼いた腐り肉
	public static ItemFood BakedRottenFlesh = new ItemBakedRottenFlesh();
	
	//スイカジュース
	public static ItemFood MelonJuice = new ItemTataraFoodBasic(8, 5.0F);
	
	
	//ウッドヘルメット
	public static ItemArmor WoodHelmet = new ItemTataraArmor(ItemTataraArmor.WOOD, ItemTataraArmor.ArmorType.HELMET);
	
	//ウッドチェストプレート
	public static ItemArmor WoodChestplate = new ItemTataraArmor(ItemTataraArmor.WOOD, ItemTataraArmor.ArmorType.CHESTPLATE);
	
	//ウッドレギンス
	public static ItemArmor WoodLeggings = new ItemTataraArmor(ItemTataraArmor.WOOD, ItemTataraArmor.ArmorType.LEGGINGS);

	//ウッドブーツ
	public static ItemArmor WoodBoots = new ItemTataraArmor(ItemTataraArmor.WOOD, ItemTataraArmor.ArmorType.BOOTS);

	//カーボンヘルメット
	public static ItemArmor CarbonHelmet = new ItemTataraArmor(ItemTataraArmor.CARBON, ItemTataraArmor.ArmorType.HELMET);
	
	//カーボンチェストプレート
	public static ItemArmor CarbonChestplate = new ItemTataraArmor(ItemTataraArmor.CARBON, ItemTataraArmor.ArmorType.CHESTPLATE);
	
	//カーボンレギンス
	public static ItemArmor CarbonLeggings = new ItemTataraArmor(ItemTataraArmor.CARBON, ItemTataraArmor.ArmorType.LEGGINGS);

	//カーボンブーツ
	public static ItemArmor CarbonBoots = new ItemTataraArmor(ItemTataraArmor.CARBON, ItemTataraArmor.ArmorType.BOOTS);

	
	//道具袋
	public static Item ToolBag = new ItemToolBag();
	
	//不思議な卵
	public static Item WonderEgg = new ItemTataraBasic();
	
	//炭の粉
	public static Item CarbonDust = new ItemTataraBasic();
	
	//炭素繊維
	public static Item CarbonFiber = new ItemTataraBasic();
	
	//カーボン弓
	public static Item CarbonBow = new ItemCarbonBow();

	//ガラス管
	public static Item GlassTube = new ItemGlassTubeEmpty();
	
	//水入りガラス管
	public static Item GlassTubeWater = new ItemGlassTube(ItemGlassTube.Metadata.WATER);

	//溶岩入りガラス管
	public static Item GlassTubeLava = new ItemGlassTube(ItemGlassTube.Metadata.LAVA);
	
	//牛乳入りガラス管
	public static Item GlassTubeMilk = new ItemGlassTubePotion(ItemGlassTubePotion.Metadata.MILK);
	
	
	//yukariアーマー
	public static ItemArmor YukariHelmet = new ItemYukariArmor(ItemYukariArmor.ArmorType.HELMET);
	public static ItemArmor YukariChestplate = new ItemYukariArmor(ItemYukariArmor.ArmorType.CHESTPLATE);
	public static ItemArmor YukariLeggings = new ItemYukariArmor(ItemYukariArmor.ArmorType.LEGGINGS);
	public static ItemArmor YukariBoots = new ItemYukariArmor(ItemYukariArmor.ArmorType.BOOTS);
		
	
	//TestEgg
	public static Item TestEgg = new TestItemEgg();
	
	//Mobスポナー
	public static Item MobSpawner = new ItemMobSpawner();
	
	//投げ松明
	public static Item TorchBall = new ItemTorchBall();
	
	//銀インゴット
	public static Item SilverIngot = new ItemTataraBasic();
	
	
	/**
	 * アイテム情報の登録
	 */
	public static void registerItems() {
		
		TataraItems.registerItem(Magnetite, "Magnetite");
		
		TataraItems.registerItem(IronSand, "IronSand");
		
		TataraItems.registerItem(PigIron, "PigIron");
		
		TataraItems.registerItem(IronNugget, "IronNugget");

		TataraItems.registerItem(DirtClay, "DirtClay");
		
		TataraItems.registerItem(StoneBreakHammer, "StoneBreakHammer");

		TataraItems.registerItem(ExpBook, "ExpBook");

		//TataraItems.registerItem(BucketTank, "BucketTank");

		//TataraItems.registerItem(WaterTank, "WaterTank");

		TataraItems.registerItem(WoodDrill, "WoodDrill");
		TataraItems.registerItem(StoneDrill, "StoneDrill");
		TataraItems.registerItem(IronDrill, "IronDrill");
		TataraItems.registerItem(GoldDrill, "GoldDrill");
		TataraItems.registerItem(DiamondDrill, "DiamondDrill");
		
		TataraItems.registerItem(StoneReinforcedDrill, "StoneReinforcedDrill");
		TataraItems.registerItem(IronReinforcedDrill, "IronReinforcedDrill");
		TataraItems.registerItem(DiamondReinforcedDrill, "DiamondReinforcedDrill");
		
		TataraItems.registerItem(StoneReinforcedSuperDrill, "StoneReinforcedSuperDrill");
		
		
		TataraItems.registerItem(FriedEgg, "FriedEgg");
		
		TataraItems.registerItem(BakedPumpkin, "BakedPumpkin");
		
		TataraItems.registerItem(BakedRottenFlesh, "BakedRottenFlesh");

		TataraItems.registerItem(MelonJuice, "MelonJuice");
		
		TataraItems.registerItem(WoodHelmet, "WoodHelmet");
		TataraItems.registerItem(WoodChestplate, "WoodChestplate");
		TataraItems.registerItem(WoodLeggings, "WoodLeggings");
		TataraItems.registerItem(WoodBoots, "WoodBoots");
		
		TataraItems.registerItem(CarbonHelmet, "CarbonHelmet");
		TataraItems.registerItem(CarbonChestplate, "CarbonChestplate");
		TataraItems.registerItem(CarbonLeggings, "CarbonLeggings");
		TataraItems.registerItem(CarbonBoots, "CarbonBoots");
		
		TataraItems.registerItem(ToolBag, "ToolBag");
		
		
		TataraItems.registerItem(WonderEgg, "WonderEgg");
		
		TataraItems.registerItem(CarbonDust, "CarbonDust");	
		TataraItems.registerItem(CarbonFiber, "CarbonFiber");	

		TataraItems.registerItem(CarbonBow, "CarbonBow");

		TataraItems.registerItem(GlassTube, "GlassTube");
		
		TataraItems.registerItem(GlassTubeWater, "GlassTubeWater");
		TataraItems.registerItem(GlassTubeLava, "GlassTubeLava");
		TataraItems.registerItem(GlassTubeMilk, "GlassTubeMilk");
		
		
		
		
		TataraItems.registerItem(MobSpawner, "MobSpawner");
		
		
		TataraItems.registerItem(TorchBall, "TorchBall");
		
		
		
		/*
		TataraItems.registerItem(YukariHelmet, "YukariHelmet");
		TataraItems.registerItem(YukariChestplate, "YukariChestplate");
		TataraItems.registerItem(YukariLeggings, "YukariLeggings");
		TataraItems.registerItem(YukariBoots, "YukariBoots");
		
		*/
		
		TataraItems.registerItem(SilverIngot, "SilverIngot");
		
		TataraItems.registerItem(TestEgg, "TestEgg");
		
		//テクスチャの設定
		TataraCraft.proxy.registerItemRenderers();
		
	}
	
	/**
	 * GameRegistry.registerItemのラッパー
	 * @param item
	 * @param name
	 */
	protected static void registerItem(Item item, String name) {
		
		item.setUnlocalizedName(name).setRegistryName(TataraCraft.MOD_ID + ":" + name);
		GameRegistry.registerItem(item, name);
		
	}
	
}
