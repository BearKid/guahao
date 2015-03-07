package com.lwb.guahao.webapp.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * User: Lu Weibiao
 * Date: 2015/2/22 22:06
 */
@Component
public class WebPageComponent {
    private String appContextPath;
    @Value("${jsp.inc.path}")
    private String jspIncPath; //jsp include 文件目录路径
    @Value("${img.domain}")
    private String imgDomain; //域名
    @Value("${seo.title}")
    private String seoTitle; //页面标题

    public String getAppContextPath() {
        return appContextPath;
    }

    public void setAppContextPath(String appContextPath) {
        this.appContextPath = appContextPath;
    }

    public String getImgDomain() {
        return imgDomain;
    }

    public void setImgDomain(String imgDomain) {
        this.imgDomain = imgDomain;
    }

    public String getJspIncPath() {
        return jspIncPath;
    }

    public void setJspIncPath(String jspIncPath) {
        this.jspIncPath = jspIncPath;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }
}
