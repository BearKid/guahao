package com.lwb.guahao.webapp.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * User: Lu Weibiao
 * Date: 2015/2/22 22:06
 */
@Component
public class WebPageComponent {
    @Value("${img.domain}")
    private String imgDomain; //域名
    @Value("${seo.title}")
    private String seoTitle;

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getDomain() {
        return imgDomain;
    }

    public void setDomain(String domain) {
        this.imgDomain = domain;
    }
}
