package com.lwb.guahao.webapp.service;

import com.lwb.guahao.common.Constants;
import com.lwb.guahao.model.PerUser;
import com.lwb.guahao.webapp.dao.PerUserDao;
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

    @Transactional(readOnly = true)
    public PerUser get(Integer id){
        return perUserDao.get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(PerUser user){
        perUserDao.saveOrUpdate(user);
    }

    public void txTest(){
        PerUser user1 = new PerUser();
        user1.setName("bill");
        user1.setEmail(new Date(System.currentTimeMillis()).toString());
        user1.setIdCard("1254235");
        user1.setAccountStatus(Constants.AccountStatus.FORBIDDEN);
        user1.setCreDate(new Date());
        user1.setPassword("123");
        this.saveOrUpdate(user1);
        System.out.println("user1 save ok!");
        PerUser user = new PerUser();
        user.setId(1);
        user.setName("bill");
        user.setEmail(new Date(System.currentTimeMillis()).toString());
        user.setIdCard("1254235");
        user.setAccountStatus(Constants.AccountStatus.FORBIDDEN);
        user.setCreDate(new Date());
        user.setPassword("123");
        this.saveOrUpdate(user);
        System.out.println("user save ok!");
    }
}
