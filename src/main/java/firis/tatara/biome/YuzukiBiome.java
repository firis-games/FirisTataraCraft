package firis.tatara.biome;

import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeEndDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenSwamp;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class YuzukiBiome extends BiomeGenBase {

	/**
	 * 
	 * @param id
	 */
	public YuzukiBiome(int id) {
		this(id, false);
	}
	
	
	public YuzukiBiome(int id, boolean register) {
		
		
		super(id, true);
		
		
		//バイオームをいろいろやってみる
		
		this.minHeight = height_Default.rootHeight;
        this.maxHeight = height_Default.variation;
        this.minHeight = (float) 0.1;
        this.maxHeight = (float) 0.1;
        this.temperature = 1.0F;
        //this.rainfall = 0.5F;
        this.waterColorMultiplier = 16777215;
        this.spawnableMonsterList = Lists.<BiomeGenBase.SpawnListEntry>newArrayList();
        this.spawnableCreatureList = Lists.<BiomeGenBase.SpawnListEntry>newArrayList();
        this.spawnableWaterCreatureList = Lists.<BiomeGenBase.SpawnListEntry>newArrayList();
        this.spawnableCaveCreatureList = Lists.<BiomeGenBase.SpawnListEntry>newArrayList();
        this.enableRain = false;
        this.worldGeneratorTrees = new WorldGenTrees(false);
        this.worldGeneratorBigTree = new WorldGenBigTree(false);
        this.worldGeneratorSwamp = new WorldGenSwamp();
        
        
		//地表の設定
		this.topBlock = Blocks.nether_brick.getDefaultState();
		//地中
		this.fillerBlock = Blocks.soul_sand.getDefaultState();
		
		
		this.theBiomeDecorator = new YuzukiBiomeDecorator();
		
		
		
	}
	
	
	
	
	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int p_180622_4_, int p_180622_5_, double p_180622_6_)
    {
		
        //this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, p_180622_4_, p_180622_5_, p_180622_6_);
		int i = worldIn.getSeaLevel();
        IBlockState iblockstate = this.topBlock;
        IBlockState iblockstate1 = this.fillerBlock;
        int j = -1;
        //int k = (int)(p_180622_6_ / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int k = (int)(p_180622_6_ / 3.0D + 3.0D + 1 * 0.25D);
        int l = p_180622_4_ & 15;
        int i1 = p_180622_5_ & 15;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int j1 = 255; j1 >= 0; --j1)
        {
        	//if (j1 <= rand.nextInt(5))
        	/*
        	if (120 < j1 && j1 <150) {
        		chunkPrimerIn.setBlockState(i1, j1, l, Blocks.diamond_ore.getDefaultState());
        		continue;
        	}
        	*/
        	if (64 < j1) {
        		chunkPrimerIn.setBlockState(i1, j1, l, Blocks.air.getDefaultState());
        		continue;        		
        	}
        	//ここの判断がうまくいかない
        	/*
        	if(chunkPrimerIn.getBlockState(i1, j1, l).getBlock() == Blocks.stone 
        			&& Blocks.stone.getMetaFromState(chunkPrimerIn.getBlockState(i1, j1, l)) == 0) {
        		chunkPrimerIn.setBlockState(i1, j1, l, Blocks.netherrack.getDefaultState());
        	}
        	*/
        	
        	//if (j1 <= rand.nextInt(5))
       		if (j1 < 1)
            {
            	//@here
        		//岩盤の判定っぽい
                chunkPrimerIn.setBlockState(i1, j1, l, Blocks.bedrock.getDefaultState());
            }
            else
            {
                IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);
                
                //@here
                if (iblockstate2.getBlock() == Blocks.gold_block) {
                	chunkPrimerIn.setBlockState(i1, j1, l, Blocks.iron_block.getDefaultState());
                }

                if (iblockstate2.getBlock().getMaterial() == Material.air)
                {
                    j = -1;
                }
                else if (iblockstate2.getBlock() == Blocks.stone)
                {
                	//強制変換
                	//if (((BlockStone)iblockstate2.getBlock()).VARIANT.getName() == BlockStone.EnumType.STONE.getName()) {
                	
                	
                    if (j == -1)
                    {
                        if (k <= 0)
                        {
                            iblockstate = null;
                            //@here 変更
                            iblockstate1 = Blocks.emerald_block.getDefaultState();
                            
                            
                        }
                        else if (j1 >= i - 4 && j1 <= i + 1)
                        {
                            iblockstate = this.topBlock;
                            iblockstate1 = this.fillerBlock;
                        }

                        if (j1 < i && (iblockstate == null || iblockstate.getBlock().getMaterial() == Material.air))
                        {
                            if (this.getFloatTemperature(blockpos$mutableblockpos.set(p_180622_4_, j1, p_180622_5_)) < 0.15F)
                            {
                                iblockstate = Blocks.ice.getDefaultState();
                            }
                            else
                            {
                                iblockstate = Blocks.water.getDefaultState();
                            }
                        }

                        j = k;

                        if (j1 >= i - 1)
                        {
                            chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
                        }
                        else if (j1 < i - 7 - k)
                        {
                            iblockstate = null;
                            //@here
                            iblockstate1 = Blocks.end_stone.getDefaultState();
                            chunkPrimerIn.setBlockState(i1, j1, l, Blocks.end_stone.getDefaultState());
                        }
                        else
                        {
                            chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
                        }
                    }
                    else if (j > 0)
                    {
                        --j;
                        chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);

                        if (j == 0 && iblockstate1.getBlock() == Blocks.sand)
                        {
                            j = rand.nextInt(4) + Math.max(0, j1 - 63);
                            iblockstate1 = iblockstate1.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND ? Blocks.red_sandstone.getDefaultState() : Blocks.sandstone.getDefaultState();
                        }
                    }
                }
            }
        }
        

    }
	
	@Override
	public BiomeGenBase.TempCategory getTempCategory()
    {
        return (double)this.temperature < 0.2D ? BiomeGenBase.TempCategory.COLD : ((double)this.temperature < 1.0D ? BiomeGenBase.TempCategory.MEDIUM : BiomeGenBase.TempCategory.WARM);
    }
	
	
	
	
	

}
