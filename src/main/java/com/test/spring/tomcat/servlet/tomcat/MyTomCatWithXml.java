package com.test.spring.tomcat.servlet.tomcat;

import java.util.HashMap;
import java.util.List;

import com.test.spring.tomcat.servlet.util.UtilsXml;
import org.dom4j.Element;

/**
 * @author : zhangjh
 * @date : 2019-03-19
 * Description: ʹ��web.xml��ʽ��ʼ����TomCat
 */
public class MyTomCatWithXml extends BaseTomCat {

    /** web.xml���÷�ʽ�������洢servlet */
    private static final HashMap<String, String> SERVLET = new HashMap<String, String>();

    public MyTomCatWithXml(int port) {
        super(port);
    }

    @Override
    void init() {

        this.urlServletMap = new HashMap<String, String>();
        try {
            System.out.println("���������ļ���ʼ");

            // web.xml��ʽ
            UtilsXml xml = new UtilsXml(UtilsXml.class.getResource("/") + "web.xml");

            // ��servlet��洢�����������ɶ���
            List<Element> list = xml.getNodes("servlet");
            for (Element element : list) {
                SERVLET.put(element.elementText("servlet-name"), element.elementText("servlet-class"));
            }

            // ����ӳ���ϵ
            List<Element> mappings = xml.getNodes("servlet-mapping");
            for (Element mapping : mappings) {
                urlServletMap.put(mapping.elementText("url-pattern"), SERVLET.get(mapping.elementText("servlet-name")));
            }

            System.out.println("���������ļ�����");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyTomCatWithXml myTomCat = new MyTomCatWithXml(8080);

        myTomCat.start();
    }
}
