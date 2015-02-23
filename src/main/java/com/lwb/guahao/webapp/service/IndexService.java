package com.lwb.guahao.webapp.service;

import com.lwb.guahao.webapp.dao.IndexDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Lu Weibiao on 2015/2/14 20:29.
 */
@Service
public class IndexService {
    @Resource
    private IndexDao indexDao;
}
