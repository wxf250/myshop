package com.wxf.utils;

/**
 * 项目的常量类
 */
public class Constants {
    public static final String TAG = "method";
    public static final String FORWARD = "forward:";
    public static final String REDIRECT = "redirect:";
    public static final String FLAG = ":";
    public static final String INDEX = "index";
    // 用户模块常量类
    public static final String HAS_USER = "1";
    public static final String NOT_HAS_USER = "0";
    // 用户已激活
    public static final Integer USER_ACTIVE = 1;
    // 用户未激活
    public static final Integer USER_NOT_ACTIVE = 0;
    // 用户为普通用户
    public static final Integer USER_MEMBER = 0;
    // 用户为管理员
    public static final Integer USER_MANAGER = 1;

    /*
    用户激活常量
     */
    public static final Integer ACTIVE_SUCCESS = 1;
    public static final Integer ACTIVE_FAIL = 0;
    public static final Integer IS_ACTIVE = 2;

    /*
    用户自动登录用户名
     */
    public static final String AUTO_NAME = "autoName";
    /*
    地址常量
     */
    public static final Integer DEFAULT_ADDRESS = 1; // 默认地址
    public static final Integer NO_DEFAULT_ADDRESS = 0; // 不是默认地址

    /*
    订单状态
     */
    public static final Integer HAS_PAY = 1; // 已付款
    public static final Integer NO_PAY = 0; // 未付款



}
