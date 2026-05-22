# hello world 插件示例
```kotlin
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
class HelloWorldAction: AnAction() {
    override fun actionPerformed(p0: AnActionEvent) {
        Messages.showMessageDialog(
            p0.project,
            "Hello World! 🎉\n这是我的第一个 JetBrains 插件",
            "Hello World",
            Messages.getInformationIcon()
        )
    }
}
```
这是一个hello world插件的代码示例。当用户点击插件时，会弹出一个消息框显示 "Hello World! 🎉\n这是我的第一个 JetBrains 插件"。
你可以将这段代码复制到你的插件项目中，并根据需要进行修改和扩展。
# AnAction
`AnAction` 是 JetBrains IntelliJ 平台 SDK 里的一个抽象类，它是你开发插件时，让 IDE 响应用户操作的核心入口。

通俗地说：IDE 里每一个可以被用户"触发"的东西（菜单项、快捷键、工具栏按钮、右键菜单），背后都是一个 `AnAction` 的子类。

---

它长什么样？

```java
// 简化版源码示意
public abstract class AnAction {
    // 用户触发时执行（子类必须实现）
    public abstract void actionPerformed(@NotNull AnActionEvent e);
    
    // 决定菜单是否显示/变灰（可选重写）
    public void update(@NotNull AnActionEvent e) {}
}
```

你的 `HelloWorldAction` 继承它，就是告诉 IDE：

> "当用户点了这个菜单项，请执行我 `actionPerformed` 里的代码。"

---

核心方法详解

方法	什么时候调用	用途
`actionPerformed(e)`	用户点击菜单或按快捷键时	必须实现。写你的业务逻辑（弹窗、修改代码、打开文件等）
`update(e)`	菜单显示前（每次打开菜单都会检查）	控制菜单是否可见、是否变灰、显示什么图标
`getActionUpdateThread()`	决定 `update()` 在哪个线程跑	默认后台线程，避免阻塞 UI

---

一个实际例子：update() 的用法

假设你想让菜单只在打开 Java 文件时才可用：

```kotlin
class HelloWorldAction : AnAction() {
    
    override fun update(e: AnActionEvent) {
        // 获取当前编辑的文件
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        
        // 如果不是 .java 文件，就把菜单变灰
        e.presentation.isEnabledAndVisible = file?.extension == "java"
    }

    override fun actionPerformed(e: AnActionEvent) {
        Messages.showMessageDialog(e.project, "Hello!", "提示", Messages.getInformationIcon())
    }
}
```

---

类比理解

平台	对应概念
Android	`Activity` 或 `OnClickListener`
前端 JS	`onclick` 事件处理器
Spring	`@RequestMapping` 控制器方法
VS Code 插件	`registerCommand` 注册的命令

---

一句话总结

> `AnAction` 是 JetBrains IDE 插件开发中的动作基类。你继承它、重写 `actionPerformed()`，就能把自定义逻辑挂到 IDE 的菜单、快捷键或按钮上。