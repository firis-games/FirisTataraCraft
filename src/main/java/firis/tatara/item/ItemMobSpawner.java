package firis.tatara.item;

import java.util.List;

import firis.tatara.TataraCraft;
import firis.tatara.common.ITataraItemInventory;
import firis.tatara.common.TataraItemBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMobSpawner extends TataraItemBlock {

	/**
	 * コンストラクタ
	 */
	public ItemMobSpawner() {
		
		super(Blocks.mob_spawner);
		
		//初期化
		this.init();
	}
	
	/**
	 * モブスポナーアイテムの初期化
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
        this.setMaxStackSize(1);/*スタックできる量。デフォルト64**/ 
		
	}
	
	
	
	//** ItemBlockで上書きされたのを元に戻す */
    /**
     * gets the CreativeTab this item is displayed on
     */
	/*
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab()
    {
        return TataraCraft.tabTatara;
    }
    */
    
    
    /**
	 * インフォに情報をだす
	 */
	/**
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if (!stack.hasTagCompound()) {
        	tooltip.add(StatCollector.translateToLocal("entity." + "Pig" + ".name"));
        	return;
        }
		NBTTagCompound compound = stack.getTagCompound();
		
		//NBTからクラス情報へ読み込みを行う
		String mobId = compound.getString("mobId");
		//とりあえずmobIDが正しいかどうかチェック
		if(EntityList.isStringValidEntityName(mobId)){
			
			//正常
			//Class <? extends Entity > oclass = EntityList.stringToClassMapping.get(mobId);

			tooltip.add(StatCollector.translateToLocal("entity." + mobId + ".name"));

		}
		
		
		
		
		
		
		//ItemStack item = new ItemStack(Item.getByNameOrId(itemId), 1, itemMetadata);
		//tooltip.add(item.getDisplayName() + ":" + itemStackSize.toString());
		
    }
    
    
    
    
    /**
     * 実際にブロックが設置された後に呼び出される処理
     * Called to actually place the block, after the location is determined
     * and all permission checks have been made.
     *
     * @param stack The item stack that was used to place the block. This can be changed inside the method.
     * @param player The player who is placing the block. Can be null if the block is not being placed by a player.
     * @param side The side the player (or machine) right-clicked on.
     */
	@Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
		boolean result = false;
		
		//親クラスを呼び出す
		result = super.placeBlockAt(stack, player, world, pos, side, hitZ, hitZ, hitZ, newState);
        
		TileEntity tileentity = world.getTileEntity(pos);
		if (tileentity instanceof TileEntityMobSpawner)
        {
			/*
			 * addDungeonMob("Skeleton", 100);
        addDungeonMob("Zombie",   200);
        addDungeonMob("Spider",   100);
			 */
	        if (!stack.hasTagCompound()) {
	        	return result;
	        }
			NBTTagCompound compound = stack.getTagCompound();
			
			//NBTからクラス情報へ読み込みを行う
			String mobId = compound.getString("mobId");
			
			String mobName = mobId;
			//String mobName = "CaveSpider";
            ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName(mobName);
            
            //TataraCraft.logger.info(((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().getEntityNameToSpawn());
        }
		
		
		/*
		//NBTが存在する場合
		//ItemStackからTileEntityへの書き込みを行う
		if (result && stack.hasTagCompound()) {
			
			//TileEntityを取得
			TileEntity blockTileEntity = world.getTileEntity(pos);
			if (blockTileEntity == null
					|| !(blockTileEntity instanceof ITataraItemInventory)) {
				return result;
			}
			ITataraItemInventory itiiBlockTileEntity = (ITataraItemInventory)blockTileEntity;
			
			//ItemStackのNBTをITataraItemInventoryへ書き込み
			itiiBlockTileEntity.writeItemInventoryFromNBT(stack.getTagCompound());
			
		}
		*/
		
        return result;
    }
	
	
	
}
