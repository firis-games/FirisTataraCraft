package firis.tatara.item;

import java.util.List;

import firis.tatara.TataraCraft;
import firis.tatara.entity.EntityTorchBall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTorchBall extends Item {
	
	/**
	 * コンストラクタ
	 */
	public ItemTorchBall() {
		
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
		this.setMaxDamage(512); //*耐久値の設定。デフォルト0*/
		/*.setFull3D()*//*３D表示で描画させる。ツールや骨、棒等。*/
		/*.setContainerItem(Items.stick)*//*クラフト時にアイテムを返却できるようにしている際の返却アイテムの指定。*/
		/*.setPotionEffect(PotionHelper.ghastTearEffect)*//*指定文字列に対応した素材として醸造台で使える。PotionHelper参照のこと。*/
		this.setNoRepair();//*修理レシピを削除し、金床での修繕を出来なくする*/
        this.setMaxStackSize(1);/*スタックできる量。デフォルト64*/ 
		
	}
	
	/**
	 * エンチャントエフェクトの有無を判断する
	 */
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
		return true;
    }
	
    /**
     * allows items to add custom lines of information to the mouseover description
     */
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
		Integer cnt = stack.getMaxDamage() - stack.getItemDamage();
		
		tooltip.add(Blocks.torch.getLocalizedName() + ":" + cnt.toString());
    }
	
	
	/*
	 * 右クリック時に動く
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer playerIn) {
		
		//耐久度を減らす
		if (stack.getItemDamage() < stack.getMaxDamage()) {
			stack.damageItem(1, playerIn);
		} else {
			//耐久度0の場合は何もしない
			return stack;
		}
		
		//サーバのみ・Entityを生成
		if (!world.isRemote)
        {
			EntityTorchBall entity = new EntityTorchBall(world, playerIn);
			world.spawnEntityInWorld(entity);
        }
		
        return stack;
	}
	
}
