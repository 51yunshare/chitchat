package com.chitchat.admin.controller.sys;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.chitchat.admin.api.dto.ClientAuthDTO;
import com.chitchat.admin.bean.SysOauthClient;
import com.chitchat.admin.dto.SysOauthClientPageListRequest;
import com.chitchat.admin.service.SysOauthClientServiceI;
import com.chitchat.admin.vo.sys.ClientPageVO;
import com.chitchat.common.base.BaseController;
import com.chitchat.common.base.ResultTemplate;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api(tags = "客户端接口")
@RestController
@RequestMapping("/admin/oauth")
@RequiredArgsConstructor
public class SysOauthClientController extends BaseController {

    private final SysOauthClientServiceI clientService;

    @ApiOperation(value = "客户端分页列表")
    @GetMapping
    public ResultTemplate listClientPages(@RequestBody SysOauthClientPageListRequest pageListRequest) {
        PageInfo<ClientPageVO> result = clientService.list(pageListRequest);
        return this.success(result, result.getList());
    }

    @ApiOperation(value = "客户端详情")
    @GetMapping("/{clientId}")
    public ResultTemplate detail(@ApiParam("客户端ID") @PathVariable String clientId) {
        SysOauthClient client = clientService.getByClientId(clientId);
        return this.success(client);
    }

    @ApiOperation(value = "新增客户端")
    @PostMapping
    public ResultTemplate add(@RequestBody SysOauthClient client) {
        clientService.insert(client);
        return this.success();
    }

    @ApiOperation(value = "修改客户端")
    @PutMapping(value = "/{clientId}")
    public ResultTemplate update(@ApiParam("客户端ID") @PathVariable Long id,
                                 @RequestBody SysOauthClient client) {
        client.setId(id);
        clientService.updateByPrimaryKeySelective(client);
        return this.success();
    }

    @ApiOperation(value = "删除客户端")
    @DeleteMapping("/{ids}")
    public ResultTemplate delete(@ApiParam("客户端ID，多个以英文逗号(,)分割") @PathVariable("ids") String ids) {
        clientService.deleteByIds(Arrays.asList(ids.split(",")));
        return this.success();
    }

    @ApiOperation(value = "获取 OAuth2 客户端认证信息", notes = "Feign 调用", hidden = true)
    @GetMapping("/getOAuth2ClientById")
    public ClientAuthDTO getOAuth2ClientById(@ApiParam("客户端ID") @RequestParam String clientId) {
        SysOauthClient client = clientService.getByClientId(clientId);
        Assert.isTrue(client != null, "OAuth2 客户端不存在");
        ClientAuthDTO clientAuthDTO = new ClientAuthDTO();
        BeanUtil.copyProperties(client, clientAuthDTO);
        return clientAuthDTO;
    }
}
