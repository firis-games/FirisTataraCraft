package firis.tatara.inventory;

import firis.tatara.common.ITataraItemInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;

public class InventoryToolBag implements ITataraItemInventory {

	//
	private InventoryPlayer inventoryPlayer;
	
	//
	private ItemStack currentItem;
	
	/**
	 * IInventoryの保存用領域
	 */
	public ItemStack[] inventorySlotItems;
	
	/**
	 * Itemの種類を格納する領域
	 */
	public String itemId;
	public int itemMetadata;
	public Long itemStackSize;
	
	
	/**
	 * コンストラクタ
	 */
	public InventoryToolBag(InventoryPlayer inventory) {
		
		inventoryPlayer = inventory;
		currentItem = inventoryPlayer.getCurrentItem();
		
		//保存領域の初期化
		this.inventorySlotItems = new ItemStack[getSizeInventory()];
		
		itemId = null;
		itemMetadata = 0;
		itemStackSize = (long) 0;
		
	}
	
	
	@Override
	public void markDirty() {
		
		//同期処理
		
	}
	

	
	
	/**
	 * **************************************************
	 * 拡張メソッド
	 * **************************************************
	 */
	
	/**
	 * アイテム情報が一致するかを確認
	 * @param stack
	 * @return
	 */
	public boolean isStorageItemEquals(ItemStack stack) {
		boolean result = false;
		
		//アイテムIDとメタデータの比較をする
		if (this.itemId != null 
				&& stack != null
				&& this.itemId.equals(stack.getItem().getRegistryName()) 
				&& itemMetadata == stack.getMetadata()) {
			result = true;
		}
		
		
		return result;
	}
	
	/**
	 * 見本型でアイテムを設定する
	 */
	public boolean setSlotSampleItemStack(int index, ItemStack stack) {
		
		boolean result = false;
		
		//設定できるかの判断
		if ((itemId != null && !"".equals(itemId)) || itemStackSize > 0 || stack == null) {
			//何もしない
			return result;
		}
		//例外アイテム
		if (stack.getItem().isDamageable() || stack.hasTagCompound()) {
			return result;
		}
		
		
		//設定
		itemId = stack.getItem().getRegistryName();
		itemMetadata = stack.getMetadata();
		itemStackSize = (long) 0;
		
		//出力スロットの更新を行う
		this.reloadStorageSlot();
		
		return result;
		
	}
	
	/**
	 * 見本型でアイテムを設定する
	 */
	public boolean setSlotSampleItemStackClear(int index) {
		
		boolean result = false;
		
		//設定できるかの判断
		if (itemStackSize > 0) {
			return false;
		}
		
		//クリアする
		//設定
		this.clear();
		
		//出力スロットの更新を行う
		this.reloadStorageSlot();

		result= true;
		
		return result;
		
	}
	
	
	/**
	 * 格納できるか判定を行い、
	 */
	public boolean setStorageSlotItemStack(int index, ItemStack stack) {
		
		boolean result = false;
		
		if (!this.isStorageItemEquals(stack)) {
			//
			return result;
		}
		
		//StackSizeを加算する、実際のセットは行わない
		itemStackSize += stack.stackSize;
		
		//出力スロットの更新を行う
		this.reloadStorageSlot();
		
		result = true;
		
		return result;
	}
	
	/**
	 * 出力用のアイテムスロットを再生成
	 */
	public void reloadStorageSlot() {
		
		if (itemId == null) {
			itemId = "";
		}
		Item item = Item.getByNameOrId(itemId);
		
		if (item == null) {
			this.setInventorySlotContents(0, null);
			this.setInventorySlotContents(1, null);
			this.setInventorySlotContents(2, null);
			this.setInventorySlotContents(3, null);
			return;
		}
		
		int  itemSize = item.getItemStackLimit();
		
		ItemStack stack0 = new ItemStack(Item.getByNameOrId(itemId), 1, itemMetadata);
		ItemStack stack1 = new ItemStack(Item.getByNameOrId(itemId), (int) (this.itemStackSize % itemSize), itemMetadata);
		ItemStack stack2 = new ItemStack(Item.getByNameOrId(itemId), itemSize, itemMetadata);
		
		if (this.itemStackSize < itemSize) {
			stack2 = null;
		}
		if (stack1.stackSize == 0) {
			stack1 = null;
		}
		
		//インベントリにセット
		this.setInventorySlotContents(0, stack0);
		this.setInventorySlotContents(1, null);
		this.setInventorySlotContents(2, stack1);
		this.setInventorySlotContents(3, stack2);
		
	}
	
	
	/**
	 * 格納できるか判定を行い、
	 */
	public ItemStack getStorageSlotItemStack(int index, int stackSize) {
		
		ItemStack stack = new ItemStack(Item.getByNameOrId(itemId), 1, itemMetadata);
		
		//StackSizeを加算する、実際のセットは行わない
		if (this.itemStackSize < stackSize) {
			stack.stackSize = this.itemStackSize.intValue();
			this.itemStackSize = (long) 0;
		} else {
			stack.stackSize = stackSize;
			this.itemStackSize -= stackSize;
		}
		
		if(stack.stackSize == 0) {
			stack = null;
		}
		
		//出力スロットの更新を行う
		this.reloadStorageSlot();
		
		return stack;
	}
	
	
	
	
	
	
	
	
	/**
	 * **************************************************
	 * IInventory
	 * **************************************************
	 */
	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return null;
	}
	
	/**
	 * インベントリのサイズを返却する
	 */
	@Override
	public int getSizeInventory() {
		return 4;
	}

	/**
	 * スロット用の処理
	 * たぶんインデックスに応じてアイテム情報を渡す
	 */
	@Override
	public ItemStack getStackInSlot(int index) {
		
		//ItemStackを返却する
		return this.inventorySlotItems[index];
		
	}
	
	/**
	 * スロット用の処理
	 * スロットのインデックスとアイテムの数を渡す
	 * アイテムを取り出すときの制御
	 * 内部に保持してるスタックサイズを確認して問題なければ許可するって処理をやってると思われる
	 * 
	 * とりあえずiron chestのソースをコピペ
	 * 
	 */
	@Override
	public ItemStack decrStackSize(int index, int count) {
		
		if (this.inventorySlotItems[index] != null)
        {
            if (this.inventorySlotItems[index].stackSize <= count)
            {
                ItemStack itemstack = this.inventorySlotItems[index];
                this.inventorySlotItems[index] = null;
                markDirty();
                return itemstack;
            }
            ItemStack itemstack1 = this.inventorySlotItems[index].splitStack(count);
            if (this.inventorySlotItems[index].stackSize == 0)
            {
            	this.inventorySlotItems[index] = null;
            }
            markDirty();
            return itemstack1;
        } else {
            return null;
		}
		
	}

	/**
	 * スロット用の処理
	 * 指定のスロットのアイテムを取り出す？削除するのどっちかだと思う
	 * 
	 * とりあえずiron chestのソースをコピペ
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (this.inventorySlotItems[index] != null)
        {
            ItemStack var2 = this.inventorySlotItems[index];
            this.inventorySlotItems[index] = null;
            return var2;
        } else
        {
            return null;
        }
	}
	
	/**
	 * スロット用の処理
	 * 指定のアイテムをスロットにセットする
	 * 上限をちゃんと見てる感じがするから確認
	 * 
	 * iron chestのコピペ
	 * 
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
	
		this.inventorySlotItems[index] = stack;
		
        if (stack != null && stack.stackSize > getInventoryStackLimit())
        {
        	stack.stackSize = getInventoryStackLimit();
        }
        markDirty();
	}

	/**
	 * スロット用の処理
	 * インベントリのスタック上限数を返す
	 * とりあえず通常スタック数の64を設定する
	 */
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	/**
	 * 何も考えずに書く
	 * 
	 * プレイヤーがGUIを開けるかどうか。
	 * 引数は開こうとしたプレイヤー。開けるならtrue、だめならfalse
	 * 主に距離判定を入れるみたい。チェストとかって一定距離以上離れると開けなくなるじゃないですか。
	 * それを実現するためのメソッド。
	 * 
	 * とりあえず iron chestのソースをこぴぺ
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		/*
		//iron chestのソースのまんま
		if (worldObj == null)
		{
		    return true;
		}
		if (worldObj.getTileEntity(pos) != this)
		{
		    return false;
		}
		return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64D;
		*/
		return true;
	}
	
	/**
	 * 開いたときに呼ばれる
	 */
	@Override
	public void openInventory(EntityPlayer player) {
		
		//NBTからデータを読み込む
		readFromNBT(player);
	}
	
	/**
	 * 閉じたときに呼ばれる
	 */
	@Override
	public void closeInventory(EntityPlayer player) {

		//NBTへデータを書き込む
		writeToNBT(player);
		
	}

	
	/**
	 * スロットにアイテムを入れれるかどうかの許可を制御するとおもわれる
	 */
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		
		if (index == 0) {
			return false;
		}
		
		return true;
	}

	
	/**
	 * かまどでは処理時間や燃焼時間等の情報をContainer側に受け渡すのに使ってる
	 * 直接TileEntityを操作できるので無理やり使う必要はなさそう
	 * 
	 * getField
	 * setField
	 * getFieldCount
	 * 
	 */
	@Override
	public int getField(int id) {
		return 0;
	}
	@Override
	public void setField(int id, int value) {
	}
	@Override
	public int getFieldCount() {
		return 0;
	}

	/**
	 * 初期化等に利用する
	 */
	@Override
	public void clear() {
		itemId = null;
		itemMetadata = 0;
		itemStackSize = (long) 0;
	}
	
	
	/**
	 * ItemStackのNBTからデータを読み込む
	 */
	public void readFromNBT(EntityPlayer playerIn) {
		
		//NBTTagを確認
		//NBTが存在しない場合は初期化
		if (!currentItem.hasTagCompound()) {
			currentItem.setTagCompound(new NBTTagCompound());
			//currentItem.getTagCompound().setTag("inventorySlotItems", new NBTTagList());
			currentItem.getTagCompound().setString("inventorySlotItemName", "");
			currentItem.getTagCompound().setInteger("inventorySlotItemMetadata", 0);
			currentItem.getTagCompound().setLong("inventorySlotItemStackSize", 0);
		}
		
		NBTTagCompound compound = currentItem.getTagCompound();
		
		//NBTからクラス情報へ読み込みを行う
		this.itemId = compound.getString("inventorySlotItemName");
		
		this.itemMetadata = compound.getInteger("inventorySlotItemMetadata");
		
		this.itemStackSize = compound.getLong("inventorySlotItemStackSize");
		
		//スロットの再読み込み
		this.reloadStorageSlot();

	}
	
	/**
	 * ItemStackのNBTへデータを書き込む
	 */
	public void writeToNBT(EntityPlayer playerIn) {
		
		NBTTagCompound compound = new NBTTagCompound();
		
		//クラス情報からNBT情報を作成する
		compound.setString("inventorySlotItemName", this.itemId);

		compound.setInteger("inventorySlotItemMetadata", this.itemMetadata);

		compound.setLong("inventorySlotItemStackSize", this.itemStackSize);

		
		playerIn.inventory.getCurrentItem().setTagCompound(compound);

		
	}


	//ItemInventoryの拡張メソッド
	//**************************************************

	/**
	 * 内部インベントリのNBT保存先名
	 */
	@Override
	public String getItemInventoryNBTName() {
		return "inventorySlotItems";
	}

	/**
	 * NBTを内部インベントリへセット
	 */
	@Override
	public void writeItemInventoryFromNBT(NBTTagCompound nbt) {
		
	}
	
	
	/**
	 * 内部インベントリへセットをNBTへ
	 */
	@Override
	public void readItemInventoryToNBT(NBTTagCompound nbt) {
		
	}
	

	@Override
	public void setItemInventory(ItemStack stack, int index) {
		this.inventorySlotItems[index] = stack;
	}

	@Override
	public ItemStack getItemInventory(int index) {
		return this.inventorySlotItems[index];
	}

	/**
	 * プレイヤーのカレントアイテムの制御を行う
	 */
	@Override
	public boolean isSlotCanTakeStack() {
        return true;
	}
}
