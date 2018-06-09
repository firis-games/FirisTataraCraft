package firis.tatara.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityTorchBall extends EntityThrowable {

	protected EntityPlayer owner;
	
	/**
	 * ※spawnEntityの中で読んでるから定義がいるので定義しておかないとおかしくなる
	 * @param worldIn
	 */
	public EntityTorchBall(World worldIn) {

		super(worldIn);
	}
	
	public EntityTorchBall(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);
		
		this.owner = (EntityPlayer) shooter;
	}

	/**
	 * サーバとクライアントの処理はきれいにしてない
	 * とりあえずこれでうごいてるからそのまま
	 * Entityが衝突した際の処理
	 */
	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {

		//サーバのみ処理を行う
		if (this.worldObj.isRemote)
        {
			return;
        }
		
		//何かしらのEntityにヒットした場合
		if (p_70184_1_.entityHit != null){
			
			//松明をドロップする
			ItemStack stack = new ItemStack(Blocks.torch);
			EntityItem eItem = new EntityItem(this.worldObj, p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, stack);
			this.worldObj.spawnEntityInWorld(eItem);
			
			//Entityを消滅させる
			if (!this.worldObj.isRemote)
	        {
	            this.setDead();
	        }
			
			return;
		}
		
		//ブロックにヒットした場合
		if(p_70184_1_.typeOfHit.name() == "BLOCK") {
			
			BlockPos pos = p_70184_1_.getBlockPos();
			EnumFacing side = p_70184_1_.sideHit;
			
			ItemStack setStack = new ItemStack(Blocks.torch);
			
			
			/*
			Item item = Item.getItemFromBlock(Blocks.torch);
			
			boolean ret = item.onItemUse(setStack, null, this.worldObj, pos, side, 0, 0, 0);
			if (!this.worldObj.isRemote)
	        {
	            this.setDead();
	        }
			
			return;
			*/
			//BlockStateで設定する
			//クリエイティブ判定の場合に復元に利用する
            if (!this.worldObj.isRemote)
	        {
	            //ItemStackのonUseItemを実行
				boolean result = setStack.onItemUse(this.owner, this.worldObj, pos, side, 0, 0, 0);
				
				if(!result) {
					//松明をドロップする
					ItemStack stack = new ItemStack(Blocks.torch);
					EntityItem eItem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, stack);
					this.worldObj.spawnEntityInWorld(eItem);
					
				}
			
	            this.setDead();
	        }
			
			return;
		}		
	}

}
