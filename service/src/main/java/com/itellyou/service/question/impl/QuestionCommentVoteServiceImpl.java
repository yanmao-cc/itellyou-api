package com.itellyou.service.question.impl;

import com.itellyou.dao.question.QuestionCommentVoteDao;
import com.itellyou.model.event.QuestionCommentEvent;
import com.itellyou.model.question.QuestionCommentModel;
import com.itellyou.model.question.QuestionCommentVoteModel;
import com.itellyou.model.sys.EntityAction;
import com.itellyou.model.sys.VoteType;
import com.itellyou.service.common.VoteService;
import com.itellyou.service.event.OperationalPublisher;
import com.itellyou.service.question.QuestionCommentSearchService;
import com.itellyou.service.question.QuestionCommentService;
import com.itellyou.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.Map;

@Service
public class QuestionCommentVoteServiceImpl implements VoteService<QuestionCommentVoteModel> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final QuestionCommentVoteDao voteDao;
    private final QuestionCommentService commentService;
    private final QuestionCommentSearchService searchService;
    private final OperationalPublisher operationalPublisher;

    @Autowired
    public QuestionCommentVoteServiceImpl(QuestionCommentVoteDao voteDao, QuestionCommentService commentService, QuestionCommentSearchService searchService, OperationalPublisher operationalPublisher){
        this.voteDao = voteDao;
        this.commentService = commentService;
        this.searchService = searchService;
        this.operationalPublisher = operationalPublisher;
    }

    @Override
    public int insert(QuestionCommentVoteModel voteModel) {
        return voteDao.insert(voteModel);
    }

    @Override
    public int deleteByTargetIdAndUserId(Long commentId, Long userId) {
        return voteDao.deleteByCommentIdAndUserId(commentId,userId);
    }

    @Override
    public QuestionCommentVoteModel findByTargetIdAndUserId(Long commentId, Long userId) {
        return voteDao.findByCommentIdAndUserId(commentId,userId);
    }

    @Override
    @Transactional
    public Map<String, Object> doVote(VoteType type, Long id, Long userId, Long ip) {
        try{
            QuestionCommentVoteModel voteModel = findByTargetIdAndUserId(id,userId);

            if(voteModel != null){
                int result = deleteByTargetIdAndUserId(id,userId);
                if(result != 1) throw new Exception("删除Vote失败");
                result = commentService.updateVote(voteModel.getType(),-1,id);
                if(result != 1) throw new Exception("更新Vote失败");
            }
            if(voteModel == null || !voteModel.getType().equals(type)){
                int result = insert(new QuestionCommentVoteModel(id,type, DateUtils.getTimestamp(),userId, ip));
                if(result != 1) throw new Exception("写入Vote失败");
                result = commentService.updateVote(type,1,id);
                if(result != 1) throw new Exception("更新Vote失败");
            }

            QuestionCommentModel commentModel = searchService.findById(id);
            if(commentModel == null) throw new Exception("获取评论失败");
            if(commentModel.getCreatedUserId().equals((userId))) throw new Exception("不能给自己点赞");
            Map<String,Object> data = new HashMap<>();
            data.put("id",commentModel.getId());
            data.put("parentId",commentModel.getParentId());
            data.put("support",commentModel.getSupport());
            data.put("oppose",commentModel.getOppose());
            if(voteModel != null){
                operationalPublisher.publish(new QuestionCommentEvent(this,
                        voteModel.getType().equals(VoteType.SUPPORT) ? EntityAction.UNLIKE : EntityAction.UNDISLIKE,
                        commentModel.getId(),commentModel.getCreatedUserId(),userId, DateUtils.getTimestamp(),ip));
            }
            if(voteModel == null || !voteModel.getType().equals(type)){
                operationalPublisher.publish(new QuestionCommentEvent(this,
                        type.equals(VoteType.SUPPORT) ? EntityAction.LIKE : EntityAction.DISLIKE,
                        commentModel.getId(),commentModel.getCreatedUserId(),userId, DateUtils.getTimestamp(),ip));
            }
            return data;
        }catch (Exception e){
            logger.error(e.getLocalizedMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }
    }
}