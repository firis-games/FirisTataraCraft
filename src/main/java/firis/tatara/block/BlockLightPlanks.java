package firis.tatara.block;

import firis.tatara.TataraCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * 光源付き木材ブロック
 */
public class BlockLightPlanks extends Block {

	/**
	 * コンストラクタ
	 */
	public BlockLightPlanks()
    {
        super(Material.wood);
        
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
		this.setHardness(1.5F); /*硬さ*/
		this.setResistance(1.0F); /*爆破耐性*/
		this.setStepSound(Block.soundTypeWood); /*ブロックの上を歩いた時の音*/
		/*setBlockUnbreakable();*//*ブロックを破壊不可に設定*/
		/*setTickRandomly(true);*//*ブロックのtick処理をランダムに。デフォルトfalse*/
		/*disableStats();*//*ブロックの統計情報を保存しない*/
		//setLightOpacity(1);/*ブロックの透過係数。デフォルト０（不透過）*/
		this.setLightLevel(1.0F); /*明るさ 1.0F = 15*/
		//setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);/*当たり判定*/
		
		//初期BlockStateの設定
		//this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
		
	}

}
