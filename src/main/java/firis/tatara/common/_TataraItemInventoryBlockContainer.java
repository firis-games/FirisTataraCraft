package firis.tatara.common;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * BlockContainerの拡張
 * inventoryを持つブロックでアイテム状態でもNBTを保持する場合に利用する
 * 
 * 
 * まだ共通かしないのでこいつは使わない
 * 
 */
public abstract class _TataraItemInventoryBlockContainer extends BlockContainer {

	/**
	 * コンストラクタ
	 */
	protected _TataraItemInventoryBlockContainer(Material materialIn) {
		super(materialIn);
	}
	

	
	/**
	 * チェストのばら撒きソースを活用
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
		//TileEntity tileentity = worldIn.getTileEntity(pos);

		/*
        if (tileentity instanceof IInventory)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }
        */
        super.breakBlock(worldIn, pos, state);
    }
	
    /**
     * Spawns this Block's drops into the World as EntityItems.
     */
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
	}
	
	
	
}
