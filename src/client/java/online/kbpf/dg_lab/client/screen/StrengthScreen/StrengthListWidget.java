package online.kbpf.dg_lab.client.screen.StrengthScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.List;

public class StrengthListWidget extends AbstractSelectionList<StrengthListWidget.Entry> {

    public StrengthListWidget(Minecraft minecraftClient, int width, int height, int y, int itemHeight) {
        super(minecraftClient, width, height, y, itemHeight);
    }

    public void addWaveformEntry(Entry entry) {
        this.addEntry(entry);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {
    }

    public static class Entry extends AbstractSelectionList.Entry<Entry> {
        private final StrengthListWidget parent;
        private final EditBox waveformDataText;
        private final Button sendButton;
        private final Font textRenderer;
        private final Component text;

        public Entry(StrengthListWidget parent, Font textRenderer, Component text, Runnable runnable) {
            this.parent = parent;
            waveformDataText = new EditBox(textRenderer, 100, 15, Component.literal("输入波形代码"));
            sendButton = Button.builder(Component.literal("\u2750"), button -> {
                Minecraft.getInstance().keyboardHandler.setClipboard(waveformDataText.getValue());
            }).tooltip(Tooltip.create(Component.literal("点击复制波形代码"))).build();
            this.textRenderer = textRenderer;
            this.text = text;
        }

        public List<GuiEventListener> children() {
            return List.of(waveformDataText, sendButton);
        }

        @Override
        public void extractContent(GuiGraphicsExtractor context, int mouseX, int mouseY, boolean selected, float deltaTicks) {
            int entryWidth = parent.getRowWidth();
            int y = this.getY();
            int x = parent.getRowLeft();
            
            waveformDataText.setX((int) (x + (entryWidth / 2.5)));
            waveformDataText.setY(y);
            waveformDataText.setWidth(entryWidth / 2);
            waveformDataText.setHeight(15);
            waveformDataText.extractWidgetRenderState(context, mouseX, mouseY, deltaTicks);

            sendButton.setX((int) (x + (entryWidth / 2.5) + ((double) entryWidth * 0.51)));
            sendButton.setY(y);
            sendButton.setWidth(15);
            sendButton.setHeight(15);
            sendButton.extractRenderState(context, mouseX, mouseY, deltaTicks);

            context.text(textRenderer, this.text, x, y + 5, 0xffffff);
        }
    }
}