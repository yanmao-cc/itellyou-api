package com.itellyou.service.sys.impl;

import com.itellyou.dao.sys.ReportDao;
import com.itellyou.model.article.ArticleCommentModel;
import com.itellyou.model.article.ArticleInfoModel;
import com.itellyou.model.column.ColumnInfoModel;
import com.itellyou.model.question.QuestionAnswerCommentModel;
import com.itellyou.model.question.QuestionAnswerModel;
import com.itellyou.model.question.QuestionCommentModel;
import com.itellyou.model.question.QuestionInfoModel;
import com.itellyou.model.sys.ReportAction;
import com.itellyou.model.sys.ReportModel;
import com.itellyou.model.tag.TagInfoModel;
import com.itellyou.model.sys.EntityType;
import com.itellyou.model.user.UserInfoModel;
import com.itellyou.service.article.ArticleCommentSearchService;
import com.itellyou.service.article.ArticleSearchService;
import com.itellyou.service.column.ColumnSearchService;
import com.itellyou.service.question.QuestionAnswerCommentSearchService;
import com.itellyou.service.question.QuestionAnswerSearchService;
import com.itellyou.service.question.QuestionCommentSearchService;
import com.itellyou.service.question.QuestionSearchService;
import com.itellyou.service.sys.ReportService;
import com.itellyou.service.tag.TagSearchService;
import com.itellyou.service.user.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportDao reportDao;
    private final UserSearchService userSearchService;
    private final QuestionSearchService questionSearchService;
    private final QuestionAnswerSearchService answerSearchService;
    private final ArticleSearchService articleSearchService;
    private final ColumnSearchService columnSearchService;
    private final QuestionCommentSearchService questionCommentSearchService;
    private final QuestionAnswerCommentSearchService answerCommentSearchService;
    private final ArticleCommentSearchService articleCommentSearchService;
    private final TagSearchService tagSearchService;

    @Autowired
    public ReportServiceImpl(ReportDao reportDao, UserSearchService userSearchService, QuestionSearchService questionSearchService, QuestionAnswerSearchService answerSearchService, ArticleSearchService articleSearchService, ColumnSearchService columnSearchService, QuestionCommentSearchService questionCommentSearchService, QuestionAnswerCommentSearchService answerCommentSearchService, ArticleCommentSearchService articleCommentSearchService, TagSearchService tagSearchService){
        this.reportDao = reportDao;
        this.userSearchService = userSearchService;
        this.questionSearchService = questionSearchService;
        this.answerSearchService = answerSearchService;
        this.articleSearchService = articleSearchService;
        this.columnSearchService = columnSearchService;
        this.questionCommentSearchService = questionCommentSearchService;
        this.answerCommentSearchService = answerCommentSearchService;
        this.articleCommentSearchService = articleCommentSearchService;
        this.tagSearchService = tagSearchService;
    }

    @Override
    public int insert(ReportAction action, EntityType type, Long targetId, String description, Long userId, Long ip) throws Exception {
        Long targetUserId = null;
        switch (type){
            case USER:
                UserInfoModel infoModel = userSearchService.findById(targetId);
                if(infoModel != null) targetUserId = infoModel.getId();
                break;
            case QUESTION:
                QuestionInfoModel questionInfoModel = questionSearchService.findById(targetId);
                if(questionInfoModel != null) targetUserId = questionInfoModel.getCreatedUserId();
                break;
            case ANSWER:
                QuestionAnswerModel answerModel = answerSearchService.findById(targetId);
                if(answerModel != null) targetUserId = answerModel.getCreatedUserId();
                break;
            case ARTICLE:
                ArticleInfoModel articleInfoModel = articleSearchService.findById(targetId);
                if(articleInfoModel != null) targetUserId = articleInfoModel.getCreatedUserId();
                break;
            case COLUMN:
                ColumnInfoModel columnInfoModel = columnSearchService.findById(targetId);
                if(columnInfoModel != null) targetUserId = columnInfoModel.getCreatedUserId();
                break;
            case QUESTION_COMMENT:
                QuestionCommentModel questionCommentModel = questionCommentSearchService.findById(targetId);
                if(questionCommentModel != null) targetUserId = questionCommentModel.getCreatedUserId();
                break;
            case ANSWER_COMMENT:
                QuestionAnswerCommentModel answerCommentModel = answerCommentSearchService.findById(targetId);
                if(answerCommentModel != null) targetUserId = answerCommentModel.getCreatedUserId();
                break;
            case ARTICLE_COMMENT:
                ArticleCommentModel articleCommentModel = articleCommentSearchService.findById(targetId);
                if(articleCommentModel != null) targetUserId = articleCommentModel.getCreatedUserId();
                break;
            case TAG:
                TagInfoModel tagInfoModel = tagSearchService.findById(targetId);
                if(tagInfoModel != null) targetUserId = tagInfoModel.getCreatedUserId();
                break;

        }
        if(targetUserId == null) throw new Exception("错误的TargetId");
        ReportModel model = new ReportModel();
        model.setAction(action);
        model.setType(type);
        model.setDescription(description);
        model.setTargetId(targetId);
        model.setTargetUserId(targetUserId);
        model.setCreatedUserId(userId);
        model.setCreatedIp(ip);
        return reportDao.insert(model);
    }

    @Override
    public List<ReportModel> search(Long id, Map<ReportAction, HashSet<EntityType>> actionsMap, Integer state, Long targetUserId, Long userId, Long beginTime, Long endTime, Long ip, Map<String, String> order, Integer offset, Integer limit) {
        return reportDao.search(id,actionsMap,state,targetUserId,userId,beginTime,endTime,ip,order,offset,limit);
    }

    @Override
    public int count(Long id, Map<ReportAction, HashSet<EntityType>> actionsMap, Integer state, Long targetUserId, Long userId, Long beginTime, Long endTime, Long ip) {
        return reportDao.count(id,actionsMap,state,targetUserId,userId,beginTime,endTime,ip);
    }
}