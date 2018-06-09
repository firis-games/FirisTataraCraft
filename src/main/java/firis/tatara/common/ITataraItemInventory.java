package firis.tatara.common;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * IInventoryの拡張
 * inventoryを持つブロックでアイテム状態でもNBTを保持する場合に利用する
 *
 */
public interface ITataraItemInventory extends IInventory {

	/**
	 * IInventryの内部インベントリのNBTのタグ名
	 * @return
	 */
	public String getItemInventoryNBTName();
	
	
	/**
	 * NBTを内部インベントリへ書き込む
	 * @param nbt
	 */
	public void writeItemInventoryFromNBT(NBTTagCompound nbt);
	
	/**
	 * 内部インベントリをNBTへ書き込む
	 * @param nbt
	 */
	public void readItemInventoryToNBT(NBTTagCompound nbt);
	
	/**
	 * IInventoryの内部インベントリへItemStackをセットする
	 * @param stack
	 * @param index
	 */
	public void setItemInventory(ItemStack stack, int index);
	
	
	/**
	 * IInventoryの内部インベントリのItemStackを取得する
	 * @param index
	 * @return
	 */
	public ItemStack getItemInventory(int index);
	
	/**
	 * スロットの移動可否判定を行うかどうか
	 * @param stack
	 * @param slotIndex
	 * @param playerIn
	 * @return
	 */
	public boolean isSlotCanTakeStack();
	
}
