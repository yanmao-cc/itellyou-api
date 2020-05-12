package com.itellyou.service.user.impl;

import com.itellyou.dao.user.UserLoginLogDao;
import com.itellyou.model.user.UserLoginLogModel;
import com.itellyou.service.user.UserLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "login_token")
@Service
public class UserLoginLogServiceImpl implements UserLoginLogService {

    private final UserLoginLogDao userLoginLogDao;

    @Autowired
    public UserLoginLogServiceImpl(UserLoginLogDao userLoginLogDao){
        this.userLoginLogDao = userLoginLogDao;
    }

    @Override
    @CacheEvict
    public int insert(UserLoginLogModel logInfo) {
        return userLoginLogDao.insert(logInfo);
    }

    @Override
    @Cacheable
    public UserLoginLogModel find(String token) {
        return userLoginLogDao.find(token);
    }

    @Override
    @CacheEvict(key = "#token")
    public int setDisabled(Boolean status, String token) {
        return userLoginLogDao.setDisabled(status,token);
    }
}