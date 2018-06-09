package firis.tatara.item;

import firis.tatara.TataraCraft;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.Potion;


/**
 * 焼いた腐り肉用クラス
 */
public class ItemBakedRottenFlesh extends ItemFood {

	//満腹度回復量
	private static int amount = 4;

	//隠し満腹度回復量（腹持ち）
	private static float saturation = 0.8F;		
	
	/**
	 * コンストラクタ
	 */
	public ItemBakedRottenFlesh() {
		
		super(amount, saturation, false);
		
		//初期化
		this.init();
	}

	/**
	 * たたらクラフト食べ物アイテムの初期化
	 */
	protected void init() {

		//クリエイティブタブ
		this.setCreativeTab(TataraCraft.tabTatara);
		
		//システム名の登録はアイテム登録時に行うのでここでは記述しない
		
		//その他の設定項目
		/* this.setMaxStackSize(64);/*スタックできる量。デフォルト64*/  
		//this.setAlwaysEdible();	//お腹すいてなくても食べれる。

		//空腹の状態異常
		//ポーション効果 ID, 時間(秒), レベル(書いた値+1になる), 確率(0.0～1.0F　1.0Fの時100%)
		this.setPotionEffect(Potion.hunger.id, 30, 0, 0.1F);			
	}
}
