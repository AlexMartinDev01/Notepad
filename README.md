# Windows 10 Notepad - Java AWT & Swing Implementation

这是一个基于Java AWT和Swing实现的Windows 10风格记事本应用，包含完整的文本编辑功能和现代化的用户界面设计。

## 项目框架结构

### 目录结构
```
java_notepad/
├── AWT/                      # AWT版本实现
│   ├── Windows10Notepad.java  # 主程序文件
│   ├── Windows10Notepad.class # 编译后的类文件
│   ├── Windows10Notepad.jar   # 可执行JAR文件
│   └── run.bat                # Windows运行脚本
├── SWING/                     # Swing版本实现
│   ├── Windows10NotepadSwing.java # 主程序文件
│   ├── Windows10NotepadSwing.class # 编译后的类文件
│   ├── Windows10NotepadSwing.jar   # 可执行JAR文件
│   └── run.bat                    # Windows运行脚本
└── README.md                   # 项目说明文档
```

### 功能模块划分

| 模块 | 功能描述 | AWT版本支持 | Swing版本支持 |
|------|----------|-------------|---------------|
| 文件操作 | 新建、打开、保存、另存为、打印、退出 | ✅ | ✅ |
| 编辑操作 | 撤销、重做、剪切、复制、粘贴、删除 | ⚠️ 部分支持 | ✅ |
| 查找替换 | 查找、替换、转到行 | ❌ | ❌ |
| 格式设置 | 自动换行、字体设置 | ⚠️ 部分支持 | ✅ |
| 查看设置 | 状态栏显示 | ✅ | ✅ |
| 帮助信息 | 关于对话框 | ✅ | ✅ |
| 附加功能 | 插入时间日期、全选 | ✅ | ✅ |

## 开发流程

### 1. 需求分析
- 实现Windows 10风格的记事本应用
- 支持基本的文本编辑功能
- 提供友好的用户界面
- 支持文件操作
- 实现格式设置

### 2. 技术选型
- **AWT版本**：使用Java AWT组件库，适合学习基础GUI编程
- **Swing版本**：使用Java Swing组件库，提供更丰富的组件和更好的用户体验

### 3. 核心功能设计

#### 3.1 主窗口设计
- 菜单栏（File, Edit, Format, View, Help）
- 文本编辑区域
- 状态栏

#### 3.2 文件操作模块
```java
// 核心文件操作方法
private void fileNew()      // 新建文件
private void fileOpen()     // 打开文件
private boolean fileSave()  // 保存文件
private boolean fileSaveAs()// 另存为
private void exitApplication() // 退出应用
```

#### 3.3 编辑功能模块
```java
// 核心编辑操作方法
private void undo()         // 撤销
private void redo()         // 重做
private void cut()          // 剪切
private void copy()         // 复制
private void paste()        // 粘贴
private void delete()       // 删除
private void selectAll()    // 全选
```

#### 3.4 格式设置模块
```java
// 核心格式设置方法
private void toggleWordWrap() // 切换自动换行
private void changeFont()     // 更改字体
```

#### 3.5 状态栏模块
```java
// 状态栏相关方法
private void createStatusBar() // 创建状态栏
private void updateStatusBar() // 更新状态栏信息
private void toggleStatusBar() // 切换状态栏显示
```

### 4. 代码实现

#### 4.1 AWT版本实现要点
- 使用`Frame`作为主窗口
- 使用`MenuBar`、`Menu`、`MenuItem`构建菜单
- 使用`TextArea`作为文本编辑区域
- 自定义`MessageBox`类实现对话框功能
- 实现`ActionListener`和`WindowListener`接口处理事件

#### 4.2 Swing版本实现要点
- 使用`JFrame`作为主窗口
- 使用`JMenuBar`、`JMenu`、`JMenuItem`构建菜单
- 使用`JTextArea`和`JScrollPane`实现文本编辑区域
- 使用`JLabel`实现状态栏
- 使用`UndoManager`实现撤销/重做功能
- 自定义`JFontChooser`类实现字体选择
- 使用Swing内置对话框（`JOptionPane`）

### 5. 编译与运行

#### 5.1 编译代码
```bash
# AWT版本编译
javac Windows10Notepad.java

# Swing版本编译
javac Windows10NotepadSwing.java
```

#### 5.2 运行应用
```bash
# AWT版本运行
java Windows10Notepad

# Swing版本运行
java Windows10NotepadSwing
```

#### 5.3 生成可执行JAR文件
```bash
# AWT版本打包
jar cvfe Windows10Notepad.jar Windows10Notepad *.class

# Swing版本打包
jar cvfe Windows10NotepadSwing.jar Windows10NotepadSwing *.class
```

## 功能详细说明

### 文件菜单
- **New** (`Ctrl+N`)：创建新文档
- **Open...** (`Ctrl+O`)：打开现有文件
- **Save** (`Ctrl+S`)：保存当前文档
- **Save As...**：另存为新文件
- **Print...** (`Ctrl+P`)：打印文档（未实现）
- **Exit**：退出应用

### 编辑菜单
- **Undo** (`Ctrl+Z`)：撤销上一步操作
- **Redo** (`Ctrl+Y`)：重做上一步操作
- **Cut** (`Ctrl+X`)：剪切选中的文本
- **Copy** (`Ctrl+C`)：复制选中的文本
- **Paste** (`Ctrl+V`)：粘贴剪贴板内容
- **Delete** (`Del`)：删除选中的文本
- **Find...** (`Ctrl+F`)：查找文本（未实现）
- **Replace...** (`Ctrl+H`)：替换文本（未实现）
- **Go To...**：跳转到指定行（未实现）
- **Select All** (`Ctrl+A`)：全选文本
- **Time/Date** (`F5`)：插入当前时间和日期

### 格式菜单
- **Word Wrap**：切换自动换行
- **Font...**：打开字体选择对话框

### 查看菜单
- **Status Bar**：显示/隐藏状态栏

### 帮助菜单
- **About Notepad**：显示关于对话框

## 技术亮点

### AWT版本
- 纯AWT实现，无需额外依赖
- 自定义消息框组件
- 状态栏实时显示行号、列号和字符数
- 支持基本的文件操作
- 响应式设计

### Swing版本
- 现代化的Swing UI设计
- 完整的撤销/重做功能
- 高级字体选择器
- 支持自动换行
- 系统外观集成
- 线程安全的UI更新
- 更好的用户体验

## 开发环境要求

- **JDK版本**：JDK 8或更高版本
- **开发工具**：任何Java IDE（Eclipse、IntelliJ IDEA、NetBeans等）
- **操作系统**：Windows、macOS、Linux

## 发布到GitHub的步骤

### 1. 创建GitHub仓库
1. 登录GitHub账号
2. 点击右上角的"+"按钮，选择"New repository"
3. 填写仓库名称（如"java-notepad"）
4. 选择仓库类型（公开/私有）
5. 勾选"Initialize this repository with a README"
6. 点击"Create repository"

### 2. 本地初始化Git仓库
```bash
# 进入项目目录
cd e:\Project\JAVA\java_notepad

# 初始化Git仓库
git init

# 添加远程仓库
git remote add origin https://github.com/your-username/java-notepad.git

# 添加所有文件
git add .

# 提交初始版本
git commit -m "Initial commit: Windows 10 Notepad Java implementation"

# 推送到GitHub
git push -u origin master
```

### 3. 优化GitHub展示
1. 添加项目描述和标签
2. 上传项目截图
3. 添加使用说明
4. 添加贡献指南
5. 添加许可证文件

## 项目截图

### AWT版本
![AWT版本截图](https://via.placeholder.com/800x600?text=AWT+Version+Screenshot)

### Swing版本
![Swing版本截图](https://via.placeholder.com/800x600?text=Swing+Version+Screenshot)

## 许可证

本项目采用MIT许可证，详情请查看LICENSE文件。

## 贡献

欢迎提交Issue和Pull Request来改进这个项目！

## 更新日志

### v1.0 (2024-01-01)
- 初始版本发布
- 实现AWT版本记事本
- 实现Swing版本记事本
- 支持基本的文本编辑功能
- 支持文件操作
- 支持格式设置

## 致谢

- 感谢Java社区提供的优秀GUI库
- 感谢Windows 10设计团队的灵感
- 感谢所有为开源项目做出贡献的开发者

---


**Happy Coding! 🚀**
