package com.github.felixqing.fitstmyhelloworldplugin

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