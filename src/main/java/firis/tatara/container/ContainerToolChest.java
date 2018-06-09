package firis.tatara.container;

import firis.tatara.TataraCraft;
import firis.tatara.TataraItems;
import firis.tatara.common.ITataraItemInventory;
import firis.tatara.inventory.slot.SlotPlayerHotbarInventory;
import firis.tatara.item.ItemToolBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerToolChest extends Container {
	
	//Block:tileEntity
	//Item :IInventory
	private ITataraItemInventory iInventory;
	
	/**
	 * コンストラクタ
	 */
	public ContainerToolChest(InventoryPlayer playerInv, ITataraItemInventory iInventory) {
		
		this.iInventory = iInventory;
		
		//基準座標
		int xBasePos = 0;
		int yBasePos = 0;
		
		//チェスト部分のスロット設定
		//**************************************************		
		//基準座標設定
		xBasePos = 8;
		yBasePos = 18;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j++) {
            	int slotIndex = j + i * 9;
            	int xPos = xBasePos + j * 18;
            	int yPos = yBasePos + i * 18;
            	
                this.addSlotToContainer(new Slot(this.iInventory, slotIndex, xPos, yPos));
            }
        }
        
        //プレイヤー関連のスロット設定（半共通）
      	//**************************************************
        //インベントリ

        //基準座標設定
		xBasePos = 8;
		yBasePos = 140;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
            	int slotIndex = j + i * 9 + 9; //index 9 からスタート
            	int xPos = xBasePos + j * 18;
            	int yPos = yBasePos + i * 18;
            	
                this.addSlotToContainer(new Slot(playerInv, slotIndex, xPos, yPos));
            }
        }
        
        //ホットバー
        xBasePos = 8;
		yBasePos = 198;
		for (int i = 0; i < 9; i++) {
			int slotIndex = i; //index 0 からスタート
        	int xPos = xBasePos + i * 18;
        	int yPos = yBasePos;
            	
            this.addSlotToContainer(new SlotPlayerHotbarInventory(playerInv, slotIndex, xPos, yPos, iInventory.isSlotCanTakeStack()));
		}
		
		//IInventoryの開いたときの処理を行う
		this.iInventory.openInventory(playerInv.player);
		
	}
	
	
	/**
	 * GUIを表示するかどうかを返す
	 * 通常チェストのように使える使えないの判断がある場合はここに記述する
	 */
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	
	/**
	 * コンテナが閉じる場合に呼び出される
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer playerIn) {
    	
    	super.onContainerClosed(playerIn);
    	
    	//閉じたときの処理を行う
    	this.iInventory.closeInventory(playerIn);
    }

    /*
	 * かまどのソース
	 * shiftキーを押しながら動かしたときにここをつかって移動の制御をしてる
	 * デフォルトだとなんでか落ちるけど理由がわからん
	 * とりあえず無効化させるのにnullを強制で返却する
	 * http://ryokusitai.blog.fc2.com/blog-entry-30.html
	 * @param playerIn : クリックしたプレイヤー
 　	　* @param index　　　 :  クリックしたスロットのインデックス
	 */
	
    /**
     * Take a stack from the specified inventory slot.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

    	ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(index);
        
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            
            if (index < 54) {
            	//第4引数が前からか後ろからセットするかのフラグ
                if (!mergeItemStack(itemstack1, 54, inventorySlots.size(), false)) {
                    return null;
                }
            }else if (!mergeItemStack(itemstack1, 0, 54, false)){
                return null;
            }
            
            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
    
    /**
     * Handles slot click.
     * clickedButton 0:左クリック 1:右クリック
     * mode 0:追加キーなし 1:Shiftキー
     */
    @Override
    public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn) {
    	
    	//インベントリからツールチェストの場合
    	//Shiftキー押しながらかつ手持アイテムがないかつアイテムがスロットにある場合は一度全アイテムを走査する
    	if (slotId >= 0 && this.getSlot(slotId) != null) {
	    	ItemStack item = this.getSlot(slotId).getStack();
	    	
	    	//Shift左クリックの場合
	    	if (mode == 1 && clickedButton == 0 && item != null) {
	    		
	    		for (int i = 0; i < this.iInventory.getSizeInventory(); i++){
	    			//インベントリを走査する
	    			Slot slot= getSlot(i);
	    			if (i == slotId || slot.getStack() == null) {
	    				continue;
	    			}
	    			
	    			//ツールバッグかどうかを確認する
	    			if (slot.getStack().getItem() instanceof ItemToolBag) {
	    				//ツールバッグの場合は中身を確認
	    				ItemToolBag toolbag = (ItemToolBag) slot.getStack().getItem();
	    				
	    				if(toolbag.hasToolBagInventoryItem(slot.getStack(), item)) {
	    					//存在する場合
	    					
	    					//中身に追加して手持の分は消去する
	    					//クライアントでNBTの書き込みをするとなんかおかしいのでそこだけやらない
	    					if(!playerIn.worldObj.isRemote) {
	    						toolbag.addToolBagInventoryItem(slot.getStack(), item);
	    					}
	    					
	    					//初期化
	    					this.getSlot(slotId).putStack(null);
	    					playerIn.inventoryContainer.detectAndSendChanges();
//	    					this.iInventory.setItemInventory(null, slotId);
	    					return null;
	    				}
	    			}
	    		}
	    	}
    	}
	    	
    	//Shift右クリックの場合
    	if (mode == 1 && clickedButton == 1) {

    		//手持のアイテム
    		ItemStack cursorStack = playerIn.inventory.getItemStack();
    		
    		//手持アイテムがツールバッグかどうか
    		if (cursorStack != null && cursorStack.getItem() instanceof ItemToolBag) {
    			
    			//手持アイテムがツールバックだったら処理を行う
    			ItemToolBag toolbag = (ItemToolBag) cursorStack.getItem();
    			
    			//スロットを確認はやらない
    			boolean result = false;
    			/*
    			if (!result && item == null) {
    				//対象スロットにアイテムなし
    				result = true;
    			}
    			if (!result && toolbag.hasToolBagInventoryItem(cursorStack, item)) {
    				result = true;
    			}
    			*/
    			result = true;
    			if (result) {
    				//対象スロットのアイテムが一致
    				//クライアントでNBTの書き込みをするとなんかおかしいのでそこだけやらない
					if(!playerIn.worldObj.isRemote) {
						ItemStack stack = toolbag.removeToolBagInventoryItem(cursorStack, 1);
						
						//プレイヤーインベントリに入れる
						playerIn.inventory.addItemStackToInventory(stack);
						
						
					}
					
					playerIn.inventoryContainer.detectAndSendChanges();
    				result = true;
    				return null;
    			}
    		}
    	}
    	
    	
    	
    	return super.slotClick(slotId, clickedButton, mode, playerIn);
    	
    }
}
