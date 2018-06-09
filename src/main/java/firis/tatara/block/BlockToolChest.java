package firis.tatara.block;

import java.util.List;

import firis.tatara.TataraCraft;
import firis.tatara.common.ITataraItemInventory;
import firis.tatara.common.TataraFacingBlockContainer;
import firis.tatara.entity.TileEntityToolChest;
import firis.tatara.gui.TataraGui;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * ツールチェスト用のクラス
 */
public class BlockToolChest extends TataraFacingBlockContainer {

	private ITataraItemInventory iInventory = null;
	
	/**
	 * コンストラクタ
	 */
	public BlockToolChest() {
		
		//材質レベルはとりあえずピストンに合わせておく
		super(Material.piston);

		//初期化
        this.init();
	}
	
	/**s
	 * ブロックの初期化
	 */
	protected void init() {

		//クリエイティブタブ
		this.setCreativeTab(TataraCraft.tabTatara);
		
		//システム名の登録はアイテム登録時に行うのでここでは記述しない
		
        /** ブロックの設定を行う */
		this.setHardness(1.5F); /*硬さ*/
		this.setResistance(10.0F); /*爆破耐性*/
		this.setStepSound(Block.soundTypeStone); /*ブロックの上を歩いた時の音*/
		/*setBlockUnbreakable();*//*ブロックを破壊不可に設定*/
		/*setTickRandomly(true);*//*ブロックのtick処理をランダムに。デフォルトfalse*/
		/*disableStats();*//*ブロックの統計情報を保存しない*/
		//setLightOpacity(1);/*ブロックの透過係数。デフォルト０（不透過）*/
		//this.setLightLevel(1.0F); /*明るさ 1.0F = 15*/
		//setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);/*当たり判定*/
		
		//初期BlockStateの設定
		//this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
		
		//方角モード
		setFacingMode(0);
		
	}
	
	

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TileEntityToolChest();
	}
	
	
	/*
	 * 右クリック
	 */
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		if(!worldIn.isRemote) {
			
			//GUIを開く
			playerIn.openGui(TataraCraft.INSTANCE, TataraGui.GUI_ID.TOOL_CHEST.getId(), worldIn, pos.getX(), pos.getY(), pos.getZ());

		}
		
		
		
		return true;
	}
		
	/**
	 * ブロック破壊時の処理
	 * 通常ではTileEntityの削除を行っているがその前にTileEntityを保存する
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
		//TileEntityを保存 ITataraItemInventory
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity != null && tileEntity instanceof ITataraItemInventory) {
			this.iInventory = (ITataraItemInventory)tileEntity;
		}
			
		super.breakBlock(worldIn, pos, state);
    }	
	
	/**
     * This returns a complete list of items dropped from this block.
     *
     * @param world The current world
     * @param pos Block position in world
     * @param state Current state
     * @param fortune Breakers fortune level
     * @return A ArrayList containing all items this block drops
     */
	@Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
		
		List<ItemStack> result = super.getDrops(world, pos, state, fortune);
		
		//ItemStackのリストから自分自身のItemBlockを取得する
		Item itemBlock = Item.getItemFromBlock(state.getBlock());
		
		for (ItemStack stack : result) {
			
			//同じ場合インベントリ情報を書き込む
			if (stack.getItem() == itemBlock) {
				
				//TileEntityToolChest tileEntity = (TileEntityToolChest) world.getTileEntity(pos);
				ITataraItemInventory tileEntity = this.iInventory;

				if (tileEntity != null) {

					NBTTagCompound itemNbt = new NBTTagCompound();
					
					//NBTへインベントリ情報を書き込む
					tileEntity.readItemInventoryToNBT(itemNbt);

					stack.setTagCompound(itemNbt);
				}
			}
			
		}
		return result;
    }
	
}
