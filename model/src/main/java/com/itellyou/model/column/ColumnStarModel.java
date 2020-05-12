package com.itellyou.model.column;

import com.itellyou.model.common.StarModel;
import com.itellyou.util.annotation.JSONDefault;
import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JSONDefault(includes = "base")
public class ColumnStarModel extends StarModel {
    private Long columnId;

    public ColumnStarModel(Long id, Long createdTime, Long userId, Long ip) {
        super();
        this.columnId = id;
        this.setCreatedUserId(userId);
        this.setCreatedTime(createdTime);
        this.setCreatedIp(ip);
    }
}