package com.study.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.monitor.modal.entity.ServerEntity;
import com.study.monitor.modal.request.ServerQO;
import com.study.monitor.modal.response.ApiResponse;
import com.study.monitor.service.ServerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servers")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService){
        this.serverService = serverService;
    }

    @GetMapping({"", "/"})
    public ApiResponse<IPage<ServerEntity>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                @RequestParam(required = false) String serverNameFilter,
                                                @RequestParam(required = false) String hostnameFilter) {
        Page<ServerEntity> page = new Page<>(pageNum, pageSize);
        ServerQO serverQO = new ServerQO();
        serverQO.setServerNameFilter(serverNameFilter);
        serverQO.setHostnameFilter(hostnameFilter);
        return ApiResponse.success(serverService.selectMyPage(page, serverQO));
    }

    @PostMapping({"", "/"})
    public ApiResponse<Boolean> save(@RequestBody ServerEntity serverEntity) {
        return ApiResponse.success(serverService.saveOrUpdateEntity(serverEntity));
    }

//    @DeleteMapping("/{serverId}")
//    public boolean deleteById(@PathVariable(name = "serverId") Integer serverId){
//        return serverService.deleteById(ruleId);
//    }


    @GetMapping("/all")
    public ApiResponse<List<ServerEntity>> findAll(@RequestParam(name = "ruleId", required = false) Integer ruleId) {
        ServerQO serverQO = new ServerQO();
        serverQO.setRuleId(ruleId);
        return ApiResponse.success(serverService.findByParams(serverQO));
    }
}
