package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.constants.Constants;
import com.lwb.guahao.model.Doctor;
import com.lwb.guahao.model.Hospital;
import com.lwb.guahao.model.PerUser;
import com.lwb.guahao.webapp.component.BuildAppConfigComponent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Function:
 *
 * @autor:Lu Weibiao Date: 2015/3/5 17:14
 */
@Service
@Transactional
public class AppInitService {
    @Resource
    private BuildAppConfigComponent buildAppConfigComponent;
    @Resource
    private HospitalService hospitalService;
    @Resource
    private DoctorService doctorService;
    @Resource
    private PerUserService perUserService;
    public void init(){
        if(buildAppConfigComponent.isDebug()){
            //医院账号
            Hospital hospital = new Hospital();
            hospital.setEmail("1030240314@qq.com");
            hospital.setPassword("123");
            hospital.setName("芦苇医院");
            hospital.setAreaCode(441900);
            hospital.setLinkman("卢先生");
            hospital.setAddress("东莞神马镇牛逼村掉咋天大厦");
            hospital.setTelPhone("0769-86413795");
            hospital.setBrief("东莞神马镇牛逼村掉咋天大厦，技术牛逼，你的第一选择。");
            hospital.setAccountStatusCode(Constants.AccountStatus.UN_VERIFIED);
            Hospital newHospital = hospitalService.register(hospital).getData();
            //医生账号
            Doctor doctor = new Doctor();
            doctor.setAccountName("lwb");
            doctor.setPassword("123");
            doctor.setName("卢大伟");
            doctor.setAge(22);
            doctor.setSex(1);
            doctor.setTitle("专家");
            doctor.setPrice(20.0);
            doctor.setGoodAtTags("腻害,牛逼,十年经验");
            doctor.setBrief("简介简介简介简介简介简介");
            doctor.setDeptClassCode(1001);
            doctor.setHospitalId(newHospital.getId());
//            doctor.setHospital(newHospital);
            doctorService.register(doctor);
            //个人账号
            PerUser perUser = new PerUser();
            perUser.setPassword("123");
            perUser.setIdCard("441900199301206735");
            perUser.setName("芦苇米");
            perUser.setEmail("luwibiao306@163.com");
            perUser.setMobilePhone("15889676260");
            perUserService.register(perUser);
        }
    }
}
