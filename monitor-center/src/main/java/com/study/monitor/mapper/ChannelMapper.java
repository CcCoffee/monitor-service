package com.study.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.monitor.modal.entity.ChannelEntity;
import com.study.monitor.modal.request.ChannelQO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChannelMapper extends BaseMapper<ChannelEntity> {

    List<ChannelEntity> findByParams(ChannelQO channelQO);

    IPage<ChannelEntity> selectMyPage(Page<ChannelEntity> page, ChannelQO channelQO);

    int insertChannel(ChannelEntity entity);
}