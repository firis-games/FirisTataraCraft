package firis.tatara.item;

import firis.tatara.TataraCraft;
import firis.tatara.common.TataraItemInventoryItemBlock;
import firis.tatara.gui.TataraGui;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemBlockToolChest extends TataraItemInventoryItemBlock {
	
	public ItemBlockToolChest(Block block) {
		super(block);
				
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
		//this.setHasSubtypes(true);//*ダメージ値等で複数の種類のアイテムを分けているかどうか。デフォルトfalse*/
		//this.setMaxDamage(405);//*耐久値の設定。デフォルト0*/
		/*.setFull3D()*//*３D表示で描画させる。ツールや骨、棒等。*/
		//this.setContainerItem(this);//*クラフト時にアイテムを返却できるようにしている際の返却アイテムの指定。*/
		/*.setPotionEffect(PotionHelper.ghastTearEffect)*//*指定文字列に対応した素材として醸造台で使える。PotionHelper参照のこと。*/
		//this.setNoRepair();//*修理レシピを削除し、金床での修繕を出来なくする*/
        this.setMaxStackSize(1); /*スタックできる量。デフォルト64*/ 
		
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
				playerIn.openGui(TataraCraft.INSTANCE, TataraGui.GUI_ID.TOOL_CHEST_ITEM.getId(), world, 0, 0, 0);
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
}
