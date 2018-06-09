package firis.tatara.renderer;

import firis.tatara.TataraBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ブロック用のレンダラークラス
 */
@SideOnly(Side.CLIENT)
public class BlockRenderer {

	/**
	 * ブロック用のテクスチャを設定
	 */
	public static void registerBlockRenderers() {
		
		registerBlockRender(TataraBlocks.LightPlanks);
		
		registerBlockRender(TataraBlocks.LightGrass);
		
		registerBlockRender(TataraBlocks.CobbleStone2Times);
		
		registerBlockRender(TataraBlocks.CobbleStone3Times);
		
		registerBlockRender(TataraBlocks.SilverOre);
		
		registerBlockRender(TataraBlocks.ToolChest);
		
		registerBlockRender(TataraBlocks.MobSpawnerExtend);
		
		
	}
	
	
	/**
	 * ブロックのテクスチャ用の設定を行う
	 * @param item
	 */
	protected static void registerBlockRender(Block block) {
		
		//ItemStackのmetadataで種類を分けて描画させたい場合。登録名を予め登録する。今回は使ってない。
        //ModelBakery.addVariantName(sampleItem, MOD_ID + ":" + "sampleItem0", MOD_ID + ":" + "sampleItem1");
        //モデルJSONファイルのファイル名を登録。1IDで1つだけなら、登録名はGameRegistryでの登録名と同じものにする。
        //1IDでmetadata別複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
		
		
		//MetaIDごとにテクスチャを変更するパターンは考慮してない
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
		
	}
	
}
