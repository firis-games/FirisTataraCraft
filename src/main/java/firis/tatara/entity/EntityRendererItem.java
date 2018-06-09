package firis.tatara.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
@SideOnly(Side.CLIENT)
public class EntityRendererItem<T extends Entity> extends Render<T> {

	protected final Item item;
	protected final RenderItem renderItem;
	
	/**
	 * コンストラクタ
	 * @param renderManager
	 * @param item
	 * @param renderItem
	 */
	public EntityRendererItem(RenderManager renderManager, Item item, RenderItem renderItem) {
		super(renderManager);
		
		this.item = item;
		this.renderItem = renderItem;
		
	}


    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
	@Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return TextureMap.locationBlocksTexture;
    }
	
	/**
	 * アイテムの描画
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     */
	@SuppressWarnings("unchecked")
	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        
        this.bindTexture(TextureMap.locationBlocksTexture);
        this.renderItem.renderItem(new ItemStack(this.item), ItemCameraTransforms.TransformType.GROUND);
        
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
	

}
