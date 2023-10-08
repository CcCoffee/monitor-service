package com.study.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.monitor.entity.ServerEntity;
import com.study.monitor.qo.ServerQO;

import java.util.List;

public interface ServerService extends IService<ServerEntity> {
    List<String> getAgentHostNameList();
    List<ServerEntity> listAll();
    List<ServerEntity> findByParams(ServerQO serverQO);
}