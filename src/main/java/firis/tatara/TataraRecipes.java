package firis.tatara;

import firis.tatara.recipe.MobSpawnerRecipe;
import firis.tatara.recipe.TorchBallRecipe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * たたらクラフトのレシピ定義
 * レシピ等を書く
 */
public class TataraRecipes {

	
	/**
	 * クラフトレシピの追加
	 */
	public static void addRecipes() {
		
		//磁鉄鉱
    	GameRegistry.addShapelessRecipe(new ItemStack(TataraItems.Magnetite, 1),
    			new ItemStack(TataraItems.StoneBreakHammer, 1, 32767),
    			new ItemStack(Item.getByNameOrId("minecraft:stone"), 1, 1));
    	
    	GameRegistry.addShapelessRecipe(new ItemStack(TataraItems.Magnetite, 1),
    			new ItemStack(TataraItems.StoneBreakHammer, 1, 32767),
    			new ItemStack(Item.getByNameOrId("minecraft:stone"), 1, 3));
    	
    	GameRegistry.addShapelessRecipe(new ItemStack(TataraItems.Magnetite, 1),
    			new ItemStack(TataraItems.StoneBreakHammer, 1, 32767),
    			new ItemStack(Item.getByNameOrId("minecraft:stone"), 1, 5));
    	
    	//砂鉄
    	GameRegistry.addShapelessRecipe(new ItemStack(TataraItems.IronSand, 1),
    			new ItemStack(TataraItems.StoneBreakHammer, 1, 32767),
    			new ItemStack(TataraItems.Magnetite, 1),
    			new ItemStack(TataraItems.Magnetite, 1),
    			new ItemStack(TataraItems.Magnetite, 1),
    			new ItemStack(TataraItems.Magnetite, 1),
    			new ItemStack(TataraItems.Magnetite, 1),
    			new ItemStack(TataraItems.Magnetite, 1),
    			new ItemStack(TataraItems.Magnetite, 1),
    			new ItemStack(TataraItems.Magnetite, 1));
    	
    	//鉄インゴット
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 1),
    			new ItemStack(TataraItems.IronNugget, 1),
    			new ItemStack(TataraItems.IronNugget, 1),
    			new ItemStack(TataraItems.IronNugget, 1),
    			new ItemStack(TataraItems.IronNugget, 1),
    			new ItemStack(TataraItems.IronNugget, 1),
    			new ItemStack(TataraItems.IronNugget, 1),
    			new ItemStack(TataraItems.IronNugget, 1),
    			new ItemStack(TataraItems.IronNugget, 1),
    			new ItemStack(TataraItems.IronNugget, 1));
    	//鉄からナゲット
    	GameRegistry.addShapelessRecipe(new ItemStack(TataraItems.IronNugget, 9),
    			new ItemStack(Items.iron_ingot, 1));
    	
    	//土粘土
    	GameRegistry.addShapelessRecipe(new ItemStack(TataraItems.DirtClay, 4),
    			new ItemStack(Blocks.dirt, 1),
    			new ItemStack(Blocks.dirt, 1),
    			new ItemStack(Blocks.dirt, 1),
    			new ItemStack(Blocks.dirt, 1),
    			new ItemStack(Blocks.dirt, 1),
    			new ItemStack(Blocks.dirt, 1),
    			new ItemStack(Blocks.dirt, 1),
    			new ItemStack(Blocks.dirt, 1),
    			new ItemStack(Items.water_bucket, 1));
    	
    	//石割ハンマー
    	GameRegistry.addRecipe(new ItemStack(TataraItems.StoneBreakHammer),
                "xyx",
                " y ",
                " y ",
                'x', Items.iron_ingot,
                'y', Blocks.log
        );
    	
    	//スイカジュース
    	GameRegistry.addRecipe(new ItemStack(TataraItems.MelonJuice),
                "xx",
                "xx",
                "xx",
                'x', Items.melon
        );
    	
    	//木製ドリル
    	GameRegistry.addRecipe(new ItemStack(TataraItems.WoodDrill),
                "xxx",
                "xxx",
                " y ",
                'x', Blocks.log,
                'y', Items.stick
        );
    	
    	//石製ドリル
    	GameRegistry.addRecipe(new ItemStack(TataraItems.StoneDrill),
                "xxx",
                "xxx",
                " y ",
                'x', Blocks.cobblestone,
                'y', Items.stick
        );
    	
    	//鉄製ドリル
    	GameRegistry.addRecipe(new ItemStack(TataraItems.IronDrill),
                "xxx",
                "xxx",
                " y ",
                'x', Items.iron_ingot,
                'y', Items.stick
        );
    	
    	//金製ドリル
    	GameRegistry.addRecipe(new ItemStack(TataraItems.GoldDrill),
                "xxx",
                "xxx",
                " y ",
                'x', Items.gold_ingot,
                'y', Items.stick
        );
    	
    	//ダイア製ドリル
    	GameRegistry.addRecipe(new ItemStack(TataraItems.DiamondDrill),
                "xxx",
                "xxx",
                " y ",
                'x', Items.diamond,
                'y', Items.stick
        );
    	
    	//石製強化ドリル
    	GameRegistry.addRecipe(new ItemStack(TataraItems.StoneReinforcedDrill),
                "xxx",
                "xxx",
                " y ",
                'x', TataraBlocks.CobbleStone2Times,
                'y', Items.stick
        );
    	
    	//鉄製強化ドリル
    	GameRegistry.addRecipe(new ItemStack(TataraItems.IronReinforcedDrill),
                "xxx",
                "xxx",
                " y ",
                'x', Blocks.iron_block,
                'y', Items.stick
        );
    	
    	//ダイア製強化ドリル
    	GameRegistry.addRecipe(new ItemStack(TataraItems.DiamondReinforcedDrill),
                "xxx",
                "xxx",
                " y ",
                'x', Blocks.diamond_block,
                'y', Items.stick
        );
    	
    	//石製スーパードリル
    	GameRegistry.addRecipe(new ItemStack(TataraItems.StoneReinforcedSuperDrill),
                "xxx",
                "xxx",
                " y ",
                'x', TataraBlocks.CobbleStone3Times,
                'y', Items.stick
        );
    	
    	//ウッドヘルメット
    	GameRegistry.addRecipe(new ItemStack(TataraItems.WoodHelmet),
                "xxx",
                "x x",
                'x', Blocks.log
        );
    	//ウッドチェストプレート
    	GameRegistry.addRecipe(new ItemStack(TataraItems.WoodChestplate),
    			"x x",
    			"xxx",
                "xxx",
                'x', Blocks.log
        );
    	//ウッドレギンス
    	GameRegistry.addRecipe(new ItemStack(TataraItems.WoodLeggings),
                "xxx",
                "x x",
                "x x",
                'x', Blocks.log
        );
    	//ウッドブーツ
    	GameRegistry.addRecipe(new ItemStack(TataraItems.WoodBoots),
                "X X",
                "X X",
                'X', Blocks.log
        );
    	
    	//光る木材
    	GameRegistry.addRecipe(new ItemStack(TataraBlocks.LightPlanks),
                "x",
                "y",
                'x', Blocks.planks,
                'y', Blocks.torch
        );
    	//光るガラス
    	GameRegistry.addRecipe(new ItemStack(TataraBlocks.LightGrass),
                "x",
                "y",
                'x', Blocks.glass,
                'y', Blocks.torch
        );
    	
    	//2倍圧縮丸石
    	GameRegistry.addRecipe(new ItemStack(TataraBlocks.CobbleStone2Times),
                "xxx",
                "xyx",
                "xxx",
                'x', Blocks.cobblestone,
                'y', Blocks.stone
        );
    	GameRegistry.addShapelessRecipe(new ItemStack(Blocks.cobblestone, 9),
    			new ItemStack(TataraBlocks.CobbleStone2Times, 1));
    	
    	//3倍圧縮丸石
    	GameRegistry.addRecipe(new ItemStack(TataraBlocks.CobbleStone3Times),
                "xxx",
                "xxx",
                "xxx",
                'x', TataraBlocks.CobbleStone2Times
        );
    	GameRegistry.addShapelessRecipe(new ItemStack(TataraBlocks.CobbleStone2Times, 9),
    			new ItemStack(TataraBlocks.CobbleStone3Times, 1));
    	
    	//粘土
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.clay_ball, 4),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.GlassTubeWater, 1));

    	//粘土ブロック
    	GameRegistry.addShapelessRecipe(new ItemStack(Blocks.clay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1),
    			new ItemStack(TataraItems.DirtClay, 1));
    	
    	//砂利
    	GameRegistry.addShapelessRecipe(new ItemStack(Blocks.gravel, 1),
    			new ItemStack(Blocks.cobblestone, 1),
    			new ItemStack(Blocks.cobblestone, 1),
    			new ItemStack(TataraItems.StoneBreakHammer, 1, 32767));
    	//砂
    	GameRegistry.addShapelessRecipe(new ItemStack(Blocks.sand, 1),
    			new ItemStack(Blocks.gravel, 1),
    			new ItemStack(Blocks.gravel, 1),
    			new ItemStack(TataraItems.StoneBreakHammer, 1, 32767));
    	
    	//ツールチェスト
    	GameRegistry.addRecipe(new ItemStack(TataraBlocks.ToolChest),
                "xxx",
                "xyx",
                "xxx",
                'x', Blocks.stone,
                'y', Blocks.chest
        );
    	
    	//未経験の本
    	GameRegistry.addRecipe(new ItemStack(TataraItems.ExpBook, 1, 0),
                "xxx",
                "xyx",
                "xxx",
                'x', Items.leather,
                'y', Items.book
        );
    	
    	//ツールバック
    	GameRegistry.addRecipe(new ItemStack(TataraItems.ToolBag),
                "x x",
                " y ",
                "x x",
                'x', Items.leather,
                'y', Blocks.chest
        );
    	GameRegistry.addRecipe(new ItemStack(TataraItems.ToolBag),
                "x x",
                " y ",
                "x x",
                'x', Blocks.wool,
                'y', Blocks.chest
        );
    	
    	
    	//スポーンエッグ関連
    	//ふしぎなたまご
    	GameRegistry.addRecipe(new ItemStack(TataraItems.WonderEgg, 1, 0),
                "xxx",
                "xyx",
                "xxx",
                'x', Blocks.quartz_block,
                'y', Blocks.diamond_block
        );
    	//ぶたのスポーンエッグ
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1, 90),
    			new ItemStack(TataraItems.WonderEgg),
    			new ItemStack(Items.porkchop));
    	//ひつじのスポーンエッグ
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1, 91),
    			new ItemStack(TataraItems.WonderEgg),
    			new ItemStack(Blocks.wool, 1, 32767));
    	//牛のスポーンエッグ
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1, 92),
    			new ItemStack(TataraItems.WonderEgg),
    			new ItemStack(Items.leather));
    	//にわとりのスポーンエッグ
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1, 93),
    			new ItemStack(TataraItems.WonderEgg),
    			new ItemStack(Items.egg));
    	//うさぎのスポーンエッグ
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1, 101),
    			new ItemStack(TataraItems.WonderEgg),
    			new ItemStack(Items.rabbit_hide));
    	
    	//ゾンビのスポーンエッグ
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1, 54),
    			new ItemStack(TataraItems.WonderEgg),
    			new ItemStack(Items.rotten_flesh));
    	
    	//スケルトンのスポーンエッグ
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1, 51),
    			new ItemStack(TataraItems.WonderEgg),
    			new ItemStack(Items.bone));
    	
    	//クモのスポーンエッグ
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1, 52),
    			new ItemStack(TataraItems.WonderEgg),
    			new ItemStack(Items.string));
    	
    	//クリーパーのスポーンエッグ
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1, 50),
    			new ItemStack(TataraItems.WonderEgg),
    			new ItemStack(Items.gunpowder));
    	
    	
    	//炭の粉
    	GameRegistry.addShapelessRecipe(new ItemStack(TataraItems.CarbonDust, 1),
    			new ItemStack(TataraItems.StoneBreakHammer, 1, 32767),
    			Items.coal,Items.coal);
    	GameRegistry.addShapelessRecipe(new ItemStack(TataraItems.CarbonDust, 1),
    			new ItemStack(TataraItems.StoneBreakHammer, 1, 32767),
    			new ItemStack(Items.coal,1,1), new ItemStack(Items.coal,1,1), new ItemStack(Items.coal,1,1), new ItemStack(Items.coal,1,1));
    	
    	//炭素繊維
    	GameRegistry.addRecipe(new ItemStack(TataraItems.CarbonFiber),
                "xyx",
                "yzy",
                "xyx",
                'x', TataraItems.CarbonDust,
                'y', Blocks.iron_bars,
                'z', Blocks.wool
        );
    	
    	//カーボンヘルメット
    	GameRegistry.addRecipe(new ItemStack(TataraItems.CarbonHelmet),
                "xxx",
                "x x",
                'x', TataraItems.CarbonFiber
        );
    	//カーボンチェストプレート
    	GameRegistry.addRecipe(new ItemStack(TataraItems.CarbonChestplate),
    			"x x",
    			"xxx",
                "xxx",
                'x', TataraItems.CarbonFiber
        );
    	//カーボンレギンス
    	GameRegistry.addRecipe(new ItemStack(TataraItems.CarbonLeggings),
                "xxx",
                "x x",
                "x x",
                'x', TataraItems.CarbonFiber
        );
    	//カーボンブーツ
    	GameRegistry.addRecipe(new ItemStack(TataraItems.CarbonBoots),
    			"X X",
                "X X",
                'X', TataraItems.CarbonFiber
        );
    	
    	//カーボン弓
    	GameRegistry.addRecipe(new ItemStack(TataraItems.CarbonBow),
    			" yx",
                "y x",
                " yx",
                'x', Items.string,
                'y', TataraItems.CarbonFiber
        );
    	
    	//ガラス管
    	GameRegistry.addRecipe(new ItemStack(TataraItems.GlassTube, 16),
    			"xyx",
                " x ",
                'x', Blocks.glass,
                'y', TataraItems.CarbonDust
        );

    	//牛乳入りガラス管
    	GameRegistry.addRecipe(new ItemStack(TataraItems.GlassTubeMilk, 8),
    			"xxx",
    			"xyx",
                "xxx",
                'x', TataraItems.GlassTube,
                'y', Items.milk_bucket
        );
    	
    	//黒曜石
    	GameRegistry.addShapelessRecipe(new ItemStack(Blocks.obsidian, 1),
    			TataraItems.GlassTubeWater, TataraItems.GlassTubeLava);    	
    	
    	
    	//見せる用のレシピ
    	ItemStack tmptmpItems = new ItemStack(TataraItems.MobSpawner);
    	NBTTagCompound tmptmpNbt = new NBTTagCompound();
    	tmptmpNbt.setString("mobId", "Cow");
    	tmptmpItems.setTagCompound(tmptmpNbt);
    	GameRegistry.addShapelessRecipe(tmptmpItems,
    			new ItemStack(TataraItems.MobSpawner),
    			new ItemStack(TataraItems.ExpBook, 1, 1),
    			new ItemStack(Items.spawn_egg, 1, 92));
    	
    	//モブスポナー
    	GameRegistry.addRecipe(new MobSpawnerRecipe());
    	
    	//投げ松明
    	GameRegistry.addRecipe(new ItemStack(TataraItems.TorchBall, 1, TataraItems.TorchBall.getMaxDamage()),
    			"xxx",
                "xyx",
                "xxx",
                'x', Blocks.torch,
                'y', Items.string
        );
    	
    	//投げ松明回復用
    	GameRegistry.addRecipe(new TorchBallRecipe());
    	
    	//スポナー拡張ブロック
    	GameRegistry.addShapelessRecipe(new ItemStack(TataraBlocks.MobSpawnerExtend),
    			new ItemStack(Blocks.redstone_block),
    			new ItemStack(Blocks.lapis_block));
    	
    	//銀のつかいみち
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.gold_ingot),
    			new ItemStack(TataraItems.SilverIngot),
    			new ItemStack(TataraItems.SilverIngot));
    	
    	
    	//バニラのレシピを追加する
    	addVanillaRecipes();
	}
	
	
	/**
	 * バニラレシピを追加する
	 */
	public static void addVanillaRecipes() {
		
		//チェインヘルメット
    	GameRegistry.addRecipe(new ItemStack(Items.chainmail_helmet),
                "xxx",
                "x x",
                'x', Blocks.iron_bars
        );
    	//チェインチェストプレート
    	GameRegistry.addRecipe(new ItemStack(Items.chainmail_chestplate),
    			"x x",
    			"xxx",
                "xxx",
                'x', Blocks.iron_bars
        );
    	//チェインレギンス
    	GameRegistry.addRecipe(new ItemStack(Items.chainmail_leggings),
                "xxx",
                "x x",
                "x x",
                'x', Blocks.iron_bars
        );
    	//チェインブーツ
    	GameRegistry.addRecipe(new ItemStack(Items.chainmail_boots),
                "x　x",
                "x x",
                'x', Blocks.iron_bars
        );
    	
    	//砂
    	GameRegistry.addRecipe(new ItemStack(Blocks.sand, 4, 0),
                "x",
                'x', Blocks.sandstone
        );
    	
    	//粘土ブロック
    	GameRegistry.addRecipe(new ItemStack(Blocks.clay, 4, 0),
                "xx",
                "xx",
                'x', Blocks.stained_hardened_clay
        );
    	
    	//クモの糸（暫定）
    	GameRegistry.addShapelessRecipe(new ItemStack(Items.string, 16),
    			new ItemStack(Items.shears, 1, 32767),
    			new ItemStack(Blocks.wool, 1, 32767),new ItemStack(Blocks.wool, 1, 32767),new ItemStack(Blocks.wool, 1, 32767),new ItemStack(Blocks.wool, 1, 32767),
    			new ItemStack(Blocks.wool, 1, 32767),new ItemStack(Blocks.wool, 1, 32767),new ItemStack(Blocks.wool, 1, 32767),new ItemStack(Blocks.wool, 1, 32767));
    
    	
    	//木のピッケル
    	ItemStack wooden_pickaxe = new ItemStack(Items.wooden_pickaxe, 1);
    	wooden_pickaxe.addEnchantment(Enchantment.unbreaking, 1);
    	wooden_pickaxe.addEnchantment(Enchantment.efficiency, 1);
    	GameRegistry.addRecipe(wooden_pickaxe,
                "xxx",
                " x ",
                " x ",
                'x', Blocks.log
        );
    	
    	//石のピッケル
    	ItemStack stone_pickaxe = new ItemStack(Items.stone_pickaxe, 1);
    	stone_pickaxe.addEnchantment(Enchantment.unbreaking, 1);
    	stone_pickaxe.addEnchantment(Enchantment.efficiency, 1);
    	GameRegistry.addRecipe(stone_pickaxe,
                "xxx",
                " y ",
                " y ",
                'x', Blocks.cobblestone,
                'y', Blocks.log
        );
    	
    	ItemStack stone_pickaxe2 = new ItemStack(Items.stone_pickaxe, 1);
    	stone_pickaxe2.addEnchantment(Enchantment.unbreaking, 3);
    	stone_pickaxe2.addEnchantment(Enchantment.efficiency, 3);
    	GameRegistry.addRecipe(stone_pickaxe2,
                "xxx",
                " y ",
                " y ",
                'x', TataraBlocks.CobbleStone2Times,
                'y', Blocks.log
        );
    	
    	//ダイヤモンド
    	ItemStack diamond_pickaxe = new ItemStack(Items.diamond_pickaxe, 1, 1562 - (1562-2)/4);
    	diamond_pickaxe.addEnchantment(Enchantment.fortune, 10);
    	diamond_pickaxe.addEnchantment(Enchantment.efficiency, 10);
    	//diamond_pickaxe.addEnchantment(Enchantment.unbreaking, 5);
    	GameRegistry.addRecipe(diamond_pickaxe,
                "xxx",
                " y ",
                " y ",
                'x', Blocks.diamond_block,
                'y', Blocks.iron_block
        );
    	
	}
	
	
	/**
	 * かまどレシピの追加
	 */
	public static void addSmeltingRecipes() {
		
		/*
		 	バニラの経験値
			0.1F 　砂、丸石、石炭鉱石
			0.15F　原木
			0.2F 　サボテン、ラピス鉱石
			0.3F 　粘土
			0.35F　牛肉、豚肉、鶏肉、生魚、ジャガイモ
			0.7F 　鉄鉱石、レッドストーン鉱石
			1.0F 　ダイヤモンド鉱石、エメラルド鉱石、金鉱石 
		 */
		
		//銑鉄
		GameRegistry.addSmelting(new ItemStack(TataraItems.IronSand, 1),
				new ItemStack(TataraItems.PigIron, 1),
				0.1f);
		
		//鉄ナゲット
		GameRegistry.addSmelting(new ItemStack(TataraItems.PigIron, 1),
				new ItemStack(TataraItems.IronNugget, 1),
				0.1f);
		
		//焼き魚
		GameRegistry.addSmelting(new ItemStack(Items.fish, 1, 2),
				new ItemStack(Items.cooked_fish, 1),
				0.1f);
		GameRegistry.addSmelting(new ItemStack(Items.fish, 1, 3),
				new ItemStack(Items.cooked_fish, 1),
				0.1f);
		
		//目玉焼き
		GameRegistry.addSmelting(new ItemStack(Items.egg, 1),
				new ItemStack(TataraItems.FriedEgg, 1),
				0.1f);
		
		//焼きかぼちゃ
		GameRegistry.addSmelting(new ItemStack(Blocks.pumpkin, 1),
				new ItemStack(TataraItems.BakedPumpkin, 1),
				0.1f);
		
		//焼いた腐り肉
		GameRegistry.addSmelting(new ItemStack(Items.rotten_flesh, 1),
				new ItemStack(TataraItems.BakedRottenFlesh, 1),
				0.1f);
		
		//銀インゴット
		GameRegistry.addSmelting(new ItemStack(TataraBlocks.SilverOre, 1),
				new ItemStack(TataraItems.SilverIngot, 1),
				0.1f);		
		
		//燃料を追加
		addSmeltingFuel();
	}
	
	
	/**
	 * かまどレシピの燃料を追加
	 */
	public static void addSmeltingFuel() {
		GameRegistry.registerFuelHandler(new IFuelHandler(){
			@Override
			public int getBurnTime(ItemStack fuel) {
				if (fuel.getItem().equals(TataraItems.CarbonDust)) {
					//石炭の2倍
					return 3600;
				}
				return 0;
			}
			
		});
	}
	
}
