package com.lwb.guahao.webapp.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.lwb.guahao.common.Constants;
import com.lwb.guahao.model.PerUser;
import com.lwb.guahao.webapp.dao.PerUserDao;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Lu Weibiao on 2015/2/14 22:02.
 */
@Service
@Transactional
public class PerUserService {
    @Resource
    private PerUserDao perUserDao;

    @Transactional
    public PerUser register(PerUser user){
        PerUser newUser = new PerUser();
        BeanUtils.copyProperties(user, newUser);
        user.setAccountStatus(Constants.AccountStatus.UN_VERIFIED);
        user.setCreateDate(new Date());
        perUserDao.save(newUser);
        return newUser;
    }
}
