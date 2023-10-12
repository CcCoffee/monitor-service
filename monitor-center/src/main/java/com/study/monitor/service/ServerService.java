package com.study.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.monitor.modal.entity.ServerEntity;
import com.study.monitor.modal.request.ServerQO;

import java.util.List;

public interface ServerService extends IService<ServerEntity> {
    List<String> getAgentHostNameList();
    List<ServerEntity> findByParams(ServerQO serverQO);
    IPage<ServerEntity> selectMyPage(Page<ServerEntity> page, ServerQO serverQO);

    boolean saveOrUpdateEntity(ServerEntity serverEntity);
}