package firis.tatara.item.fluid;

import firis.tatara.TataraCraft;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ItemFluidContainer;

public class ItemBucketTank extends ItemFluidContainer {

	/**
	 * 流体の最大容量
	 */
	protected static int fluidCapacity = 64000;
	
	/**
	 * コンストラクタ
	 */
	public ItemBucketTank() {
		
		//コンストラクタ
		super(0, fluidCapacity);
		
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
		this.setHasSubtypes(true);//*ダメージ値等で複数の種類のアイテムを分けているかどうか。デフォルトfalse*/
		/*.setMaxDamage(256)*//*耐久値の設定。デフォルト0*/
		/*.setFull3D()*//*３D表示で描画させる。ツールや骨、棒等。*/
		/*.setContainerItem(Items.stick)*//*クラフト時にアイテムを返却できるようにしている際の返却アイテムの指定。*/
		/*.setPotionEffect(PotionHelper.ghastTearEffect)*//*指定文字列に対応した素材として醸造台で使える。PotionHelper参照のこと。*/
		/*.setNoRepair()*//*修理レシピを削除し、金床での修繕を出来なくする*/
        this.setMaxStackSize(1);/*スタックできる量。デフォルト64*/ 
		
	}
	
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
	
}
