package com.croot.cdf;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;


/**
 * @author twilight
 * @since V1.0
 */
public class MyToolWindowFactory implements ToolWindowFactory {
    public void createToolWindowContent(
            @NotNull Project project,
            @NotNull ToolWindow toolWindow) {
        // 此处方法将会在点击ToolWindow的时候触发
        // 获取ContentManager
        ContentManager contentManager = toolWindow.getContentManager();
        Content labelContent =
                contentManager.getFactory() // 内容管理器获取工厂类
                        .createContent( // 创建Content（组件类实例、显示名称、是否可以锁定）
                                new MyWebToolWindowContent2().getContent(),
//                                new JLabel("aaa"),
                                "MyTab",
                                false
                        );
        // 利用ContentManager添加Content
        contentManager.addContent(labelContent);
    }

}
