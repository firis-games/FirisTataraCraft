package firis.tatara.event;

import java.util.Random;

import firis.tatara.TataraBlocks;
import firis.tatara.TataraCraft;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventOreGen {
	
	/**
	 * 鉱石生成時のイベント
	 */
    @SubscribeEvent
    public void OreGenEvent(OreGenEvent.Post event){
    
    	
    	
    	//とりあえずWikiをこぴぺ
    	
    	
    	//金鉱石のロジックをもってくる
    	//ChunkProviderSettingsに設定があるけどとりあえずそのままもってくる
    	int goldCount = 2;
    	int goldMinHeight = 0;
        int goldMaxHeight = 32;
        int goldSize = 9;
        
        /*
        public int ironSize = 9;
        public int ironCount = 20;
        public int ironMinHeight = 0;
        public int ironMaxHeight = 64;
        */
        
        //鉄とおなじ
        goldSize = 9;
        goldCount = 20;
        goldMinHeight = 0;
        goldMaxHeight = 64;
        
        //goldMaxHeight = 48;
        //goldCount = 3;

    	//this.goldGen = new WorldGenMinable(Blocks.gold_ore.getDefaultState(), this.chunkProviderSettings.goldSize);
    	WorldGenerator goldGen = new WorldGenMinable(TataraBlocks.SilverOre.getDefaultState(), goldSize);

    	
    	this.genStandardOre1(event, goldCount, goldGen, goldMinHeight, goldMaxHeight);

    }
    
    
    
    /**
     * package net.minecraft.world.biome.BiomeDecorator からもってきた
     * Standard ore generation helper. Generates most ores.
     */
    protected void genStandardOre1(OreGenEvent event, int blockCount, WorldGenerator generator, int minHeight, int maxHeight)
    {
        if (maxHeight < minHeight)
        {
            int i = minHeight;
            minHeight = maxHeight;
            maxHeight = i;
        }
        else if (maxHeight == minHeight)
        {
            if (minHeight < 255)
            {
                ++maxHeight;
            }
            else
            {
                --minHeight;
            }
        }

        BlockPos pos = event.pos;
        Random rnd = event.rand;
        World world = event.world;
        
        for (int j = 0; j < blockCount; ++j)
        {
            BlockPos blockpos = pos.add(rnd.nextInt(16), rnd.nextInt(maxHeight - minHeight) + minHeight, rnd.nextInt(16));
            generator.generate(world, rnd, blockpos);
            
        }
    }
	
    
}
