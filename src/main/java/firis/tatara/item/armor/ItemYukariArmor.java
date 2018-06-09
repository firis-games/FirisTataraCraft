package firis.tatara.item.armor;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import firis.tatara.TataraCraft;
import firis.tatara.TataraItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ゆかりアーマー
 * @author computer
 *
 */
public class ItemYukariArmor extends ItemArmor {

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
	//yukariアーマー
	public static final ItemArmor.ArmorMaterial YUKARI = EnumHelper.addArmorMaterial("YUKARI", "", 100, new int[] { 1, 1, 1, 1 }, 100);
	
	public final ArmorType at;
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
		public UUID getUuid() {
			return UUID.nameUUIDFromBytes(((Integer)this.id).toString().getBytes());
		}
	}
	/**
	 * 
	 * @param material
	 * @param renderIndex
	 * @param armorType
	 */
	public ItemYukariArmor(ItemYukariArmor.ArmorType armorType) {
		
		//バニラの設定
		//renderIndexは以下でArmorTextureを指定する場合はどんな値でも良い
		//0 is cloth, 1 is chain, 2 is iron, 3 is diamond and 4 is gold.
		super(YUKARI, -1, armorType.getId());
		
		at = armorType;
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
		
		int layer = (slot == ItemYukariArmor.ArmorType.LEGGINGS.getSlotNo()) ? 2 : 1;
		return TataraCraft.MOD_ID + ":textures/armor/yukari_layer_" + layer + ".png";

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
		if (this.getArmorMaterial() == YUKARI) {
			//ダイヤブロック
			return new ItemStack(Blocks.diamond_block);
		}
		
		return new ItemStack(this.getArmorMaterial().getRepairItem());
	}
	
	
	/**
     * ItemStack sensitive version of getItemAttributeModifiers
     */
	@Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack)
    {
		Multimap<String, AttributeModifier> list = super.getAttributeModifiers(stack);

		//UUIDでアイテムの設定を判断、とりあえずスロットごとに別のをみるようにしてる
		//slot設定があるみたいだけど
		//1.8でないっぽい？1.9でなければAttributeModifierを継承してスロットをもたせるかなぁ
		//AttributeModifier attr = new AttributeModifier(itemModifierUUID, "yukariammor", 5.0D, 0);
		AttributeModifier attr = new AttributeModifier(at.getUuid(), "yukariammor", 5.0D, 0);
		//list.put(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), attr);
		list.put("generic.maxHealth", attr);
		
		//AttributeModifier attr2 = new AttributeModifier(itemModifierUUID, "generic.movementSpeed", 0.1D, 0);
		//list.put("generic.movementSpeed", attr2);

		//onItemTooltipでツールチップに表示するやつを制御できそう
		//とりあえずは何もしない？
		
		TataraCraft.logger.info("属性返却用");
		return list;
    }
	//****************************************************************************************************
	
	
	/**
	 * こっちはアイテムスロットとかアーマースロット問わずに動いてるみたい
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
	@Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    	
    	
    	//アーマー着ているかを確認する
    	//TataraCraft.logger.info(itemSlot);
    	
    }
	
	
	
    /**
     * こっちは装備してるときのみ動く
     * Called to tick armor in the armor slot. Override to do something
     */
	@Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack){
		
		
		
		
		if (world.isRemote) {
			return;
		}
		//テスト
		TataraCraft.logger.info("テスト111");
		
		/*
		//アーマーを着ている場合にはここにチェックが入る
		
    	//アーマー着ているかを確認する
    	TataraCraft.logger.info(itemStack);
    	
    	//ポーション効果 ID, 時間(秒), レベル(書いた値+1になる), 確率(0.0～1.0F　1.0Fの時100%)
    	//ハラ減り
    	PotionEffect potion = new PotionEffect(Potion.hunger.id, 20, 0);
    	
    	//tick単位
    	potion = new PotionEffect(Potion.moveSpeed.id, 20, 4, false, false);
    	
    	player.addPotionEffect(potion);
    	
    	potion = new PotionEffect(Potion.jump.id, 20, 0, false, false);
    	
    	player.addPotionEffect(potion);
    	*/
    	
    	
    	
    	
    }
	
}
