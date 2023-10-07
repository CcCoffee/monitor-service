package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.monitor.entity.ServerEntity;
import com.study.monitor.mapper.ServerMapper;
import com.study.monitor.service.ServerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServerServiceImpl extends ServiceImpl<ServerMapper, ServerEntity> implements ServerService {

    @Override
    public List<String> getAgentHostNameList() {
        List<ServerEntity> serverEntities = this.baseMapper.selectList(null);
        List<String> hostnameList = new ArrayList<>();
        for(ServerEntity serverEntity: serverEntities) {
            String hostname = serverEntity.getHostname();
            if(serverEntity.getServerName().equals("Laptop")) {
                hostname = "localhost";
            }
            hostnameList.add(hostname);
        }
        return hostnameList;
    }

    @Override
    public List<ServerEntity> listAll() {
        return this.baseMapper.selectList(null);
    }
}