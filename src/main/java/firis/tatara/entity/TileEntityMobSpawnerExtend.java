package firis.tatara.entity;

import java.util.ArrayList;
import java.util.List;

import firis.tatara.TataraCraft;
import firis.tatara.common.util.TataraJavaUtil;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;

public class TileEntityMobSpawnerExtend extends TileEntity implements ITickable{

	/**
	 * 
	 */
	@Override
	public void update() {
		
		//サーバのみ処理を行う
		if (this.worldObj.isRemote) {
			return;
		}
		
		//レッドストーン信号を受けてるときは何もしない
		if (this.worldObj.isBlockPowered(this.pos)) {
			//レッドストーン信号
			TataraCraft.logger.info("redstone");
			return;
		}
		
		//隣接するブロックにスポナーがあるか確認
		List<BlockPos> posList = new ArrayList<BlockPos>();
		//上
		if (this.worldObj.getBlockState(this.pos.up()).getBlock() == Blocks.mob_spawner) {
			posList.add(this.pos.up());
		}
		//下
		if (this.worldObj.getBlockState(this.pos.down()).getBlock() == Blocks.mob_spawner) {
			posList.add(this.pos.down());
		}
		//北
		if (this.worldObj.getBlockState(this.pos.north()).getBlock() == Blocks.mob_spawner) {
			posList.add(this.pos.north());
		}
		//南
		if (this.worldObj.getBlockState(this.pos.south()).getBlock() == Blocks.mob_spawner) {
			posList.add(this.pos.south());
		}
		//東
		if (this.worldObj.getBlockState(this.pos.east()).getBlock() == Blocks.mob_spawner) {
			posList.add(this.pos.east());
		}
		//西
		if (this.worldObj.getBlockState(this.pos.west()).getBlock() == Blocks.mob_spawner) {
			posList.add(this.pos.west());
		}
		
		
		if (posList.size() > 0) {
			
			for (int i = 0; i < posList.size(); i++) {
				TileEntity tileentity = this.worldObj.getTileEntity(posList.get(i));
				
				//スポナーの変数書き換え
				TataraJavaUtil.setDeclaredField(net.minecraft.tileentity.MobSpawnerBaseLogic.class, 
	    				((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic(), 
	    				40, "spawnDelay", "field_98286_b");
			}
			
		}
		/*
		if (this.worldObj.getBlockState(this.pos.down()).getBlock() == Blocks.mob_spawner) {

			BlockPos pos1 = this.pos.down();
			
			TileEntity tileentity = this.worldObj.getTileEntity(pos1);

    		//mobIDを取得
    		boolean result = TataraJavaUtil.setDeclaredField(net.minecraft.tileentity.MobSpawnerBaseLogic.class, 
    				((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic(), 
    				40, "spawnDelay", "field_9dddddd8288_a");
		}
		*/
		
	}

}
