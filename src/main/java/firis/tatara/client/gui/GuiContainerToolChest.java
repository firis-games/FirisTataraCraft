package firis.tatara.client.gui;

import firis.tatara.common.ITataraItemInventory;
import firis.tatara.container.ContainerToolChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ツールチェストのクラス
 */
@SideOnly(Side.CLIENT)
public class GuiContainerToolChest extends GuiContainer {
	
	//GUIの表示名
	private String displayNameGui;
	
	//プレイヤーインベントリの表示名
	private String displayNamePlayerInventory;
	
	/**
	 * コンストラクタ
	 */
	public GuiContainerToolChest(InventoryPlayer playerInv, ITataraItemInventory tileEntityInv) {
		
		super(new ContainerToolChest(playerInv, tileEntityInv));
		
		//GUIの基準サイズを設定
		this.xSize = 176;
		this.ySize = 222;
		
		this.displayNameGui = StatCollector.translateToLocal("tile.ToolChest.name");
		
		this.displayNamePlayerInventory = playerInv.getDisplayName().getUnformattedText();;

	}
	
	
	/**
	 * GUIに文字の描画を行う
     * Draw the foreground layer for the GuiContainer (everything in front of the items). Args : mouseX, mouseY
     */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		

        
        //this.fontRendererObj.drawString(blockDispName, xSize / 2 - this.fontRendererObj.getStringWidth(blockDispName) / 2, 6, 4210752);
        
        
        //this.fontRendererObj.getStringWidth(blockDispName)
        //文字の長さを計算してくれる
        //xSize / 2 - this.fontRendererObj.getStringWidth(blockDispName)
        
        
        this.fontRendererObj.drawString(this.displayNameGui, 8, 6, 4210752);
        this.fontRendererObj.drawString(this.displayNamePlayerInventory, 8, 128, 4210752);
        
	}
	
	
	/**
	 * GUIに画像の描画を行う
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		ResourceLocation GuiTextures = new ResourceLocation("tataracraft", "textures/gui/toolchest.png");
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiTextures);
        
        int xSize = 176;
        int ySize = 222;
        
        int i = (this.width - xSize) / 2;
        int j = (this.height - ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, xSize, ySize);
		
	}
	
	
	/**
	 * GUIが開いている時にゲームの処理を止めるかどうかを設定
	 * とりあえずは必要ないかなぁ
	 * GUIの背景や、かまどの炎、矢印の処理を行う。 
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
		
	}

}
