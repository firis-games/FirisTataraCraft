package firis.tatara.block;

import firis.tatara.TataraCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockTataraBasic extends Block {

	private BlockType blockType;
	
	/**
	 * 汎用ブロックの定義
	 * 材質、硬さ、爆破耐性、音の設定
	 */
	public static enum BlockType {
		
		COBBLE_STONE_2(Material.rock, 4.0F, 10.0F, Block.soundTypeStone),
		COBBLE_STONE_3(Material.rock, 8.0F, 12.0F, Block.soundTypeStone),
		//銀鉱石(設定は金鉱石と一緒)
		SILVER_ORE(Material.iron, 3.0F, 5.0F, Block.soundTypePiston);
		
		private Material material;
		private float hardness;
		private float resistance;
		private SoundType soundtype;
		
		private BlockType(final Material material, final float hardness, final float resistance, final SoundType soundtype) {
			this.material = material;
			this.hardness = hardness;
			this.resistance = resistance;
			this.soundtype = soundtype;
		}
		public Material getMaterial() {
			return this.material;
		}
		public float getHardness() {
			return this.hardness;
		}
		public float getResistance() {
			return this.resistance;
		}
		public SoundType getSoundtype() {
			return this.soundtype;
		}
	}
	
	
	/**
	 * コンストラクタ
	 */
	public BlockTataraBasic(BlockTataraBasic.BlockType blockType)
    {
		
        super(blockType.getMaterial());
        
		this.blockType = blockType;
		
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
		this.setHardness(this.blockType.getHardness()); /*硬さ*/
		this.setResistance(this.blockType.getResistance()); /*爆破耐性*/
		this.setStepSound(this.blockType.getSoundtype()); /*ブロックの上を歩いた時の音*/
		/*setBlockUnbreakable();*//*ブロックを破壊不可に設定*/
		/*setTickRandomly(true);*//*ブロックのtick処理をランダムに。デフォルトfalse*/
		/*disableStats();*//*ブロックの統計情報を保存しない*/
		//setLightOpacity(1);/*ブロックの透過係数。デフォルト０（不透過）*/
		//this.setLightLevel(1.0F); /*明るさ 1.0F = 15*/
		//setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);/*当たり判定*/
		
		//初期BlockStateの設定
		//this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
		
		/*
		if (this.blockType.equals(BlockType.SILVER_ORE)) {
			//採掘レベルを設定？
			this.setHarvestLevel("pickaxe", 2);
		}
		*/
		
	}

}