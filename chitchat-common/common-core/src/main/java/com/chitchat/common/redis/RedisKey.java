package com.chitchat.common.redis;

import com.chitchat.common.constant.RedisConstants;

/**
 * redis缓存KEY
 *
 * Created by Js on 2022/8/12 .
 **/
public final class RedisKey {


    public static String getUserToken(String token, Integer userType, Integer userId){
        return token + "t" + userType + "u" + userId;
    }

    public static String getVerifyCode(String vKey){
        return "CAPTCHA:" + vKey;
    }

    public static String getVerifyCodeAccount(String account){
        return "CAPTCHA:NUM:" + account;
    }

    public static String getSmsCode(Class c, String mobile){
        return c.getName() + ".mobile." + mobile;
    }

    /**
     * 礼物版本号(用于判断礼物效果图是否修改)
     *
     * @return
     */
    public static String getGiftVersion(){
        return RedisConstants.GIFT_VERSION + "VERSION";
    }

    /**
     * 互粉
     *
     * @return
     */
    @Deprecated
    public static String getMutualFans(Long userId, Long fansId){
        return RedisConstants.MUTUAL_FANS + userId + ":" + fansId;
    }

    /**
     * 用户/房间粉丝数
     *
     * @param userId
     * @return
     */
    @Deprecated
    public static String getFansNum(Long userId, int type){
        return RedisConstants.FAN_NUM + type + ":" + userId;
    }

    /**
     * 用户关注数(区分关注用户还是关注房间)
     *
     * @param userId
     * @return
     */
    @Deprecated
    public static String getFollowNum(Long userId, int type){
        return RedisConstants.FOLLOW_NUM_USER + type + ":" + userId;
    }

    /**
     * 用户粉丝
     *
     * @param userId
     * @param type
     * @return
     */
    public static String getUserFollower(Long userId, int type){
        return RedisConstants.USER_FOLLOWER + type + ":" + userId;
    }

    /**
     * 用户关注
     *
     * @param userId
     * @param type
     * @return
     */
    public static String getUserFollowing(Long userId, int type){
        return RedisConstants.USER_FOLLOWING + type + ":" + userId;
    }

    /**
     * 用户贡献值
     *
     * @param userId
     * @return
     */
    public static String getUserContributionNum(Long userId){
        return RedisConstants.USER_CONTRIBUTION + userId;
    }

    /**
     * 用户在房间的贡献值
     *
     * @param userId
     * @param roomId
     * @return
     */
    public static String getUserRoomContributionNum(Long userId, Long roomId){
        return RedisConstants.USER_CONTRIBUTION + userId + ":" + roomId;
    }

    /**
     * 用户接受到贡献值
     *
     * @param userId
     * @return
     */
    public static String getUserReceivingContributionNum(Long userId){
        return RedisConstants.USER_RECEIVING_CONTRIBUTION + userId;
    }

    /**
     * 用户在房间接受到贡献值
     *
     * @param userId
     * @return
     */
    public static String getUserRoomReceivingContributionNum(Long userId, Long roomId){
        return RedisConstants.USER_RECEIVING_CONTRIBUTION + userId + ":" + roomId;
    }

    /**
     * 游客数量
     *
     * @param userId
     * @return
     */
    public static String getVisitorsNum(Long userId){
        return RedisConstants.USER_VISITOR + userId;
    }

    /**
     * 朋友数量
     *
     * @param userId
     * @return
     */
    public static String getFriendsNum(Long userId){
        return RedisConstants.USER_FRIEND + userId;
    }

    /**
     * 用户每天金币消费累计
     * @param userId
     * @param date
     * @return
     */
    public static String getUserConsumptionNumOfDay(Long userId, String date){
        return RedisConstants.USER_CONSUMPTION_DAY + userId + ":" + date;
    }

    /************** 房间信息 **************/


    /**
     * 房间在线人数
     *
     * @param roomId
     * @return
     */
    public static String getRoomOnlineNum(Long roomId){
        return RedisConstants.ROOM_ONLINE + roomId;
    }

    /**
     * 房间接受到贡献值
     *
     * @param roomId
     * @return
     */
    public static String getRoomContributionNum(Long roomId){
        return RedisConstants.ROOM_CONTRIBUTION + roomId;
    }
    /**
     * 房间热度值
     *
     * @param roomId
     * @return
     */
    public static String getRoomHotNum(Long roomId){
        return RedisConstants.ROOM_HOT + roomId;
    }
}
