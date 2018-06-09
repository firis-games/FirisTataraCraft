package firis.tatara.common;

/**
 * メタデータを持つアイテム用インターフェース
 */
public interface ITataraMetaItem {

	
	/**
	 * Metadataの最大値を取得する
	 * getMaxDamageから取得しようとするとアイテム欄の耐久ゲージがでてきてしまうため
	 * 別途定義する
	 */
	public int getMaxMetadata();
	
	/**
	 * 描画に利用するjsonのメタデータごとの名前を取得する
	 * 設定がない場合はnullを返す
	 */
	public String getItemVariantsName(int metadata);
		
}
