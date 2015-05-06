package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.ApiRet;
import com.lwb.guahao.common.constants.Constants;
import com.lwb.guahao.common.util.SecurityUtil;
import com.lwb.guahao.model.PerUser;
import com.lwb.guahao.webapp.dao.PerUserDao;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Lu Weibiao on 2015/2/14 22:02.
 */
@Service
@Transactional
public class PerUserService {
    private final static Logger logger = Logger.getLogger(PerUserService.class);
    @Resource
    private PerUserDao perUserDao;

    /**
     * 个人账号注册
     * @param user
     * @return
     */
    public ApiRet<PerUser> register(PerUser user){
        ApiRet<PerUser> apiRet = new ApiRet<PerUser>();
        if(!isRegistered(user)){
            PerUser newUser = new PerUser();
            BeanUtils.copyProperties(user, newUser);
            newUser.setPassword(SecurityUtil.password(newUser.getPassword()));
            newUser.setAccountStatusCode(Constants.AccountStatus.UN_VERIFIED);
            newUser.setIsEmailBound(false);
            newUser.setIsMobileBound(false);
            newUser.setCreateDateTime(new Date());
            perUserDao.save(newUser);
            apiRet.setRet(ApiRet.RET_SUCCESS);
            apiRet.setMsg("注册成功");
            apiRet.setData(newUser);
        } else {
            apiRet.setRet(ApiRet.RET_FAIL);
            apiRet.setMsg("账号已被注册，请检查邮箱和身份证");
        }
        return apiRet;
    }

    /**
     * 判断用户是否已注册
     * 身份证和邮箱都必须对应唯一账号，但允许多个账号绑定同一个手机号
     * @param user
     * @return
     */
    @Transactional(readOnly = true)
    public boolean isRegistered(PerUser user){
        boolean isRegistered;
        if(perUserDao.existsByEmail(user.getEmail()) || perUserDao.existsByIdCard(user.getIdCard())){
            //erUserDao.existsByMobilePhone(user.getMobilePhone())

            isRegistered = true;
        } else{
            isRegistered = false;
        }
        return isRegistered;
    }
}
