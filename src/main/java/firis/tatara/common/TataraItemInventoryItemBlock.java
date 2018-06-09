package firis.tatara.common;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * ItemBlockの拡張
 * inventoryを持つブロックでアイテム状態でもNBTを保持する場合に利用する
 * 
 * placeBlockAt
 * ItemStackのNBTを読み込みTileEntityへ書き込む
 * 書き込む際にはITataraInventoryのインターフェースを経由する
 * 
 */
public abstract class TataraItemInventoryItemBlock extends ItemBlock {

	/**
	 * コンストラクタ
	 */
	public TataraItemInventoryItemBlock(Block block) {
		super(block);
	}
	
	
    /**
     * 実際にブロックが設置された後に呼び出される処理
     * Called to actually place the block, after the location is determined
     * and all permission checks have been made.
     *
     * @param stack The item stack that was used to place the block. This can be changed inside the method.
     * @param player The player who is placing the block. Can be null if the block is not being placed by a player.
     * @param side The side the player (or machine) right-clicked on.
     */
	@Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
		boolean result = false;
		
		//親クラスを呼び出す
		result = super.placeBlockAt(stack, player, world, pos, side, hitZ, hitZ, hitZ, newState);
        
		//NBTが存在する場合
		//ItemStackからTileEntityへの書き込みを行う
		if (result && stack.hasTagCompound()) {
			
			//TileEntityを取得
			TileEntity blockTileEntity = world.getTileEntity(pos);
			if (blockTileEntity == null
					|| !(blockTileEntity instanceof ITataraItemInventory)) {
				return result;
			}
			ITataraItemInventory itiiBlockTileEntity = (ITataraItemInventory)blockTileEntity;
			
			//ItemStackのNBTをITataraItemInventoryへ書き込み
			itiiBlockTileEntity.writeItemInventoryFromNBT(stack.getTagCompound());
			
		}
		
        return result;
    }
}
