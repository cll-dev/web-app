package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdDesignerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

/**
 * 设计师表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 17:14:43
 */
@Mapper
public interface AdDesignerDao extends BaseMapper<AdDesignerEntity> {

    @Select("select d.*,o.url as headImgUrl from ad_designer d left join ad_oss o on d.head_img_id=o.id limit #{start},#{end}")
    List<AdDesignerEntity> findPageList(long start,long end);

    @Select("select d.*,o.url as headImgUrl from ad_designer d left join ad_oss o on d.head_img_id=o.id where d.id=#{id}")
    AdDesignerEntity findById(Long id);

    @SelectProvider(type= DesignerProvider.class,method = "findPages")
    List<AdDesignerEntity> findPages(long start,long end);

    @SuppressWarnings("unused")
    class DesignerProvider{
        public String findPages(Map<String,Object> params){
            long start = (long)params.get("start");
            long end = (long)params.get("end");

            return new SQL(){{
                SELECT("d.*,o.url as headImgUrl");
                FROM("ad_designer d");
                LEFT_OUTER_JOIN("ad_oss o on d.head_img_id=o.id order by  d.view_counts desc limit "+start+","+end);
            }}.toString();
        }
    }
}
