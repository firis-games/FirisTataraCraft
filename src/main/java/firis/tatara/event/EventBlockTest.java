package firis.tatara.event;

import firis.tatara.TataraCraft;
import firis.tatara.common.ITataraDestoryAll;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventBlockTest {

	
    @SubscribeEvent
    public void onBreakEvent(BlockEvent.BreakEvent event){
    	TataraCraft.logger.info("BlockEvent.BreakEvent");
    	
    	//適正アイテムの判断はちょっとここでやるにはきつそう
    	//あきらめるか
    	
    }
    
    
	/**
	 * 適正ツールでの採掘時のイベントかな
	 * @param event
	 */
    @SubscribeEvent
    public void onHarvestDropsEvent(BlockEvent.HarvestDropsEvent event){
    	
    	TataraCraft.logger.info("BlockEvent.HarvestDropsEvent1:" + event.drops);
    	TataraCraft.logger.info("BlockEvent.HarvestDropsEvent2:" + event.world.getBlockState(event.pos));
    	TataraCraft.logger.info("BlockEvent.HarvestDropsEvent3:" + event.harvester);
    	TataraCraft.logger.info("BlockEvent.HarvestDropsEvent4:" + event.state);
    	TataraCraft.logger.info("BlockEvent.HarvestDropsEvent5:" + event.world.getTileEntity(event.pos));
    	
    	if (event.harvester != null) {
    		
    		//nullじゃなければ処理をやる？
  
    	  	int i = 0;
        	i++;
        	
        	if(i>0){
        		
        	}  		
    		
    	}
    	
  
    	
    }
}
