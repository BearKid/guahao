package com.lwb.guahao.webapp.component;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Lu Weibiao
 * Date: 2015/3/15 11:17
 */
@Component
public class PagingComponent {
    public String getQueryStringWithoutPn(HttpServletRequest request){
        String curQueryString = request.getQueryString();
        Matcher matcher = Pattern.compile("pn=\\d+").matcher(curQueryString);
        return matcher.replaceFirst("");
    }
}
