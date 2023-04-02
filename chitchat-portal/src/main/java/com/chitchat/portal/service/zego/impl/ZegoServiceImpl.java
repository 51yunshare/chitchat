package com.chitchat.portal.service.zego.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncodeUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chitchat.common.enumerate.HttpMethodName;
import com.chitchat.portal.config.ZegoConfigProperties;
import com.chitchat.portal.dto.room.*;
import com.chitchat.portal.enumerate.EnumZegoRequestEvent;
import com.chitchat.portal.enumerate.EnumZegoServiceType;
import com.chitchat.portal.service.zego.ZegoServiceI;
import com.chitchat.portal.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * Created by Js on 2022/8/16 .
 **/
@Service
@RequiredArgsConstructor
public class ZegoServiceImpl implements ZegoServiceI {

    private final ZegoConfigProperties zegoConfig;

    /**
     * 获取服务类型的服务地址
     *
     * @param type
     * @return
     */
    private final String getServiceUrl(EnumZegoServiceType type) {
        return zegoConfig.getServerUrl().replace("xxx", type.getName());
    }

    /**
     * 公告参数封装
     *
     * @return
     */
    private final RequestUtil.CommonParam getCommonParam() {
        return RequestUtil.getCommonParam(Long.parseLong(zegoConfig.getAppId()), zegoConfig.getServerSecret());
    }

    /**
     * 获取房间用户列表
     * <p>
     * https://rtc-api.zego.im/?Action=DescribeUserNum
     * &RoomId[]=123
     * &RoomId[]=456
     * &<公共请求参数>
     *
     * @param roomId
     * @return
     */
    @Override
    public JSONArray getRoomUserNumByRoomId(Long roomId) {
        Map<String, Object> param = BeanUtil.beanToMap(RequestUtil.getCommonParam(Long.parseLong(zegoConfig.getAppId()), zegoConfig.getServerSecret()));
        param.put("RoomId[]", roomId);
        System.out.println(param);
        RequestUtil.InterRequest request = RequestUtil.InterRequest.builder()
                .httpMethod(HttpMethodName.GET)
                .param(param)
                .uri(RequestUtil.getUrl(getServiceUrl(EnumZegoServiceType.实时语音), EnumZegoRequestEvent.获取房间人数))
                .build();
        JSONObject response = RequestUtil.request(request, false, true);
        return response.getJSONObject("Data").getJSONArray("UserCountList");
    }

    /**
     * 房间人员列表
     * <p>
     * https://rtc-api.zego.im/?Action=DescribeUserList
     * &RoomId=room_demo
     * &Mode=0
     * &Limit=2
     * &Marker=
     * &<公共请求参数>
     *
     * @param requestDTO
     * @return
     */
    @Override
    public JSONObject getRoomUserList(RoomUserListRequestDTO requestDTO) {
        Map<String, Object> param = BeanUtil.beanToMap(RequestUtil.getCommonParam(Long.parseLong(zegoConfig.getAppId()), zegoConfig.getServerSecret()));
        param.put("RoomId", requestDTO.getRoomId());
        param.put("Mode", requestDTO.getMode());
        param.put("Limit", requestDTO.getLimit());
        param.put("Marker", requestDTO.getMarker());
        System.out.println(param);
        RequestUtil.InterRequest request = RequestUtil.InterRequest.builder()
                .httpMethod(HttpMethodName.GET)
                .param(param)
                .uri(RequestUtil.getUrl(getServiceUrl(EnumZegoServiceType.实时语音), EnumZegoRequestEvent.获取房间用户列表))
                .build();
        JSONObject response = RequestUtil.request(request, false, true);
        return response.getJSONObject("Data");
    }

    /**
     * 房间踢人
     * <p>
     * https://rtc-api.zego.im/?Action=KickoutUser
     * &RoomId=123
     * &UserId[]=a&UserId[]=b
     * &CustomReason=%E8%B8%A2%E5%87%BA%E7%94%A8%E6%88%B7-%E5%8E%9F%E5%9B%A0
     * &<公共请求参数>
     * <p>
     * 重复参数UserId[]不使用map
     *
     * @param requestDTO
     */
    @Override
    public JSONObject doRoomKickOutUser(RoomKickOutUserRequestDTO requestDTO) {
        Map<String, Object> params = BeanUtil.beanToMap(this.getCommonParam());
        params.put("RoomId", requestDTO.getRoomId());
        params.put("CustomReason", URLEncodeUtil.encode(requestDTO.getCustomReason()));
        RequestUtil.InterRequest request = RequestUtil.InterRequest.builder()
                .httpMethod(HttpMethodName.GET)
                .param(params)
                .uri(RequestUtil.getUrl(this.getServiceUrl(EnumZegoServiceType.实时语音), EnumZegoRequestEvent.踢出房间用户))
                .build();
        JSONObject response = RequestUtil.request(request, true, "UserId[]", requestDTO.getAccountId());
        return response;
    }

    /**
     * 房间推送广播消息/弹幕消息
     * <p>
     * https://rtc-api.zego.im/?Action=SendBroadcastMessage
     * &RoomId=123
     * &UserId=2323
     * &UserName=345
     * &MessageCategory=1
     * &MessageContent=hello+zego+big+im+-+%E5%B9%BF%E6%92%AD%E6%B6%88%E6%81%AF
     * &<公共请求参数>
     *
     * @param requestDTO
     * @param msgRequestEvent
     * @return
     */
    @Override
    public JSONObject doSendBroadcastMessage(RoomSendBroadcastRequestDTO requestDTO, EnumZegoRequestEvent msgRequestEvent) {
        Map<String, Object> params = BeanUtil.beanToMap(this.getCommonParam());
        params.put("RoomId", requestDTO.getRoomId());
        params.put("UserId", requestDTO.getAccountId());
        params.put("UserName", requestDTO.getNickName());
        params.put("MessageCategory", requestDTO.getMessageCategory());
        params.put("MessageContent", URLEncodeUtil.encode(requestDTO.getMessageContent()));
        RequestUtil.InterRequest request = RequestUtil.InterRequest.builder()
                .httpMethod(HttpMethodName.GET)
                .param(params)
                .uri(RequestUtil.getUrl(this.getServiceUrl(EnumZegoServiceType.实时语音), msgRequestEvent))
                .build();
        JSONObject response = RequestUtil.request(request, false, true);
        return response.getJSONObject("Data");
    }

    /**
     * 房间推送自定义消息
     * <p>
     * https://rtc-api.zego.im/?Action=SendCustomCommand
     * &RoomId=123321
     * &FromUserId=123
     * &ToUserId[]=123
     * &ToUserId[]=456
     * &MessageContent=%E6%8E%A8%E9%80%81%E8%87%AA%E5%AE%9A%E4%B9%89%E4%BF%A1%E6%81%AF-%E6%96%B0%E6%8E%A5%E5%8F%A3
     * &<公共请求参数>
     *
     * @param requestDTO
     * @return
     */
    @Override
    public JSONObject doSendCustomCommand(RoomSendCustomRequestDTO requestDTO) {
        return doSendCustomCommand(requestDTO.getFromUserId(), requestDTO.getRoomId().toString(), requestDTO.getMessageContent(), requestDTO.getToUserId());
    }

    @Override
    public JSONObject doSendCustomCommand(String fromUserId, String roomId, String messageContent, Set<Long> toUserId) {
        Map<String, Object> params = BeanUtil.beanToMap(this.getCommonParam());
        params.put("RoomId", roomId);
        params.put("FromUserId", fromUserId);
        params.put("MessageContent", URLEncodeUtil.encode(messageContent));
        System.out.println(params);
        RequestUtil.InterRequest request = RequestUtil.InterRequest.builder()
                .httpMethod(HttpMethodName.GET)
                .param(params)
                .uri(RequestUtil.getUrl(this.getServiceUrl(EnumZegoServiceType.实时语音), EnumZegoRequestEvent.推送自定义消息))
                .build();
        JSONObject response = RequestUtil.request(request, true, "ToUserId[]", toUserId);
        return response.getJSONObject("Data");
    }

    /**
     * 增加房间流
     * <p>
     * <p>
     * https://rtc-api.zego.im/?Action=AddStream
     * &RoomId=123
     * &UserId=2323
     * &UserName=df
     * &StreamId=852
     * &StreamTitle=%E6%B5%81%E6%A0%87%E9%A2%98&ExtraInfo=%E9%99%84%E5%8A%A0%E4%BF%A1%E6%81%AF
     * &<公共请求参数>
     *
     * @param requestDTO
     * @return
     */
    @Override
    public JSONObject doAddStream(RoomAddStreamRequestDTO requestDTO) {
        Map<String, Object> params = this.aboutStream(requestDTO.getRoomId(), requestDTO.getAccountId(), requestDTO.getNickName(),
                requestDTO.getStreamId());
        params.put("StreamTitle", URLEncodeUtil.encode(requestDTO.getStreamTitle()));
        params.put("ExtraInfo", URLEncodeUtil.encode(requestDTO.getExtraInfo()));
        RequestUtil.InterRequest request = RequestUtil.InterRequest.builder()
                .httpMethod(HttpMethodName.GET)
                .param(params)
                .uri(RequestUtil.getUrl(this.getServiceUrl(EnumZegoServiceType.实时语音), EnumZegoRequestEvent.增加房间流))
                .build();
        JSONObject response = RequestUtil.request(request, false, true);
        return response;
    }

    /**
     * 删除房间流
     * <p>
     * https://rtc-api.zego.im/?Action=DeleteStream
     * &RoomId=123
     * &UserId=2323
     * &UserName=df
     * &StreamId=852
     * &<公共请求参数>
     *
     * @param requestDTO
     * @return
     */
    @Override
    public JSONObject doDeleteStream(RoomDeletedStreamRequestDTO requestDTO) {
        Map<String, Object> params = this.aboutStream(requestDTO.getRoomId(), requestDTO.getAccountId(), requestDTO.getNickName(),
                requestDTO.getStreamId());
        RequestUtil.InterRequest request = RequestUtil.InterRequest.builder()
                .httpMethod(HttpMethodName.GET)
                .param(params)
                .uri(RequestUtil.getUrl(this.getServiceUrl(EnumZegoServiceType.实时语音), EnumZegoRequestEvent.删除房间流))
                .build();
        JSONObject response = RequestUtil.request(request, false, true);
        return response;
    }

    /**
     * 关于房间流封装
     *
     * @param roomId
     * @param userId
     * @param userName
     * @param streamId
     * @return
     */
    private Map<String, Object> aboutStream(Long roomId, String userId, String userName, Integer streamId) {
        Map<String, Object> params = BeanUtil.beanToMap(this.getCommonParam());
        params.put("RoomId", roomId);
        params.put("UserId", userId);
        params.put("UserName", userName);
        params.put("StreamId", streamId);
        return params;
    }

    /**
     * 获取简易流列表
     * <p>
     * https://rtc-api.zego.im/?Action=DescribeSimpleStreamList
     * &RoomId=123
     * &<公共请求参数>
     *
     * @param roomId
     * @return
     */
    @Override
    public JSONObject getSimpleStreamList(Long roomId) {
        Map<String, Object> params = BeanUtil.beanToMap(this.getCommonParam());
        params.put("RoomId", roomId);
        RequestUtil.InterRequest request = RequestUtil.InterRequest.builder()
                .httpMethod(HttpMethodName.GET)
                .param(params)
                .uri(RequestUtil.getUrl(this.getServiceUrl(EnumZegoServiceType.实时语音), EnumZegoRequestEvent.获取简易流列表))
                .build();
        JSONObject response = RequestUtil.request(request, false, true);
        return response.getJSONObject("Data");
    }

    /**
     * 关闭房间
     *
     * https://rtc-api.zego.im/?Action=CloseRoom
     * &RoomId=123
     * &<公共请求参数>
     *
     * @param roomId
     * @return
     */
    @Override
    public JSONObject doCloseRoom(Long roomId) {
        Map<String, Object> params = BeanUtil.beanToMap(this.getCommonParam());
        params.put("RoomId", roomId);
        RequestUtil.InterRequest request = RequestUtil.InterRequest.builder()
                .httpMethod(HttpMethodName.GET)
                .param(params)
                .uri(RequestUtil.getUrl(this.getServiceUrl(EnumZegoServiceType.实时语音), EnumZegoRequestEvent.关闭房间))
                .build();
        JSONObject response = RequestUtil.request(request, false, true);
        return response.getJSONObject("Data");
    }
}
