package com.itellyou.dao.article;

import com.itellyou.model.article.ArticleCommentModel;
import com.itellyou.model.sys.VoteType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ArticleCommentDao {
    int insert(ArticleCommentModel commentModel);

    ArticleCommentModel findById(Long id);

    List<ArticleCommentModel> search(@Param("ids") Collection<Long> ids, @Param("articleId") Long articleId, @Param("parentIds") Collection<Long> parentIds, @Param("replyId") Long replyId, @Param("userId") Long userId,
                                           @Param("isDeleted") Boolean isDeleted,
                                           @Param("minComment") Integer minComment, @Param("maxComment") Integer maxComment,
                                           @Param("minSupport") Integer minSupport, @Param("maxSupport") Integer maxSupport,
                                           @Param("minOppose") Integer minOppose, @Param("maxOppose") Integer maxOppose,
                                           @Param("beginTime") Long beginTime, @Param("endTime") Long endTime,
                                           @Param("ip") Long ip,
                                           @Param("order") Map<String, String> order,
                                           @Param("offset") Integer offset,
                                           @Param("limit") Integer limit);

    int count(@Param("ids") Collection<Long> ids, @Param("articleId") Long articleId, @Param("parentIds") Collection<Long> parentIds, @Param("replyId") Long replyId, @Param("userId") Long userId,
                    @Param("isDeleted") Boolean isDeleted,
                    @Param("minComment") Integer minComment, @Param("maxComment") Integer maxComment,
                    @Param("minSupport") Integer minSupport, @Param("maxSupport") Integer maxSupport,
                    @Param("minOppose") Integer minOppose, @Param("maxOppose") Integer maxOppose,
                    @Param("beginTime") Long beginTime, @Param("endTime") Long endTime,
                    @Param("ip") Long ip);

    List<ArticleCommentModel> searchChild(@Param("ids") Collection<Long> ids, @Param("articleId") Long articleId, @Param("parentIds") Collection<Long> parentIds, @Param("replyId") Long replyId, @Param("userId") Long userId,
                                     @Param("isDeleted") Boolean isDeleted, @Param("childCount") Integer childCount,
                                     @Param("minComment") Integer minComment, @Param("maxComment") Integer maxComment,
                                     @Param("minSupport") Integer minSupport, @Param("maxSupport") Integer maxSupport,
                                     @Param("minOppose") Integer minOppose, @Param("maxOppose") Integer maxOppose,
                                     @Param("beginTime") Long beginTime, @Param("endTime") Long endTime,
                                     @Param("ip") Long ip,
                                     @Param("order") Map<String, String> order);

    int updateDeleted(@Param("id") Long id, @Param("isDeleted") Boolean isDeleted);

    int updateComments(@Param("id") Long id, @Param("value") Integer value);

    int updateVote(@Param("type") VoteType type, @Param("value") Integer value, @Param("id") Long id);
}
