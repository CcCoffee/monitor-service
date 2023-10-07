package com.study.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.monitor.entity.ServerEntity;

import java.util.List;

public interface ServerService extends IService<ServerEntity> {
    List<String> getAgentHostNameList();
    List<ServerEntity> listAll();
}