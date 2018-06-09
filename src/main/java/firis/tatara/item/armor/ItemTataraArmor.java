package firis.tatara.item.armor;

import java.util.List;

import firis.tatara.TataraCraft;
import firis.tatara.TataraItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTataraArmor extends ItemArmor {

	/**
	 * 新しいArmorMaterialを追加するAPI
	 * 引数は(Materia名, テクスチャ名, 耐久力基本値, 防御力, エンチャント補正)
	 * 以下はバニラのツールの数値(テクスチャ名は省略)
	 *    革("LEATHER",  5, new int[] { 1, 3, 2, 1 }, 15)
	 * チェイン("CHAIN",   15, new int[] { 2, 5, 4, 1 }, 12)
	 *    鉄("IRON",    15, new int[] { 2, 6, 5, 2 },  9)
	 *    金("GOLD",     7, new int[] { 2, 5, 3, 1 }, 25)
	 *  ダイア("DIAMOND", 33, new int[] { 3, 8, 6, 3 }, 10)
	 *
	 * とりあえずエンチャント補正以外は各種ツールと同じに設定しておく
	 */
	//木製
	public static final ItemArmor.ArmorMaterial WOOD = EnumHelper.addArmorMaterial("WOOD", "", 4, new int[] { 1, 3, 2, 1 }, 1);

	//炭素繊維
	public static final ItemArmor.ArmorMaterial CARBON = EnumHelper.addArmorMaterial("CARBON", "", 15, new int[] { 3, 8, 6, 3 }, 5);

	
	/**
	 * アーマータイプの定数
	 * 0 = helmet, 1 = plate, 2 = legs and 3 = boots
	 */
	public static enum ArmorType {
		HELMET(0),
		CHESTPLATE(1),
		LEGGINGS(2),
		BOOTS(3);
		
		private int id;
		
		private ArmorType(final int id) {
			this.id = id;
		}
		public int getId() {
			return this.id;
		}
		public int getSlotNo() {
			//アーマーのスロット番号はarmorTypeの逆順
			return 4 - id;
			
		}
	}
	/**
	 * 
	 * @param material
	 * @param renderIndex
	 * @param armorType
	 */
	public ItemTataraArmor(ItemArmor.ArmorMaterial material, ItemTataraArmor.ArmorType armorType) {
		
		//バニラの設定
		//renderIndexは以下でArmorTextureを指定する場合はどんな値でも良い
		//0 is cloth, 1 is chain, 2 is iron, 3 is diamond and 4 is gold.

		super(material, -1, armorType.getId());
		
		this.init();
	}
	
	protected void init() {

		//クリエイティブタブ
		this.setCreativeTab(TataraCraft.tabTatara);
		
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		
		super.addInformation(stack, playerIn, tooltip, advanced);
		
		//ツールチップへメッセージを表示する
		//tooltip.add("" + ": " + (stack.getMaxDamage() - stack.getItemDamage()));
	}
	
	
	/**
	 * エンティティ用のアーマーテクスチャのパスを返却する
	 * @param stack
	 * @param entity
	 * @param slot 
	 * @param type
	 * @return
	 */
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		
		int layer = (slot == ItemTataraArmor.ArmorType.LEGGINGS.getSlotNo()) ? 2 : 1;
		return TataraCraft.MOD_ID + ":textures/armor/" + getArmorMaterial().name().toLowerCase() + "_layer_" + layer + ".png";

	}
	
	/**
	 * 修理アイテムの判定
	 */
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		
		boolean isRepairable = super.getIsRepairable(toRepair, repair);
		
		// カスタム判定
		if (!isRepairable) {
			if (this.getMaterialRepairItem().getItem() == repair.getItem()) {
				isRepairable = true;
			}
		}
		
		return isRepairable;
		
	}
	
	
	/**
	 * アーマーの修理アイテム
	 * @return
	 */
	public ItemStack getMaterialRepairItem() {
		
		//ウッド製品の修理アイテム
		if (this.getArmorMaterial() == WOOD) {
			//原木
			return new ItemStack(Blocks.log);
		} else if(this.getArmorMaterial() == CARBON) {
			//炭素繊維
			return new ItemStack(TataraItems.CarbonFiber);
		}
		
		return new ItemStack(this.getArmorMaterial().getRepairItem());
	}
	
}
