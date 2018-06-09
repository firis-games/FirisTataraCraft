package firis.tatara.event;

import firis.tatara.TataraItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * ドロップイベントを追加する
 * @author computer
 *
 */
public class TataraEntityDropEvent {

	/**
	 * ドロップイベント
	 * @param event
	 */
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event) {
		
		World world = event.entityLiving.worldObj;
		
		//座標
		double x = event.entityLiving.posX;
		double y = event.entityLiving.posY;
		double z = event.entityLiving.posZ;
				
		
		//クライアントの場合はなにもしない
		if(world.isRemote) {
			return;
		}
		
		//ランダムでやるかな
		
		//ブタだったらドロップをいじる？
		if(event.entityLiving instanceof EntityPig){
			//70ﾊﾟｰで出す
			if(new java.util.Random().nextInt(100) <= 70) {
				event.drops.add(new EntityItem(world, x, y, z, new ItemStack(Items.leather, 1)));
			}
		}
		
		//にわとりだったらドロップをいじる？
		if(event.entityLiving instanceof EntityChicken){
			//1ﾊﾟｰで出す
			if(new java.util.Random().nextInt(100) <= 1) {
				event.drops.add(new EntityItem(world, x, y, z, new ItemStack(TataraItems.WonderEgg, 1)));
			}
		}
		
	}
	
	
}
