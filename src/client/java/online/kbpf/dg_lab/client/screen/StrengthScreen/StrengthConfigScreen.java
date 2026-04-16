package online.kbpf.dg_lab.client.screen.StrengthScreen;

import online.kbpf.dg_lab.client.Dg_labClient;
import online.kbpf.dg_lab.client.Config.StrengthConfig;
import online.kbpf.dg_lab.client.screen.ConfigScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import static online.kbpf.dg_lab.client.screen.ConfigScreen.*;


@Environment(EnvType.CLIENT)
public class StrengthConfigScreen extends Screen {


    private AbstractSliderButton ADamageStrength, BDamageStrength;
    private Button DamageStrength;
    private AbstractSliderButton ADelayTime, BDelayTime;
    private Button DelayTime;
    private AbstractSliderButton ADownTime, BDownTime;
    private Button DownTime;
    private AbstractSliderButton ADownValue, BDownValue;
    private Button DownValue;
    private AbstractSliderButton ADeathStrength, BDeathStrength;
    private Button DeathStrength;
    private AbstractSliderButton ADeathDelay, BDeathDelay;
    private Button DeathDelay;
    private AbstractSliderButton AMin, BMin;
    private Button Min;

    public StrengthConfigScreen() {
        super(Component.literal("强度配置界面"));
    }


    public void close() {
        Screen configScreen = new ConfigScreen();
        Minecraft.getInstance().setScreen(configScreen);
    }

    @Override
    protected void init() {

        StrengthConfig strengthConfig = Dg_labClient.strengthConfig;
        ADamageStrength = new AbstractSliderButton(width / 2 - 205, 20, 100, ButtonHeight, Component.literal("A每伤害强度" + String.format("%.2f", strengthConfig.getADamageStrength())), strengthConfig.getADamageStrength() / 20) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                float ADamageStrength = (float) (this.value * 20);
                strengthConfig.setADamageStrength(ADamageStrength);
                this.setMessage(Component.literal("A每伤害强度" + String.format("%.2f", strengthConfig.getADamageStrength())));
            }
        };

        BDamageStrength = new AbstractSliderButton(width / 2 - 105, 20, 100, ButtonHeight, Component.literal("B每伤害强度" + String.format("%.2f", strengthConfig.getBDamageStrength())), strengthConfig.getBDamageStrength() / 20) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                float BDamageStrength = (float) (this.value * 20);
                strengthConfig.setBDamageStrength(BDamageStrength);
                this.setMessage(Component.literal("B每伤害强度" + String.format("%.2f", strengthConfig.getBDamageStrength())));
            }
        };

        DamageStrength = Button.builder(Component.literal("?"), button -> {}).bounds(width / 2 - 215, 20, 10, ButtonHeight).tooltip(Tooltip.create(Component.literal("每受到半颗心伤害增加的强度\n受伤时增加强度若小于1则增加1\n大于一的强度数值9舍0入\n若为0则不增加"))).build();

        ADelayTime = new AbstractSliderButton(width / 2 + 5, 20, 100, ButtonHeight, Component.literal("A强度下降等待" + strengthConfig.getADelayTime() * 50 + "ms"), (double) strengthConfig.getADelayTime() / 120) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                int tmp = (int) (this.value * 120);
                this.setMessage(Component.literal("A强度下降等待" + tmp * 50 + "ms"));
                strengthConfig.setADelayTime(tmp);
            }
        };

        BDelayTime = new AbstractSliderButton(width / 2 + 105, 20, 100, ButtonHeight, Component.literal("B强度下降等待" + strengthConfig.getBDelayTime() * 50 + "ms"), (double) strengthConfig.getBDelayTime() / 120) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                int tmp = (int) (this.value * 120);
                this.setMessage(Component.literal("B强度下降等待" + tmp * 50 + "ms"));
                strengthConfig.setBDelayTime(tmp);
            }
        };

        DelayTime = Button.builder(Component.literal("?"), button -> {}).bounds(width / 2 + 205, 20, 10, ButtonHeight).tooltip(Tooltip.create(Component.literal("受伤等待一段时间后强度开始下降\n再次受伤将会覆盖当前的等待时间\n非叠加,是覆盖"))).build();

        ADownTime = new AbstractSliderButton(width / 2 + 5, ButtonHeight + ButtonDistance + 20, 100, ButtonHeight, Component.literal("A强度下降间隔" + strengthConfig.getADownTime() * 50 + "ms"), (double) strengthConfig.getADownTime() / 120) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                int tmp = (int) (this.value * 120);
                if (tmp == 0) tmp = 1;
                this.setMessage(Component.literal("A强度下降间隔" + tmp * 50 + "ms"));
                strengthConfig.setADownTime(tmp);
            }
        };

        BDownTime = new AbstractSliderButton(width / 2 + 105, ButtonHeight + ButtonDistance + 20, 100, ButtonHeight, Component.literal("B强度下降间隔" + strengthConfig.getBDownTime() * 50 + "ms"), (double) strengthConfig.getBDownTime() / 120) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                int tmp = (int) (this.value * 120);
                if (tmp == 0) tmp = 1;
                this.setMessage(Component.literal("B强度下降间隔" + tmp * 50 + "ms"));
                strengthConfig.setBDownTime(tmp);
            }
        };

        DownTime = Button.builder(Component.literal("?"), button -> {}).bounds(width / 2 + 205, ButtonHeight + ButtonDistance + 20, 10, ButtonHeight).tooltip(Tooltip.create(Component.literal("强度下降的时候每过此时间强度下降一次"))).build();

        ADownValue = new AbstractSliderButton(width / 2 - 205, ButtonHeight + ButtonDistance + 20, 100, ButtonHeight, Component.literal("A强度下降数值" + strengthConfig.getADownValue()), (double) strengthConfig.getADownValue() / 20) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                int tmp = (int) (this.value * 20);
                this.setMessage(Component.literal("A强度下降数值" + tmp));
                strengthConfig.setADownValue(tmp);
            }
        };

        BDownValue = new AbstractSliderButton(width / 2 - 105, ButtonHeight + ButtonDistance + 20, 100, ButtonHeight, Component.literal("A强度下降数值" + strengthConfig.getBDownValue()), (double) strengthConfig.getBDownValue() / 20) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                int tmp = (int) (this.value * 20);
                this.setMessage(Component.literal("B强度下降数值" + tmp));
                strengthConfig.setBDownValue(tmp);
            }
        };

        DownValue = Button.builder(Component.literal("?"), button -> {}).bounds(width / 2 - 215, ButtonHeight + ButtonDistance + 20, 10, ButtonHeight).tooltip(Tooltip.create(Component.literal("每次强度下降的时候下降的数值"))).build();

        ADeathStrength = new AbstractSliderButton(width / 2 - 205, 2 * (ButtonHeight + ButtonDistance) + 20, 100, ButtonHeight, Component.literal("A死亡增加强度" + strengthConfig.getADeathStrength()), (double) strengthConfig.getADeathStrength() / 200) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                int tmp = (int) (this.value * 200);
                strengthConfig.setADeathStrength(tmp);
                this.setMessage(Component.literal("A死亡增加强度" + tmp));
            }
        };

        BDeathStrength = new AbstractSliderButton(width / 2 - 105, 2 * (ButtonHeight + ButtonDistance) + 20, 100, ButtonHeight, Component.literal("B死亡增加强度" + strengthConfig.getBDeathStrength()), (double) strengthConfig.getBDeathStrength() / 200) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                int tmp = (int) (this.value * 200);
                strengthConfig.setBDeathStrength(tmp);
                this.setMessage(Component.literal("B死亡增加强度" + tmp));
            }
        };

        DeathStrength = Button.builder(Component.literal("?"), button -> {}).bounds(width / 2 - 215, 2 * (ButtonHeight + ButtonDistance) + 20, 10, ButtonHeight).tooltip(Tooltip.create(Component.literal("死亡时增加的强度\n计算完受伤强度后叠加\n和受伤强度同时作用\n死亡时将会发送\n死亡时收到的伤害x每伤害强度+死亡增加强度"))).build();

        ADeathDelay = new AbstractSliderButton(width / 2 + 5, 2 * (ButtonHeight + ButtonDistance) + 20, 100, ButtonHeight, Component.literal("A死亡时强度下降等待" + strengthConfig.getADeathDelay() * 50 + "ms"), (double) strengthConfig.getADeathDelay() / 120) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                int tmp = (int) (this.value * 120);
                this.setMessage(Component.literal("A下降等待" + tmp * 50 + "ms"));
                strengthConfig.setADeathDelay(tmp);
            }
        };

        BDeathDelay = new AbstractSliderButton(width / 2 + 105, 2 * (ButtonHeight + ButtonDistance) + 20, 100, ButtonHeight, Component.literal("B死亡时强度下降等待" + strengthConfig.getBDeathDelay() * 50 + "ms"), (double) strengthConfig.getBDeathDelay() / 120) {
            @Override
            protected void updateMessage() {
            }

            @Override
            protected void applyValue() {
                int tmp = (int) (this.value * 120);
                this.setMessage(Component.literal("B下降等待" + tmp * 50 + "ms"));
                strengthConfig.setBDeathDelay(tmp);
            }
        };

        DeathDelay = Button.builder(Component.literal("?"), button -> {}).bounds(width / 2 + 205, 2 * (ButtonHeight + ButtonDistance) + 20, 10, ButtonHeight).tooltip(Tooltip.create(Component.literal("同强度下降等待\n此值在死亡时生效\n非叠加,是覆盖"))).build();

        AMin = new AbstractSliderButton(width / 2 - 205, 3 * (ButtonHeight + ButtonDistance) + 20, 100, ButtonHeight, Component.literal("A最低强度" + strengthConfig.getAMin()), (double) strengthConfig.getAMin() / 200) {
            @Override
            protected void updateMessage() {

            }

            @Override
            protected void applyValue() {
                int tmp = (int) (value * 200);
                setMessage(Component.literal("A最低强度" + tmp));
                strengthConfig.setAMin(tmp);
            }
        };

        BMin = new AbstractSliderButton(width / 2 - 105, 3 * (ButtonHeight + ButtonDistance) + 20, 100, ButtonHeight, Component.literal("B最低强度" + strengthConfig.getBMin()), (double) strengthConfig.getBMin() / 200) {
            @Override
            protected void updateMessage() {

            }

            @Override
            protected void applyValue() {
                int tmp = (int) (value * 200);
                setMessage(Component.literal("B最低强度" + tmp));
                strengthConfig.setBMin(tmp);
            }
        };

        Min = Button.builder(Component.literal("?"), button -> {}).bounds(width / 2 - 215, 3 * (ButtonHeight + ButtonDistance) + 20, 10, ButtonHeight).tooltip(Tooltip.create(Component.literal("通道最低强度\n强度下降时将不会低于此值\n此值实际受血量比例影响\n例如损失10%血量最低强度就为此值x10%\n损失50%血量最低强度就为此值x50%"))).build();



        addRenderableWidget(ADamageStrength);
        addRenderableWidget(BDamageStrength);
        addRenderableWidget(DamageStrength);
        addRenderableWidget(ADelayTime);
        addRenderableWidget(BDelayTime);
        addRenderableWidget(DelayTime);
        addRenderableWidget(ADownTime);
        addRenderableWidget(BDownTime);
        addRenderableWidget(DownTime);
        addRenderableWidget(ADownValue);
        addRenderableWidget(BDownValue);
        addRenderableWidget(DownValue);
        addRenderableWidget(ADeathStrength);
        addRenderableWidget(BDeathStrength);
        addRenderableWidget(DeathStrength);
        addRenderableWidget(ADeathDelay);
        addRenderableWidget(BDeathDelay);
        addRenderableWidget(DeathDelay);
        addRenderableWidget(AMin);
        addRenderableWidget(BMin);
        addRenderableWidget(Min);
    }


}
