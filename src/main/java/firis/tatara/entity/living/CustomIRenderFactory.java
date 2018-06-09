package firis.tatara.entity.living;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class CustomIRenderFactory implements IRenderFactory {

	@Override
	public Render<?> createRenderFor(RenderManager manager) {
		// TODO 自動生成されたメソッド・スタブ
		return new RenderChickenCustom(manager);
	}
}
