package com.lyfen.pear.common.utils.ip;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.lyfen.pear.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取地址类
 *
 * @author lyfen
 */
@Slf4j
public class AddressUtils {

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        Map<String, Object> param = new HashMap<>();
        param.put("ip", ip);
        String rspStr = HttpUtil.post(IP_URL, param);
        if (StringUtils.isEmpty(rspStr)) {
            log.error("获取地理位置异常 {}", ip);
            return address;
        }
        JSONObject obj = JSONObject.parseObject(rspStr);
        JSONObject data = obj.getObject("data", JSONObject.class);
        String region = data.getString("region");
        String city = data.getString("city");
        address = region + " " + city;
        return address;
    }
}
