package firis.tatara.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * アイテムから開くGUIのスロットの制御を行う
 */
public class SlotPlayerHotbarInventory extends Slot {

	private boolean isCanTakeStack = false;
	
	/**
	 * コンストラクタ
	 * @param inventoryIn
	 * @param index
	 * @param xPosition
	 * @param yPosition
	 */
	public SlotPlayerHotbarInventory(IInventory inventoryIn, int index, int xPosition, int yPosition, boolean isCanTakeStack) {

		super(inventoryIn, index, xPosition, yPosition);

		this.isCanTakeStack = isCanTakeStack;
		
	}
	
	/**
	 * このスロットのアイテムが動かせるかどうかの判断を行う
     * Return whether this slot's stack can be taken from this slot.
     */
    public boolean canTakeStack(EntityPlayer playerIn)
    {
    	if (isCanTakeStack) {
    		//スロットの判断を行う
    		if (playerIn.inventory.getCurrentItem() == this.getStack()) {
    			return false;
    		}
    	}
    	return true;
    }
    

}
