package gysm.slf.develop.standard.constant.response;

import java.util.HashMap;

/**
 * 一般用JSONObject
 */
public class MsgBox {
  private static java.util.Map map = new HashMap();

  public Object success (Object data) {
    map.put("status", Map.SUCCESS_CODE);
    map.put("description", Map.SUCCESS_DESC);
    map.put("data", data);
    return map;
  };

  public Object fail () {
    map.put("status", Map.FAIL_CODE);
    map.put("description", Map.FAIL_DESC);
    return map;
  };

  public Object timeout () {
    map.put("status", Map.FAIL_CODE);
    map.put("description", Map.FAIL_DESC);
    return map;
  };
}
