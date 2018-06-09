package firis.tatara.item;

import java.io.IOException;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

import firis.tatara.TataraCraft;
import firis.tatara.TataraItems;
import firis.tatara.gui.TataraGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.ItemModelMesherForge;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.ISmartItemModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class _ItemStorageBox extends Item {

	
	/**
	 * コンストラクタ
	 */
	public _ItemStorageBox() {
		
		//初期化
		this.init();
	}
	
	/**
	 * アイテムの初期化
	 */
	protected void init() {

		//クリエイティブタブ
		this.setCreativeTab(TataraCraft.tabTatara);
		
		//システム名の登録はアイテム登録時に行うのでここでは記述しない
		
		//その他の設定項目
        /* this.setUnlocalizedName("Magnetite")/*システム名の登録*/
		/*.setHasSubtypes(true)*//*ダメージ値等で複数の種類のアイテムを分けているかどうか。デフォルトfalse*/
		/*.setMaxDamage(256)*//*耐久値の設定。デフォルト0*/
		/*.setFull3D()*//*３D表示で描画させる。ツールや骨、棒等。*/
		/*.setContainerItem(Items.stick)*//*クラフト時にアイテムを返却できるようにしている際の返却アイテムの指定。*/
		/*.setPotionEffect(PotionHelper.ghastTearEffect)*//*指定文字列に対応した素材として醸造台で使える。PotionHelper参照のこと。*/
		/*.setNoRepair()*//*修理レシピを削除し、金床での修繕を出来なくする*/
        /* this.setMaxStackSize(64);/*スタックできる量。デフォルト64*/ 
				
	}
	
	/**
	 * 右クリック時の処理を行う
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer playerIn) {
		
		if(!world.isRemote){
			
			//クライアントの処理
			//ModelLoader.setCustomModelResourceLocation(item.getItem(), 0, new ModelResourceLocation("tataracraft:StoneReinforcedSuperDrill", "inventory"));
			
			//IBakedModel bakedModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(modelResourceLocation);
			
			
			item.setTagCompound(new NBTTagCompound());
			
			item.getTagCompound().setString("test", "1");

		}
		return item;
	}
	
	//**********
	
	@SubscribeEvent
    public void onBakingModelEvent(ModelBakeEvent event) {
        /* 形状のパクリ元 */
        ResourceLocation rl = new ResourceLocation("block/cactus");
        try {
            /* パクリ元のIModelを取得。注意：ModelResourceLocationでも取れるが、IRetexturableModelではない。 */
            IModel model = event.modelLoader.getModel(rl);
            if (model instanceof IRetexturableModel) {
            	
            	
                IBakedModel bakedModel = new tataraRetexturedBakedModel((IRetexturableModel) model);
                
                ModelResourceLocation testModel = new ModelResourceLocation("tataracraft:DiamondDrill", "inventory");
                
                /* ブロック描画用モデルのセット。ISmartBlockModel継承のものが良い。 */
                //event.modelRegistry.putObject(normalMRL, bakedModel);
                /* インベントリ描画用のモデルのセット。ISmartItemModel継承のものが良い */
                event.modelRegistry.putObject(testModel, bakedModel);
                
                
            }
        } catch (IOException e) {
            /* モデル指定がミスるとここに飛ぶ */
            e.printStackTrace();
        }
    }
	
    @SubscribeEvent
    public void textureStitch(TextureStitchEvent.Pre event) {
        TextureMap textureMap = event.map;
        /* JSONで指定していないテクスチャはこのタイミングで登録する。 */
        //textureMap.registerSprite(modTexRL);
    }
	
	/**
	 * モデルの管理用のアイテムか？
	 * @author computer
	 *
	 */
	@SideOnly(Side.CLIENT)
	private static class tataraRetexturedBakedModel implements ISmartItemModel {

		private final IRetexturableModel retexturableModel;
		
        /* bakeメソッドで必要。staticなクラスに置いても良いと思う。 */
        private Function<ResourceLocation, TextureAtlasSprite> textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
            public TextureAtlasSprite apply(ResourceLocation location) {
                return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
            }
        };
        
		/**
		 * 動的にアイテムを入れ替えるならこれ？か
		 */
		@Override
        public IBakedModel handleItemState(ItemStack stack) {
			
			//モデル用のなにか？
			ItemModelMesherForge itemModelMesher = (ItemModelMesherForge) Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
			
			ItemStack aaa = new ItemStack(Items.emerald);
			
			IBakedModel ibakedmodel = itemModelMesher.getItemModel(aaa);

			if (stack.hasTagCompound()) {
				ItemStack bbbb = new ItemStack(Items.apple);
				
				ibakedmodel = itemModelMesher.getItemModel(bbbb);
			}
			
			
			//IRetexturableModel reModel = (IRetexturableModel) ibakedmodel;
			
			/*
			ResourceLocation modTexRL = new ResourceLocation("tataracraft:StoneReinforcedSuperDrill");
			reModel.getTextures().add(modTexRL);
			reModel = (IRetexturableModel) reModel.bake(reModel.getDefaultState(), Attributes.DEFAULT_BAKED_FORMAT, textureGetter);
			*/
			return ibakedmodel;
			
			/*
			Items.apple.getModel(stack, null, 0);
			
			
			ResourceLocation modTexRL = new ResourceLocation("tataracraft:StoneReinforcedSuperDrill");
			*/
			
            /* retextureメソッドで、JSONで指定したテクスチャを入れ替えてモデルを生成できる。
             * 今回はsideに指定したテクスチャを羊毛テクスチャに入れ替え */
			/*
			return retexturableModel.retexture(ImmutableMap.of("layer0", "tataracraft:StoneReinforcedSuperDrill",
                    "layer1", modTexRL.toString()))
						.bake(retexturableModel.getDefaultState(), Attributes.DEFAULT_BAKED_FORMAT, textureGetter);
			*/
		}
		
		
        
        public tataraRetexturedBakedModel(IRetexturableModel model) {
            retexturableModel = model;
        }
        
		@Override
		public List<BakedQuad> getFaceQuads(EnumFacing p_177551_1_) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public List<BakedQuad> getGeneralQuads() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public boolean isAmbientOcclusion() {
			// TODO 自動生成されたメソッド・スタブ
			return false;
		}

		@Override
		public boolean isGui3d() {
			// TODO 自動生成されたメソッド・スタブ
			return false;
		}

		@Override
		public boolean isBuiltInRenderer() {
			// TODO 自動生成されたメソッド・スタブ
			return false;
		}

		@Override
		public TextureAtlasSprite getParticleTexture() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public ItemCameraTransforms getItemCameraTransforms() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

	}
	
	
}
