package firis.tatara.recipe;

import firis.tatara.TataraItems;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MobSpawnerRecipe implements IRecipe {

	/**
	 * レシピが一致しているかを確認する
	 */
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		
		//クラフトテーブルに並べたのが
		//inv
		//3×3の形で取得できる　inv.getStackInRowAndColumn(1, 1)
		//inv.getStackInSlot(0)こっちであたまから順番に
		int mobSpawner = 0;
		int spawnEgg = 0;
		int expBook30 = 0;
		int other = 0;
		
		boolean result = false;
		
		
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			
			ItemStack stack = inv.getStackInSlot(i);
		
			if (stack == null) {
				continue;
			}
			
			//スポナー
			if (stack.getItem() == TataraItems.MobSpawner) {
				mobSpawner +=1;
			}
			//スポーンエッグ
			else if (stack.getItem() == Items.spawn_egg) {
				spawnEgg +=1;
			}
			//経験値本30
			else if (stack.getItem() == TataraItems.ExpBook && stack.getMetadata() == 1) {
				expBook30 += 1;
			}
			else {
				other += 1;
			}
			
		}
		
		//チェック
		if (mobSpawner == 1 
				&& spawnEgg == 1
				&& expBook30 == 1
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
		
		ItemStack spawnEgg = null;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			
			ItemStack stack = inv.getStackInSlot(i);
			if (stack == null) {
				continue;
			}
			
			//スポナー
			//スポーンエッグ
			if (stack.getItem() == Items.spawn_egg) {
				spawnEgg = stack;
				break;
			}
		}
		
		
		ItemStack stack = new ItemStack(TataraItems.MobSpawner, 1);
		
		//EntityList.getEntityID(null);
		String mobId = EntityList.getStringFromID(spawnEgg.getMetadata());
		
		
		//EntityList.entityEggs
		
		//NBTへ書き込む
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("mobId", mobId);
		stack.setTagCompound(nbt);
		
		
		// TODO 自動生成されたメソッド・スタブ
		return stack;
	}

	@Override
	public int getRecipeSize() {
		// TODO 自動生成されたメソッド・スタブ
		return 3;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO 自動生成されたメソッド・スタブ
		return new ItemStack(Blocks.mob_spawner);
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
