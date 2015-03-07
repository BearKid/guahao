package com.lwb.guahao.webapp.service;

import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.webapp.component.BuildAppConfigComponent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Function:
 *
 * @autor:Lu Weibiao Date: 2015/3/5 17:14
 */
@Service
public class AppInitService {
    @Resource
    private BuildAppConfigComponent buildAppConfigComponent;
    @Resource HospitalService hospitalService;
    public void init(){
        if(buildAppConfigComponent.isDebug()){
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
    }
}
