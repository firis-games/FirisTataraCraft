package firis.tatara.item;

import firis.tatara.TataraCraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

/**
 * 石割りハンマークラス
 */
public class ItemStoneBreakHammer extends Item {
	
	/**
	 * コンストラクタ
	 */
	public ItemStoneBreakHammer() {
		
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
		this.setMaxDamage(405);//*耐久値の設定。デフォルト0*/
		/*.setFull3D()*//*３D表示で描画させる。ツールや骨、棒等。*/
		this.setContainerItem(this);//*クラフト時にアイテムを返却できるようにしている際の返却アイテムの指定。*/
		/*.setPotionEffect(PotionHelper.ghastTearEffect)*//*指定文字列に対応した素材として醸造台で使える。PotionHelper参照のこと。*/
		this.setNoRepair();//*修理レシピを削除し、金床での修繕を出来なくする*/
        this.setMaxStackSize(1);/*スタックできる量。デフォルト64*/ 
		
	}
	

	/**
	 * **************************************************
	 * クラフト関連の処理を追加 
	 * **************************************************
	 */
	private boolean repair = false;
	
    //修理以外ならクラフト後にgetContainerItemStackを呼び出す
    @Override
    public boolean hasContainerItem()
    {
        return !repair;
    }
    
    //修理以外ならクラフト後にgetContainerItemStackを呼び出す。1.7.10以降はこちらを推奨
    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return !repair;
    }
    
    //クラフト後のアイテムを、ダメージを与えて返す
    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        if (itemStack != null && itemStack.getItem() == this)
        {
            itemStack.setItemDamage(itemStack.getItemDamage() + 1);
        }
        return itemStack;
    }
    
    //修理関連を実装する場合は下記をみる
    /*
    //修理かどうかを判定する
  	@SubscribeEvent
  	public void onCrafting(PlayerEvent.ItemCraftedEvent event)
  	{
  		//IDが無くなったので、アイテムインスタンスで比較。
  		repair = StoneCutter.cutter == event.crafting.getItem();
  	}
  	*/
	//*****************************************************************************************************/
    
    
    /**
	 * 右クリックイベント
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer playerIn) {
		
		/*
		//実験用ロジック
		//テスト用
		if (playerIn.isSneaking()) {
			world.createExplosion(playerIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ(), 100.0F, false);
		} else {
			//if (!world.isRemote) {
				BlockPos pos = playerIn.getBedLocation();
				if (pos != null) {
					playerIn.setPosition(pos.getX(), pos.getY(), pos.getZ());
					playerIn.clientUpdateEntityNBT(null);
				}
				world.playSoundAtEntity(playerIn, "mob.endermen.portal", 1.0F, 0.1F);
				if(world.isRemote) {
					
					//クライアントだけ
					for (int i = 0; i < 10; ++i)
		            {
		                world.spawnParticle(EnumParticleTypes.PORTAL, playerIn.getPosition().getX(),playerIn.getPosition().getY(), playerIn.getPosition().getZ(), 1,1,1);
		            }
				}
				//world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "mob.endermen.portal", 1.0F, 1.0F);
				
			//}
		}
		*/
		
		
		return itemStack;
	}
}
