package cn.littleBird.core.tools;

import com.alibaba.fastjson.JSONObject;

public class Response {

    private static JSONObject msg;
    public static final int SUCCESS_CODE = 200;// 成功
    public static final int FAIL_CODE = 400;// 失败
    public static final int EXCEPTION_CODE = 99;// 异常
    public static final int TIMEOUT_CODE = 401;// 异常
    public static final int NOTFOUND_EXCEPTION = 404;
    public static final int LOGIN_INVALID = 405;// 登录失效

    public static final String SUCCESS_DESC = "请求成功";
    public static final String FAIL_DESC = "请求失败";
    public static final String EXCEPTION_DESC = "请求异常";
    public static final String TIMEOUT_DESC = "请求超时";

    // 删除标志 0：正常 1：已删除
    public static final Integer EXIST_FLAG = 0;
    public static final Integer DELETE_FLAG = 1;

    public static JSONObject getMsg() {
        msg = new JSONObject();
        msg.put("code", "");
        msg.put("msg", "");
        msg.put("data", "");
        return msg;
    }

//    public static JSONObject getMsg(String status,Object object) {
//        msg = new JSONObject();
//        status = status.toLowerCase();//success,port:53465
//        String port = status.substring(status.indexOf("port:") + 5);
//        if ("success".equals(status.substring(0,7))) {
//            msg.put("code", SUCCESS_CODE);
//            msg.put("msg", SUCCESS_DESC);
//        } else if ("fail".equals(status.substring(0,4))) {
//            msg.put("code", FAIL_CODE);
//            msg.put("msg", FAIL_DESC);
//        } else if ("timeout".equals(status.substring(0,7))) {
//            msg.put("code", TIMEOUT_CODE);
//            msg.put("msg", TIMEOUT_DESC);
//        } else if ("exception".equals(status.substring(0,9))) {
//            msg.put("code", EXCEPTION_CODE);
//            msg.put("msg", EXCEPTION_DESC);
//        }
//        msg.put("port",port);
//        msg.put("data",object);
//        return msg;
//    }
    public static JSONObject getMsg(String status,Object object) {
        msg = new JSONObject();
        status = status.toLowerCase();//success,port:53465
        String port = status.substring(status.indexOf("port:") + 5);
        if ("fail".equals(status.substring(0,4))) {
            msg.put("code", FAIL_CODE);
            msg.put("msg", FAIL_DESC);
        } else if ("success".equals(status.substring(0,7))) {
            msg.put("code", SUCCESS_CODE);
            msg.put("msg", SUCCESS_DESC);
        } else if ("timeout".equals(status.substring(0,7))) {
            msg.put("code", TIMEOUT_CODE);
            msg.put("msg", TIMEOUT_DESC);
        } else if ("exception".equals(status.substring(0,9))) {
            msg.put("code", EXCEPTION_CODE);
            msg.put("msg", EXCEPTION_DESC);
        }
        msg.put("port",port);
        msg.put("data",object);
        return msg;
    }

    public static JSONObject getMsg(String status,String port,Object object) {
        msg = getMsg(status,object);
        msg.put("port",port);
        return msg;
    }
}
