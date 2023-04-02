package com.chitchat.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Js on 2022/7/23
 */
@Data
public class BaseRequest  implements Serializable {
    private UserDto userInfo;
}
