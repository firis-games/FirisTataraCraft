package firis.tatara.recipe;

import firis.tatara.TataraItems;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TorchBallRecipe implements IRecipe {

	/**
	 * レシピが一致しているかを確認する
	 */
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		
		//クラフトテーブルに並べたのが
		//inv
		//3×3の形で取得できる　inv.getStackInRowAndColumn(1, 1)
		//inv.getStackInSlot(0)こっちであたまから順番に
		int coal = 0;
		int charcoal = 0;
		int torch = 0;
		int torchBall = 0;
		int other = 0;
		
		boolean result = false;
		
		
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			
			ItemStack stack = inv.getStackInSlot(i);
		
			if (stack == null) {
				continue;
			}
			
			//石炭
			if (stack.getItem() == Items.coal && stack.getMetadata() == 0) {
				coal +=1;
			}
			//木炭
			else if (stack.getItem() == Items.coal && stack.getMetadata() == 1) {
				charcoal +=1;
			}
			//松明
			else if (stack.getItem() == Item.getItemFromBlock(Blocks.torch)) {
				torch += 1;
			}
			//投げ松明
			else if (stack.getItem() == TataraItems.TorchBall && stack.getMetadata() != 0) {
				torchBall += 1;
			}
			else {
				other += 1;
			}
			
		}
		
		//チェック
		if (coal + charcoal + torch > 0
				&& torchBall == 1
				&& other == 0) {
			result = true;
			
		}

		return result;
	}

	/**
	 * レシピの結果を返却する
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		
		int coal = 0;
		int charcoal = 0;
		int torch = 0;
		
		ItemStack torchStack = null;
		
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack == null) {
				continue;
			}
			//石炭
			if (stack.getItem() == Items.coal && stack.getMetadata() == 0) {
				coal +=1;
			}
			//木炭
			else if (stack.getItem() == Items.coal && stack.getMetadata() == 1) {
				charcoal +=1;
			}
			//松明
			else if (stack.getItem() == Item.getItemFromBlock(Blocks.torch)) {
				torch += 1;
			}
			//投げ松明
			else if (stack.getItem() == TataraItems.TorchBall) {
				torchStack = stack.copy();
			}
		}
		
		int repair = 0;
		
		//石炭
		repair += coal * 4;
		//木炭
		repair += charcoal * 4;
		//松明
		repair += torch * 1;
		
		//修理
		repair = torchStack.getItemDamage() - repair;
		if(repair < 0){
			repair = 0;
		}
		torchStack.setItemDamage(repair);
		
		return torchStack;
	}

	@Override
	public int getRecipeSize() {
		// TODO 自動生成されたメソッド・スタブ
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO 自動生成されたメソッド・スタブ
		return new ItemStack(TataraItems.TorchBall);
	}

	
	/**
	 * クラフト後のアイテムを返す
	 * たぶん残数とか考慮する必要あり
	 */
	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		// TODO 自動生成されたメソッド・スタブ
		ItemStack[] stacks = new ItemStack[inv.getSizeInventory()];
        for (int i = 0; i < stacks.length; ++i)
        {
            ItemStack itemstack = inv.getStackInSlot(i);
            stacks[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
        }
		return stacks;
	}

}
