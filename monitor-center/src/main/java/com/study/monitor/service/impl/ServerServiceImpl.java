package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.monitor.entity.ServerEntity;
import com.study.monitor.mapper.ServerMapper;
import com.study.monitor.service.ServerService;
import org.springframework.stereotype.Service;

@Service
public class ServerServiceImpl extends ServiceImpl<ServerMapper, ServerEntity> implements ServerService {
    
}