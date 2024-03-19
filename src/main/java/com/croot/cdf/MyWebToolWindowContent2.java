package com.croot.cdf;


import com.croot.cdf.util.CdfModuleUtil;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefBrowserBase;
import com.intellij.ui.jcef.JBCefJSQuery;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLoadHandlerAdapter;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

public class MyWebToolWindowContent2 {

    private final JPanel content;

    /**
     * 构造函数
     */
    public MyWebToolWindowContent2() {
        this.content = new JPanel(new BorderLayout());
        // 判断所处的IDEA环境是否支持JCEF
        if (!JBCefApp.isSupported()) {
            this.content.add(new JLabel("当前环境不支持JCEF", SwingConstants.CENTER));
            return;
        }
        // 创建 JBCefBrowser
        JBCefBrowserBase jbCefBrowser = new JBCefBrowser();

        // 将 JBCefBrowser 的UI控件设置到Panel中
        this.content.add(jbCefBrowser.getComponent(), BorderLayout.CENTER);
        // 加载URL
//        jbCefBrowser.loadURL("https://www.baidu.com/");
//        jbCefBrowser.loadURL("file:///D:/workspace/plug-test/src/main/resources/demo.html");

        jbCefBrowser.loadHTML(loadHtml("demo.html"));


        //加载java与javaScript交互的代码
        buildInteractive(jbCefBrowser);
    }


        private void buildInteractive(JBCefBrowserBase jbCefBrowser) {
            JBCefJSQuery dataJSQuery = JBCefJSQuery.create(jbCefBrowser);
            dataJSQuery.addHandler((String args) -> {
                String data = null;
                try {
                    data = getData();
                    System.out.println("============queryData" + data);
                }catch (Exception e) {
                    return new JBCefJSQuery.Response("", 500, "执行错误:" + e.getMessage());
                }

                return new JBCefJSQuery.Response(data);
            });

            //下载java的handler代码
            JBCefJSQuery downJSQuery = JBCefJSQuery.create(jbCefBrowser);
            downJSQuery.addHandler((String args) -> {
                System.out.println("============downLoad" + args);
                return new JBCefJSQuery.Response(args);
            });

            //获取module
            JBCefJSQuery moduleQuery = JBCefJSQuery.create(jbCefBrowser);
            moduleQuery.addHandler((String args) -> {
                List<String> moduleNames = CdfModuleUtil.getModuleNames();
                System.out.println("==========modules" + moduleNames);
                return new JBCefJSQuery.Response(moduleNames.toString());
            });

            jbCefBrowser.getJBCefClient().addLoadHandler(new CefLoadHandlerAdapter() {

                @Override
                public void onLoadEnd(CefBrowser browser, CefFrame frame, int httpStatusCode) {
                    jbCefBrowser.getCefBrowser().executeJavaScript(
                            "window.cdfModules = function() {" +
                                    "return  " + moduleQuery.inject(
                                    "",
                                    "function(response){alert('moduleRefresh' + response);}",
                                    "function(errorCode, errorMsg){alert('cdfModules，msg=' + errorMsg);}") + // 5
                                    "};",
                            jbCefBrowser.getCefBrowser().getURL(), 0);


                    jbCefBrowser.getCefBrowser().executeJavaScript( // 4
                            "window.callBackJavaGetData = function() {" +
                                    "return  " + dataJSQuery.inject(
                                            "",
                                    "function(response){setNewData(response);}",
                                    "function(errorCode, errorMsg){alert('callBackJavaGetDataError，msg=' + errorMsg);}") + // 5
                                    "};",
                            jbCefBrowser.getCefBrowser().getURL(), 0
                    );

                    //3.创建一个js的方法来调用handler
                    jbCefBrowser.getCefBrowser().executeJavaScript( // 4
                            "cdfDownLoadByStr = function(param) {" +
                                    downJSQuery.inject(
                                            "param",
                                            "function(response){alert('DownLoadSuccess，msg=' + response);}",
                                            "function(response){alert('DownLoadError，msg=' + error);}") + // 5
                                    "};",
                            jbCefBrowser.getCefBrowser().getURL(), 0
                    );
                }
            }, jbCefBrowser.getCefBrowser());
        }


    private String getData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8080/dataDictionary/allMetaData")
                .build();

        String responseBody = "";
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                responseBody = response.body().string();
                System.out.println(responseBody);
            } else {
                System.err.println("Unexpected code " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    /**
     * 加载html
     *
     * @param htmlName
     * @return
     */
    private String loadHtml(String htmlName) {
        String htmlStr = "";
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(htmlName);
            if (inputStream == null) {
                // 文件未找到处理
                System.err.println("HTML file not found");
                return htmlStr;
            }
            htmlStr = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            inputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlStr;
    }


    /**
     * 返回创建的JPanel
     *
     * @return JPanel
     */
    public JPanel getContent() {
        return content;
    }
}