package firis.tatara.item;

import firis.tatara.TataraCraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;


/**
 * ガラス管のクラス（空）
 */
public class ItemGlassTubePotion extends Item {
	
	/**
	 * メタデータの定義
	 */
	public static enum Metadata {
		MILK(3, "milk");
		
		private int metadata;
		private String name;
		
		private Metadata(int metadata, String name) {
			this.metadata = metadata;
			this.name = name;
		}
		public int getMetadata() {
			return this.metadata;
		}
		public String getName() {
			return this.name;
		}
	}
	
	/**
	 * 初期化時に保存する
	 */
	private ItemGlassTubePotion.Metadata metadata;
	
	/**
	 * コンストラクタ
	 */
	public ItemGlassTubePotion(ItemGlassTubePotion.Metadata metadata) {
		
		this.metadata = metadata;
		
		//初期化
		this.init();
	}
	
	/**
	 * たたらクラフトベーシックアイテムの初期化
	 */
	protected void init() {

		//クリエイティブタブ
		this.setCreativeTab(TataraCraft.tabTatara);
		
		//システム名の登録はアイテム登録時に行うのでここでは記述しない
		
		//その他の設定項目
        /* this.setUnlocalizedName("Magnetite")/*システム名の登録*/
		/*.setHasSubtypes(true)*//*ダメージ値等で複数の種類のアイテムを分けているかどうか。デフォルトfalse*/
		/*.setMaxDamage(256)*//*耐久値の設定。デフォルト0*/
		/*.setFull3D()*//*３D表示で描画させる。ツールや骨、棒等。*/
		/*.setContainerItem(Items.stick)*//*クラフト時にアイテムを返却できるようにしている際の返却アイテムの指定。*/
		/*.setPotionEffect(PotionHelper.ghastTearEffect)*//*指定文字列に対応した素材として醸造台で使える。PotionHelper参照のこと。*/
		/*.setNoRepair()*//*修理レシピを削除し、金床での修繕を出来なくする*/
        this.setMaxStackSize(64);/*スタックできる量。デフォルト64*/ 
		
	}
	
    /**
     * 右クリック時に利用
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        if (!playerIn.capabilities.isCreativeMode)
        {
            --stack.stackSize;
        }

        if (!worldIn.isRemote)
        {
            playerIn.curePotionEffects(stack);
        }

        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return stack;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 24;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
        return itemStackIn;
    }
}
