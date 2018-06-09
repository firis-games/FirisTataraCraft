package firis.tatara.biome;

import java.util.Random;

import net.minecraft.block.BlockStone;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import net.minecraft.world.gen.feature.WorldGenerator;

public class YuzukiBiomeDecorator extends BiomeDecorator {
	
	protected WorldGenerator spikeGen = new WorldGenSpikes(Blocks.redstone_block);

	@Override
    protected void genDecorations(BiomeGenBase biomeGenBaseIn)
    {
        this.generateOres();

        if (this.randomGenerator.nextInt(5) == 0)
        {
            int i = this.randomGenerator.nextInt(16) + 8;
            int j = this.randomGenerator.nextInt(16) + 8;
            this.spikeGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(i, 0, j)));
        }

        if (this.field_180294_c.getX() == 0 && this.field_180294_c.getZ() == 0)
        {
            EntityDragon entitydragon = new EntityDragon(this.currentWorld);
            entitydragon.setLocationAndAngles(0.0D, 128.0D, 0.0D, this.randomGenerator.nextFloat() * 360.0F, 0.0F);
            this.currentWorld.spawnEntityInWorld(entitydragon);
        }
    }
    
    
    @Override
    public void decorate(World worldIn, Random random, BiomeGenBase p_180292_3_, BlockPos p_180292_4_)
    {
		if (this.currentWorld != null)
		{
			throw new RuntimeException("Already decorating");
		}
		else
		{
			this.currentWorld = worldIn;
			String s = worldIn.getWorldInfo().getGeneratorOptions();
		
			if (s != null)
			{
			    this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory(s).func_177864_b();
			}
			else
			{
			    this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory("").func_177864_b();
			}
		
			this.randomGenerator = random;
			this.field_180294_c = p_180292_4_;
			this.dirtGen = new WorldGenMinable(Blocks.gold_block.getDefaultState(), this.chunkProviderSettings.dirtSize);
			this.gravelGen = new WorldGenMinable(Blocks.gold_block.getDefaultState(), this.chunkProviderSettings.gravelSize);
			this.graniteGen = new WorldGenMinable(Blocks.gold_block.getDefaultState(), this.chunkProviderSettings.graniteSize);
			this.dioriteGen = new WorldGenMinable(Blocks.gold_block.getDefaultState(), this.chunkProviderSettings.dioriteSize);
			this.andesiteGen = new WorldGenMinable(Blocks.gold_block.getDefaultState(), this.chunkProviderSettings.andesiteSize);
			this.coalGen = new WorldGenMinable(Blocks.coal_ore.getDefaultState(), this.chunkProviderSettings.coalSize);
			this.ironGen = new WorldGenMinable(Blocks.iron_ore.getDefaultState(), this.chunkProviderSettings.ironSize);
			this.goldGen = new WorldGenMinable(Blocks.gold_ore.getDefaultState(), this.chunkProviderSettings.goldSize);
			this.redstoneGen = new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), this.chunkProviderSettings.redstoneSize);
			this.diamondGen = new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), this.chunkProviderSettings.diamondSize);
			this.lapisGen = new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), this.chunkProviderSettings.lapisSize);
			this.genDecorations(p_180292_3_);
			this.currentWorld = null;
			this.randomGenerator = null;
		}
    }
    
    
}
