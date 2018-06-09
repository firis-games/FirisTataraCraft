package firis.tatara.gui;

import firis.tatara.TataraCraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class TataraGuiHandler implements IGuiHandler {

	/**
	 * サーバ側の処理
	 */
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		//return TataraGui.getServerGuiElement(ID, player, world, x, y, z);
		return TataraCraft.proxy.getServerGuiElement(ID, player, world, x, y, z);
		
	}

	/**
	 * クライアント側の処理
	 */
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		//return TataraGui.getClientGuiElement(ID, player, world, x, y, z);
		return TataraCraft.proxy.getClientGuiElement(ID, player, world, x, y, z);
		
	}

}
