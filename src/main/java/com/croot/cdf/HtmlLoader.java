package com.croot.cdf;

import com.intellij.ui.jcef.JBCefBrowser;

import javax.swing.*;
import java.net.URL;

public class HtmlLoader {
    public static void main(String[] args) {
        URL htmlUrl = HtmlLoader.class.getClassLoader().getResource("demo.html");
        System.out.println("===========" + htmlUrl.toString());
        // 创建JFrame来展示浏览器内容
//        JFrame frame = new JFrame("JBCefBrowser Example");
//        frame.setSize(800, 600);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // 创建JBCefBrowser实例
//        JBCefBrowser browser = new JBCefBrowser();
//
//        // 将JBCefBrowser组件添加到JFrame中
//        frame.add(browser.getComponent());
//
//        // 获取资源文件的URL
//        URL htmlUrl = HtmlLoader.class.getResource("demo.html");
//        if (htmlUrl != null) {
//            // 使用file协议加载本地HTML文件
//            browser.loadURL(htmlUrl.toString());
//        } else {
//            System.err.println("Failed to find the HTML file.");
//        }
//
//        // 显示窗口
//        frame.setVisible(true);
    }
}
