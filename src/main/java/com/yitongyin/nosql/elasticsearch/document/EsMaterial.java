package com.yitongyin.nosql.elasticsearch.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Document(indexName = "yitongyin_material", type = "bus_material", shards = 1, replicas = 0)
//@Mapping(mappingPath = "material_mapping.json")
public class EsMaterial implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    private Long id;

    private Long bmtid;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面图片资源id
     */
    private Long coverResId;
    /**
     * 素材文件类型
     */
    private Integer fileType;
    /**
     * 素材文件资源id
     */
    private Long fileResId;
    /**
     * 关键字
     */
//    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart",type = FieldType.Text)
    private String tagKey;
    /**
     * 创建日期
     */
    private Date createTime;

    private Integer viewCounts;

    private Integer sendCounts;
    private List<Long> bitIds;

}
