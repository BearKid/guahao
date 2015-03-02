package com.lwb.guahao.webapp.listener;

import com.lwb.guahao.webapp.component.WebPageComponent;
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
        System.out.println("####ContextPath:" + sc.getContextPath() + "####");
        sc.setAttribute("contextPath", StringUtils.isEmpty(sc.getContextPath())?"/":sc.getContextPath()); //应用上下文路径
        System.out.println("####ContextPath:" + (StringUtils.isEmpty(sc.getContextPath())?"/":sc.getContextPath()) + "####");
        System.out.println("========= appliction[guahao] initialization is finished ===========");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("========= appliction[guahao] is destroyed ===========");
    }
}
