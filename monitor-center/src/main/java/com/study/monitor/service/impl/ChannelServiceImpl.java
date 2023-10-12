package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.monitor.mapper.ChannelMapper;
import com.study.monitor.modal.entity.ChannelEntity;
import com.study.monitor.modal.request.ChannelQO;
import com.study.monitor.service.ChannelService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, ChannelEntity> implements ChannelService {

    @Override
    public List<ChannelEntity> findByParams(ChannelQO channelQO) {
        return this.baseMapper.findByParams(channelQO);
    }

    @Override
    public IPage<ChannelEntity> selectMyPage(Page<ChannelEntity> page, ChannelQO channelQO) {
        return this.baseMapper.selectMyPage(page, channelQO);
    }

    @Override
    public Boolean saveOrUpdateEntity(ChannelEntity channel) {
        Date now = new Date();
        if (null == channel.getCreatedBy() || channel.getCreatedBy().isEmpty()) {
            channel.setCreatedBy("admin");
        }
        if (null == channel.getUpdatedBy() || channel.getUpdatedBy().isEmpty()) {
            channel.setUpdatedBy("admin");
        }
        if(channel.getId() == null) {
            channel.setCreateDate(now);
            channel.setUpdateDate(now);
            return this.baseMapper.insertChannel(channel) == 0;
        } else {
            return this.saveOrUpdate(channel);
        }
    }
}
