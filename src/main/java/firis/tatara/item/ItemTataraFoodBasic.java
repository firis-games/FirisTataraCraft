package firis.tatara.item;

import firis.tatara.TataraCraft;
import net.minecraft.item.ItemFood;


/**
 * 効果なし食べ物用クラス
 */
public class ItemTataraFoodBasic extends ItemFood {

	/**
	 * コンストラクタ
	 * @param amount 満腹度回復量
	 * @param saturation 隠し満腹度回復量（腹持ち）
	 * 
	 */
	public ItemTataraFoodBasic(int amount, float saturation) {
		
		//引数は順に、満腹度回復量・隠し満腹度回復量（腹持ち）・オオカミが食べられるか 
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
		//this.setPotionEffect(Potion.poison.id, 5, 0, 0.6F);	//ポーション効果 ID, 時間(秒), レベル(書いた値+1になる), 確率(0.0～1.0F　1.0Fの時100%)		
	}
}
