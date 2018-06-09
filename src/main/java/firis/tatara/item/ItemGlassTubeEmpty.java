package firis.tatara.item;

import firis.tatara.TataraCraft;
import firis.tatara.TataraItems;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;


/**
 * ガラス管のクラス（空）
 */
public class ItemGlassTubeEmpty extends Item {
	
	/**
	 * メタデータの定義
	 */
	public static enum Metadata {
		EMPTY(0, ""),
		WATER(1, "water"),
		LAVA(2, "lava"),
		MILK(3, "milk");
		
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
	 * コンストラクタ
	 */
	public ItemGlassTubeEmpty() {
		
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
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, true);

        if (movingobjectposition == null)
        {
            return itemStackIn;
        }
        else
        {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                BlockPos blockpos = movingobjectposition.getBlockPos();

                if (!worldIn.isBlockModifiable(playerIn, blockpos))
                {
                    return itemStackIn;
                }

                if (!playerIn.canPlayerEdit(blockpos.offset(movingobjectposition.sideHit), movingobjectposition.sideHit, itemStackIn))
                {
                    return itemStackIn;
                }
                
                //ここから液体判定
                //液体判定を流体辞書経由に変更
                //--------------------------------------------------
                //ブロックの流体情報を取得
				Fluid blockFluid = FluidRegistry.lookupFluidForBlock(worldIn.getBlockState(blockpos).getBlock());
				
				//流体の水かつ水源かどうか
				//水チェック
				if (FluidRegistry.getFluid("water") == blockFluid
						&& worldIn.getBlockState(blockpos).getValue(BlockLiquid.LEVEL).intValue() == 0) {
					
					//ブロックに空気をセット
					worldIn.setBlockToAir(blockpos);
					
					Item fluidItem = TataraItems.GlassTubeWater;
					
					--itemStackIn.stackSize;
                    playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

                    if (itemStackIn.stackSize <= 0)
                    {
                        return new ItemStack(fluidItem);
                    }

                    if (!playerIn.inventory.addItemStackToInventory(new ItemStack(fluidItem)))
                    {
                        playerIn.dropPlayerItemWithRandomChoice(new ItemStack(fluidItem, 1, 0), false);
                    }
					
				}
				
				//溶岩チェック
				if (FluidRegistry.getFluid("lava") == blockFluid
						&& worldIn.getBlockState(blockpos).getValue(BlockLiquid.LEVEL).intValue() == 0) {
					
					//ブロックに空気をセット
					worldIn.setBlockToAir(blockpos);
					
					Item fluidItem = TataraItems.GlassTubeLava;
					
					--itemStackIn.stackSize;
                    playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

                    if (itemStackIn.stackSize <= 0)
                    {
                        return new ItemStack(fluidItem);
                    }

                    if (!playerIn.inventory.addItemStackToInventory(new ItemStack(fluidItem)))
                    {
                        playerIn.dropPlayerItemWithRandomChoice(new ItemStack(fluidItem, 1, 0), false);
                    }
					
				}
            }

            return itemStackIn;
        }
    }
	
}
