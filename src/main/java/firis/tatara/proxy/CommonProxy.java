package firis.tatara.proxy;

import firis.tatara.gui.TataraGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy {
	
	/**
	 * Itemのテクスチャ設定を行う
	 */
	public void registerItemRenderers() {
		
	}
	
	/**
	 * Blockのテクスチャ設定を行う
	 */
	public void registerBlockRenderers() {
		
	}
	
	
	/**
	 * サーバ側のGUIを取得する
	 */
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return TataraGui.getServerGuiElement(ID, player, world, x, y, z);
	}
	
	
	/**
	 * クライアント側のGUIを取得する
	 */
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
}
