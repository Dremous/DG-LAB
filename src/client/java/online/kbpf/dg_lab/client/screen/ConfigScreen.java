package online.kbpf.dg_lab.client.screen;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import online.kbpf.dg_lab.client.Dg_labClient;
import online.kbpf.dg_lab.client.createQR.ToolQR;
import online.kbpf.dg_lab.client.Config.WaveformConfig;
import online.kbpf.dg_lab.client.screen.StrengthScreen.StrengthConfigScreen;
import online.kbpf.dg_lab.client.screen.WaveformScreen.WaveformConfigScreen;

import static online.kbpf.dg_lab.client.Dg_labClient.*;


@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {

    public static final int ButtonHeight = 20, ButtonDistance = 5;

    public Button saveFile;
    public Button webSocketConfig;
    public Button createQR;
    public Button StrengthConfig;
    public Button WaveFormConfig;
    public Button CustomConfig;
    public Button MaxStrength;
    public Button TwoPlayerMode;


    public AbstractSliderButton RenderingPositionX;
    public AbstractSliderButton RenderingPositionY;
    public AbstractSliderButton SPQS;

    public EditBox secondPlayerName;





//    Screen customScreen = new CustomScreen();

    public ConfigScreen() {
        // 此参数为屏幕的标题，进入屏幕中，复述功能会复述。
        super(Component.literal("配置界面"));
    }


    @Override
    protected void init() {

        Minecraft client = Minecraft.getInstance();

        int width1 = client.getWindow().getGuiScaledWidth();
        int height1 = client.getWindow().getGuiScaledHeight();

        CustomConfig = Button.builder(Component.literal("test"), button -> {
        }).bounds((int) ((double) width / 2 - (width * 0.4) - 5), 140, (int) (width * 0.4), ButtonHeight).build();

        SPQS = new AbstractSliderButton((int) ((double) width / 2 + 5), 140 - (2 * (ButtonDistance + ButtonHeight)), (int) (width * 0.2), ButtonHeight, Component.literal("2P退出强度：" + secondPlayerQuitStrength), secondPlayerQuitStrength * 0.005) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                int tmp = (int) (this.value * 200);
                secondPlayerQuitStrength = tmp;
                SPQS.setMessage(Component.literal("2P退出强度：" + ((tmp == 0)? "已关闭" : tmp)));
            }
        };

        RenderingPositionX = new AbstractSliderButton(width / 2 + 5, 140 - ButtonDistance - ButtonHeight, (int) (width * 0.2) - 6, ButtonHeight, Component.literal((modConfig.getRenderingPositionX() >= width1 || modConfig.getRenderingPositionY() >= height1) ? "已关闭强度显示" : ("显示位置X:" + modConfig.getRenderingPositionX())), (double) modConfig.getRenderingPositionX() / width1) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                int tmp = (int) (this.value * width1);
                modConfig.setRenderingPositionX(tmp);
                if (modConfig.getRenderingPositionX() >= width1 || modConfig.getRenderingPositionY() >= height1) {
                    this.setMessage(Component.literal("已关闭强度显示"));
                    RenderingPositionY.setMessage(Component.literal("已关闭强度显示"));
                } else {
                    this.setMessage(Component.literal("显示位置X:" + tmp));
                    RenderingPositionY.setMessage(Component.literal("显示位置Y:" + modConfig.getRenderingPositionY()));
                }
            }
        };

        RenderingPositionY = new AbstractSliderButton(RenderingPositionX.getX() + RenderingPositionX.getWidth(), 140 - ButtonDistance - ButtonHeight, (int) (width * 0.2) - 6, ButtonHeight, Component.literal((modConfig.getRenderingPositionX() >= width1 || modConfig.getRenderingPositionY() >= height1) ? "已关闭强度显示" : ("显示位置Y:" + modConfig.getRenderingPositionY())), (double) modConfig.getRenderingPositionY() / height1) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                int tmp = (int) (this.value * height1);
                modConfig.setRenderingPositionY(tmp);
                if (modConfig.getRenderingPositionX() >= width1 || modConfig.getRenderingPositionY() >= height1) {
                    this.setMessage(Component.literal("已关闭强度显示"));
                    RenderingPositionX.setMessage(Component.literal("已关闭强度显示"));
                } else {
                    this.setMessage(Component.literal("显示位置Y:" + tmp));
                    RenderingPositionX.setMessage(Component.literal("显示位置X:" + modConfig.getRenderingPositionX()));
                }
            }
        };

        MaxStrength = Button.builder(Component.literal((modConfig.isRenderingMax()) ? "开" : "关"), button -> {
            modConfig.setRenderingMax(!modConfig.isRenderingMax());
            MaxStrength.setMessage(Component.literal((modConfig.isRenderingMax()) ? "开" : "关"));
        }).bounds(RenderingPositionY.getX() + RenderingPositionX.getWidth(), 140 - ButtonDistance - ButtonHeight, 12, ButtonHeight).build();

        saveFile = Button.builder(Component.literal("保存配置到文件"), button -> {
                    strengthConfig.savaFile();
                    modConfig.savaFile();
                    WaveformConfig.saveWaveform(waveformMap);
                })
                .bounds((int) ((double) width / 2 - (width * 0.4) - 5), 20, (int) (width * 0.4), ButtonHeight).build();

        webSocketConfig = Button.builder(Component.literal("连接设置"), button -> {
                    Screen WebSocketConfigScreen = new WebSocketConfigScreen();
                    client.setScreen(WebSocketConfigScreen);
                })
                .bounds(width / 2 + 5, 20, (int) (width * 0.4), ButtonHeight).build();

        StrengthConfig = Button.builder(Component.literal("强度设置"), button -> {
            Screen strengthConfigScreen = new StrengthConfigScreen();
            client.setScreen(strengthConfigScreen);
        }).bounds((int) ((double) width / 2 - (width * 0.4) - 5), 20 + ButtonHeight + ButtonDistance, (int) (width * 0.4), ButtonHeight).build();

        WaveFormConfig = Button.builder(Component.literal("波形设置"), button -> {
            Screen waveformConfigScreen = new WaveformConfigScreen();
            client.setScreen(waveformConfigScreen);
        }).bounds(width / 2 + 5, 20 + ButtonHeight + ButtonDistance, (int) (width * 0.4), ButtonHeight).build();

        createQR = Button.builder(Component.literal("创建连接二维码并打开"), button -> {
            ToolQR.CreateQR();
        }).bounds((int) ((double) width / 2 - (width * 0.4) - 5), 140 - ButtonDistance - ButtonHeight, (int) (width * 0.4), ButtonHeight).build();


        TwoPlayerMode = Button.builder(Component.literal((twoPlayerMode) ? "本地双人模式：开" : "本地双人模式：关"), button -> {
            IntegratedServer server = client.getSingleplayerServer();
            if (server == null || !server.isPublished()) return;
            twoPlayerMode = !twoPlayerMode;
            TwoPlayerMode.setMessage(Component.literal((twoPlayerMode) ? "本地双人模式：开" : "本地双人模式：关"));
        }).bounds((int) ((double) width / 2 - (width * 0.4) - 5), 140 - (2 * (ButtonDistance + ButtonHeight)), (int) (width * 0.4), ButtonHeight).build();

        secondPlayerName = new EditBox(client.font, (int) ((double) width / 2 + 6 + (int) (width * 0.2)), 140 - (2 * (ButtonDistance + ButtonHeight)), (int) (width * 0.2), ButtonHeight, Component.literal(""));
        secondPlayerName.setMaxLength(16);
        secondPlayerName.setValue(secondPlayer);
        secondPlayerName.setEditable(true);

        addRenderableWidget(saveFile);
        addRenderableWidget(webSocketConfig);
        addRenderableWidget(StrengthConfig);
        addRenderableWidget(WaveFormConfig);
        addRenderableWidget(createQR);
        addRenderableWidget(RenderingPositionX);
        addRenderableWidget(RenderingPositionY);
        addRenderableWidget(MaxStrength);
        addRenderableWidget(TwoPlayerMode);
        addRenderableWidget(SPQS);
        addRenderableWidget(secondPlayerName);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        super.extractRenderState(context, mouseX, mouseY, delta);
        TwoPlayerMode.setMessage(Component.literal((twoPlayerMode) ? "本地双人模式：开" : "本地双人模式：关"));
    }
}
