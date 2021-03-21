package com.ma.car.result;

/*
* @author ma
* @date 2021/03
*/

public enum ResultCode {

    SUCCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
    /*SUCCESS(200,"成功"),
    TOKEN_IS_EXPIRE(401,"用户消息过期"),
    TOKEN_NOT_EXIST(403,"非法访问"),
    PARAM_NOT_EXIST(100,"参数不全！"),
    USER_NOT_EXIST(500,"账号或密码错误！"),
    USER_IS_EXIST(501,"用户已注册！"),
    USER_IS_EXPIRE(502,"用户已失效"),
    ADMIN_UC_IS_EXIST(600,"此权限已经存在")*/
}
