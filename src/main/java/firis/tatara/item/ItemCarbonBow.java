package firis.tatara.item;

import firis.tatara.TataraCraft;
import firis.tatara.common.ITataraMetaItem;
import firis.tatara.entity.EntityCarbonArrow;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**
 * カーボン弓
 */
public class ItemCarbonBow extends Item implements ITataraMetaItem {

	
	/**
	 * コンストラクタ
	 */
	public ItemCarbonBow() {
		
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
		//this.setHasSubtypes(true);//*ダメージ値等で複数の種類のアイテムを分けているかどうか。デフォルトfalse*/
		/*.setMaxDamage(256)*//*耐久値の設定。デフォルト0*/
		/*.setFull3D()*//*３D表示で描画させる。ツールや骨、棒等。*/
		/*.setContainerItem(Items.stick)*//*クラフト時にアイテムを返却できるようにしている際の返却アイテムの指定。*/
		/*.setPotionEffect(PotionHelper.ghastTearEffect)*//*指定文字列に対応した素材として醸造台で使える。PotionHelper参照のこと。*/
		/*.setNoRepair()*//*修理レシピを削除し、金床での修繕を出来なくする*/
        /* this.setMaxStackSize(64);/*スタックできる量。デフォルト64*/ 
		
		this.setMaxStackSize(1);
        this.setMaxDamage(100);
        
	}
	
    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft)
    {
    	//TataraCraft.logger.info(timeLeft);
        boolean flag = playerIn.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

        if (flag || playerIn.inventory.hasItem(Items.arrow))
        {
            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            net.minecraftforge.event.entity.player.ArrowLooseEvent event = new net.minecraftforge.event.entity.player.ArrowLooseEvent(playerIn, stack, i);
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;
            i = event.charge;
            /*
            float f = (float)i / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }
			*/
            //判定を変更
            //おそらくチックで判断してると思われる
            float f = (float)i / 20.0F;
            //f = (f * f + f * 2.0F) / 3.0F;

            //1秒以下はキャンセル
            if ((double)f < 1.0D)
            {
                return;
            }

            //3秒チャージ
            if (f > 3.0F)
            {
                f = 3.0F;
            }

            //倍率アップ
            EntityArrow entityarrow = new EntityArrow(worldIn, playerIn, f * 2.5F);
            //EntityCarbonArrow entityarrow = new EntityCarbonArrow(worldIn, playerIn, f * 2.5F);
            

            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

            if (j > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

            if (k > 0)
            {
                entityarrow.setKnockbackStrength(k);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
            {
                entityarrow.setFire(100);
            }

            stack.damageItem(1, playerIn);
            worldIn.playSoundAtEntity(playerIn, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else
            {
                playerIn.inventory.consumeInventoryItem(Items.arrow);
            }

            playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

            if (!worldIn.isRemote)
            {
                worldIn.spawnEntityInWorld(entityarrow);
            }
        }
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
        return stack;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        net.minecraftforge.event.entity.player.ArrowNockEvent event = new net.minecraftforge.event.entity.player.ArrowNockEvent(playerIn, itemStackIn);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return event.result;

        if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(Items.arrow))
        {
        	playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
        }

        return itemStackIn;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 1;
    }
    
    
    /**
     * 右クリックし長押ししたときの描画
     * Player, Render pass, and item usage sensitive version of getIconIndex.
     *
     * @param stack The item stack to get the icon for.
     * @param player The player holding the item
     * @param useRemaining The ticks remaining for the active item.
     * @return Null to use default model, or a custom ModelResourceLocation for the stage of use.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public net.minecraft.client.resources.model.ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
    {
    	ModelResourceLocation modelresourcelocation = null;
    	
    	//RenderItem.class の renderItemModelForEntityでやってる判定からこっちにもってきた
    	//int i = stack.getMaxItemUseDuration() - entityplayer.getItemInUseCount();
    	int i = stack.getMaxItemUseDuration() - useRemaining;
    	
//        if (i >= 18)
    	if(useRemaining == 0) {
    		//デフォルト
    		modelresourcelocation = new ModelResourceLocation("tataracraft:CarbonBow", "inventory");
    		
    	}else if (i >= 60)
        {
            modelresourcelocation = new ModelResourceLocation("tataracraft:CarbonBow_pulling_2", "inventory");
        }
//        else if (i > 13)
        else if (i > 30)
        {
            modelresourcelocation = new ModelResourceLocation("tataracraft:CarbonBow_pulling_1", "inventory");
        }
        else if (i > 0)
        {
            modelresourcelocation = new ModelResourceLocation("tataracraft:CarbonBow_pulling_0", "inventory");

        }
        
        return modelresourcelocation;
    }
    
	//メタデータでrender書き換えるために追加
	//****************************************************************************************************

	/**
	 * Metadataの最大値を返却
	 */
	@Override
	public int getMaxMetadata() {
		return 3;
	}

	/**
	 * Rendererに使う名称を返却する
	 * GetModelで設定を行っているのでそれで使う用に用意
	 */
	@Override
	public String getItemVariantsName(int metadata) {
		
		String name = this.getRegistryName();
		
		//弓の書式に合わせる
		switch (metadata) {
			case 0:
				name = name + "";
				break;
			case 1:
				name = name + "_pulling_0";
				break;
			case 2:
				name = name + "_pulling_1";
				break;
			case 3:
				name = name + "_pulling_2";
				break;
		}
		return name;
	}
	
	//-----------
	/**
	 * 視野制御用のイベント
	 * @param event
	 */
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onFOVUpdate(FOVUpdateEvent event){
		
		EntityPlayer player = event.entity;
		ItemStack using = player.getItemInUse();
		
		if (using != null && using.getItem() instanceof ItemCarbonBow) {
			float f = player.getItemInUseDuration() / 20.0F;
	
			/*
			if (f > 1.0F) {
				f = 1.0F;
			} else {
				f *= f;
			}
			*/
			if (f > 3.0F) {
				f = 1.0F;
			} else {
				f = (f / 3.0F);
			}
	
			//event.newfov *= 1.0F - f * 0.15F;
			event.newfov *= 1.0F - f * 0.4F;
		}
	}
	
}
