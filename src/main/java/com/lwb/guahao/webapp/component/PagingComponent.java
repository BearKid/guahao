package com.lwb.guahao.webapp.component;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        String queryString = request.getQueryString();
        if(!StringUtils.isEmpty(queryString)) {
            Matcher matcher = Pattern.compile("pn=\\d+").matcher(queryString);
            queryString = matcher.replaceFirst("");
        }
        return queryString;
    }
}
