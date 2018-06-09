package firis.tatara.renderer;

import firis.tatara.TataraItems;
import firis.tatara.common.ITataraMetaItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * アイテム用のレンダラークラス
 */
@SideOnly(Side.CLIENT)
public class ItemRenderer {

	/**
	 * アイテム用のテクスチャを設定
	 */
	public static void registerItemRenderers() {
		
		registerItemRender(TataraItems.Magnetite);
		
		registerItemRender(TataraItems.IronSand);
		
		registerItemRender(TataraItems.PigIron);
		
		registerItemRender(TataraItems.IronNugget);
		
		registerItemRender(TataraItems.DirtClay);
		
		registerItemRender(TataraItems.StoneBreakHammer);
		
		registerItemRender(TataraItems.ExpBook);
		
		registerItemRender(TataraItems.WoodDrill);
		registerItemRender(TataraItems.StoneDrill);
		registerItemRender(TataraItems.IronDrill);
		registerItemRender(TataraItems.GoldDrill);
		registerItemRender(TataraItems.DiamondDrill);
		
		registerItemRender(TataraItems.StoneReinforcedDrill);
		registerItemRender(TataraItems.IronReinforcedDrill);
		registerItemRender(TataraItems.DiamondReinforcedDrill);
		registerItemRender(TataraItems.StoneReinforcedSuperDrill);
		
		
		registerItemRender(TataraItems.FriedEgg);
		
		registerItemRender(TataraItems.BakedPumpkin);

		registerItemRender(TataraItems.BakedRottenFlesh);
		
		registerItemRender(TataraItems.MelonJuice);
		
		
		registerItemRender(TataraItems.WoodHelmet);
		registerItemRender(TataraItems.WoodChestplate);
		registerItemRender(TataraItems.WoodLeggings);
		registerItemRender(TataraItems.WoodBoots);
		
		registerItemRender(TataraItems.CarbonHelmet);
		registerItemRender(TataraItems.CarbonChestplate);
		registerItemRender(TataraItems.CarbonLeggings);
		registerItemRender(TataraItems.CarbonBoots);
		
		registerItemRender(TataraItems.WonderEgg);
		
		registerItemRender(TataraItems.CarbonDust);
		registerItemRender(TataraItems.CarbonFiber);
		registerItemRender(TataraItems.CarbonBow);
		
		registerItemRender(TataraItems.GlassTube);
		registerItemRender(TataraItems.GlassTubeWater);
		registerItemRender(TataraItems.GlassTubeLava);
		registerItemRender(TataraItems.GlassTubeMilk);
		
		
		registerItemRender(TataraItems.TestEgg);
		
		
		registerItemRender(TataraItems.MobSpawner);
		
		registerItemRender(TataraItems.TorchBall);
		
		registerItemRender(TataraItems.YukariHelmet);
		registerItemRender(TataraItems.YukariChestplate);
		registerItemRender(TataraItems.YukariLeggings);
		registerItemRender(TataraItems.YukariBoots);
		
		
		registerItemRender(TataraItems.SilverIngot);
		
	}
	
	
	/**
	 * アイテムのテクスチャ用の設定を行う
	 * @param item
	 */
	protected static void registerItemRender(Item item) {
		
		//ItemStackのmetadataで種類を分けて描画させたい場合。登録名を予め登録する。
        //ModelBakery.addVariantName(sampleItem, MOD_ID + ":" + "sampleItem0", MOD_ID + ":" + "sampleItem1");
        //モデルJSONファイルのファイル名を登録。1IDで1つだけなら、登録名はGameRegistryでの登録名と同じものにする。
        //1IDでmetadata別複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
		
		if (!(item instanceof ITataraMetaItem)) {
			//通常のアイテム
			//MetaIDごとにテクスチャを変更するパターンは考慮してない
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));

		} else {
			
			//メタデータによる分岐がある場合
			ITataraMetaItem mitem = (ITataraMetaItem)item;
			
			for (int i = 0; i <= mitem.getMaxMetadata(); i++) {
				
				String vname = mitem.getItemVariantsName(i);
				if (vname != null) {
					ModelBakery.registerItemVariants(item, new ResourceLocation(vname));
					ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(vname, "inventory"));
				}
			}
		}
	}
	
}
