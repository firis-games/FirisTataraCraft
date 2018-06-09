package firis.tatara.item;

import java.util.List;

import firis.tatara.TataraCraft;
import firis.tatara.common.ITataraMetaItem;
import firis.tatara.common.util.TataraExpUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 経験値本のクラス
 */
public class ItemExpBook extends Item implements ITataraMetaItem {
	
	/**
	 * メタデータの定義
	 */
	private static enum Metadata {
		EXPBOOK_EMPTY(0, 0),
		EXPBOOK_30(1, 30),
		EXPBOOK_50(2, 50);
		
		private int metadata;
		private int level;
		
		private Metadata(int metadata, int level) {
			this.metadata = metadata;
			this.level = level;
		}
		public int getMetadata() {
			return this.metadata;
		}
		public int getLevel() {
			return this.level;
		}
	}
	
	
	/**
	 * コンストラクタ
	 */
	public ItemExpBook() {
	
		
		//初期化
		this.init();
	}

	/**
	 * アイテムの初期化
	 */
	protected void init() {

		//クリエイティブタブ
		this.setCreativeTab(TataraCraft.tabTatara);
		
		//システム名の登録はアイテム登録時に行うのでここでは記述しない
		
		//その他の設定項目
		
		//メタデータによるアイテム切替を実装する
		//アイテムのメタデータは耐久のパラメータを使う
		
		//メタデータによる複数のアイテムを分けて管理する
		this.setHasSubtypes(true);
		
		//メタデータの最大値を設定
		//ゲージを出さない場合は0を設定する
		this.setMaxDamage(0);
		
		//スタックサイズ
		this.setMaxStackSize(64);
		
	}
	
	//****************************************************************************************************
	
	/**
	 * プレイヤーの経験値を本へ書き込む
	 * Metadataが0の場合のみこちらの処理を行う
	 */
	protected ItemStack writeExpBook(ItemStack item, World world, EntityPlayer playerIn) {
		
		int level = playerIn.experienceLevel;
		ItemExpBook.Metadata meta = Metadata.EXPBOOK_EMPTY;
		
		//Lvの確認を行う
		for (ItemExpBook.Metadata value : ItemExpBook.Metadata.values()) {
			if (value.getLevel() <= level) {
				//レベルを超えている場合はmetaへセットする
				if (meta.getLevel() < value.getLevel()) {
					meta = value;
				}
			}
		}
		
		//メタデータが初期値の場合は何もしない
		if (meta.getMetadata() != 0) {
			
			//レベル計算を行う
			boolean result = TataraExpUtil.subtractionPlayerLevel(playerIn, meta.getLevel());
			
			if (result) {
				
				//経験値本を生成
				ItemStack expBook = new ItemStack(item.getItem(), 1, meta.getMetadata());
				
				//元本のスタックを計算
				if (item.stackSize == 1) {
					//スタックサイズが1の場合は入れ替える
					item = expBook;
				} else {
					//それ以外の場合はインベントリの操作
					if(playerIn.inventory.addItemStackToInventory(expBook)) {
						//インベントリへ収納
						item.stackSize -= 1;
					} else {
						//ワールドへドロップ
						item.stackSize -= 1;
						playerIn.dropItem(expBook, true, false);
					}
				}
			}
		}
		
		return item;
	}
	
	/**
	 * 経験値本をプレイヤーへ書き込む
	 * Metadataが0以外の場合のみこちらの処理を行う
	 */
	protected ItemStack readExpBook(ItemStack item, World world, EntityPlayer playerIn) {
		
		ItemExpBook.Metadata meta = Metadata.EXPBOOK_EMPTY;
		
		//Metaデータの取得を行う
		for (ItemExpBook.Metadata value : ItemExpBook.Metadata.values()) {
			if (getMetadata(item) == value.getMetadata()) {
				//metaへセットする
				meta = value;
				break;
			}
		}
		
		//メタデータが初期値の場合は何もしない
		if (meta.getMetadata() != 0) {
			
			//レベル計算を行う
			TataraExpUtil.additionPlayerLevel(playerIn, meta.getLevel());
			
			//アイテムの計算を行う
			item.stackSize -= 1;
			
		}
		
		return item;
	}
	
	//****************************************************************************************************
	
	/**
	 * メタデータの値を返却する
     * Converts the given ItemStack damage value into a metadata value to be placed in the world when this Item is
     * placed as a Block (mostly used with ItemBlocks).
     */
	@Override
    public int getMetadata(int damage)
    {
        return damage;
    }
	
	/**
	 * クリエイティブタブにアイテムを登録する
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
		//定義分のアイテムを生成
		for (ItemExpBook.Metadata meta : ItemExpBook.Metadata.values()) {
			subItems.add(new ItemStack(itemIn, 1, meta.getMetadata()));
		}
        
    }
	
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
	@Override
    public String getUnlocalizedName(ItemStack stack)
    {
		String name = super.getUnlocalizedName(stack);
        return name + stack.getItemDamage();
    }	
	
	/**
	 * 右クリック時の処理を行う
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer playerIn) {
		
		if(!world.isRemote){
			
			//メタデータによって処理を分岐する
			if (this.getMetadata(item) == Metadata.EXPBOOK_EMPTY.getMetadata()) {
				//経験値本への書き出し
				return writeExpBook(item, world, playerIn);
			} else {
				//経験値本の読み込み
				return readExpBook(item, world, playerIn);
			}
		}
		
		return item;
	}
	

	//****************************************************************************************************

	/**
	 * Metadataの最大値を返却
	 */
	@Override
	public int getMaxMetadata() {
		return ItemExpBook.Metadata.values().length - 1;
	}

	/**
	 * Rendererに使う名称を返却する
	 */
	@Override
	public String getItemVariantsName(int metadata) {
		
		//基本的にmetadataを登録名の後ろにつけるだけ(Item0,Item1等)
		String name = this.getRegistryName();
		name = name + metadata;

		return name;
	}
	
}
