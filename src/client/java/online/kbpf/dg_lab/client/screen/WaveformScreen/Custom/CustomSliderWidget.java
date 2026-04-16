package online.kbpf.dg_lab.client.screen.WaveformScreen.Custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

import static online.kbpf.dg_lab.client.screen.WaveformScreen.Custom.CustomScreen.list;

public abstract class CustomSliderWidget extends AbstractSliderButton {

    private boolean sliderFocused;
    private static final Identifier TEXTURE = Identifier.withDefaultNamespace("widget/slider");
    private static final Identifier HIGHLIGHTED_TEXTURE = Identifier.withDefaultNamespace("widget/slider_highlighted");
    private static final Identifier HANDLE_TEXTURE = Identifier.withDefaultNamespace("widget/slider_handle");
    private static final Identifier HANDLE_HIGHLIGHTED_TEXTURE = Identifier.withDefaultNamespace("widget/slider_handle_highlighted");

    public CustomSliderWidget(int x, int y, int width, int height, Component text, double value) {
        super(x, y, width, height, text, value);
    }

    public void setValue(int value) {
        this.setMessage(Component.literal(String.valueOf(value)));
        this.value = (double) value / 100;
    }

    @Override
    public void extractWidgetRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        Minecraft minecraftClient = Minecraft.getInstance();

        int color = Mth.clamp((int)(this.alpha * 255.0F), 0, 255) << 24 | (this.active ? 0xFFFFFF : 0x999999);
        context.blit(this.getTexture(), this.getX(), this.getY(), 0, 0, this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight());
        context.blit(this.getHandleTexture(), this.getX() + (int)(this.value * (double)(this.width - 8)), this.getY(), 0, 0, 8, this.getHeight(), 8, this.getHeight());
        context.text(
                minecraftClient.font,
                this.getMessage(),
                this.getX() + this.getWidth(),
                this.getY(),
                color,
                true
        );
    }

    private Identifier getTexture() {
        return this.isFocused() && !this.sliderFocused ? HIGHLIGHTED_TEXTURE : TEXTURE;
    }

    private Identifier getHandleTexture() {
        return !this.isHovered() && !this.sliderFocused ? HANDLE_TEXTURE : HANDLE_HIGHLIGHTED_TEXTURE;
    }

    @Override
    protected abstract void updateMessage();

    @Override
    protected abstract void applyValue();
}
