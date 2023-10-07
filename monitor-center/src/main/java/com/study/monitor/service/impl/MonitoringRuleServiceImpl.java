package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.monitor.dto.ServerRulesDTO;
import com.study.monitor.entity.MonitoringRuleEntity;
import com.study.monitor.entity.ServerEntity;
import com.study.monitor.mapper.MonitoringRuleMapper;
import com.study.monitor.mapper.ServerMapper;
import com.study.monitor.service.MonitoringRuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonitoringRuleServiceImpl extends ServiceImpl<MonitoringRuleMapper, MonitoringRuleEntity> implements MonitoringRuleService {

    private final ServerMapper serverMapper;

    @Autowired
    public MonitoringRuleServiceImpl(ServerMapper serverMapper){
        this.serverMapper = serverMapper;
    }

    @Override
    public List<ServerRulesDTO> getAllRules() {
        List<ServerEntity> serverEntities = serverMapper.selectAll();
        return serverEntities.stream().map(serverEntity -> {
            ServerRulesDTO serverRulesDTO = new ServerRulesDTO();
            BeanUtils.copyProperties(serverEntity, serverRulesDTO);
            return serverRulesDTO;
        }).collect(Collectors.toList());
    }
}