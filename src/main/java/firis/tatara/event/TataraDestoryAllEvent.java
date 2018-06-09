package firis.tatara.event;

import java.util.ArrayList;

import firis.tatara.common.ITataraDestoryAll;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 一括破壊用イベント定義
 */
public class TataraDestoryAllEvent {

	/**
	 * ブロックイベントのブロック破壊時に呼び出される
	 * @param event
	 * 引数にBreakEventを指定する事でブロックが破壊された時に呼び出される 1の方に当たる部分
	 */
    @SubscribeEvent
    public void onBreakEvent(BlockEvent.BreakEvent event){
    	
    	//プレイヤー情報とアイテムを確認
    	if (event.getPlayer() == null || event.getPlayer().getHeldItem() == null) {
    		//割り込み処理を行わない
    		return;
    	}
    	
    	//手持ちアイテムがハンマーか確認
    	EntityPlayer player = event.getPlayer();
    	
    	Item handItem = player.getHeldItem().getItem();
    	
    	
    	//一括破壊の判定
    	if(handItem instanceof ITataraDestoryAll) {
    	} else {
    		return;
    	}
    	
    	//**************************************************
    	
    	//ワールド
        World world = event.world;
        
    	//破壊対象ブロックの座標
    	Integer x = event.pos.getX();
        Integer y = event.pos.getY();
        Integer z = event.pos.getZ();        
    	
    	//一括範囲破壊
      //**************************************************
        if (((ITataraDestoryAll) handItem).isMineAll(player.getHeldItem())) {
        	
        	//@debug
        	event.getPlayer().addChatMessage(new ChatComponentTranslation("Mine All"));
        	
        	this.AreaDestoryAll(world, player, new BlockPos(x, y ,z), true);
        	return;
        	
    	} else if (((ITataraDestoryAll) handItem).isDigAll(player.getHeldItem())) {
    		
    		//@debug
        	event.getPlayer().addChatMessage(new ChatComponentTranslation("Dig All"));
        	
        	this.AreaDestoryAll(world, player, new BlockPos(x, y ,z), false);
        	return;
        	
    	} else if (((ITataraDestoryAll) handItem).isCutAll(player.getHeldItem())) {
    		
    		//@debug
        	event.getPlayer().addChatMessage(new ChatComponentTranslation("Cut All"));
        	
        	this.ChainDestoryAll(world, player, new BlockPos(x, y ,z));
        	return;
    		
    	}
    }
    
    /**
     * 範囲の一括破壊処理
     * @param world
     * @param player
     * @param basePos 破壊されたブロック座標
     * @param underDestory 下方向への破壊を行うかの判断
     * @return
     */
    public void AreaDestoryAll(World world, EntityPlayer player, BlockPos basePos, boolean underDestory) {
    	
    	//デストロイブロックでいける
        //破壊面を判断できるかを確認したかったけど無理そう
        //破壊点から3×3×3マスを破壊するか？
        //ブロック破壊をどうにかやるけど完全破壊しないように気をつけないと
        //まずは問答無用で破壊する？
        //とりあえず破壊ブロックを中心のブロック座標リストをつくる？
                
        //とりあえずは3×3×3を破壊することを想定する
        //設定化可能
    	int destoryArea = 3;
    	    	
    	int destoryAreaCal = (destoryArea - 1) / 2;
    	
    	//from　to を設定してみる？
        //BlockPos from = new BlockPos(x + 1, y + 1 ,z + 1);
        //BlockPos to = new BlockPos(x - 1, y - 1 ,z - 1);
    	BlockPos from = basePos.up(destoryAreaCal).north(destoryAreaCal).west(destoryAreaCal);
        BlockPos to = basePos.down(destoryAreaCal).south(destoryAreaCal).east(destoryAreaCal);
        
        //下方向へ破壊を行わない
        if (!underDestory) {
        	from = basePos.up(destoryArea - 1).north(destoryAreaCal).west(destoryAreaCal);
            to = basePos.south(destoryAreaCal).east(destoryAreaCal);
        }
        
        //範囲内の破壊を行う
        for (BlockPos element : BlockPos.getAllInBox(from, to)) {
        	
        	//耐久値が0の場合は強制終了
       		/*
        	if(player.getHeldItem().getMaxDamage() - player.getHeldItem().getItemDamage() <= 0) {
        		break;
        	}
       		*/
       		//耐久値が0の判断を変更
       		if (player.getHeldItem().stackSize == 0) {
       			break;
       		}
        	
        	//破壊可否の判断
        	//適正ツールかどうかしか判断できない
        	//破壊の起点が適正かどうかだけみる？
        	//素手で破壊できるか・適正ツールか？
        	//適正ツールの判断はいらないかも
        	if ((world.getBlockState(element).getBlock().getMaterial().isToolNotRequired() 
        			|| player.getHeldItem().canHarvestBlock(world.getBlockState(element).getBlock()))
        			&& !world.getBlockState(element).getBlock().equals(Blocks.air)) {
        		
        		//破壊できる場合は破壊する
            	world.destroyBlock(element, true);
            	/*
            	EntityPlayerMP playermp = (EntityPlayerMP)player;
            	//プレイヤーのブロック破壊イベントかな？
            	playermp.theItemInWorldManager.blockRemoving(element);
            	//world.destroyBlock(new BlockPos(x, y-1 ,z), true);
            	*/
            	
            	//ツールの耐久度を減らす
            	//手持ちの耐久を減らす(1)
            	player.getHeldItem().damageItem(1, player);
        	}
        }
    }
    
    
    
    /**
     * 連鎖破壊の一括破壊処理
     * @param world
     * @param player
     * @param basePos 破壊されたブロック座標
     * @param underDestory 下方向への破壊を行うかの判断
     * @return
     */
    public void ChainDestoryAll(World world, EntityPlayer player, BlockPos basePos) {
    	
    	//対象ブロックチェック用
    	Block baseBlock = world.getBlockState(basePos).getBlock();
    	
    	
    	//基準ブロックと同じものを走査して存在すれば破壊する
    	//ただしリミッターを掛けておく
    	//16ブロックまで強制
    	int destoryLimitter = 16;
    	if (baseBlock.getMaterial() == Material.wood 
    			|| baseBlock.getMaterial() == Material.leaves
    			|| baseBlock.getMaterial() == Material.vine) {
    		//植物系だけ128ブロックリミッター
    		destoryLimitter = 128;
    	}
    	
    	
    	//まずブロックの走査をする
    	ArrayList<BlockPos> searchList = new ArrayList<BlockPos>();
    	
    	//走査用のブロック座標リスト
    	searchList.add(basePos);
    	

    	
    	int posIndex = 0;
    	while(posIndex < searchList.size()) {
    		
    		//走査基準点
        	BlockPos from = searchList.get(posIndex).up().north().west();
            BlockPos to = searchList.get(posIndex).down().south().east();
            
            //範囲内の走査を行う
            for (BlockPos element : BlockPos.getAllInBox(from, to)) {
            	
            	Block elementBlock = world.getBlockState(element).getBlock();
            	
            	//同じブロックか判断する
            	if (elementBlock == baseBlock) {
            		
            		//破壊できるかつ重複してない場合は走査リストへ追加する
            		boolean check = false;
            		for (BlockPos pos : searchList) {
            			if (pos.equals(element)) {
            				check = true;
            				break;
            			}
            		}
            		if (!check) {
            			searchList.add(element);
            		}
            	}
            	
            }
            posIndex++;
            
            if (searchList.size() > destoryLimitter) {
            	//リミッターを超えた場合は走査をストップ
            	break;
            }
    	}
    	
    	//走査後に対象を破壊する
    	//仕様上、破壊基準点も含まれるので先頭は無視
    	//DestoryAllでも含まれてるけど問題出てないからとりあえずはスルーで
    	for (BlockPos element : searchList) {
    		
    		//耐久値が0の場合は強制終了
    		/*
        	if(player.getHeldItem().getMaxDamage() - player.getHeldItem().getItemDamage() <= 0) {
           		break;
        	}
        	*/
        	//耐久値が0の判断を変更
       		if (player.getHeldItem().stackSize == 0) {
       			break;
       		}
        	
    		//破壊可否の判断
        	//適正ツールかどうかしか判断できない
        	//破壊の起点が適正かどうかだけみる？
        	//素手で破壊できるか・適正ツールか？
        	//適正ツールの判断はいらないかも
        	if (world.getBlockState(element).getBlock().getMaterial().isToolNotRequired() 
        			|| player.getHeldItem().canHarvestBlock(world.getBlockState(element).getBlock())) {
        		
        		//破壊できる場合は破壊する
            	world.destroyBlock(element, true);
            	//world.destroyBlock(new BlockPos(x, y-1 ,z), true);
            	
            	//ツールの耐久度を減らす
            	//手持ちの耐久を減らす(1)
            	player.getHeldItem().damageItem(1, player);
        		
        		
        	}
    		
    	}    	
    }
    
}
