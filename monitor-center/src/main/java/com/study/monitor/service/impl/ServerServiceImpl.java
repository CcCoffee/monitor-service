package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.monitor.modal.entity.ServerEntity;
import com.study.monitor.mapper.ServerMapper;
import com.study.monitor.modal.request.ServerQO;
import com.study.monitor.service.ServerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<ServerEntity> findByParams(ServerQO serverQO) {
        return this.baseMapper.findByParams(serverQO);
    }

    @Override
    public IPage<ServerEntity> selectMyPage(Page<ServerEntity> page, ServerQO serverQO) {
        return this.baseMapper.selectMyPage(page, serverQO);
    }

    @Override
    public boolean saveOrUpdateEntity(ServerEntity server) {
        Date now = new Date();
        if (null == server.getCreatedBy() || server.getCreatedBy().isEmpty()) {
            server.setCreatedBy("admin");
        }
        if (null == server.getUpdatedBy() || server.getUpdatedBy().isEmpty()) {
            server.setUpdatedBy("admin");
        }
        if(server.getId() == null) {
            server.setCreateDate(now);
            server.setUpdateDate(now);
        }
        return this.saveOrUpdate(server);
    }
}