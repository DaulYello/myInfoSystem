package com.personal.info.myInfoSystem.modular.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.personal.info.myInfoSystem.modular.system.entity.Relation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Mapper
public interface RelationMapper extends BaseMapper<Relation> {

}
