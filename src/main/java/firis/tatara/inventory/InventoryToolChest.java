package firis.tatara.inventory;

import firis.tatara.common.ITataraItemInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;

public class InventoryToolChest implements ITataraItemInventory {

	//
	private InventoryPlayer inventoryPlayer;
	
	//
	private ItemStack currentItem;
	
	/**
	 * IInventoryの保存用領域
	 */
	public ItemStack[] inventorySlotItems;
	
	
	/**
	 * コンストラクタ
	 */
	public InventoryToolChest(InventoryPlayer inventory) {
		
		inventoryPlayer = inventory;
		currentItem = inventoryPlayer.getCurrentItem();
		
		//保存領域の初期化
		this.inventorySlotItems = new ItemStack[getSizeInventory()];
		
	}
	
	
	@Override
	public void markDirty() {
		
		//同期処理
		
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
		return 54;
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
	}
	
	
	/**
	 * ItemStackのNBTからデータを読み込む
	 */
	public void readFromNBT(EntityPlayer playerIn) {
		
		//NBTTagを確認
		//NBTが存在しない場合は初期化
		if (!currentItem.hasTagCompound()) {
			currentItem.setTagCompound(new NBTTagCompound());
			currentItem.getTagCompound().setTag("inventorySlotItems", new NBTTagList());
		}
		
		NBTTagCompound compound = currentItem.getTagCompound();
		
		//NBTからクラス情報へ読み込みを行う
		this.inventorySlotItems = new ItemStack[getSizeInventory()];
		
		//inventorySlotItemsの読み込み
		NBTTagList nbtTagList = compound.getTagList("inventorySlotItems", Constants.NBT.TAG_COMPOUND);
		
		for (int i = 0; i < nbtTagList.tagCount(); i++) {
			NBTTagCompound cmd = nbtTagList.getCompoundTagAt(i);
			int j = cmd.getByte("Slot") & 0xff;
			if (j >= 0 && j < this.inventorySlotItems.length) {
				this.inventorySlotItems[j] = ItemStack.loadItemStackFromNBT(cmd);
			}
		}
	}
	
	/**
	 * ItemStackのNBTへデータを書き込む
	 */
	public void writeToNBT(EntityPlayer playerIn) {
		
		NBTTagCompound compound = new NBTTagCompound();
		
		//クラス情報からNBT情報を作成する
		NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < this.inventorySlotItems.length; i++) {
			
			if (this.inventorySlotItems[i] != null) {
				NBTTagCompound cmd = new NBTTagCompound();
				cmd.setByte("Slot", (byte) i);
				this.inventorySlotItems[i].writeToNBT(cmd);
				nbtTagList.appendTag(cmd);
			}
			
		}
		compound.setTag("inventorySlotItems", nbtTagList);
		
		//**********
		//ItemStack result = new ItemStack(currentItem.getItem(), 1);
		//result.setTagCompound(compound);
		
		playerIn.inventory.getCurrentItem().setTagCompound(compound);
		//再セット
		//inventoryPlayer.mainInventory[inventoryPlayer.currentItem] = result;
		
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
		
		//内部インベントリ初期化
		this.clear();
		
		//ItemStackのinventorySlotItemsの読み込み
		NBTTagList nbtTagList = nbt.getTagList(this.getItemInventoryNBTName(), Constants.NBT.TAG_COMPOUND);
		
		for (int i = 0; i < nbtTagList.tagCount(); i++) {
			NBTTagCompound cmd = nbtTagList.getCompoundTagAt(i);
			int j = cmd.getByte("Slot") & 0xff;
			if (j >= 0 && j < this.inventorySlotItems.length) {
				this.inventorySlotItems[j] = ItemStack.loadItemStackFromNBT(cmd);
			}
		}
	}
	
	
	/**
	 * 内部インベントリへセットをNBTへ
	 */
	@Override
	public void readItemInventoryToNBT(NBTTagCompound nbt) {
		//クラス情報からNBT情報を作成する
		NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < this.inventorySlotItems.length; i++) {
			
			if (this.inventorySlotItems[i] != null) {
				NBTTagCompound cmd = new NBTTagCompound();
				cmd.setByte("Slot", (byte) i);
				this.inventorySlotItems[i].writeToNBT(cmd);
				nbtTagList.appendTag(cmd);
			}
			
		}
		nbt.setTag(this.getItemInventoryNBTName(), nbtTagList);

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
