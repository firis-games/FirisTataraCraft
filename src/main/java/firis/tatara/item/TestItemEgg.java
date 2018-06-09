package firis.tatara.item;

import firis.tatara.TataraCraft;
import firis.tatara.entity.EntityTest;
import firis.tatara.entity.living.EntityChickenCustom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * 磁鉄鉱のアイテム用クラス
 * 
 * @author computer
 *
 */
public class TestItemEgg extends Item {

	
	// コンストラクタ
	public TestItemEgg() {
	
		//初期の設定をとりあえずここに書いておく
		//クリエイティブタブ
		this.setCreativeTab(TataraCraft.tabTatara);
		
		//設定を行う
	//	this.setCreativeTab(CreativeTabs.tabFood)/*クリエイティブのタブ*/
        //this.setUnlocalizedName("TestEgg")/*システム名の登録*/
		/*.setHasSubtypes(true)*//*ダメージ値等で複数の種類のアイテムを分けているかどうか。デフォルトfalse*/
		/*.setMaxDamage(256)*//*耐久値の設定。デフォルト0*/
		/*.setFull3D()*//*３D表示で描画させる。ツールや骨、棒等。*/
		/*.setContainerItem(Items.stick)*//*クラフト時にアイテムを返却できるようにしている際の返却アイテムの指定。*/
		/*.setPotionEffect(PotionHelper.ghastTearEffect)*//*指定文字列に対応した素材として醸造台で使える。PotionHelper参照のこと。*/
		/*.setNoRepair()*//*修理レシピを削除し、金床での修繕を出来なくする*/
        //.setMaxStackSize(64);/*スタックできる量。デフォルト64*/
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
	
	
	
	/*
	 * 右クリック時に動く
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer playerIn) {
		
		//右クリックしたときに呼ばれるらしい
		
		
		
		if (!world.isRemote)
        {
			if (playerIn.isSneaking()) {
				
				EntityChickenCustom entityageable = new EntityChickenCustom(world);
				
	            entityageable.setLocationAndAngles(playerIn.posX, playerIn.posY, playerIn.posZ, 0.0F, 0.0F);
	            world.spawnEntityInWorld(entityageable);
				
			}else {
				EntityTest entityarrow = new EntityTest(world, playerIn, 1.0F * 1.0F);
				world.spawnEntityInWorld(entityarrow);
			}
        }
		
        return item;
	}
	
	/*
	 * ブロックに対して右クリックした際に動く
	 */
    /**
     * Called when a Block is right-clicked with this Item
     */
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		
		/*
		EntityLightningBolt lightningBolt = new EntityLightningBolt(worldIn, pos.getX(), pos.getY(), pos.getZ());  //雷を生成する処理 2の方に当たる部分
		worldIn.addWeatherEffect(lightningBolt);          //同上

        return true;
        */
		
		

		return false;
    }
	
	/**
	 * エンティティに対して右クリック
	 */
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target)
    {
		/*
		World world = playerIn.worldObj;
		
		int x = target.getPosition().getX();
		int y = target.getPosition().getY();
		int z = target.getPosition().getZ();
		
		EntityLightningBolt lightningBolt = new EntityLightningBolt(world, x, y, z);  //雷を生成する処理 2の方に当たる部分
		world.addWeatherEffect(lightningBolt);          //同上
		*/
		
		

		
        return true;
    }

	
}


