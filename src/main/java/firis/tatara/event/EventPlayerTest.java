package firis.tatara.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.AttributeModifier;

import firis.tatara.TataraCraft;
import firis.tatara.TataraItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventPlayerTest {
	
	
	
	/**
	 * ハーベストのイベント割り込み？
	 * ツール系のレベルが設定されているやつは呼び出されない
	 * ピッケルの判定とかにはむりっぽい
	 * @param event
	 */
	@SubscribeEvent
	public void onHarvestCheck(PlayerEvent.HarvestCheck event){
	
		TataraCraft.logger.info("HarvestCheck");
	}
	
	/**
	 * 落下時のイベント
	 * distanceを0にしたらダメージ無効化できる
	 * @param event
	 * 引数にBreakEventを指定する事でブロックが破壊された時に呼び出される 1の方に当たる部分
	 */
    @SubscribeEvent
    public void LivingUpdateEvent(LivingEvent.LivingUpdateEvent event){
		
    	if(event.entityLiving instanceof EntityPlayer) {
	    	
			
			EntityPlayer player = (EntityPlayer) event.entityLiving;
	
			//アーマーのスロットIDは1がブーツで逆順番っぽい
			ItemStack stack = player.getEquipmentInSlot(4);
			
			//プレイヤーサイドから確認
			if (stack != null && stack.getItem() == TataraItems.YukariHelmet) {
				TataraCraft.logger.info("update");

				
				//AttributeModifier attr = new AttributeModifier("generic.maxHealth1111111", 40.0D, 1);
				
				//player.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(attr);
				
				//player.getAttributeMap().applyAttributeModifiers(stack.getAttributeModifiers());
	            
				/*
				Multimap<String, AttributeModifier> list = ArrayListMultimap.create();
				
				AttributeModifier attr = new AttributeModifier("generic.maxHealth", 40, 1);
				list.containsEntry("YukariHelmet", attr);
				list.put("YukariHelmet", attr);
//				list = stack.getAttributeModifiers();
				player.getAttributeMap().applyAttributeModifiers(list);
				player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40);
				//player.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5);
				*/
			}
    	}
		
		
		
	}
	
	
	
	/**
	 * 落下時のイベント
	 * distanceを0にしたらダメージ無効化できる
	 * @param event
	 * 引数にBreakEventを指定する事でブロックが破壊された時に呼び出される 1の方に当たる部分
	 */
    @SubscribeEvent
    public void LivingFallEvent(LivingFallEvent event){
    
    	if(event.entityLiving instanceof EntityPlayer) {
    		
    		EntityPlayer player = (EntityPlayer) event.entityLiving;

    		//アーマーのスロットIDは1がブーツで逆順番っぽい
    		ItemStack stack = player.getEquipmentInSlot(1);
    		
    		//ブーツかどうか確認
    		if (stack != null && stack.getItem() == TataraItems.YukariBoots) {
    			TataraCraft.logger.info("落下ダメージ無効化:");
        		TataraCraft.logger.info(event.distance);
        		event.distance = 0;
    		}
    		
    	}
    }
	
	
	/**
	 * なんかダメージ受けたときに動くっぽい？
	 * エンティティが何らかの有効なDamageSourceによって攻撃される時に発生する。以下の原因を含む: 炎、溶岩、溺死、飢え、サボテンとの接触、落下 など。 全ての攻撃元はnet.minecraft.src.DamageSourceを参照 
	 * DamageSource source
    ダメージの原因（種類） 
int amount
    負うダメージ 
	 */
    @SubscribeEvent
    public void LivingHurtEvent(LivingHurtEvent event){
    
    	if(event.entityLiving instanceof EntityPlayer) {
    		
    		EntityPlayer player = (EntityPlayer) event.entityLiving;

    		//アーマーのスロットIDは1がブーツで逆順番っぽい
    		ItemStack stack = player.getEquipmentInSlot(3);
    		
    		//溶岩
    		if (event.source.isFireDamage() == true) {
    			if (stack != null && stack.getItem() == TataraItems.YukariChestplate) {
        			//溶岩だけ無効化してみる
        			event.ammount = 0;
        			
        			TataraCraft.logger.info("ダメージ無効化処理:");
            		TataraCraft.logger.info(event.source);
            		
            		//燃える時間を消去
            		player.extinguish();
    			}    			
    		}
    		
    		//event.ammount = 0;
    		
    	}
    }
    
    

    /**
     * エンダーのテレポートとエンダーアイのテレポートイベント
     * @param event
     */
    @SubscribeEvent
    public void EnderTeleportEvent(EnderTeleportEvent event){
    	
    	/*
    	//エンダーマンのテレポート無効化
    	if(event.entityLiving instanceof EntityEnderman) {
    		TataraCraft.logger.info("テレポートキャンセル:");
    		//キャンセルかな？
    		event.setCanceled(true);
    	}
    	*/
    }
    
    
    
}
