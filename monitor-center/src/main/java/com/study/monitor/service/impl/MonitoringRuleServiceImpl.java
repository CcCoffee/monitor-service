package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.monitor.modal.dto.ServerRulesDTO;
import com.study.monitor.modal.entity.MonitoringRuleEntity;
import com.study.monitor.modal.entity.ServerEntity;
import com.study.monitor.modal.entity.ServerMonitoringRuleEntity;
import com.study.monitor.mapper.MonitoringRuleMapper;
import com.study.monitor.mapper.ServerMapper;
import com.study.monitor.modal.request.RuleQO;
import com.study.monitor.service.MonitoringRuleService;
import com.study.monitor.service.ServerMonitoringRuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonitoringRuleServiceImpl extends ServiceImpl<MonitoringRuleMapper, MonitoringRuleEntity> implements MonitoringRuleService {

    private final ServerMapper serverMapper;
    private final ServerMonitoringRuleService serverMonitoringRuleService;

    @Autowired
    public MonitoringRuleServiceImpl(ServerMapper serverMapper, ServerMonitoringRuleService serverMonitoringRuleService){
        this.serverMapper = serverMapper;
        this.serverMonitoringRuleService = serverMonitoringRuleService;
    }

    @Override
    public List<ServerRulesDTO> getAllServerRules() {
        List<ServerEntity> serverEntities = serverMapper.selectAllWithRules();
        return serverEntities.stream().map(serverEntity -> {
            ServerRulesDTO serverRulesDTO = new ServerRulesDTO();
            BeanUtils.copyProperties(serverEntity, serverRulesDTO);
            return serverRulesDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public MonitoringRuleEntity selectWithServersById(Integer ruleId) {
        return this.baseMapper.selectWithServersById(ruleId);
    }

    @Override
    public boolean saveServerMonitoringRule(ServerMonitoringRuleEntity serverMonitoringRuleEntity) {
        return serverMonitoringRuleService.save(serverMonitoringRuleEntity);
    }

    @Transactional
    @Override
    public boolean saveOrUpdateEntity(MonitoringRuleEntity rule) {
        Date now = new Date();
        if (null == rule.getCreatedBy() || rule.getCreatedBy().isEmpty()) {
            rule.setCreatedBy("admin");
        }
        if (null == rule.getUpdatedBy() || rule.getUpdatedBy().isEmpty()) {
            rule.setUpdatedBy("admin");
        }
        if(rule.getId() == null) {
            rule.setCreateDate(now);
            rule.setUpdateDate(now);
        }
        if(rule.getType().equals("PROCESS")) {
            rule.setLogFilePath(null);
            rule.setLogPatterns(null);
        } else {
            rule.setProcessNameRegex(null);
        }
        this.saveOrUpdate(rule);
        List<ServerEntity> linkedServers = rule.getLinkedServers();
        List<ServerMonitoringRuleEntity> serverMonitoringRuleEntities = linkedServers.stream().map(server -> {
            ServerMonitoringRuleEntity serverMonitoringRuleEntity = new ServerMonitoringRuleEntity();
            serverMonitoringRuleEntity.setMonitoringRuleId(rule.getId());
            serverMonitoringRuleEntity.setServerId(server.getId());
            serverMonitoringRuleEntity.setCreateDate(now);
            serverMonitoringRuleEntity.setUpdateDate(now);
            return serverMonitoringRuleEntity;
        }).collect(Collectors.toList());
        serverMonitoringRuleService.lambdaUpdate().eq(ServerMonitoringRuleEntity::getMonitoringRuleId, rule.getId()).remove();
        serverMonitoringRuleService.saveBatch(serverMonitoringRuleEntities);
        return true;
    }

    @Transactional
    @Override
    public boolean deleteById(Integer ruleId) {
        serverMonitoringRuleService.lambdaUpdate().eq(ServerMonitoringRuleEntity::getMonitoringRuleId, ruleId).remove();
        return this.removeById(ruleId);
    }

    @Override
    public IPage<MonitoringRuleEntity> selectMyPage(Page<MonitoringRuleEntity> page, RuleQO ruleQO) {
        return this.baseMapper.selectMyPage(page, ruleQO);
    }
}