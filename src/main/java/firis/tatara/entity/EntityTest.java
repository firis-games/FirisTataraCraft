package firis.tatara.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityTest extends EntityThrowable implements IProjectile  {
//public class EntityTest extends EntityThrowable implements IProjectile  {

	/**
	 * spawnEntityの中で読んでるから定義がいる
	 * @param worldIn
	 */
	public EntityTest(World worldIn) {

		super(worldIn);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public EntityTest(World worldIn, EntityLivingBase shooter, float velocity) {
		super(worldIn, shooter);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
		// TODO 自動生成されたメソッド・スタブ
		super.setThrowableHeading(x, y, z, velocity, inaccuracy);
		
	}


	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		// TODO 自動生成されたメソッド・スタブ
		
		
		
		//第一引数に渡したEntityのみダメージを食らわなくなる
		//2,3,4は爆発の座標
		//5は爆発の威力TNTがたしか4ぐらい
		//6がブロック破壊するかどうか
		//this.worldObj.createExplosion(this.worldObj.playerEntities.get(0), this.posX, this.posY, this.posZ, 10.0F, false);
		
		EntityLightningBolt lightningBolt = new EntityLightningBolt(this.worldObj,this.posX, this.posY, this.posZ);  //雷を生成する処理 2の方に当たる部分
		this.worldObj.addWeatherEffect(lightningBolt);          //同上
		
        if (p_70184_1_.entityHit != null)
        {
            int i = 0;

            if (p_70184_1_.entityHit instanceof EntityBlaze)
            {
                i = 3;
            }

            p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);
        }

        //for (int j = 0; j < 8; ++j)
        for (int j = 0; j < 1; ++j)
        {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
		
	}

}
