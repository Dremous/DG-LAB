# DG_LAB Mod Agents

## 项目概述
- **类型**: Minecraft Fabric 模组
- **用途**: 实现游戏与 DG_LAB 硬件设备的 WebSocket 通信，控制电刺激设备输出
- **环境**: 仅客户端 (environment: "client")
- **目标版本**: Minecraft 26.1

## 构建系统
- **工具**: Fabric Loom 1.15
- **Java**: 25+
- **Gradle**: 9.4.0
- **构建命令**: `gradlew build`
- **运行命令**: `gradlew runClient` / `gradlew runServer`

## 源码结构
```
src/
├── main/java/online/kbpf/dg_lab/     # 主入口 (ModInitializer)
│   └── Dg_lab.java
├── client/java/online/kbpf/dg_lab/  # 客户端入口 (ClientModInitializer)
│   ├── Dg_labClient.java
│   ├── mixin/                         # Mixin 注入
│   │   ├── ClientPlayerEntityMixin.java
│   │   └── tick.java
│   └── client/
│       ├── hud/hud.java              # HUD 渲染
│       ├── screen/                    # 配置界面 (多个 Screen 类)
│       ├── entity/                    # 数据实体
│       ├── webSocketServer/           # WebSocket 服务器
│       └── Tool/                      # 波形工具
```

## 关键依赖
- `fabric-loader` - 0.18.4
- `fabric-api` - 0.145.4+26.1.2
- `ZXing core 3.5.3` - QR 码生成
- `Java-WebSocket 1.5.7` - WebSocket 通信

## 26.1 关键 API 变化（必须掌握）

### Screen 类
- `render(GuiGraphicsExtractor, int, int, float)` → `extractRenderState(GuiGraphicsExtractor, int, int, float)`
- `close()` 方法现在是 final，不能 override → 直接调用或用其他方式处理

### GuiGraphicsExtractor 绘制文本
- `drawString(Font, Component, int, int, int)` → `text(Font, Component, int, int, int)`
- `drawTextWithShadow(...)` → `text(..., true)` 最后一个参数为是否绘制阴影

### Button / EditBox 渲染
- **错误**: `widget.render(context, mouseX, mouseY, delta)`
- **正确**: `widget.extractRenderState(context, mouseX, mouseY, delta)` 或 `widget.extractWidgetRenderState(...)`

### EditBox API
- `setText(String)` → `setValue(String)`
- `getText()` → `getValue()`
- `setPlaceholder(Component)` → `setHint(Component)`
- `setChangedListener(...)` → `setResponder(...)`

### Tooltip API
- `Tooltip.ofComponent(Component)` → `Tooltip.create(Component)`

### Identifier 创建
- `Identifier.of("namespace", "path")` → `Identifier.fromNamespaceAndPath("namespace", "path")`
- `new Identifier("namespace", "path")` 不再可用（private 构造函数）

### 客户端命令 API
- `ClientCommandManager.literal()` / `argument()` → `ClientCommands.literal()` / `argument()`
- import: `net.fabricmc.fabric.api.client.command.v2.ClientCommands`

### Style 类
- `Style.empty()` → `Style.EMPTY`（静态字段）

### 快捷键 API (26.1)
- `KeyBindingHelper` 包: `net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper`
- 注册方法: `KeyMappingHelper.registerKeyMapping()` (不是 `registerKeyBinding`)
- Category 创建: `KeyMapping.Category.register(Identifier)` (不是 `create`)

### Player sendMessage
- `player.sendMessage(Component, boolean)` 已移除
- `sendMessage(Component, false)` → `player.sendSystemMessage(Component)`
- `sendMessage(Component, true)` → `player.sendOverlayMessage(Component)`

### AbstractSelectionList
- `getScrollbarX()` → `scrollBarX()`
- `Entry.render()` 和 `Entry.children()` 在 26.1 中方法签名不同，需确认父类是否有这些方法
- `Entry.extractContent(GuiGraphicsExtractor, int, int, boolean, float)` 必须实现

### Minecraft Server API (tick.java 双人模式用)
- `client.isIntegratedServerRunning()` → `client.hasSingleplayerServer()`
- `client.getServer()` → `client.getSingleplayerServer()`
- `server.isRemote()` → `server.isPublished()`
- `server.getPlayerManager()` → `server.getPlayerList()`

### Mixin 注意事项
- **不要在 @Mixin 类中直接扩展目标类并调用 super()** - 26.1 中 LocalPlayer 构造函数签名已大幅变更
- 使用 `@Inject` 注入方法而不是尝试覆盖构造函数

### Mixin 字段变化 (26.1)
- `Minecraft.world` → `Minecraft.level`
- `Minecraft.server` → `Minecraft.singleplayerServer`
- `LivingEntity.lastDamageTaken` → `LivingEntity.lastHurt`

## Mixin 注入点
- `ClientPlayerEntityMixin` (Mixin LocalPlayer) - 监听 `hurtTo` 方法处理伤害事件
- `tick` (Mixin Minecraft) - 游戏 tick 循环处理强度衰减和波形发送

## 配置文件
- `config/dg-lab/ModConfig.json`
- `config/dg-lab/WaveformConfig.json`
- `config/dg-lab/StrengthConfig.json`

## 版本更新参考
- Minecraft 版本: 26.1
- Fabric Loader: 0.18.4
- Fabric API: 0.145.4+26.1.2
- Mod 版本: 1.1.4-fabric-26.1
