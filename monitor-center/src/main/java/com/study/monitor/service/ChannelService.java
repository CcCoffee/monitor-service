package com.study.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.monitor.modal.entity.ChannelEntity;
import com.study.monitor.modal.request.ChannelQO;

import java.util.List;

public interface ChannelService extends IService<ChannelEntity> {

    List<ChannelEntity> findByParams(ChannelQO channelQO);
    IPage<ChannelEntity> selectMyPage(Page<ChannelEntity> page, ChannelQO channelQO);

    Boolean saveOrUpdateEntity(ChannelEntity channelEntity);
}
