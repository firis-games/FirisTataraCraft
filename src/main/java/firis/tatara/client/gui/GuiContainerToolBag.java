package firis.tatara.client.gui;

import firis.tatara.common.ITataraItemInventory;
import firis.tatara.container.ContainerToolBag;
import firis.tatara.inventory.InventoryToolBag;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ツールバッグのGUIクラス
 */
@SideOnly(Side.CLIENT)
public class GuiContainerToolBag extends GuiContainer {
	
	//GUIの表示名
	private String displayNameGui;
	
	//プレイヤーインベントリの表示名
	private String displayNamePlayerInventory;
	
	//
	InventoryToolBag inventoryToolBag;
	
	/**
	 * コンストラクタ
	 */
	public GuiContainerToolBag(InventoryPlayer playerInv, ITataraItemInventory tileEntityInv) {
		
		super(new ContainerToolBag(playerInv, tileEntityInv));
		
		this.inventoryToolBag = (InventoryToolBag) tileEntityInv;
		
		//GUIの基準サイズを設定
		this.xSize = 175;
		this.ySize = 158;
		
		this.displayNameGui = StatCollector.translateToLocal("item.ToolBag.name");
		
		this.displayNamePlayerInventory = playerInv.getDisplayName().getUnformattedText();;

	}
	
	
	/**
	 * GUIに文字の描画を行う
     * Draw the foreground layer for the GuiContainer (everything in front of the items). Args : mouseX, mouseY
     */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		String ItemCount = "";
		
		ItemCount = this.inventoryToolBag.itemStackSize.toString();
		
		this.fontRendererObj.drawString(this.displayNameGui, 8, 6, 4210752);
        this.fontRendererObj.drawString(this.displayNamePlayerInventory, 8, 64, 4210752);
        
        this.fontRendererObj.drawString(ItemCount, 10, 36, 4210752);
        
	}
	
	
	/**
	 * GUIに画像の描画を行う
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		ResourceLocation GuiTextures = new ResourceLocation("tataracraft", "textures/gui/toolbag.png");
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiTextures);
        
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		
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
