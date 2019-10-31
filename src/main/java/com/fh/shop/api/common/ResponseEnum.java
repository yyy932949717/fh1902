package com.fh.shop.api.common;

public enum ResponseEnum {
    USER_LOCK(1000,"账户锁定24小时"),
    USER_SUCCESS(1111,"登陆成功"),
    USERNAME_PASSWORD_IS_NULL(1000,"用户名或密码为空"),
    VIPINFO_IS_NULL(1001,"不存在该用户,请核对用户名"),
    VIP_PASSWORD_ERROR(1002,"会员密码错误"),
    LOGIN_LOCK(1003,"账户已锁定,明日再试！"),
    UPDATE_PASSWORD_NULL(1004,"请注意密码不能为空！"),
    UPDATE_PASSWORD_CONFIRM(1005,"两次密码不一致！"),
    UPDATE_PASSWORD_OLDPASSWORD(1006,"原密码不正确！"),
    UPDATE_PASSWORD_SUCCESS(1007,"修改成功,请重新登陆！"),
    FORGETPASSWORD_USER_NULL(1008,"用户不存在,请判断邮箱是否正确"),
    IDS_IS_NULL(1009,"批量删除ID为空"),
    BRAND_IS_NULL(1010,"ID不存在"),
    PHONECODE_IS_ERROE(1012,"验证码发送失败！"),
    PHONE_IS_NULL(1013,"手机号码为空！"),
    VIP_NAME_IS_NULL(1014,"用户名为空！"),
    VIP_CODE_IS_NULL(1015,"手机验证码为空！"),
    REDIS_CODE_IS_ERROR(1016,"验证码失效！！"),
    VIP_NAME_EXIST(1017,"用户名已注册！！"),
    VIP_PHONE_EXIST(1018,"手机号码已注册！！"),
    VIP_EMAIL_EXIST(1019,"邮箱已注册！！"),
    PHONE_CODE_ERROR(1020,"验证码错误！！"),
    VIP_EMAIL_NULL(1021,"邮箱为空！！"),
    VIP_PHONE_NO(1022,"不是手机号码！！"),
    VIP_PASSWORD_NULL(1023,"密码为空！"),
    HEADER_IS_NULL(1024,"头信息为空！"),
    HEADER_IS_ERROR(1025,"头信息不正确！"),
    HEADER_IS_ERROR2(1026,"头信息出错！"),
    VIPKEY_IS_ESISTS(1027,"登录超时！"),
    SHOP_IS_NO(1028,"商品不存在！"),
    SHOP_ISUP_IS_NO(1029,"商品已下架！"),
    VIPCART_IS_NULL(1030,"购物车为空！"),
    VIPPHONE_ERROR(1031,"该手机号码需要注册！"),
    TOKEN_IS_NULL(1032,"token不存在！"),
    CFCZ(1033,"请勿重复操作！"),
    OREDER_ERROR(1034,"购物车中商品库存不足，下订单失败！"),
    PAYLOG_IS_NULL(1035,"订单不存在！"),
    PAYCODE_IS_ERROR(1036,"支付超时,请重新获取二维码！")
    ;

    private  int code;
    private  String msg;

   private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
