package com.study.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.monitor.modal.entity.MonitoringRuleEntity;
import com.study.monitor.modal.request.RuleQO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MonitoringRuleMapper extends BaseMapper<MonitoringRuleEntity> {

    MonitoringRuleEntity selectWithServersById(Integer ruleId);

    IPage<MonitoringRuleEntity> selectMyPage(Page<MonitoringRuleEntity> page, RuleQO ruleQO);
}