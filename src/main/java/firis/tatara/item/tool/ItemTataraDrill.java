package firis.tatara.item.tool;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import firis.tatara.TataraCraft;
import firis.tatara.common.ITataraDestoryAll;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ドリル用クラス
 */
/*
 * 新しいToolMaterialを追加するAPIs
 * 引数はMateria名(採掘レベル, 耐久度, 採掘速度, Entityに対するダメージ, エンチャント補正)
 * 以下はバニラのツールの数値
 *    WOOD(0,   59,  2.0F, 0.0F, 15) : 木ツール
 *   STONE(1,  131,  4.0F, 1.0F,  5) : 石ツール
 *    IRON(2,  250,  6.0F, 2.0F, 14) : 鉄ツール
 * EMERALD(3, 1561,  8.0F, 3.0F, 10) : ダイアツール
 *    GOLD(0,   32, 12.0F, 0.0F, 22) : 金ツール
 *
 *　別素材のアイテムを追加する場合は上を利用する
 */
public class ItemTataraDrill extends ItemTool implements ITataraDestoryAll {

	//2倍圧縮丸石
	//耐久が石ツールの6倍
	public static final Item.ToolMaterial STONE_2TIMES = EnumHelper.addToolMaterial("STONE_2TIMES",1, 786, 4.0F, 1.0F, 5);

	//3倍圧縮丸石
	//耐久が鉄ツールの8倍
	//攻撃能力がダイアツール
	public static final Item.ToolMaterial STONE_3TIMES = EnumHelper.addToolMaterial("STONE_3TIMES",1, 2000, 4.0F, 3.0F, 5);
	
	//鉄ブロック
	//耐久が鉄ツールの6倍
	public static final Item.ToolMaterial IRON_BLOCK = EnumHelper.addToolMaterial("IRON_BLOCK",2, 1500, 6.0F, 2.0F, 14);
	
	//ダイアブロック
	//耐久がダイヤツールの6倍
	public static final Item.ToolMaterial DIAMOND_BLOCK = EnumHelper.addToolMaterial("DIAMOND_BLOCK",3, 9366, 8.0F, 3.0F, 10);

	
	//ピッケル適正ブロック判定
	private static final Set<Block> PICKAXE_EFFECTIVE_ON = Sets.newHashSet(new Block[] {Blocks.activator_rail, Blocks.coal_ore, Blocks.cobblestone, Blocks.detector_rail, Blocks.diamond_block, Blocks.diamond_ore, Blocks.double_stone_slab, Blocks.golden_rail, Blocks.gold_block, Blocks.gold_ore, Blocks.ice, Blocks.iron_block, Blocks.iron_ore, Blocks.lapis_block, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.mossy_cobblestone, Blocks.netherrack, Blocks.packed_ice, Blocks.rail, Blocks.redstone_ore, Blocks.sandstone, Blocks.red_sandstone, Blocks.stone, Blocks.stone_slab});

	//斧適正ブロック判定
	private static final Set<Block> AXE_EFFECTIVE_ON = Sets.newHashSet(new Block[] {Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.melon_block, Blocks.ladder});
	
	//シャベル適正ブロック判定
	private static final Set<Block> SHOVEL_EFFECTIVE_ON = Sets.newHashSet(new Block[] {Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand});
	
	
	/**
	 * コンストラクタ
	 * @param material
	 */
	public ItemTataraDrill(Item.ToolMaterial material) {
		
		//コンストラクタ
		super(1.0F, material, new HashSet<Block>());    
		
		this.init();
		
	}
	
	
	protected void init() {

		//クリエイティブタブ
		this.setCreativeTab(TataraCraft.tabTatara);
		
		//システム名の登録はアイテム登録時に行うのでここでは記述しない
		
		//その他の設定項目
        /* this.setUnlocalizedName("Magnetite")/*システム名の登録*/
		/*.setHasSubtypes(true)*//*ダメージ値等で複数の種類のアイテムを分けているかどうか。デフォルトfalse*/
		/*.setMaxDamage(256)*//*耐久値の設定。デフォルト0*/
		this.setFull3D();//*３D表示で描画させる。ツールや骨、棒等。*/
		/*.setContainerItem(Items.stick)*//*クラフト時にアイテムを返却できるようにしている際の返却アイテムの指定。*/
		/*.setPotionEffect(PotionHelper.ghastTearEffect)*//*指定文字列に対応した素材として醸造台で使える。PotionHelper参照のこと。*/
		this.setNoRepair();//*修理レシピを削除し、金床での修繕を出来なくする*/
        //this.setMaxStackSize(1);/*スタックできる量。デフォルト64*/ 
	}
	
	//ここでこのツールがなんかを示す
	//適正ツールの判断とか採掘スピードとかはここをみてるよう
	@Override
	public Set<String> getToolClasses(ItemStack stack)
	{
		//return toolClass != null ? com.google.common.collect.ImmutableSet.of(toolClass) : super.getToolClasses(stack);
		Set<String> ret = new HashSet<String>();
		
		ret.add("pickaxe");
		ret.add("axe");
		ret.add("shovel");

		return ret;
	}
	
	/**
	 * ダメージ判定用
	 */
	@Override
	public float getStrVsBlock(ItemStack stack, Block block){
		
		float damage = 1.0F;
		
		//ItemToolの流用
		if(PICKAXE_EFFECTIVE_ON.contains(block) || AXE_EFFECTIVE_ON.contains(block) || SHOVEL_EFFECTIVE_ON.contains(block)){
			damage = this.efficiencyOnProperMaterial;
		}
		
		//ピッケルロジックの流用
		if (block.getMaterial() != Material.iron || block.getMaterial() == Material.anvil || block.getMaterial() == Material.rock) {
			damage = this.efficiencyOnProperMaterial;
		}
		return damage;
	}
	
	
	/**
     * Check whether this Item can harvest the given Block
     * ブロックの適正判定を行う
     */
	@Override
    public boolean canHarvestBlock(Block blockIn){
		
		//ピッケルロジックの流用
		if (blockIn == Blocks.obsidian) {
			return this.toolMaterial.getHarvestLevel() >= 3;
		}
		if (blockIn == Blocks.diamond_block || blockIn == Blocks.diamond_ore) {
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
		if (blockIn == Blocks.emerald_block || blockIn == Blocks.emerald_ore) {
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
		if (blockIn == Blocks.gold_block || blockIn == Blocks.gold_ore) {
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
		if (blockIn == Blocks.iron_block || blockIn == Blocks.iron_ore) {
			return this.toolMaterial.getHarvestLevel() >= 1;
		}
		if (blockIn == Blocks.lapis_block || blockIn == Blocks.lapis_ore) {
			return this.toolMaterial.getHarvestLevel() >= 1;
		}
		if (blockIn == Blocks.redstone_ore || blockIn == Blocks.lit_redstone_ore) {
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
		
		
		
		
		
		if (blockIn.getMaterial() == Material.rock) {
			return true;
		}
		/*
		if (blockIn.getMaterial() == Material.iron) {
			return true;
		}
		*/
		if (blockIn.getMaterial() == Material.iron) {
			return this.toolMaterial.getHarvestLevel() >= blockIn.getHarvestLevel(blockIn.getBlockState().getBaseState());
		}
		
		
		if (blockIn.getMaterial() == Material.anvil) {
			return this.toolMaterial.getHarvestLevel() >= 0;
		}
		
		//シャベルロジックの流用
		if(blockIn == Blocks.snow || blockIn == Blocks.snow_layer){
			return true;
		}
		
        return false;
    }
	
	
	/**
	 * NBTタグから一括破壊モードを取得する
	 * NBTはItemStackにリンクしている
	 */
	protected int getMode(ItemStack itemStack) {
		
		int mode = 0;
		
		NBTTagCompound nbt = itemStack.getTagCompound();
		if (nbt == null) {
			//NBTタグが存在していないため初期化してセットを行う
			nbt = new NBTTagCompound();
			nbt.setInteger("mode", mode);
			itemStack.setTagCompound(nbt);
		} 
		mode = nbt.getInteger("mode");
		
		return mode;
	}
	
	/**
	 * NBTタグへ一括破壊モードを保存する
	 */
	protected void setMode(ItemStack itemStack, int mode) {
		
		NBTTagCompound nbt = itemStack.getTagCompound();
		
		if (nbt == null) {
			//NBTタグが存在していないため初期化してセットを行う
			nbt = new NBTTagCompound();
		}
		nbt.setInteger("mode", mode);
		itemStack.setTagCompound(nbt);
	}
	
	/**
	 * 右クリックイベント
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer playerIn) {
		
		if(!world.isRemote){
			
			//スニーク状態の右クリックでモードの切替
			if(playerIn.isSneaking()){
				
				int mode = this.getMode(itemStack);
								
				//モードの切替
				mode++;
				
				/*
				 * 0:ノーマル
				 * 1:範囲破壊
				 * 2:範囲破壊(下方無破壊)
				 * 3:連鎖破壊
				 */
				if(mode > 3){
					mode = 0;
				}
				
				//NBTへ保存
				this.setMode(itemStack, mode);
				
				String mode_message = "";
				switch(mode) {
					case 0:
						mode_message = "Normal Mode";
						break;

					case 1:
						mode_message = "Area Destory Mode";
						break;
						
					case 2:
						mode_message = "Area Destory Mode [under nobreak]";
						break;
						
					case 3:
						mode_message = "Chain Destory Mode";
						break;
				}
				
				playerIn.addChatMessage(new ChatComponentTranslation(mode_message));
				
			} else {
				
			}
			
		}
		return itemStack;
	}
	
	/**
	 * ブロックに対して右クリックイベント
	 * Called when a Block is right-clicked with this Item 
	 */
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		boolean result = false;
		if(!worldIn.isRemote){
			
			//スニーク状態の右クリックでモードの切替
			if(playerIn.isSneaking()) {
				
			} else {
				//スニークの時は松明をおかない
				
				/**
				 * ブロックをただ設置すればいいだけの場合は
				 * BlockState blockState = Block.getDefaultState();
				 * worldIn.setBlockState(pos, blockState);
				 * でブロックの情報をセットしてやればいい
				 * セットする前にBlock.isAirやBlock.isReplaceableやworldIn.isSideSolid(pos, side)等で
				 * 実際に設置可能かを判断しないとブロックの有無にかかわらず強制設置する
				 * 
				 * 今回の松明みたいに壁に張り付くタイプはさらにUPの場合、各方角の場合で制御がかわったり
				 * 設置対象のブロックによって挙動がかなりかわっていたので単純な壁の貼り付けは問題なかったが
				 * 細かい挙動が異なるのでプログラムのトレースはあきらめて
				 * 実際にブロック設置の際に呼ばれているロジックをそのままこちらで呼び出して利用する
				 * 
				 */
				/**
				 * 処理の流れはItemStackから呼ばれているonItemUseの流れを参考にしている
				 * ItemStack -> ItemBlockの順番でonItemUseが呼ばれている
				 * 呼び出し元はonPlayerRightClick
				 * 
				 * 設置については全部ブロック側に任せているのでこちらでは制御してない
				 * 制御してるのはインベントリ内のアイテムの処理
				 * （クリエイティブならスタック数減らさないとかスタック数0の場合インベントリから削除するとか）
				 * 
				 */
				//プレイヤーインベントリ取得
				IInventory invPlayer = playerIn.inventory;
				int invSize = invPlayer.getSizeInventory();
				int checkIndex = -1;
				for (int i = 0; i < invSize; i++) {
					
					ItemStack invStack = invPlayer.getStackInSlot(i);
					
					//中身を判断
					//とりあえず松明かどうかを判断する
					if (invStack != null &&
							invStack.getItem() == Item.getItemFromBlock(Blocks.torch)) {
						//松明だったらOK
						checkIndex = i;
						break;
					}	
				}
				
				//走査結果に対象アイテムがある場合は設置処理を行う
				if (checkIndex > -1) {
					
					//インベントリ内の対象アイテムスタックを取得
					ItemStack setStack = invPlayer.getStackInSlot(checkIndex);
					
					//クリエイティブ判定の場合に復元に利用する
					int meta = setStack.getMetadata();
	                int size = setStack.stackSize;
	
	                //ItemStackのonUseItemを実行
					result = setStack.onItemUse(playerIn, worldIn, pos, side, hitX, hitY, hitZ);
					
					/**
					 * クリエイティブ判定を行う場合はMinecraft.getMinecraft().playerController.isInCreativeMode()を利用する
					 * worldオブジェクトのworldInfoの中にGameTypeが存在するがそちらはコマンドでゲームモードを切り替えても反映されてない
					 * おそらくゲーム作成時のモードを保存してる？
					 */
					//クリエイティブの場合はスタック数を減らさない
					//マルチだとおかしい
					//playerIn.capabilities.isCreativeModeに変更してみる
					//if (result == true && Minecraft.getMinecraft().playerController.isInCreativeMode()) {
					if (result == true && playerIn.capabilities.isCreativeMode) {
						//メタデータとスタック数を復元
						setStack.setItemDamage(meta);
						setStack.stackSize = size;
					}
					
					//インベントリのスタック数が0の場合はnullセットを行う
					if (setStack.stackSize <= 0) {
						//スロットの初期化
						//参考にしたところではForgeのイベントの呼び出しを行ってるが今回必要ないのでコメントアウト
						invPlayer.setInventorySlotContents(checkIndex, null);
						//playerIn.inventory.mainInventory[checkIndex] = null;
						//net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(thisPlayerMP, stack)
						
					}
					
					
					/**
					 * プレイヤーインベントリ操作してもクライアント側のGUIは更新されないので
					 * アイテムスタック数の表示がかわらない
					 * 
					 * 同期処理についてはContainerがもってるdetectAndSendChangesが中でICrafting使ってるので
					 * Container系がもってるdetectAndSendChangesを利用して同期を行う
					 */
					//同期処理
					playerIn.inventoryContainer.detectAndSendChanges();
				}
			}
		}
		
		return result;
    }
	
	/**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
	
    
	/**
	 * 一括破壊が利用可能か確認する
	 */
	@Override
	public boolean isMineAll(ItemStack itemStack) {
		
		return getMode(itemStack) == 1 ? true : false;
	}

	/**
	 * 一括破壊が利用可能か確認する
	 */
	@Override
	public boolean isDigAll(ItemStack itemStack) {

		return getMode(itemStack) == 2 ? true : false;
	}

	/**
	 * 一括破壊が利用可能か確認する
	 */
	@Override
	public boolean isCutAll(ItemStack itemStack) {

		return getMode(itemStack) == 3 ? true : false;
	}
	

}
