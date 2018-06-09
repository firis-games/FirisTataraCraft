package firis.tatara.event;

import java.lang.reflect.Field;
import java.util.List;

import firis.tatara.TataraCraft;
import firis.tatara.TataraItems;
import firis.tatara.common.util.TataraJavaUtil;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class EventBlockMobSpawner {
	
	/**
	 * ブロックイベントのブロック破壊時に呼び出される
	 * @param event
	 * 引数にBreakEventを指定する事でブロックが破壊された時に呼び出される 1の方に当たる部分
	 * ブロックイベントが多重起動できるか確認する
	 */
    @SubscribeEvent
    public void onBreakEvent(BlockEvent.BreakEvent event){
    
    	//プレイヤー情報とアイテムを確認
    	if (event.getPlayer() == null || event.getPlayer().getHeldItem() == null) {
    		//割り込み処理を行わない
    		return;
    	}
    	
    	Block block = event.world.getBlockState(event.pos).getBlock();
    	if (block != Blocks.mob_spawner) {
    		return;
    	}
    	
    	TataraCraft.logger.info("EventBlockMobSpawner:mob_spawner");
    	
    	//モブスポナーのエンチャントレベルを取得
    	int encLevel = EnchantmentHelper.getEnchantmentLevel(TataraCraft.enchantmentMobSpawner.effectId, event.getPlayer().getHeldItem());
    	
    	/** とりあえずダイヤピッケルのみってしておく */
    	//if (event.getPlayer().getHeldItem().getItem() == Items.diamond_pickaxe) {
       	if (encLevel > 0) {
       		
    		TileEntity tileentity = event.world.getTileEntity(event.pos);

    		//mobIDを取得
    		String mobId = (String) TataraJavaUtil.getDeclaredField(net.minecraft.tileentity.MobSpawnerBaseLogic.class, 
    				((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic(), 
    				"mobID", "field_98288_a");
    		
    		//@debug
    		//event.getPlayer().addChatMessage(new ChatComponentTranslation("mobId:" + mobId));

    		ItemStack stack = new ItemStack(TataraItems.MobSpawner, 1);
    		
    		//NBTへ書き込む
    		NBTTagCompound nbt = new NBTTagCompound();
    		nbt.setString("mobId", mobId);
    		stack.setTagCompound(nbt);
    		
    		//((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic();
    		
    		
    		//List<String> entityList = EntityList.getEntityNameList();
    		
    		//とりあえずmobIDが正しいかどうかチェック
    		if(EntityList.isStringValidEntityName(mobId)){
    			//正常
    			TataraCraft.logger.info(mobId);
    		}
    		
    		EntityItem eItem = new EntityItem(event.world, event.pos.getX(), event.pos.getY(), event.pos.getZ(), stack);
    		event.world.spawnEntityInWorld(eItem);
    	}
    }

}
