package firis.tatara.block;

import firis.tatara.TataraCraft;
import net.minecraft.block.Block;
//import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 光源付きガラスブロック
 * 地面が透過する問題あり、バニラのやつはテクスチャの設定で細かく設定してるみたい
 * 追加するならそれを参考にする必要があるためブロック登録からはずす？
 * BlockGrass
 */
public class BlockLightGrass extends Block {

	/**
	 * コンストラクタ
	 */
	public BlockLightGrass()
    {
		super(Material.grass);

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
		this.setHardness(1.0F); /*硬さ*/
		this.setResistance(2.0F); /*爆破耐性*/
		this.setStepSound(Block.soundTypeGrass); /*ブロックの上を歩いた時の音*/
		/*setBlockUnbreakable();*//*ブロックを破壊不可に設定*/
		/*setTickRandomly(true);*//*ブロックのtick処理をランダムに。デフォルトfalse*/
		/*disableStats();*//*ブロックの統計情報を保存しない*/
		//this.setLightOpacity(1);/*ブロックの透過係数。デフォルト０（不透過）*/
		this.setLightLevel(1.0F); /*明るさ 1.0F = 15*/
		//setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);/*当たり判定*/
		
		//初期BlockStateの設定
		//this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
		
	}
	
	
	/**
	 * 透過ブロックの場合はオーバーライドが必要
	 */
	@Override
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
	
	
	/**
	 * 不透明なブロックかどうか
	 * これを設定しないと隣接ブロックの接続部が透明になる
	 * デフォルトtrue
	 */
	@Override
	public boolean isOpaqueCube()
    {
        return false;
    }
	

}
