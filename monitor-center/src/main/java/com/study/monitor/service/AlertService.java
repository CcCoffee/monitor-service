package com.study.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.monitor.modal.entity.AlertEntity;
import com.study.monitor.modal.entity.ServerEntity;
import com.study.monitor.modal.request.AlertQO;

import java.util.List;

public interface AlertService extends IService<AlertEntity> {

    IPage<AlertEntity> selectMyPage(Page<ServerEntity> page, AlertQO alertQO);

    Boolean saveOrUpdateEntity(AlertEntity alertEntity);

    List<String> getAllApplicationName(AlertQO alertQO);

    Boolean updateStatus(Integer alertId, String status);
}