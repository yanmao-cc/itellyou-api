package com.itellyou.service.user.star.impl;

import com.itellyou.dao.user.UserStarDao;
import com.itellyou.model.constant.CacheKeys;
import com.itellyou.model.event.UserEvent;
import com.itellyou.model.sys.EntityAction;
import com.itellyou.model.sys.PageModel;
import com.itellyou.model.user.UserInfoModel;
import com.itellyou.model.user.UserStarDetailModel;
import com.itellyou.model.user.UserStarModel;
import com.itellyou.service.common.StarService;
import com.itellyou.service.event.OperationalPublisher;
import com.itellyou.service.user.UserInfoService;
import com.itellyou.service.user.UserSingleService;
import com.itellyou.service.user.star.UserStarSingleService;
import com.itellyou.util.DateUtils;
import com.itellyou.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = CacheKeys.USER_STAR_KEY)
@Service
public class UserStarServiceImpl implements StarService<UserStarModel> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserStarDao starDao;
    private final UserInfoService infoService;
    private final UserSingleService singleService;
    private final UserStarSingleService starSingleService;

    private final OperationalPublisher operationalPublisher;

    @Autowired
    public UserStarServiceImpl(UserStarDao starDao, UserInfoService infoService, UserSingleService singleService, UserStarSingleService starSingleService, OperationalPublisher operationalPublisher){
        this.starDao = starDao;
        this.infoService = infoService;
        this.singleService = singleService;
        this.starSingleService = starSingleService;
        this.operationalPublisher = operationalPublisher;
    }

    @Override
    @Transactional
    @Caching(evict = {  @CacheEvict(key = "T(String).valueOf(#model.userId).concat('-').concat(#model.createdUserId).concat('-detail')") , @CacheEvict(key = "T(String).valueOf(#model.userId).concat('-').concat(#model.createdUserId)") })
    public int insert(UserStarModel model) throws Exception {
        try{
            UserStarModel starModel = starSingleService.find(model.getUserId(),model.getCreatedUserId());
            if(starModel != null) return 1;
            int result = starDao.insert(model);
            if(result != 1) throw new Exception("写入关注记录失败");
            result = infoService.updateStarCount(model.getCreatedUserId(),1);
            if(result != 1) throw new Exception("更新关注数失败");
            result = infoService.updateFollowerCount(model.getUserId(),1);
            if(result != 1) throw new Exception("更新被关注数失败");
            operationalPublisher.publish(new UserEvent(this, EntityAction.FOLLOW,model.getUserId(),model.getUserId(),model.getCreatedUserId(), DateUtils.toLocalDateTime(),model.getCreatedIp()));
        }catch (Exception e){
            logger.error(e.getLocalizedMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
        RedisUtils.clear(CacheKeys.TAG_STAR_KEY + "_" + model.getCreatedUserId());
        return 1;
    }

    @Override
    @Transactional
    @Caching(evict = {  @CacheEvict(key = "T(String).valueOf(#userId).concat('-').concat(#followerId).concat('-detail')") , @CacheEvict(key = "T(String).valueOf(#userId).concat('-').concat(#followerId)") })
    public int delete(Long userId, Long followerId,Long ip) throws Exception {
        UserInfoModel infoModel = singleService.findById(userId);
        try{
            if(infoModel == null) throw new Exception("错误的用户ID");
            UserStarModel starModel = starSingleService.find(userId,followerId);
            if(starModel == null) return 1;
            int result = starDao.delete(userId,followerId);
            if(result != 1) throw new Exception("删除关注记录失败");
            result = infoService.updateStarCount(followerId,-1);
            if(result != 1) throw new Exception("更新关注数失败");
            result = infoService.updateFollowerCount(userId,-1);
            if(result != 1) throw new Exception("更新被关注数失败");
            operationalPublisher.publish(new UserEvent(this, EntityAction.UNFOLLOW,userId,followerId,userId, DateUtils.toLocalDateTime(),ip));
        }catch (Exception e){
            logger.error(e.getLocalizedMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
        RedisUtils.clear(CacheKeys.TAG_STAR_KEY + "_" + userId);
        return 1;
    }

    @Override
    public List<UserStarDetailModel> search(Collection<Long> targetId, Long userId, Long beginTime, Long endTime, Long ip, Map<String, String> order, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public int count(Collection<Long> userId, Long followerId, Long beginTime, Long endTime, Long ip) {
        return starDao.count(userId,followerId,beginTime,endTime,ip);
    }

    @Override
    public PageModel<UserStarDetailModel> page(Collection<Long> targetId, Long userId, Long beginTime, Long endTime, Long ip, Map<String, String> order, Integer offset, Integer limit) {
        return null;
    }


}
