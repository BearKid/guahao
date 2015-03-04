package com.lwb.guahao.webapp.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Function:
 *
 * @autor:Administrator Date: 2015/3/4
 * Time: 21:37
 */
@Component
public class BuildAppConfigComponent {
    @Value("${app.debug}")
    private String debug;
    public boolean isDebug(){
        return debug.equals("true");
    }
}
