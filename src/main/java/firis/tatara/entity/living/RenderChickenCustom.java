package firis.tatara.entity.living;

import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class RenderChickenCustom extends RenderLiving<EntityChickenCustom>
{
    private static final ResourceLocation snowManTextures = new ResourceLocation("textures/entity/snowman.png");

    public RenderChickenCustom(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelSnowMan(), 0.5F);
        this.addLayer(new LayerSnowmanHeadCustom(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntitySnowman entity)
    {
        return snowManTextures;
    }

    public ModelSnowMan getMainModel()
    {
        return (ModelSnowMan)super.getMainModel();
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityChickenCustom entity) {
		// TODO 自動生成されたメソッド・スタブ
		return snowManTextures;
	}
}
