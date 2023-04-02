package com.chitchat.auth.extension.third.facebook;

import lombok.Data;

/**
 * Facebook用户信息
 *
 * Created by Js on 2022/8/11.
 */
@Data
public class FacebookUserInfo {
    //唯一标识
    private String uuid;
    private String username;
    private String nickname;
    //头像
    private String avatar;
    //性别
    private String gender;
    private String blog;
    private String company;
    private String location;
    private String email;
    private String remark;
    private String source;

}
