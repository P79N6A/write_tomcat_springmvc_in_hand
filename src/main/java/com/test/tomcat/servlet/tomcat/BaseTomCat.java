package com.test.tomcat.servlet.tomcat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.test.tomcat.servlet.servlet.ServletProcess;

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
    /** �̳߳�����߳��� */
    private static final int THREAD_POOL_MAX_SIZE = 200;
    /** �̳߳س�פ�߳��� */
    private static final int THREAD_CORE_POOL_SIZE = 10;
    /** �����̴߳��ʱ�� */
    private static final int THREAD_KEEP_ALIVE = 30;

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
                ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("Tomcat-work-%d").build();
                ExecutorService exec = new ThreadPoolExecutor(
                    THREAD_CORE_POOL_SIZE,
                    THREAD_POOL_MAX_SIZE,
                    THREAD_KEEP_ALIVE,
                    TimeUnit.SECONDS,
                    new LinkedBlockingDeque<Runnable>(),
                    threadFactory
                );
                exec.submit(new ServletProcess(serverSocket.accept(), urlServletMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
