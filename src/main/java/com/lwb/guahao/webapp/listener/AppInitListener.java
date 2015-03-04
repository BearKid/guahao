package com.lwb.guahao.webapp.listener;

import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.component.BuildAppConfigComponent;
import com.lwb.guahao.webapp.component.WebPageComponent;
import com.lwb.guahao.webapp.service.HospitalService;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * User: Lu Weibiao
 * Date: 2015/2/22 21:59
 */
public class AppInitListener implements ServletContextListener{
    private ServletContext sc; //web应用上下文
    private WebApplicationContext springAppContext; //spring上下文
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("========= appliction[guahao] is initialing ===========");
        sc = servletContextEvent.getServletContext();
        springAppContext = WebApplicationContextUtils.getWebApplicationContext(sc);
        WebPageComponent webPageComponent = (WebPageComponent)springAppContext.getBean("webPageComponent"); //获取网站页面信息组件
        sc.setAttribute("imgDomain",webPageComponent.getImgDomain()); //缓存域名
        sc.setAttribute("seoTitle", webPageComponent.getSeoTitle()); //缓存网站title
        sc.setAttribute("jspIncPath",webPageComponent.getJspIncPath()); //jsp include 文件目录路径
        sc.setAttribute("contextPath", sc.getContextPath()); //应用上下文路径
        //调试用
        BuildAppConfigComponent buildAppConfigComponent = (BuildAppConfigComponent)springAppContext.getBean("buildAppConfigComponent");
        if(buildAppConfigComponent.isDebug()){
            HospitalService hospitalService = (HospitalService)springAppContext.getBean("hospitalService");
            Hospital hospital = new Hospital();
            hospital.setEmail("1030240314@qq.com");
            hospital.setPassword("123");
            hospital.setName("芦苇医院");
            hospital.setAreaCode(441900);
            hospital.setLinkman("卢先生");
            hospital.setAddress("东莞神马镇牛逼村掉咋天大厦");
            hospital.setTelPhone("0769-86413795");
            hospital.setBrief("东莞神马镇牛逼村掉咋天大厦，技术牛逼，你的第一选择。");
            hospitalService.register(hospital);
        }
        System.out.println("========= appliction[guahao] initialization is finished ===========");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("========= appliction[guahao] is destroyed ===========");
    }
}
