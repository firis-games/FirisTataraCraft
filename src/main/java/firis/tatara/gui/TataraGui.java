package firis.tatara.gui;

import firis.tatara.client.gui.GuiContainerToolBag;
import firis.tatara.client.gui.GuiContainerToolChest;
import firis.tatara.common.ITataraItemInventory;
import firis.tatara.container.ContainerToolBag;
import firis.tatara.container.ContainerToolChest;
import firis.tatara.inventory.InventoryToolBag;
import firis.tatara.inventory.InventoryToolChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * GUI関連の定義
 */
public class TataraGui {

	/**
	 * GUI ID　定義
	 */
	public static enum GUI_ID {
		TOOL_CHEST(1),
		TOOL_CHEST_ITEM(2),
		TOOL_BAG_ITEM(3);
		
		private int id;
		private GUI_ID(final int id) {
			this.id = id;
		}
		public int getId() {
			return this.id;
		}
	}
	
	
	/**
	 * サーバ側の処理
	 */
	public static Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		
		if (ID == GUI_ID.TOOL_CHEST.getId()) {
			
			//ブロックのtileEntityを取得
			ITataraItemInventory tileentity = (ITataraItemInventory) world.getTileEntity(new BlockPos(x, y ,z));
            
			return new ContainerToolChest(player.inventory, tileentity);
			//return new ContainerToolChest();
			
		} else if (ID == GUI_ID.TOOL_CHEST_ITEM.getId()) {
			
			//アイテムスタックからIInventoryを取得
			IInventory inventoryItem = new InventoryToolChest(player.inventory);
			
			return new ContainerToolChest(player.inventory, (ITataraItemInventory)inventoryItem);
			
		} else if (ID == GUI_ID.TOOL_BAG_ITEM.getId()) {
			
			//アイテムスタックからIInventoryを取得
			IInventory inventoryItem = new InventoryToolBag(player.inventory);
			
			return new ContainerToolBag(player.inventory, (ITataraItemInventory)inventoryItem);
			
			
		}
		
		return null;
		
	}
	
	/**
	 * クライアント側の処理
	 */
	public static Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		if (ID == GUI_ID.TOOL_CHEST.getId()) {
			
			//ブロックのtileEntityを取得
			ITataraItemInventory tileentity = (ITataraItemInventory) world.getTileEntity(new BlockPos(x, y ,z));
			
            return new GuiContainerToolChest(player.inventory, tileentity);
            //return new GuiContainerToolChest();
            
        } else if (ID == GUI_ID.TOOL_CHEST_ITEM.getId()) {
			
			//アイテムスタックからIInventoryを取得
			IInventory inventoryItem = new InventoryToolChest(player.inventory);
			
			return new GuiContainerToolChest(player.inventory, (ITataraItemInventory) inventoryItem);
			
		} else if (ID == GUI_ID.TOOL_BAG_ITEM.getId()) {
			
			//アイテムスタックからIInventoryを取得
			IInventory inventoryItem = new InventoryToolBag(player.inventory);
			
			return new GuiContainerToolBag(player.inventory, (ITataraItemInventory) inventoryItem);
			
		}
		
		return null;
		
	}
	
	
}
