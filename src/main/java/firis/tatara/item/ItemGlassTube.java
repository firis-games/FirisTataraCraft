package firis.tatara.item;

import firis.tatara.TataraCraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;


/**
 * ガラス管のクラス（空）
 */
public class ItemGlassTube extends Item {
	
	/**
	 * メタデータの定義
	 */
	public static enum Metadata {
		WATER(1, "water"),
		LAVA(2, "lava");
		
		private int metadata;
		private String name;
		
		private Metadata(int metadata, String name) {
			this.metadata = metadata;
			this.name = name;
		}
		public int getMetadata() {
			return this.metadata;
		}
		public String getName() {
			return this.name;
		}
	}
	
	/**
	 * 初期化時に保存する
	 */
	private ItemGlassTube.Metadata metadata;
	
	/**
	 * コンストラクタ
	 */
	public ItemGlassTube(ItemGlassTube.Metadata metadata) {
		
		this.metadata = metadata;
		
		//初期化
		this.init();
	}
	
	/**
	 * たたらクラフトベーシックアイテムの初期化
	 */
	protected void init() {

		//クリエイティブタブ
		this.setCreativeTab(TataraCraft.tabTatara);
		
		//システム名の登録はアイテム登録時に行うのでここでは記述しない
		
		//その他の設定項目
        /* this.setUnlocalizedName("Magnetite")/*システム名の登録*/
		/*.setHasSubtypes(true)*//*ダメージ値等で複数の種類のアイテムを分けているかどうか。デフォルトfalse*/
		/*.setMaxDamage(256)*//*耐久値の設定。デフォルト0*/
		/*.setFull3D()*//*３D表示で描画させる。ツールや骨、棒等。*/
		/*.setContainerItem(Items.stick)*//*クラフト時にアイテムを返却できるようにしている際の返却アイテムの指定。*/
		/*.setPotionEffect(PotionHelper.ghastTearEffect)*//*指定文字列に対応した素材として醸造台で使える。PotionHelper参照のこと。*/
		/*.setNoRepair()*//*修理レシピを削除し、金床での修繕を出来なくする*/
        this.setMaxStackSize(64);/*スタックできる量。デフォルト64*/ 
		
	}
	
    /**
     * アイテムを右クリックした際に処理を行う
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
    	MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, false);
    	if (movingobjectposition == null) {
    		return itemStackIn;
    	}
    	BlockPos blockpos = movingobjectposition.getBlockPos();
    	BlockPos blockpos1 = blockpos.offset(movingobjectposition.sideHit);

    	if (!playerIn.canPlayerEdit(blockpos1, movingobjectposition.sideHit, itemStackIn))
        {
            return itemStackIn;
        }

        if (this.tryPlaceContainedLiquid(worldIn, blockpos1) && !playerIn.capabilities.isCreativeMode)
        {
        	//設置後の処理
        	itemStackIn.stackSize--;
        	playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        	
        }
        playerIn.inventoryContainer.detectAndSendChanges();
        return itemStackIn;
    }
    
    /**
     * バケツの処理を改変
     */
    public boolean tryPlaceContainedLiquid(World worldIn, BlockPos pos)
    {
    	Material material = worldIn.getBlockState(pos).getBlock().getMaterial();
        boolean flag = !material.isSolid();

        if (!worldIn.isAirBlock(pos) && !flag)
        {
            return false;
        }
        else
        {
        	//流体ブロックを取得する方法確認
        	
        	//worldIn.provider.doesWaterVaporize();
        	
        	if (!worldIn.isRemote && flag && !material.isLiquid())
            {
                worldIn.destroyBlock(pos, true);
            }
        	
        	Fluid fluid = FluidRegistry.getFluid(this.metadata.getName());
        	Block fluidBlock = fluid.getBlock();
        	if (this.metadata == Metadata.WATER) {
        		fluidBlock = Blocks.flowing_water;
        	} else if(this.metadata == Metadata.LAVA){
        		fluidBlock = Blocks.flowing_lava;
        	}
        	worldIn.setBlockState(pos, fluidBlock.getDefaultState(), 3);
        	
            worldIn.markBlockForUpdate(pos);
            
            return true;
        }
    }
}
