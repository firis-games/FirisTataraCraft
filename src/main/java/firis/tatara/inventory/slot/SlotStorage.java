package firis.tatara.inventory.slot;

import firis.tatara.inventory.InventoryToolBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * アイテムから開くGUIのスロットの制御を行う
 */
public class SlotStorage extends Slot {
	
	/**
	 * コンストラクタ
	 * @param inventoryIn
	 * @param index
	 * @param xPosition
	 * @param yPosition
	 */
	public SlotStorage(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);		
	}
	
	/**
	 * このスロットのアイテムが動かせるかどうかの判断を行う
	 * 見本スロットの制御は手動で行うため基本無効化
     * Return whether this slot's stack can be taken from this slot.
     */
	@Override
    public boolean canTakeStack(EntityPlayer playerIn) {
    	return false;
    }

	
	
	/**
	 * 条件に応じて見本をセットする
	 */
	public ItemStack setSampleItem(ItemStack stack) {
		
		ItemStack invStack = this.inventory.getStackInSlot(this.getSlotIndex());
		
		//見本スロットがない場合
		if (invStack == null) {
			
			((InventoryToolBag)this.inventory).setSlotSampleItemStack(this.getSlotIndex(), stack);
			
			
		//見本スロットがセットされている場合
		} else {
			//保存しているアイテムがなければnull化
			((InventoryToolBag)this.inventory).setSlotSampleItemStackClear(this.getSlotIndex());
		}
		
		return null;
	}
	
	/**
	 * ストレージアイテムをセットする
	 * @return
	 */
	public ItemStack setStorageItem(ItemStack stack) {
		
		//セットアイテムのチェック
		if (((InventoryToolBag)this.inventory).setStorageSlotItemStack(this.getSlotIndex(), stack)) {
			return null;
		} else {
			return stack;
		}
	}
	
	public ItemStack getStorageItem(ItemStack stack) {
		
		//手持があったらなにもしない
		if (stack == null) {
			stack = ((InventoryToolBag)this.inventory).getStorageSlotItemStack(this.getSlotIndex(), this.getStack().stackSize);
		}
		return stack;

	}
	
}
