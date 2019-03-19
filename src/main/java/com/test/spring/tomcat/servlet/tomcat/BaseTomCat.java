package com.test.spring.tomcat.servlet.tomcat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;

import com.test.spring.tomcat.servlet.servlet.ServletProcess;

/**
 * @author : zhangjh
 * @date : 2019-03-19
 * Description: ������Tomcat�����������ֳ�ʼ����ʽ��Tomcat�̳�
 */
abstract class BaseTomCat {
    /** �����˿� */
    private int port;
    /** url��ں;���servlet��ӳ�� */
    protected Map<String, String> urlServletMap;

    public BaseTomCat(int port) {
        this.port = port;
    }

    /** ������ʵ�ֲ�ͬ�ĳ�ʼ��������֧��xml��JAVA���� */
    abstract void init();

    void start() {
        this.init();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Tomcat �������� ��ַ��localhost, �˿ڣ�" + port);

            // ��������������
            while (true) {
                new ServletProcess(serverSocket.accept(), urlServletMap).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
