package firis.tatara.proxy;

import firis.tatara.gui.TataraGui;
import firis.tatara.renderer.BlockRenderer;
import firis.tatara.renderer.ItemRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	
	/**
	 * Itemのテクスチャ設定を行う
	 */
	@Override
	public void registerItemRenderers() {
		
		ItemRenderer.registerItemRenderers();
		
	}
	
	
	/**
	 * Blockのテクスチャ設定を行う
	 */
	@Override
	public void registerBlockRenderers() {
		
		BlockRenderer.registerBlockRenderers();
		
	}
	
	/**
	 * サーバ側のGUIを取得する
	 */
	/*
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	*/
	
	
	/**
	 * クライアント側のGUIを取得する
	 */
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return TataraGui.getClientGuiElement(ID, player, world, x, y, z);
	}
	
}
