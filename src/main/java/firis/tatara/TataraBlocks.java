package firis.tatara;

import firis.tatara.block.BlockLightGrass;
import firis.tatara.block.BlockLightPlanks;
import firis.tatara.block.BlockMobSpawnerExtend;
import firis.tatara.block.BlockTataraBasic;
import firis.tatara.block.BlockToolChest;
import firis.tatara.entity.TileEntityMobSpawnerExtend;
import firis.tatara.entity.TileEntityToolChest;
import firis.tatara.item.ItemBlockToolChest;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TataraBlocks {
	
	//ライト木材ブロック
	public static Block LightPlanks = new BlockLightPlanks();
	
	//ライトガラスブロック
	public static Block LightGrass = new BlockLightGrass();
	
	//2倍圧縮丸石
	public static Block CobbleStone2Times = new BlockTataraBasic(BlockTataraBasic.BlockType.COBBLE_STONE_2);

	//3倍圧縮丸石
	public static Block CobbleStone3Times = new BlockTataraBasic(BlockTataraBasic.BlockType.COBBLE_STONE_3);
	
	//銀鉱石
	public static Block SilverOre = new BlockTataraBasic(BlockTataraBasic.BlockType.SILVER_ORE);
	
	//ツールチェスト
	public static Block ToolChest = new BlockToolChest();
	
	//スポナー拡張ブロック
	public static Block MobSpawnerExtend = new BlockMobSpawnerExtend();
	
	
	/**
	 * ブロック情報の登録
	 */
	public static void registerBlocks() {
		
		
		registerBlock(LightPlanks, "LightPlanks");
		
		registerBlock(LightGrass, "LightGrass");
		
		registerBlock(CobbleStone2Times, "CobbleStone2Times");
		
		registerBlock(CobbleStone3Times, "CobbleStone3Times");

		
		registerBlock(SilverOre, "SilverOre");
		//採掘レベルを設定？
		SilverOre.setHarvestLevel("pickaxe", 2);
			
		
				
		//registerBlock(ToolChest, "ToolChest");
		
		ToolChest.setUnlocalizedName("ToolChest").setRegistryName(TataraCraft.MOD_ID + ":" + "ToolChest");
		GameRegistry.registerBlock(ToolChest, ItemBlockToolChest.class);
		
		
		//TileEntityの登録はどうしよう
		GameRegistry.registerTileEntity(TileEntityToolChest.class, "TileEntityToolChest");
		
		
		//MobSpawner拡張ブロック
		registerBlock(MobSpawnerExtend, "MobSpawnerExtend");
		GameRegistry.registerTileEntity(TileEntityMobSpawnerExtend.class, "TileEntityMobSpawnerExtend");

		
		
		//テクスチャの設定
		TataraCraft.proxy.registerBlockRenderers();
		
	}
	
	
	/**
	 * GameRegistry.registerBlockのラッパー
	 * @param block
	 * @param name
	 */
	protected static void registerBlock(Block block, String name) {
		
		block.setUnlocalizedName(name).setRegistryName(TataraCraft.MOD_ID + ":" + name);
		
		GameRegistry.registerBlock(block);
		
	}
	
}
