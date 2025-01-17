package com.itellyou.service.question;

import com.itellyou.model.question.QuestionAnswerDetailModel;
import com.itellyou.model.question.QuestionAnswerModel;
import com.itellyou.model.question.QuestionAnswerTotalDetailModel;
import com.itellyou.model.sys.PageModel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface QuestionAnswerSearchService {

    List<QuestionAnswerDetailModel> search(Collection<Long> ids, Collection<Long> questionIds, String mode, Long searchUserId, Long userId, Boolean hasContent, Boolean isAdopted, Boolean isDisabled, Boolean isPublished, Boolean isDeleted, Long ip, Integer minComment, Integer maxComment,
                                           Integer minView, Integer maxView,
                                           Integer minSupport, Integer maxSupport,
                                           Integer minOppose, Integer maxOppose,
                                           Integer minStar, Integer maxStar,
                                           Long beginTime, Long endTime,
                                           Map<String, String> order,
                                           Integer offset,
                                           Integer limit);

    List<QuestionAnswerDetailModel> search(Collection<Long> ids, Collection<Long> questionIds, String mode, Long searchUserId, Long userId, Boolean hasContent, Boolean isAdopted, Boolean isDisabled, Boolean isPublished, Boolean isDeleted,
                                           Long beginTime, Long endTime,
                                           Map<String, String> order,
                                           Integer offset,
                                           Integer limit);

    List<QuestionAnswerDetailModel> search(Collection<Long> ids, Collection<Long> questionIds, String mode, Long searchUserId, Long userId, Boolean hasContent,
                                           Long beginTime, Long endTime,
                                           Map<String, String> order,
                                           Integer offset,
                                           Integer limit);

    List<QuestionAnswerDetailModel> search(Long questionId, Long searchUserId, Boolean hasContent, Boolean isAdopted, Boolean isDisabled, Boolean isPublished, Boolean isDeleted, Integer minComment, Integer maxComment,
                                           Integer minView, Integer maxView,
                                           Integer minSupport, Integer maxSupport,
                                           Integer minOppose, Integer maxOppose,
                                           Integer minStar, Integer maxStar,
                                           Long beginTime, Long endTime,
                                           Map<String, String> order,
                                           Integer offset,
                                           Integer limit);

    List<QuestionAnswerDetailModel> search(Long questionId, Long searchUserId, Boolean hasContent, Boolean isAdopted, Boolean isDisabled, Boolean isPublished, Boolean isDeleted, Integer minComment, Integer maxComment,
                                           Integer minView, Integer maxView,
                                           Integer minSupport, Integer maxSupport,
                                           Integer minOppose, Integer maxOppose,
                                           Integer minStar, Integer maxStar,
                                           Long beginTime, Long endTime,
                                           Integer offset,
                                           Integer limit);

    int count(Long questionId, Boolean isAdopted, Boolean isDisabled, Boolean isPublished, Boolean isDeleted, Integer minComment, Integer maxComment,
                    Integer minView, Integer maxView,
                    Integer minSupport, Integer maxSupport,
                    Integer minOppose, Integer maxOppose,
                    Integer minStar, Integer maxStar,
                    Long beginTime, Long endTime);

    List<QuestionAnswerDetailModel> search(Long questionId, Long searchUserId, Boolean hasContent, Boolean isAdopted, Boolean isDisabled, Boolean isPublished, Boolean isDeleted,
                                           Long beginTime, Long endTime,
                                           Map<String, String> order,
                                           Integer offset,
                                           Integer limit);

    List<QuestionAnswerDetailModel> search(Long questionId, Long searchUserId, Boolean hasContent, Boolean isAdopted, Boolean isDisabled, Boolean isPublished, Boolean isDeleted,
                                           Long beginTime, Long endTime,
                                           Integer offset,
                                           Integer limit);

    int count(Long questionId, Boolean isAdopted, Boolean isDisabled, Boolean isPublished, Boolean isDeleted, Long beginTime, Long endTime);

    PageModel<QuestionAnswerDetailModel> page(Long questionId, Long searchUserId, Long userId, Boolean hasContent, Boolean isAdopted, Boolean isDisabled, Boolean isPublished, Boolean isDeleted,
                                                    Long beginTime, Long endTime,
                                                    Map<String, String> order,
                                                    Integer offset,
                                                    Integer limit);

    PageModel<QuestionAnswerDetailModel> page(Collection<Long> ids, Collection<Long> questionIds, String mode, Long searchUserId, Long userId, Boolean hasContent, Boolean isAdopted, Boolean isDisabled, Boolean isPublished, Boolean isDeleted, Long ip, Integer minComment, Integer maxComment, Integer minView, Integer maxView, Integer minSupport, Integer maxSupport, Integer minOppose, Integer maxOppose, Integer minStar, Integer maxStar, Long beginTime, Long endTime, Map<String, String> order, Integer offset, Integer limit);

    List<QuestionAnswerModel> searchChild(Collection<Long> ids, Collection<Long> questionIds, String mode, Long userId,
                                          Integer childCount,
                                          Boolean isAdopted,
                                          Boolean isDisabled, Boolean isPublished, Boolean isDeleted,
                                          Long ip, Integer minComment, Integer maxComment,
                                          Integer minView, Integer maxView,
                                          Integer minSupport, Integer maxSupport,
                                          Integer minOppose, Integer maxOppose,
                                          Integer minStar, Integer maxStar,
                                          Long beginTime, Long endTime,
                                          Map<String, String> order);
    
    QuestionAnswerDetailModel getDetail(Long id, Long questionId, String mode, Long searchUserId, Long userId, Boolean hasContent, Boolean isAdopted, Boolean isDisabled, Boolean isPublished, Boolean isDeleted);
    QuestionAnswerDetailModel getDetail(Long id, Long questionId, String mode, Long searchUserId, Long userId, Boolean hasContent);
    QuestionAnswerDetailModel getDetail(Long id, Long questionId, String mode, Long searchUserId, Long userId);

    QuestionAnswerDetailModel getDetail(Long id, Long questionId, String mode, Boolean hasContent);

    QuestionAnswerDetailModel getDetail(Long id, Long questionId, Long searchUserId, Long userId, Boolean hasContent);

    QuestionAnswerDetailModel getDetail(Long id, Long questionId, Boolean hasContent);
    QuestionAnswerDetailModel getDetail(Long id, Long questionId);
    QuestionAnswerDetailModel getDetail(Long id, Boolean hasContent);

    QuestionAnswerDetailModel getDetail(Long id);

    List<QuestionAnswerTotalDetailModel> totalByUser(Collection<Long> userIds,Long searchUserId, Long questionId, Boolean isAdopted,
                                                     Boolean isDisabled, Boolean isPublished, Boolean isDeleted, Long beginTime, Long endTime,
                                                     Map<String, String> order,
                                                     Integer offset,
                                                     Integer limit);
}
