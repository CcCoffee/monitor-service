package com.study.monitor.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.monitor.dto.ServerRulesDTO;
import com.study.monitor.entity.ServerEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ServerMapper extends BaseMapper<ServerEntity> {

    List<ServerEntity> selectAll();
}