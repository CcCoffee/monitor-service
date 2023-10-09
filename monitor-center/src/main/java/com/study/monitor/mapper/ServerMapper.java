package com.study.monitor.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.monitor.modal.entity.ServerEntity;
import com.study.monitor.modal.request.ServerQO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ServerMapper extends BaseMapper<ServerEntity> {

    List<ServerEntity> selectAllWithRules();

    List<ServerEntity> findByParams(ServerQO serverQO);

    IPage<ServerEntity> selectMyPage(Page<ServerEntity> page, ServerQO serverQO);
}