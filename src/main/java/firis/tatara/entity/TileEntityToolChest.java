package firis.tatara.entity;

import firis.tatara.common.ITataraItemInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;

public class TileEntityToolChest extends TileEntity implements ITataraItemInventory {
	
	
	/**
	 * IInventoryの保存用領域
	 */
	public ItemStack[] inventorySlotItems;
	
	
	/**
	 * コンストラクタ
	 */
	public TileEntityToolChest() {

		super();
		
		//保存領域の初期化
		this.clear();
		
	}
	
	@Override
	public void markDirty() {
		
		super.markDirty();
		
		
		//同期処理
		if(worldObj != null) {
			worldObj.markBlockForUpdate(this.pos);
		}
	}
	
	
	/**
	 * **************************************************
	 * NBT関連
	 * 基本的にreadFromNBT/writeToNBTでデータのやり取りを行う
	 * ただし、同期させるには手動で同期を行うかICrafting等を利用する必要あり
	 * 手動同期の場合はWorldのworldObj.markBlockForUpdate(this.pos);
	 * で行うことができる
	 * パケット通信についてはバニラデフォルトの機能を利用
	 * カスタムパケットはまた今度
	 * **************************************************
	 */
	
	/**
	 * NBTを読み込みクラスへ反映する
	 */
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		
		super.readFromNBT(compound);
		
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
	 * クラスの情報をNBTへ反映する
	 */
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		
		super.writeToNBT(compound);
		
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
		
	}
	
	
	/**
	 * サーバサイドからクライアントサイドへ同期を行う際に呼び出される
	 * markBlockForUpdateで必要なタイミングで呼び出す
	 */
	@Override
	public Packet<?> getDescriptionPacket() {
		
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		
		return new S35PacketUpdateTileEntity(pos, 0, compound);
	}
		
	
	/**
	 * クライアントサイドでgetDescriptionPacketの処理のあとに呼び出されるとおもわれる
	 * NBTからクラス情報への反映等を行う
	 */
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
	
		super.onDataPacket(net, pkt);
		
		NBTTagCompound compound = pkt.getNbtCompound();
		this.readFromNBT(compound);
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
	}
	
	/**
	 * 開いたときに呼ばれる
	 */
	@Override
	public void openInventory(EntityPlayer player) {
	}
	
	/**
	 * 閉じたときに呼ばれる
	 */
	@Override
	public void closeInventory(EntityPlayer player) {
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
		this.inventorySlotItems = new ItemStack[getSizeInventory()];
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
	 * Blockは無条件で許可する
	 */
	@Override
	public boolean isSlotCanTakeStack() {
		return false;
	}

}
