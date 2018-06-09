package firis.tatara.container;

import firis.tatara.common.ITataraItemInventory;
import firis.tatara.inventory.InventoryToolBag;
import firis.tatara.inventory.slot.SlotPlayerHotbarInventory;
import firis.tatara.inventory.slot.SlotStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerToolBag extends Container {
	
	//Block:tileEntity
	//Item :IInventory
	private InventoryToolBag iInventory;
	
	/**
	 * コンストラクタ
	 */
	public ContainerToolBag(InventoryPlayer playerInv, ITataraItemInventory iInventory) {
		
		this.iInventory = (InventoryToolBag) iInventory;
		
		//基準座標
		int xBasePos = 0;
		int yBasePos = 0;
		
		//見本置き場
		//**************************************************	
		//基準座標設定
		xBasePos = 8;
		yBasePos = 18;
		this.addSlotToContainer(new SlotStorage(this.iInventory, 0, xBasePos, yBasePos));
		
		//In
		//**************************************************	
		//基準座標設定
		xBasePos = 62;
		yBasePos = 39;
		this.addSlotToContainer(new SlotStorage(this.iInventory, 1, xBasePos, yBasePos));
		
		//Out
		//**************************************************	
		//基準座標設定
		xBasePos = 98;
		yBasePos = 39;
		this.addSlotToContainer(new SlotStorage(this.iInventory, 2, xBasePos, yBasePos));
		this.addSlotToContainer(new SlotStorage(this.iInventory, 3, xBasePos+18, yBasePos));
		
		
		        
        //プレイヤー関連のスロット設定（半共通）
      	//**************************************************
        //インベントリ

        //基準座標設定
		xBasePos = 8;
		yBasePos = 77;
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
		yBasePos = 135;
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
            
            //0から3まで
            if (index < 4) {
            	//第4引数が前からか後ろからセットするかのフラグ
            	if (!mergeItemStack(itemstack1, 4, inventorySlots.size(), false)) {
                    return null;
                }
            	return null;
            //インベントリからシフトクリック
            }else {
            	//Inスロットにセット
            	slot.putStack((((SlotStorage)inventorySlots.get(1)).setStorageItem(slot.getStack())));
            	return null;
            }
        }
        return itemstack;
    }
    
    /**
     * Handles slot click.
     */
    @Override
    public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn) {
    	//見本スロット用の分岐
    	if (slotId > -1) {
        	//スロット判断
        	Slot slot = this.getSlot(slotId);
        	if (slot != null && slot instanceof SlotStorage) {
        		
        		SlotStorage sSlot = (SlotStorage)slot;
        		
        		if (slotId == 0) {
            		//見本スロットをクリック
            		return sSlot.setSampleItem(playerIn.inventory.getItemStack());
        		
        		} else if (slotId == 1) {
        		
        			//インスロットをクリック
        			playerIn.inventory.setItemStack(sSlot.setStorageItem(playerIn.inventory.getItemStack()));
        			return null;
            		
            		
        		} else if (slotId == 2) {
        		
        			//インスロットをクリック
        			if (sSlot.getStack() != null) {
        				//modeが1のときはシフトキーを押してる
        				if (mode == 1) {
        					//一括移動を試す
        					int svStackSize = sSlot.getStack().stackSize;
        					if (transferStackInSlot(playerIn, slotId) == null) {
        						//成功したらこちらで中身を取り出す
        						iInventory.itemStackSize -= svStackSize;
        						iInventory.reloadStorageSlot();
        						return null;
        					}
        				}
        				playerIn.inventory.setItemStack(sSlot.getStorageItem(playerIn.inventory.getItemStack()));
        			}
            		return null;
            		
        		} else if (slotId == 3) {
        		
        			//インスロットをクリック
        			if (sSlot.getStack() != null) {
        				//modeが1のときはシフトキーを押してる
        				if (mode == 1) {
        					//一括移動を試す
        					int svStackSize = sSlot.getStack().stackSize;
        					if (transferStackInSlot(playerIn, slotId) == null) {
        						//成功したらこちらで中身を取り出す
        						iInventory.itemStackSize -= svStackSize;
        						iInventory.reloadStorageSlot();
        						return null;
        					}
        				}
        				playerIn.inventory.setItemStack(sSlot.getStorageItem(playerIn.inventory.getItemStack()));
        			}
            		return null;
            		
        		}
        		
        	}
        }  	
    	return super.slotClick(slotId, clickedButton, mode, playerIn);
    	
    }
}
