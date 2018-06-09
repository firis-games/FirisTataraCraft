package firis.tatara.item;

import java.util.List;

import firis.tatara.TataraCraft;
import firis.tatara.gui.TataraGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.ItemModelMesherForge;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ISmartItemModel;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemToolBag extends Item {

	
	/**
	 * コンストラクタ
	 */
	public ItemToolBag() {
		
		//初期化
		this.init();
	}
	
	/**
	 * たたらクラフトベーシックアイテムの初期化
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
        this.setMaxStackSize(1);/*スタックできる量。デフォルト64*/ 
		
	}
	
	
	/**
	 * エンチャントエフェクトの有無を判断する
	 */
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        //return stack.isItemEnchanted();
        if (!stack.hasTagCompound()) {
        	return false;
        }
		NBTTagCompound compound = stack.getTagCompound();
		
		//NBTからクラス情報へ読み込みを行う
		String itemId = compound.getString("inventorySlotItemName");
		
		if (itemId == null || "".equals(itemId)) {
			return false;
		}
		
		return true;
		
    }
	
	
	
	/**
	 * NBTの情報を確認して設定されているアイテムが何かを確認する
	 * 一致するものが入ってればtrue
	 * @param item
	 * @return
	 */
	public boolean hasToolBagInventoryItem(ItemStack toolBag, ItemStack stack) {
		
		//ツールバッグならアイテムが一致するかをチェックする
		String itemId = null;
		int itemMetadata = 0;
		//long itemStackSize = 0;
		
		if (toolBag.hasTagCompound()) {
			NBTTagCompound compound = toolBag.getTagCompound();
			
			//NBTからクラス情報へ読み込みを行う
			itemId = compound.getString("inventorySlotItemName");
			itemMetadata = compound.getInteger("inventorySlotItemMetadata");
			//itemStackSize = compound.getLong("inventorySlotItemStackSize");
			
			if(itemId == null) {
				itemId = "";
			}
			Item item = Item.getByNameOrId(itemId);
			if (item != null) {
				ItemStack stoStack = new ItemStack(item, 1, itemMetadata);
				//一致するか確認
				if (stoStack.isItemEqual(stack)) {
					//一致
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * NBTの情報を確認して設定されているアイテムが何かを確認する
	 * 一致するものが入ってればtrue
	 * @param item
	 * @return
	 */
	public boolean addToolBagInventoryItem(ItemStack toolBag, ItemStack stack) {
		
		//ツールバッグならアイテムが一致するかをチェックする
		String itemId = null;
		int itemMetadata = 0;
		long itemStackSize = 0;
		
		if (toolBag.hasTagCompound()) {
			NBTTagCompound compound = toolBag.getTagCompound();
			
			//NBTからクラス情報へ読み込みを行う
			itemId = compound.getString("inventorySlotItemName");
			itemMetadata = compound.getInteger("inventorySlotItemMetadata");
			itemStackSize = compound.getLong("inventorySlotItemStackSize");
			
			if(itemId == null) {
				itemId = "";
			}
			Item item = Item.getByNameOrId(itemId);
			if (item != null) {
				ItemStack stoStack = new ItemStack(item, 1, itemMetadata);
				//一致するか確認
				if (stoStack.isItemEqual(stack)) {
					//一致
					toolBag.getTagCompound().setLong("inventorySlotItemStackSize", itemStackSize + stack.stackSize);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * NBTの情報を確認して設定されているアイテムが何かを確認する
	 * 指定のアイテムを設定分減らす上限以上は減らせない
	 * こっちでは一致チェックをやらない
	 * なければnullをかえす
	 * @param item
	 * @return
	 */
	public ItemStack removeToolBagInventoryItem(ItemStack toolBag, int amount) {
		
		//ツールバッグならアイテムが一致するかをチェックする
		String itemId = null;
		int itemMetadata = 0;
		long itemStackSize = 0;
		
		if (toolBag.hasTagCompound()) {
			NBTTagCompound compound = toolBag.getTagCompound();
			
			//NBTからクラス情報へ読み込みを行う
			itemId = compound.getString("inventorySlotItemName");
			itemMetadata = compound.getInteger("inventorySlotItemMetadata");
			itemStackSize = compound.getLong("inventorySlotItemStackSize");
			
			if(itemId == null) {
				itemId = "";
			}
			Item item = Item.getByNameOrId(itemId);
			if (item != null) {
				//ItemStack stoStack = new ItemStack(item, 1, itemMetadata);
				//一致するか確認
				//if (stoStack.isItemEqual(stack)) {
					
					//アイテムの計算
					if (itemStackSize <= amount) {
						amount = (int) itemStackSize;
					}

					//一致
					toolBag.getTagCompound().setLong("inventorySlotItemStackSize", itemStackSize - amount);
					
					return new ItemStack(item, amount, itemMetadata);
				//}
			}
		}
		return null;
	}
	
	
	/**
	 * 右クリック時の処理を行う
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer playerIn) {
		
		if(!world.isRemote){
			
			//右クリック時の処理
			if (playerIn.isSneaking()) {
				//親の処理を行う
				super.onItemRightClick(item, world, playerIn);
			} else {
				//GUIを開く
				playerIn.openGui(TataraCraft.INSTANCE, TataraGui.GUI_ID.TOOL_BAG_ITEM.getId(), world, 0, 0, 0);
			}
			
		}
		return item;
	}
	
	/**
	 * ブロックに対して右クリックイベント
	 * Called when a Block is right-clicked with this Item 
	 */
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		boolean result = false;

		if(!worldIn.isRemote) {
			if (playerIn.isSneaking()) {
				//親の処理を行う
				result = super.onItemUse(stack, playerIn, worldIn, pos, side, hitZ, hitZ, hitZ);
			}
		}
		return result;
		
    }
	
	/**
	 * インフォに情報をだす
	 */
	/**
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if (!stack.hasTagCompound()) {
        	return;
        }
		NBTTagCompound compound = stack.getTagCompound();
		
		//NBTからクラス情報へ読み込みを行う
		String itemId = compound.getString("inventorySlotItemName");
		int itemMetadata = compound.getInteger("inventorySlotItemMetadata");
		Long itemStackSize = compound.getLong("inventorySlotItemStackSize");
		
		if (itemId == null || "".equals(itemId)) {
			return;
		}
		ItemStack item = new ItemStack(Item.getByNameOrId(itemId), 1, itemMetadata);
		tooltip.add(item.getDisplayName() + ":" + itemStackSize.toString());
		
    }
	
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onModelBake(ModelBakeEvent event) {
        IBakedModel bakedModel = new ItemToolBagBakedModel();
		ModelResourceLocation model = new ModelResourceLocation("tataracraft:ToolBag_default", "inventory");
		
		/* ブロック描画用モデルのセット。ISmartBlockModel継承のものが良い。 */
		//event.modelRegistry.putObject(normalMRL, bakedModel);
		
		/* インベントリ描画用のモデルのセット。ISmartItemModel継承のものが良い */
		event.modelRegistry.putObject(model, bakedModel);
	}
	
	
	/**
	 * アイテムを拾ったときのイベント
	 * @param event
	 */
	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		
		EntityPlayer player = event.entityPlayer;
		ItemStack stack = event.item.getEntityItem();
		
		//プレイヤーのインベントリの中を走査する
		ItemStack[] inventory = player.inventory.mainInventory;
		
		//ループ
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null && inventory[i].getItem() instanceof ItemToolBag) {
				//ツールバッグかを確認
				//ツールバッグならアイテムが一致するかをチェックする
				String itemId = null;
				int itemMetadata = 0;
				long itemStackSize = 0;
				if (inventory[i].hasTagCompound()) {
					NBTTagCompound compound = inventory[i].getTagCompound();
					
					//NBTからクラス情報へ読み込みを行う
					itemId = compound.getString("inventorySlotItemName");
					itemMetadata = compound.getInteger("inventorySlotItemMetadata");
					itemStackSize = compound.getLong("inventorySlotItemStackSize");
					
					if(itemId == null) {
						itemId = "";
					}
					Item item = Item.getByNameOrId(itemId);
					if (item != null) {
						ItemStack stoStack = new ItemStack(item, 1, itemMetadata);
						//一致するか確認
						if (stoStack.isItemEqual(stack)) {
							//一致したらスタック数を増やす
							inventory[i].getTagCompound().setLong("inventorySlotItemStackSize", itemStackSize + stack.stackSize);
							
							//イベントのキャンセル
							int svStackSize = stack.stackSize;
							stack.stackSize = 0;
							event.setCanceled(true);
							//ベースの処理？
							player.onItemPickup(event.item, svStackSize);
							//パクリ元
							//player.worldObj.playSoundAtEntity(item, "random.pop", 0.2F, (float) (((Math.random() - Math.random()) * 0.7 + 1) * 2));
							//player.onItemPickup(item, size);
							break;
						}
					}
					
				}
				
			}
		}
		
	}
	
	
	/**
	 * ModelBakingイベント？
	 * @param event
	 */
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
    public void onBakingModelEvent(ModelBakeEvent event) {
//        IBakedModel bakedModel = new ItemToolBagBakedModel();
//		ModelResourceLocation model = new ModelResourceLocation("tataracraft:ToolBag_default", "inventory");
		
		/* ブロック描画用モデルのセット。ISmartBlockModel継承のものが良い。 */
		//event.modelRegistry.putObject(normalMRL, bakedModel);
		
		/* インベントリ描画用のモデルのセット。ISmartItemModel継承のものが良い */
//		event.modelRegistry.putObject(model, bakedModel);
		
    }
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
    public void textureStitch(TextureStitchEvent.Pre event) {
        /* JSONで指定していないテクスチャはこのタイミングで登録する。 */
        //event.map.registerSprite(new ResourceLocation("tataracraft:items/ToolBag"));
    }
	
	/**
	 * モデルの管理用のアイテムか？
	 * @author computer
	 *
	 */
	@SideOnly(Side.CLIENT)
	private static class ItemToolBagBakedModel implements ISmartItemModel {
		
		/**
		 * 動的にアイテムを入れ替えるならこれ？か
		 */
		@Override
        public IBakedModel handleItemState(ItemStack stack) {
			
			//モデル操作用のを取得
			ItemModelMesherForge itemModelMesher = (ItemModelMesherForge) Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
			
			String itemId = null;
			int itemMetadata = 0;
			if (stack.hasTagCompound()) {
				NBTTagCompound compound = stack.getTagCompound();
				
				//NBTからクラス情報へ読み込みを行う
				itemId = compound.getString("inventorySlotItemName");
				itemMetadata = compound.getInteger("inventorySlotItemMetadata");
			}
			
			if(itemId == null) {
				itemId = "";
			}
			Item item = null;
			item = Item.getByNameOrId(itemId);
			if (item == null) {
				ModelResourceLocation model = new ModelResourceLocation("tataracraft:ToolBag", "inventory");
				return itemModelMesher.getModelManager().getModel(model);
			}
			ItemStack itemStack = new ItemStack(item, 1, itemMetadata);
			
			IBakedModel ibakedmodel = itemModelMesher.getItemModel(itemStack);
			
			return ibakedmodel;
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
