package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.bitwalker.useragentutils.UserAgent;
import play.api.mvc.RequestHeader;
import play.mvc.Controller;
import play.mvc.Http;
import scala.collection.Seq;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mitesh on 17/07/16.
 */
public class CoreController extends Controller {

    public JsonNode getJsonValue(String param) {
        JsonNode jsonNode = request().body().asJson();
        if (jsonNode == null) {
            return null;
        }
        return jsonNode.path(param);
    }

    public String getQueryParam(String param) {
        return request().getQueryString(param);
    }

    public int getQueryParamInt(String param, int defaultValue) {
        String valueStr = getQueryParam(param);
        int value = defaultValue;
        if (valueStr != null) {
            try {
                value = Integer.parseInt(valueStr);
            } catch (NumberFormatException ex) {
            }
        }
        return value;
    }

    public long getQueryParamLong(String param, long defaultValue) {
        String valueStr = getQueryParam(param);
        long value = defaultValue;
        if (valueStr != null) {
            try {
                value = Long.parseLong(valueStr);
            } catch (NumberFormatException ex) {
            }
        }
        return value;
    }

    public String getJsonAsStringValue(String param) {
        JsonNode valNode = getJsonValue(param);
        if (valNode == null) {
            return null;
        }
        return valNode.asText();
    }

    public boolean getJsonAsBooleanValue(String param) {
        JsonNode valNode = getJsonValue(param);
        if (valNode == null) {
            return false;
        }
        return valNode.asBoolean();
    }

    public static Map<String, String> getBasicAnalyticsParam() {
        Map<String, String> param = new HashMap<>();
        param.put("ipAddresss", getIpAddr());
        UserAgent userAgent = new UserAgent(request().getHeader("User-Agent"));
        if (userAgent != null) {
            param.put("os", userAgent.getOperatingSystem().getName());
            param.put("browser", userAgent.getBrowser().getName());
            param.put("device_type", userAgent.getOperatingSystem().getDeviceType().getName());
            param.put("manufacturer", userAgent.getOperatingSystem().getManufacturer().getName());
        }
        param.put("event_time", (System.currentTimeMillis()/1000)+"");

        String userId = (String) Http.Context.current().args.get("userId");
        if (userId != null) {
            param.put("userId", userId+"");
        }
        return param;
    }

    private static String getIpAddr() {
        String ipAddr = "0.0.0.0";
        RequestHeader requestHeader = request()._underlyingHeader();
        Seq<String> ipAddrArr = requestHeader.headers().getAll("X-Forwarded-For");
        if (ipAddrArr != null && ipAddrArr.size() > 0) {
            ipAddr = ipAddrArr.iterator().next();
        } else {
            ipAddr = requestHeader.remoteAddress();
        }
        return ipAddr;
    }
}