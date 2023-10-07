package com.study.monitor.controller;

import com.study.monitor.entity.ServerEntity;
import com.study.monitor.service.ServerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/servers")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService){
        this.serverService = serverService;
    }

    @GetMapping({"", "/"})
    public List<ServerEntity> listAll(){
        return serverService.listAll();
    }
}
