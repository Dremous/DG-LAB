package online.kbpf.dg_lab.client.screen.WaveformScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import online.kbpf.dg_lab.client.Tool.DGWaveformTool;
import online.kbpf.dg_lab.client.entity.Waveform.Waveform;
import online.kbpf.dg_lab.client.screen.WaveformScreen.Custom.CustomScreen;


import java.util.List;

import static online.kbpf.dg_lab.client.screen.ConfigScreen.*;
import static online.kbpf.dg_lab.client.Dg_labClient.waveformMap;
import static online.kbpf.dg_lab.client.Dg_labClient.webSocketServer;

public class WaveformListWidget extends AbstractSelectionList<WaveformListWidget.Entry> {

    private final int width;


    public WaveformListWidget(Minecraft minecraftClient, int width, int height, int y, int itemHeight) {
        super(minecraftClient, width, height, y, itemHeight);
        this.width = width;
    }


    @Override
    public int getRowLeft() {
        return this.getX();
    }
    @Override
    public int getRowWidth() {
        return this.width;
    }
    @Override
    protected int scrollBarX() {
        return this.getRight() - 6;
    }


    public void addWaveformEntry(Entry entry) {
        this.addEntry(entry);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {
    }

    public static class Entry extends AbstractSelectionList.Entry<Entry> {
        private final EditBox waveformDataText;
        private final Button copyButton, pasteButton, testButton, customButton;
        private final Font textRenderer;
        private final Component text;
        private final WaveformListWidget parent;

        private Waveform waveform = new Waveform();

        public Entry(WaveformListWidget parent, Font textRenderer, Component text, String key) {
            this.parent = parent;


            if(waveformMap.containsKey(key)) waveform = waveformMap.get(key);


            waveformDataText = new EditBox(textRenderer, 100, ButtonHeight, Component.literal(""));
            waveformDataText.setMaxLength(100000);

            waveformDataText.setValue(waveform.getWaveform());



            waveformDataText.setHint(Component.literal("输入波形代码"));

            waveformDataText.setResponder(inputText -> {
                waveform.setWaveform(inputText);
            });

            customButton = Button.builder(Component.literal("✏"), button -> {
                Screen customScreen = new CustomScreen(key);
                Minecraft.getInstance().setScreen(customScreen);
            }).tooltip(Tooltip.create(Component.literal("点击修改波形"))).bounds(0, 0, 15, ButtonHeight).build();

            copyButton = Button.builder(Component.literal("\uD83D\uDCC4"), button -> {
                Minecraft.getInstance().keyboardHandler.setClipboard(waveformDataText.getValue());
            }).tooltip(Tooltip.create(Component.literal("点击复制波形代码"))).bounds(0, 0, 15, ButtonHeight).build();

            pasteButton = Button.builder(Component.literal("\uD83D\uDCCB"), button -> {
                String clipboardText = Minecraft.getInstance().keyboardHandler.getClipboard();
                waveformDataText.setValue(clipboardText);
            }).tooltip(Tooltip.create(Component.literal("点击粘贴波形代码"))).bounds(0, 0, 15, ButtonHeight).build();

            testButton = Button.builder(Component.literal("\uD83D\uDCE8"), button -> {
                webSocketServer.sendDGWaveForm(waveformDataText.getValue(), 1);
            }).tooltip(Tooltip.create(Component.literal("发送到终端1通道"))).bounds(0, 0, 15, ButtonHeight).build();


            this.textRenderer = textRenderer;
            this.text = text;

        }


        public List<GuiEventListener> children() {
            return List.of(waveformDataText, testButton, customButton);
        }

        @Override
        public void extractContent(GuiGraphicsExtractor context, int mouseX, int mouseY, boolean selected, float deltaTicks) {
            int entryWidth = parent.getRowWidth();
            int y = this.getY();
            int x = parent.getRowLeft();

            waveformDataText.setX(x + (int) (entryWidth * 0.3));
            waveformDataText.setY(y);
            waveformDataText.setWidth((int) (entryWidth * 0.1));
            waveformDataText.setHeight(ButtonHeight);
            waveformDataText.extractWidgetRenderState(context, mouseX, mouseY, deltaTicks);

            customButton.setX(waveformDataText.getX() + waveformDataText.getWidth() + 5);
            customButton.setY(y);
            customButton.extractRenderState(context, mouseX, mouseY, deltaTicks);

            testButton.setX(customButton.getX() + 20);
            testButton.setY(y);
            testButton.extractRenderState(context, mouseX, mouseY, deltaTicks);


            context.text(textRenderer, this.text, x + (int) (entryWidth * 0.15), y + 5, 0xffffffff);

            int duration = DGWaveformTool.checkAndCountValidSubstrings(waveformDataText.getValue());
            if(duration == 0)
                context.text(textRenderer, "ERROR", testButton.getX() + 20, y + 5, 0xffFF0000, true);
            else context.text(textRenderer, (duration * 100) + "ms", testButton.getX() + 15, y + 5, 0xffFFFFFF, true);
        }
    }
}