# 贪吃蛇 Android 应用

一个使用 Kotlin 开发的经典贪吃蛇游戏 Android 应用。

## 功能特点

- 经典贪吃蛇游戏玩法
- 触摸按钮控制蛇的方向（上下左右）
- 实时分数显示
- 游戏结束后自动重新开始
- 流畅的动画效果
- 现代化深色主题 UI

## 项目结构

```
app/
├── src/main/
│   ├── java/com/example/snakegame/
│   │   ├── MainActivity.kt       # 主活动，处理游戏逻辑和用户交互
│   │   └── SnakeView.kt          # 自定义视图，绘制游戏画面
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml # 主界面布局
│   │   └── values/
│   │       ├── colors.xml        # 颜色资源
│   │       ├── strings.xml       # 字符串资源
│   │       └── themes.xml        # 主题样式
│   └── AndroidManifest.xml       # 应用清单文件
```

## 技术栈

- **语言**: Kotlin
- **最低 SDK**: API 24 (Android 7.0)
- **目标 SDK**: API 34
- **UI 框架**: Android View System
- **构建工具**: Gradle

## 游戏说明

1. 点击方向按钮控制蛇的移动方向
2. 吃到红色食物增加分数和蛇的长度
3. 撞到墙壁或自己的身体游戏结束
4. 游戏结束后会显示最终分数并自动重新开始

## 构建和运行

### 使用 Android Studio

1. 用 Android Studio 打开项目
2. 点击 Run 按钮或按 Shift+F10
3. 选择目标设备（模拟器或真机）

### 使用命令行

```bash
# 构建项目
./gradlew build

# 安装到设备
./gradlew installDebug

# 运行应用
adb shell am start -n com.example.snakegame/.MainActivity
```

## 系统要求

- Android 7.0 (API 24) 或更高版本
- 屏幕分辨率: 任何支持的手机或平板

## 许可证

MIT License
