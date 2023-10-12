package com.study.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.monitor.modal.entity.ChannelEntity;
import com.study.monitor.modal.request.ChannelQO;
import com.study.monitor.modal.response.ApiResponse;
import com.study.monitor.service.ChannelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/channels")
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping({"", "/"})
    public ApiResponse<IPage<ChannelEntity>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  @RequestParam(required = false) String nameFilter,
                                                  @RequestParam(required = false) String typeFilter) {
        Page<ChannelEntity> page = new Page<>(pageNum, pageSize);
        ChannelQO channelQO = new ChannelQO();
        channelQO.setNameFilter(nameFilter);
        channelQO.setTypeFilter(typeFilter);
        return ApiResponse.success(channelService.selectMyPage(page, channelQO));
    }

    @GetMapping("/all")
    public ApiResponse<List<ChannelEntity>> findAll(@RequestParam(required = false) String nameFilter,
                                                    @RequestParam(required = false) String typeFilter,
                                                    @RequestParam(required = false) Integer ruleId) {
        ChannelQO channelQO = new ChannelQO();
        channelQO.setNameFilter(nameFilter);
        channelQO.setTypeFilter(typeFilter);
        channelQO.setRuleId(ruleId);
        return ApiResponse.success(channelService.findByParams(channelQO));
    }

    @PostMapping({"", "/"})
    public ApiResponse<Boolean> save(@RequestBody ChannelEntity channelEntity) {
        return ApiResponse.success(channelService.saveOrUpdateEntity(channelEntity));
    }

    @DeleteMapping("/{channelId}")
    public ApiResponse<Boolean> deleteById(@PathVariable(name = "channelId") Integer channelId) {
        boolean success = channelService.removeById(channelId);
        if (success) {
            return ApiResponse.success(true);
        } else {
            return ApiResponse.badRequest("Please make sure that the channel to be deleted is not being used by any rule.");
        }
    }
}
