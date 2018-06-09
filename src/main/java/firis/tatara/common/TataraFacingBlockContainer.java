package firis.tatara.common;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * かまどやディスペンサーの方角関連のメソッドを取り込んだBlockContainer
 * 
 */
public abstract class TataraFacingBlockContainer extends BlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	/**
	 * FACINGのモード切替
	 * 0:north,west,east,south
	 * 1:up,down,north,west,east,south
	 */
	public int FacingMode = 0;
	
	/**
	 * コンストラクタ
	 * @param materialIn
	 */
	protected TataraFacingBlockContainer(Material materialIn) {
		
		super(materialIn);
		
		//初期BlockStateの設定
		//方角の情報を設定する
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	
	/**
	 * プレイヤーのブロック設置時に呼ばれる
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
    
    	EnumFacing FacingState = null;
    	if (FacingMode == 0) {
    		//方角のみを判断
    		FacingState = placer.getHorizontalFacing().getOpposite();
    	}
    	if (FacingMode == 1) {
    		//UPとDOWNを含めた方向を判断
    		FacingState = BlockPistonBase.getFacingFromEntity(worldIn, pos, placer);
    	}
 
    	//改変部分
        return this.getDefaultState().withProperty(FACING, FacingState);
 
    }
    
    
    /**
     * ItemStackのmetadataからIBlockStateを生成。設置時に呼ばれる。
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    
    /**
     * IBlockStateからItemStackのmetadataを生成。ドロップ時とテクスチャ・モデル参照時に呼ばれる。
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
	
    
    /**
     * 初期BlockStateの生成。
     * BlockStateの一覧を定義するっぽい
     * BlockStateを利用する場合は必須？定義しないと落ちる
     */
    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING});
    }
    
    
	/**
	 * ブロックコンテナーを使う場合はrendertypeを-1から適時必要なものに変えないと見えない状態になる
     * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
     */
	@Override
    public int getRenderType()
    {
        return 3;
    }
	
	//**************************************************
	
	/**
	 * FACINGのモード切替
	 * 0:north,west,east,south
	 * 1:up,down,north,west,east,south
	 */
	public void setFacingMode(int mode)
	{
		this.FacingMode = mode;
	}
	
}
