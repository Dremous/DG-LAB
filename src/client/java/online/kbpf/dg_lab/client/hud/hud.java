package online.kbpf.dg_lab.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.DeltaTracker;
import net.minecraft.network.chat.Component;

import static online.kbpf.dg_lab.client.Dg_labClient.*;

public class hud implements HudElement {

    //屏幕强度显示
    public void render(GuiGraphicsExtractor drawContext, DeltaTracker tickDelta) {
        Minecraft client = Minecraft.getInstance();
        
        // TODO: Fix for 26.1 - client.player, client.world, getScaledWidth, textRenderer have changed
        // The HudElement API might also have changed - need to implement extractRenderState instead
        
        /* 原代码暂时注释
        if (client.player != null && client.world != null && (modConfig.getRenderingPositionX() < client.getWindow().getScaledWidth() || modConfig.getRenderingPositionY() < client.getWindow().getScaledHeight())) {
            int x = modConfig.getRenderingPositionX();
            int y = modConfig.getRenderingPositionY();

            Component strengthText;
            Component strengthText1;
            String A = "A", B = "B";
            if(twoPlayerMode){
                A = Minecraft.getInstance().getSession().getUsername() + ":";
                B = secondPlayer + ":";
            }
            else {
                A = "A:";
                B = "B:";
            }
            if(modConfig.isRenderingMax()) {
                strengthText = Component.literal(A + webSocketServer.getStrength().getAStrength() + ",Max:" + webSocketServer.getStrength().getAMaxStrength());
                strengthText1 = Component.literal(B + webSocketServer.getStrength().getBStrength() + ",Max:" + webSocketServer.getStrength().getBMaxStrength());
            }
            else {
                strengthText = Component.literal(A + webSocketServer.getStrength().getAStrength());
                strengthText1 = Component.literal(B + webSocketServer.getStrength().getBStrength());
            }
            drawContext.drawStringWithShadow(client.textRenderer, strengthText, x, y, 0xFFFFFFFF);
            drawContext.drawStringWithShadow(client.textRenderer, strengthText1, x, y + 9, 0xFFFFFFFF);
        }
        */
    }
    
    public void extractRenderState(GuiGraphicsExtractor arg, DeltaTracker arg2) {
        // Required method for HudElement in 26.1
    }
}
