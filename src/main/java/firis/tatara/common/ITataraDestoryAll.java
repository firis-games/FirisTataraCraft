package firis.tatara.common;

import net.minecraft.item.ItemStack;

/**
 * 一括破壊用インターフェース
 */
public interface ITataraDestoryAll {

	public boolean isMineAll(ItemStack itemStack);
	
	public boolean isDigAll(ItemStack itemStack);
	
	public boolean isCutAll(ItemStack itemStack);
	
}
