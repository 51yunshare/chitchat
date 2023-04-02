package com.chitchat.common.constant;

/**
 * Redis常量
 */
public interface RedisConstants {

    /**
     * 业务单号
     */
    String BUSINESS_NO_PREFIX = "BUSINESS_NO:";

    /**
     * 礼物（用于判断礼物效果图是否修改）
     */
    String GIFT_VERSION = "GIFT:";

    /**
     * 互粉关系
     */
    String MUTUAL_FANS = "USER:MUTUAL_FANS:";
    /**
     * 关注数量-用户
     */
    String FOLLOW_NUM_USER = "FOLLOW_NUM_USER:";
    /**
     * 用戶/房间粉丝数量
     */
    String FAN_NUM = "FAN_NUM:";
    /**
     * 关注数量-房间
     *
     */
    String FOLLOW_NUM_ROOM = "FOLLOW_NUM_ROOM:";
    /**
     * 用户个人贡献值累计
     *
     */
    String USER_CONTRIBUTION = "USER:CONTRIBUTION:";
    /**
     * 用户个人接受到贡献值累计
     *
     */
    String USER_RECEIVING_CONTRIBUTION = "USER:RECEIVING:CONTRIBUTION:";
    /**
     * 用户访客数
     */
    String USER_VISITOR = "USER:VISITOR:";
    /**
     * 用户朋友数
     */
    String USER_FRIEND = "USER:FRIEND:";
    /**
     * 用户等级
     */
    String ACCOUNT_LEVEL = "USER:LEVEL:";
    /**
     * 用户每天金币消费累计
     *
     */
    String USER_CONSUMPTION_DAY = "USER:CONSUMPTION:";
    /**
     * 用户粉丝
     */
    String USER_FOLLOWER = "USER:FOLLOWER:";
    /**
     * 用户关注
     */
    String USER_FOLLOWING = "USER:FOLLOWING:";



    /*********** 房间相关 *************/

    /**
     * 房间在线人数
     */
    String ROOM_ONLINE = "ROOM:ONLINE:";
    /**
     * 房间热度
     */
    String ROOM_HOT = "ROOM:HOT:";
    /**
     * 房间贡献值累加
     *
     */
    String ROOM_CONTRIBUTION = "ROOM:CONTRIBUTION:";
    /**
     * 用户在房间贡献值累计
     *
     */
    String ROOM_USER_CONTRIBUTION = "ROOM:USER:CONTRIBUTION:";
}
