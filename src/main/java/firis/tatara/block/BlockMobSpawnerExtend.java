package firis.tatara.block;

import firis.tatara.TataraCraft;
import firis.tatara.entity.TileEntityMobSpawnerExtend;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Mobスポナー拡張用
 */
public class BlockMobSpawnerExtend extends BlockContainer {

	/**
	 * コンストラクタ
	 */
	public BlockMobSpawnerExtend()
    {
        super(Material.redstoneLight);
        
        //初期化
        this.init();
        
    }
	
	/**
	 * ブロックの初期化
	 */
	protected void init() {

		//クリエイティブタブ
		this.setCreativeTab(TataraCraft.tabTatara);
		
		//システム名の登録はアイテム登録時に行うのでここでは記述しない
		
        /** ブロックの設定を行う */
		this.setHardness(0.1F); /*硬さ*/
		this.setResistance(1.0F); /*爆破耐性*/
		this.setStepSound(Block.soundTypeGlass); /*ブロックの上を歩いた時の音*/
		/*setBlockUnbreakable();*//*ブロックを破壊不可に設定*/
		/*setTickRandomly(true);*//*ブロックのtick処理をランダムに。デフォルトfalse*/
		/*disableStats();*//*ブロックの統計情報を保存しない*/
		//setLightOpacity(1);/*ブロックの透過係数。デフォルト０（不透過）*/
		//this.setLightLevel(1.0F); /*明るさ 1.0F = 15*/
		//setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);/*当たり判定*/
		
		//初期BlockStateの設定
		//this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
		
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
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TileEntityMobSpawnerExtend();
	}

}
