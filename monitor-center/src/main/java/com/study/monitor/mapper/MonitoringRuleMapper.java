package com.study.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.study.monitor.entity.MonitoringRuleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MonitoringRuleMapper extends BaseMapper<MonitoringRuleEntity> {
}