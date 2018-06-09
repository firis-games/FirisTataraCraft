package firis.tatara;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * たたらクラフトのレシピ定義
 * レシピ等を書く
 */
public class TataraRecipesConvert {
	
	/**
	 * クラフトレシピの追加
	 */
	public static void convertRecipes() {

		
		//アビスのレシピを改変する
		//======================================================================
		try {
			Item item = Item.getByNameOrId("abyssalcraft:shadowfragment");
			if (item != null){

				//レシピを追加する
				GameRegistry.addShapelessRecipe(new ItemStack(item),
		    			new ItemStack(Items.diamond),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget));
				//レシピを追加する
				GameRegistry.addShapelessRecipe(new ItemStack(item, 2),
		    			new ItemStack(item),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget),
		    			new ItemStack(TataraItems.IronNugget));
			}
		} catch (Exception e) {
			
		}
		//======================================================================
		
		
		
		//流体辞書へ登録
		FluidStack fluidWater = new FluidStack(FluidRegistry.getFluid("water"), 1000);
		FluidStack fluidLava = new FluidStack(FluidRegistry.getFluid("lava"), 1000);
		//FluidStack fluidMilk = new FluidStack(FluidRegistry.getFluid("milk"), 1000);
		
		FluidContainerRegistry.registerFluidContainer(fluidWater, new ItemStack(TataraItems.GlassTubeWater), new ItemStack(TataraItems.GlassTube));
		FluidContainerRegistry.registerFluidContainer(fluidLava, new ItemStack(TataraItems.GlassTubeLava), new ItemStack(TataraItems.GlassTube));
		//FluidContainerRegistry.registerFluidContainer(fluidMilk, new ItemStack(TataraItems.GlassTubeMilk), new ItemStack(TataraItems.GlassTube));
		
		
	}
	public static void convertRecipes2() {	
		//ガラス瓶の扱いを変更する
		Items.potionitem.setMaxStackSize(16);
		
		
		
		
		//流体関連を辞書へ登録
		
		FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid("water"), FluidContainerRegistry.BUCKET_VOLUME);
		FluidStack fluidStack2 = new FluidStack(FluidRegistry.getFluid("water"), 2000);
		FluidStack fluidStack3 = new FluidStack(FluidRegistry.getFluid("lava"), 1000);
		
		FluidContainerRegistry.registerFluidContainer(fluidStack, new ItemStack(TataraItems.BucketTank));
		
		
		ItemStack test = new ItemStack(Items.bucket);

		ItemStack test1 = new ItemStack(TataraItems.BucketTank);
		ItemStack test2 = new ItemStack(TataraItems.BucketTank);
		ItemStack test3 = new ItemStack(TataraItems.BucketTank);
		ItemStack test4 = new ItemStack(TataraItems.BucketTank);
		ItemStack test5 = new ItemStack(TataraItems.BucketTank);
		
		//NBTで見分けれるか確認する
		test1.setTagCompound(new NBTTagCompound());
		test1.getTagCompound().setString("Fluid", "water");
		
		
		//NBTで見分けれるか確認する
		test2.setTagCompound(new NBTTagCompound());
		test2.getTagCompound().setString("Fluid", "Lava");
		

		//液体と容器でアイテムスタックを作る
		FluidContainerRegistry.fillFluidContainer(fluidStack, test3);
		FluidContainerRegistry.fillFluidContainer(fluidStack2, test4);
		
		if(test3 == test4){
			FluidContainerRegistry.fillFluidContainer(fluidStack3, test5);	
		}
		
		FluidContainerRegistry.fillFluidContainer(fluidStack3, test5);	
		
		FluidContainerData ddd = new FluidContainerData(fluidStack, test3, test3);
		
		OreDictionary.registerOre("fWater", test3);
		
		GameRegistry.addRecipe(
				new ShapedOreRecipe(
					new ItemStack(Items.diamond, 1),
						new Object[]
						{
							"X", "Y",
							Character.valueOf('X'), "fWater",
							Character.valueOf('Y'), Blocks.cobblestone
						}));
		
		GameRegistry.addRecipe(
				new ShapedOreRecipe(
					new ItemStack(Items.emerald, 1),
						new Object[]
						{
							"XY",
							Character.valueOf('X'), test3,
							Character.valueOf('Y'), Blocks.cobblestone
						}));
		
		
	
		
		
		
		//辞書のレシピ
		
		
    	//鉱石辞書対応のテスト
    	
		//鉱石辞書の登録
		
		
		//ItemStack water = new ItemStack();
		
		
		//鉱石辞書の登録
		//鉄のナゲット
		OreDictionary.registerOre("dustIron", TataraItems.IronNugget);
		
		
		
		
		//水を鉱石辞書へ登録
		//OreDictionary.registerOre("fluidWater", );
		
		//ItemStack(TataraItems.BucketTank

		FluidContainerData[] data = FluidContainerRegistry.getRegisteredFluidContainerData();
		
		
		FluidContainerData water = data[1]; 
		
		//filledContainer
		water.fluid.isFluidEqual(new ItemStack(Items.filled_map, 1, 0));
		
		GameRegistry.addRecipe(new ItemStack(Items.filled_map, 1, 0),
                "xxx",
                "xxx",
                "xxx",
                'x', Items.paper
        );
		
		
		
		
    	//レシピの改変を行う
    	ArrayList<IRecipe> checkList = new ArrayList<IRecipe>();
    	for (IRecipe recipe : CraftingManager.getInstance().getRecipeList()) {

    		if (recipe instanceof ShapelessRecipes) {
    			//不定形レシピ
    			ShapelessRecipes sRecipe = (ShapelessRecipes)recipe;
    			
    			for (int i = 0; i < sRecipe.recipeItems.size(); i++) {
    				//水バケツかを確認
    				if (sRecipe.recipeItems.get(i) != null 
    						&& sRecipe.recipeItems.get(i).getItem() == Items.water_bucket) {
    					checkList.add(sRecipe);
    					break;
    				}
    			}

    		} else if(recipe instanceof ShapedRecipes){
    			
    			//定型レシピ
    			ShapedRecipes sRecipe = (ShapedRecipes)recipe; 
    			
    			for (int i = 0; i < sRecipe.recipeItems.length; i++) {
    				//水バケツかを確認
    				if (sRecipe.recipeItems[i] != null
    						&& sRecipe.recipeItems[i].getItem() == Items.water_bucket) {
    					checkList.add(sRecipe);
    					break;
    				}
    			}
    		}    		
    	}
    	
    	int aaaaaa1 = 0;
    	int aaaaaa2 = 0;
    	int aaaaaa3 = 0;
    	
	}
}
