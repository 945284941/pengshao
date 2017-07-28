package xin.allview.schoolcms.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.redis.*;
import com.jfinal.plugin.redis.Cache;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 微信服务号开发工具类
 * Created by tudou on 17/7/7.
 * V1.1
 */
public class WechatUitls {

    private static boolean ISUSEREDIS = PropKit.getBoolean("redis_is_open", false);

    /**
     * 配置redis
     */
    private static Cache cache = Redis.use(PropKit.get("redis_cache_name"));

    /**
     * 微信获取accessToken 先从redis里获取 如果没有通过访问微信服务器获取
     * redis 的key命名方式，`APPID`Token
     *
     * @param appID     微信的 appID
     * @param appSecret 微信的 secret
     * @return accessToken
     */
    public static String getAccessToken(String appID, String appSecret) {

        try {

            String HEAD_ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
            String accessToken;
            if (ISUSEREDIS && cache.exists(appID + "Token")) {
                accessToken = cache.get(appID + "Token");
                return accessToken;
            } else {
                String getAccess_TokenUrl = HEAD_ACCESSTOKEN + "&appid=" + appID + "&secret=" + appSecret;
                String data = HttpKit.get(getAccess_TokenUrl);
                JSONObject jsonObject = JSON.parseObject(data);
                LogUtil.writeLog("interface -> getAccess_Token():" + jsonObject.toJSONString());
                if (jsonObject.getIntValue("errcode") == 0) {
                    accessToken = jsonObject.getString("access_token");
                    if (ISUSEREDIS) {
                        cache.setex(appID + "Token", 7190, accessToken);
                    }
                    return accessToken;
                } else {
                    return "error";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 获取微信jsapiTicket
     *
     * @param appID       微信的 appID
     * @param accessToken 获取到的accessToken
     * @return jsapiTicket
     */
    public static String getJsapiTicket(String appID, String accessToken) {

        try {
            String HEAD_JSAPITICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=@@&type=jsapi";
            String jsapiTicket;

            if (ISUSEREDIS && cache.exists(appID + "Ticket")) {
                jsapiTicket = cache.get(appID + "Ticket");
                return jsapiTicket;
            } else {
                String getJsapi_Ticket = HEAD_JSAPITICKET.replace("@@", accessToken);
                LogUtil.writeLog(getJsapi_Ticket);

                String data = HttpKit.get(getJsapi_Ticket);
                JSONObject jsonObject = JSON.parseObject(data);
                LogUtil.writeLog("getJsapi_Ticket==" + jsonObject.toJSONString());

                if (jsonObject.getIntValue("errcode") == 0) {
                    jsapiTicket = jsonObject.getString("ticket");
                    LogUtil.writeLog("获取的jsapi_ticket为:" + jsapiTicket);
                    if (ISUSEREDIS) {
                        cache.setex(appID + "Ticket", 7190, jsapiTicket);
                    }
                    return jsapiTicket;
                } else {
                    LogUtil.writeLog("获取ticket失败:" + jsonObject.toJSONString());
                    return "error";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    /**
     * 获取微信jsapiTicket
     *
     * @param appID     微信的 appID
     * @param appSecret 微信的 secret
     * @param f         默认为true
     * @return jsapiTicket
     */
    public static String getJsapiTicket(String appID, String appSecret, boolean f) {
        String accessToken = getAccessToken(appID, appSecret);
        if (!accessToken.equals("error")) {
            return getJsapiTicket(appID, accessToken);
        } else {
            return "error";
        }
    }

    /**
     * 获取微信openid
     *
     * @param appID      微信的 appID
     * @param appSecret  微信的 secret
     * @param weixinCode 微信code
     * @return json openid
     */
    public static String getOpenId(String appID, String appSecret, String weixinCode) {
        String GET_WEI_XIN_OPENID =
                "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code" +
                        "&appid=" + appID + "&secret=" + appSecret + "&code=" + weixinCode;
        String data = HttpKit.get(GET_WEI_XIN_OPENID);
        LogUtil.writeLog(data);
        return data;
    }

    /**
     * 获取微信用户基本信息
     *
     * @param accessToken 返回openId 的时候 accessToken
     * @param openId openid
     * @return json 用户基本信息
     */
    public static String getWeiXinUserInfo(String accessToken, String openId) {
        String GET_WEI_XIN_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
        String data = HttpKit.get(GET_WEI_XIN_USER_INFO);
        LogUtil.writeLog(data);
        return data;
    }

    /**
     * 拼接微信公众号回调url
     * 网页授权作用域为snsapi_base
     * 只能获取微信openId
     *
     * @param appID       微信appID
     * @param redirectUri 重定向url
     * @param state       重定向参数
     * @return
     */
    public static String getWechatRedirectUrl(String appID, String redirectUri, String state) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID" +
                "&redirect_uri=URL" +
                "&response_type=code&scope=snsapi_base" +
                "&state=" + state +
                "#wechat_redirect";
        url = url.replace("URL", urlencoder(redirectUri));
        url = url.replace("APPID", appID);
        return url;
    }

    /**
     * 拼接微信公众号回调url
     * 网页授权作用域为snsapi_userinfo
     * 可以获取用户基本信息
     *
     * @param appID       微信appID
     * @param redirectUri 重定向url
     * @param state       重定向参数
     * @return
     */
    public static String getWechatRedirectUrlUserInfo(String appID, String redirectUri, String state) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID" +
                "&redirect_uri=URL" +
                "&response_type=code&scope=snsapi_userinfo" +
                "&state=" + state +
                "#wechat_redirect";
        url = url.replace("URL", urlencoder(redirectUri));
        url = url.replace("APPID", appID);
        return url;
    }

    /**
     * url转码
     *
     * @param url 要转码的url
     * @return 转码后的url
     */
    private static String urlencoder(String url) {
        if (url == null || url.trim().equals("")) {
            return url;
        }
        try {
            return URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }

}
