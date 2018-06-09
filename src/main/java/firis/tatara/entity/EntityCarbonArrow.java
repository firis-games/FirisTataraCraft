package firis.tatara.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

/**
 * 矢の実験用
 * @author computer
 *
 */
public class EntityCarbonArrow extends EntityArrow implements IProjectile {

	public EntityCarbonArrow(World worldIn, EntityLivingBase shooter, float velocity) {
		super(worldIn, shooter, velocity);
	}

	
	
}
