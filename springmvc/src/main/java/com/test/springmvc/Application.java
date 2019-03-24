package com.test.springmvc;

import com.test.springmvc.annotation.Configuration;
import com.test.springmvc.servlet.tomcat.MyTomCat;

/**
 * @author : jihong.zjh@alibaba-inc.com
 * @date : 2019-03-22
 * Description:
 */
@Configuration(basePackage = "com.test.springmvc", port = 8082)
public class Application {
    private String basePackage;
    private int port;

    /** ������ע���ȡ���� */
    private void loadConfig() {
        try {
            Class<?> clazz = Class.forName(this.getClass().getName());
            if(!clazz.isAnnotationPresent(Configuration.class)) {
                throw new RuntimeException("ȱ��Configurationע��");
            }
            Configuration configuration = clazz.getAnnotation(Configuration.class);
            String basePackage = configuration.basePackage();
            int port = configuration.port();
            this.basePackage = basePackage;
            this.port = port;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    {
        loadConfig();
    }

    private void run() {
        MyTomCat tomCat = new MyTomCat(basePackage, port);
        tomCat.start();
    }

    public static void main(String[] args) {

        new Application().run();
    }
}
