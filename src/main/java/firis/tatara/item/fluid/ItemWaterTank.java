package firis.tatara.item.fluid;

import firis.tatara.TataraCraft;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

public class ItemWaterTank extends Item implements IFluidContainerItem {

	private int capacity = 64000;
	
	/**
	 * コンストラクタ
	 */
	public ItemWaterTank() {
		
		//初期化
		this.init();
	}
	
	/**
	 * アイテムの初期化
	 */
	protected void init() {

		//クリエイティブタブ
		this.setCreativeTab(TataraCraft.tabTatara);
		
		//システム名の登録はアイテム登録時に行うのでここでは記述しない
		
		//その他の設定項目
        /* this.setUnlocalizedName("Magnetite")/*システム名の登録*/
		/*.setHasSubtypes(true)*//*ダメージ値等で複数の種類のアイテムを分けているかどうか。デフォルトfalse*/
		this.setMaxDamage(64);//*耐久値の設定。デフォルト0*/
		/*.setFull3D()*//*３D表示で描画させる。ツールや骨、棒等。*/
		/*.setContainerItem(Items.stick)*//*クラフト時にアイテムを返却できるようにしている際の返却アイテムの指定。*/
		/*.setPotionEffect(PotionHelper.ghastTearEffect)*//*指定文字列に対応した素材として醸造台で使える。PotionHelper参照のこと。*/
		/*.setNoRepair()*//*修理レシピを削除し、金床での修繕を出来なくする*/
        this.setMaxStackSize(1); /*スタックできる量。デフォルト64*/
        
	}

    /* IFluidContainerItem */
    @Override
    public FluidStack getFluid(ItemStack container)
    {
        int metadata = this.getMetadata(container);
        
        if (metadata == 0){
        	return null;
        } else {
        	return new FluidStack(FluidRegistry.getFluid("water"), FluidContainerRegistry.BUCKET_VOLUME * metadata);
        }
        
    }

    @Override
    public int getCapacity(ItemStack container)
    {
        return capacity;
    }

    /**
     * 液体補充処理
     */
    @Override
    public int fill(ItemStack container, FluidStack resource, boolean doFill)
    {
        if (resource == null)
        {
            return 0;
        }
        
        //液体補充処理
        FluidStack tankStack = this.getFluid(container);
        //タンクが空
    	if (tankStack == null) {
    		tankStack = new FluidStack(FluidRegistry.getFluid("water"), 0); 
        }
        
        //チェック処理
        if (!doFill) {
        	//液体が水かどうか
            if (!tankStack.isFluidEqual(resource)) {
            	return 0;
            }
            //容量がいっぱいでないか
            if (tankStack.amount >= this.capacity) {
            	return 0;
            }
            
            //補充分の液体が入るかどうか
            return Math.min(capacity - tankStack.amount, resource.amount);
        }

        //**************************************************
        
        //タンクが空
        //if (tankStack == null) {
        //	return 0;
        //}
        //液体が水かどうか
        if (!tankStack.isFluidEqual(resource)) {
        	return 0;
        }
        //容量がいっぱいでないか
        if (tankStack.amount >= this.capacity) {
        	return 0;
        }
        
        //
        if (this.capacity < resource.amount) {
        	//最大値まで格納
            this.setDamage(container, 64);
            return capacity;
        }

        //処理を行う
        int filled = this.capacity - tankStack.amount;
        if (resource.amount < filled) {
        	//通常処理
        	int meta = (tankStack.amount + resource.amount) / 1000;
        	this.setDamage(container, meta);
        	
            filled = resource.amount;
        } else {
        	this.setDamage(container, 64);
        }
        
        return filled;
        
    }

    @Override
    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain)
    {
        if (!container.hasTagCompound() || !container.getTagCompound().hasKey("Fluid"))
        {
            return null;
        }

        FluidStack stack = FluidStack.loadFluidStackFromNBT(container.getTagCompound().getCompoundTag("Fluid"));
        if (stack == null)
        {
            return null;
        }

        int currentAmount = stack.amount;
        stack.amount = Math.min(stack.amount, maxDrain);
        if (doDrain)
        {
            if (currentAmount == stack.amount)
            {
                container.getTagCompound().removeTag("Fluid");

                if (container.getTagCompound().hasNoTags())
                {
                    container.setTagCompound(null);
                }
                return stack;
            }

            NBTTagCompound fluidTag = container.getTagCompound().getCompoundTag("Fluid");
            fluidTag.setInteger("Amount", currentAmount - stack.amount);
            container.getTagCompound().setTag("Fluid", fluidTag);
        }
        return stack;
    }
	
	//****************************************************************************************************
	
	/**
	 * 右クリックイベント
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World worldIn, EntityPlayer playerIn) {
		
		if(!worldIn.isRemote){
			
			//スニーク状態の右クリックでモードの切替
			if(!playerIn.isSneaking()){
				
				//水を汲む
				
				//ターゲットしているブロックの情報を取得
				//第3引数で流体を候補が有効化無効化を設定
				//（動きからの推測）
				MovingObjectPosition target = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
				if (target == null 
						|| target.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
					//ターゲットが無効
					return itemStack;
				}
				
				BlockPos blockPos = target.getBlockPos();
				
				if (!worldIn.isBlockModifiable(playerIn, blockPos)
						|| !playerIn.canPlayerEdit(blockPos.offset(target.sideHit), target.sideHit, itemStack)) {
					//プレイヤーが操作できない
					return itemStack;
				}
				
				
				//ブロックの種類を判定
				//**********************************************************************
				IBlockState blockState = worldIn.getBlockState(blockPos);
				
				//ブロックの流体情報を取得
				Fluid blockFluid = FluidRegistry.lookupFluidForBlock(blockState.getBlock());
				
				//流体の水かつ水源かどうか
				if (FluidRegistry.getFluid("water") == blockFluid
						&& blockState.getValue(BlockLiquid.LEVEL).intValue() == 0) {
					
					//水をバケツに補充
					FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid("water"), FluidContainerRegistry.BUCKET_VOLUME);
					
					//水を補充できるかをチェック
					if (this.fill(itemStack, fluidStack, false) == fluidStack.amount) {

						//水を補充
						this.fill(itemStack, fluidStack, true);
						
						//空気をセット
						worldIn.setBlockToAir(blockPos);
						
						//バケツでやっている処理そのまま
						//playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
					}
				}

				//水を汲む
				playerIn.addChatMessage(new ChatComponentTranslation("テスト3"));
					
			} else {
				//playerIn.addChatMessage(new ChatComponentTranslation("テスト2"));
				
			}
			
		}
		return itemStack;
	}
	
	
	
	
	@Override
	public int getMetadata(int damage)
    {
        return damage;
    }
	
	
	/*
	 * アイテムのゲージの現在の状態を返却する
	 */
	/**
     * Queries the percentage of the 'Durability' bar that should be drawn.
     *
     * @param stack The current ItemStack
     * @return 1.0 for 100% 0 for 0%
     */
	@Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return (double)1 - ((double)(stack.getItemDamage()) / (double)stack.getMaxDamage());
    }
	
	
	
	/**
     * Determines if the durability bar should be rendered for this item.
     * Defaults to vanilla stack.isDamaged behavior.
     * But modders can use this for any data they wish.
     *
     * @param stack The current Item Stack
     * @return True if it should render the 'durability' bar.
     */
	@Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return stack.isItemDamaged();
    }

}
