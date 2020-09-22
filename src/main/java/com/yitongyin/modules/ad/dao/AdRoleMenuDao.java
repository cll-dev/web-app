package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdMenuEntity;
import com.yitongyin.modules.ad.entity.AdRoleMenuEntity;
import com.yitongyin.modules.mp.View.SceneMerchantView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色与菜单对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-12-05 13:40:32
 */
@Mapper
public interface AdRoleMenuDao extends BaseMapper<AdRoleMenuEntity> {

    @Select("SELECT b.* FROM ad_role_menu a LEFT JOIN ad_menu b on a.menu_id=b.menuId WHERE a.role_id=#{roleId}")
    List<AdMenuEntity> getMenuByRoleId(Long roleId);
	
}
