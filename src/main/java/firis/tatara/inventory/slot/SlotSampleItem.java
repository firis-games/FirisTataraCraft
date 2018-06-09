package firis.tatara.inventory.slot;

import firis.tatara.inventory.InventoryToolBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * アイテムから開くGUIのスロットの制御を行う
 */
public class SlotSampleItem extends Slot {
	
	/**
	 * コンストラクタ
	 * @param inventoryIn
	 * @param index
	 * @param xPosition
	 * @param yPosition
	 */
	public SlotSampleItem(IInventory inventoryIn, int index, int xPosition, int yPosition) {
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
			
			((InventoryToolBag)this.inventory).setSlotSampleItemStack(this.getSlotIndex(), invStack);
			/*
			//アイテムの種類を判断
			if (stack == null) {
				return null;
			}
			
			//見本を作製して設置
			ItemStack sampleStack = stack.copy();
			sampleStack.stackSize = 1;
			
			this.inventory.setInventorySlotContents(this.getSlotIndex(), sampleStack);
			*/
			
		//見本スロットがセットされている場合
		} else {
			
			//保存しているアイテムがなければnull化
			this.inventory.setInventorySlotContents(this.getSlotIndex(), null);
		}
		
		return null;
	}
	
}
