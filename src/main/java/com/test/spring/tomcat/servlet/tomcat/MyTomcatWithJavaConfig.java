package com.test.spring.tomcat.servlet.tomcat;

import java.util.HashMap;

import com.test.spring.tomcat.servlet.conf.ServletMapping;
import com.test.spring.tomcat.servlet.conf.ServletMappingConfig;

/**
 * @author : zhangjh
 * @date : 2019-03-19
 * Description: ʹ��Java���÷�ʽ��ʼ����TomCat
 */
public class MyTomcatWithJavaConfig extends BaseTomCat {

    public MyTomcatWithJavaConfig(int port) {
        super(port);
    }

    @Override
    public void init() {
        this.urlServletMap = new HashMap<String, String>();
        try {
            System.out.println("���������ļ���ʼ");

            // Java class��ʽ
            for (ServletMapping mapping : ServletMappingConfig.SERVLET_MAPPINGS) {
                this.urlServletMap.put(mapping.getUrl(), mapping.getClazz());
            }

            System.out.println("���������ļ�����");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyTomcatWithJavaConfig myTomcat = new MyTomcatWithJavaConfig(8081);

        myTomcat.start();
    }
}
