package xin.allview.schoolcms.tool;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import xin.allview.utils.Uitls;
/**
 * 友空间工具类
 * Created by tudou on 17/3/22.
 */
public class UpesnTools extends HttpTools {

    private static final String baseUrl = "https://openapi.upesn.com/";

    /**
     * 获取access_token
     */
    public String getAcessToken(String appid, String secret) throws Exception {
        String url = baseUrl + "token";
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", appid);
        data.put("secret", secret);
        JSONObject jasonObject = JSONObject.parseObject(get(url, data));
        return jasonObject.getJSONObject("data").getString("access_token");
    }
    /**
     * 获取 友空间手机版的返回参数
     */
    public String getJSAPI(String appid,String secret) throws  Exception{
        try {
            String url = baseUrl + "get_jsapi_ticket?access_token="+getAcessToken(appid,secret);
            Map<String,String> data=new HashMap<String,String>();
            return (get(url,data));
        }catch (Exception e){
            e.printStackTrace();
            return  "";
        }
    }

    /**
     *通知
     */
    public String addNotice(String appid,String secret,String id,String userId,String text,String title,String thirdUrl) {
        //https://openapi.upesn.com/notice/thirdPartyAdd?access_token=ACCESS_TOKEN
        try {
            String url = baseUrl + "notice/thirdPartyAdd?access_token=" + getAcessToken(appid,secret);
            Map<String, String> data = new HashMap<String, String>();
            data.put("qz_id","103105"); //空间id
            data.put("author_id", userId); //发送用户id(0:系统消息) integer
            data.put("member_ids", id); // 接受用户ids(个数限制100， 逗号拼接字符串)
            data.put("template_id", "11"); //模板id
            data.put("note", "{\"text\":\""+text+"\"}"); //  通知内容（json）
            data.put("push_client","2"); //1：PC,2:客户端，3:PC+客户端 int
            data.put("callback_url",thirdUrl);//第三方url
            data.put("title", "{\"title\":\""+title+"\"}");  //通知标题（json）
            return (post(url, data));
        } catch (Exception e) {
            e.printStackTrace();
            return("aa");
        }
    }
    /**
     * 第三方
     */
    public String thirdLogin(String appid,String secret){
        try {
            String url = "https://thirdpart.com/api/userlogin";
            Map<String, String> data = new HashMap<String, String>();
            data.put("account","18765155509"); //空间id
        //通知标题（json）
            return (post(url, data));
        } catch (Exception e) {
            e.printStackTrace();
            return("aa");
        }
    }
    /**
     * 友空间消息
     *
     */
    public String getMessageremind(String appid,String secret) throws  Exception{
        try{
            String url=baseUrl+"rest/message/app/txt?access_token="+getAcessToken(appid,secret);
            Map<String,String> data=new HashMap<String,String>();
            data.put("spaceId","103105");
            data.put("appId","74583");
            data.put("sendScope","list");
            String a="3071608,2910553,2910557";
            data.put("to",a);
            data.put("content","消息");
            return (post(url,data));
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 企业自建应用调用免登
     */
    /**
     * ISV应用调用免登
     */
    public String getUserInfo(String appid, String secret, String code) {
        try {
            String url =
                    baseUrl + "certified/userInfo/" + code + "?access_token=" + getAcessToken(appid, secret);
            Map<String, String> data = new HashMap<String, String>();
            return (get(url, data));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



}
