package xin.allview.schoolcms.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cedarsoftware.util.io.JsonObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import xin.allview.schoolcms.model.T_weixinuser;
import xin.allview.schoolcms.tool.WechatUitls;


/**
 * Created by Administrator on 2017/3/21.
 */
public class WeiXinController extends Controller {
    private static final String APPID = "wx027013dd566eaf30";
    private static final String APPSECRET = "fd0d366f793e4196eda946f1e6df1a60";
   /* private static final String REDIRECT_URI = "http://schoolcms.allview.xin/schoolcms/weixin/main";
    private static final String CODE_TOKEN="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+REDIRECT_URI+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";*/

    /**d
     * 微信端绑定学生页面-微信菜单进入
     *       -- created By Samous
     */
    @Before(GET.class)
    public void index(){
        String weixinCode = getPara("code");

        String openid="";
        if (weixinCode.equals("")){

            String url = WechatUitls.getWechatRedirectUrl(APPID,
                    "http://schoolcms.allview.xin/eighborhood/weixin/index"
                    , "0");
            redirect(url);
        }else{
            String data = WechatUitls.getOpenId(APPID, APPSECRET,weixinCode);
            JSONObject j = JSON.parseObject(data);
            openid=j.getString("openid");
        }

        T_weixinuser wx = T_weixinuser.dao.findFirst("SELECT tw.*,ld.departid FROM t_weixinuser tw LEFT JOIN l_departuser ld ON tw.userid=ld.userid where tw.openid = ?", openid);
        if(wx == null){
            String accessToken=WechatUitls.getAccessToken(APPID,APPSECRET);
            String info=WechatUitls.getWeiXinUserInfo(accessToken,openid);
            JSONObject jsonUserData=JSONObject.parseObject(info);
            String nickname =jsonUserData.getString("nickname");
            String userid = T_weixinuser.dao.add(nickname==null?"":nickname, openid, -1);
            if(userid!=null){
                setAttr("userid",userid);
                renderTemplate("/WEB-INF/weixin/anthen.html");
            }else{
                render("出错了");
            }


        }else{
            switch(wx.getInt("identity")) {
                case -1:
                    renderTemplate("/WEB-INF/weixin/anthen.html");
                    break;
                case 0:
                    switch (wx.getStr("status")) {
                        case "-1":    //未提交
                            setAttr("userid",wx.getInt("userid"));
                            renderTemplate("/WEB-INF/weixin/anthen.html");
                            break;
                        case "0":    //待审核
                            setAttr("userid",wx.getInt("userid"));
                            setAttr("departid",wx.getInt("departid"));
                            renderTemplate("/WEB-INF/weixin/index.html");
                            break;
                        case "2":    //审核未通过
                            setAttr("userid",wx.getInt("userid"));
                            setAttr("departid",wx.getInt("departid"));
                            renderTemplate("/WEB-INF/weixin/index.html");
                            break;
                    }
                    break;
                case 1:    //审核通过
                    setAttr("userid",wx.getInt("userid"));
                    setAttr("departid",wx.getInt("departid"));
                    renderTemplate("/WEB-INF/weixin/index.html");
                    break;

            }

        }


    }

  /*  @Before(GET.class)
    public void main(){
        render("/WEB-INF/weixin/index.html");
        try {

            //公司微信公众号信息：通过信息生成openid和access_token，转换为json格式
            String weixinCode = getPara("code");
            System.out.println("获取到的微信code是："+weixinCode);


            String dataFromWeiXin = WechatTools.queryOpenId(APPID, APPSECRET, weixinCode);
            System.out.println("获取的字符串信息：========================》" + dataFromWeiXin);
            JSONObject jsonDataFromWeiXin = JSON.parseObject(dataFromWeiXin);

            //拿取生成的openid和access_token
            String openid = jsonDataFromWeiXin.getString("openid");
            System.out.println("openid==========================>" +openid);
            if(openid == null) return;

            String access_token=jsonDataFromWeiXin.getString("access_token");

            //请求获取用户信息
            String url="https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
            String userData = Uitls.loadJSON(url);
            JSONObject jsonUserData=JSONObject.parseObject(userData);
            T_weixinuser wx = T_weixinuser.dao.findFirst("SELECT * FROM t_weixinuser where openid = ?", openid);

            String weixin_name = jsonUserData.getString("nickname");
            System.out.println("微信用户的昵称是：------------->" + weixin_name);

             //首次登录的
             if(wx == null){
                 String userid = T_weixinuser.dao.add(weixin_name==null?"":weixin_name, openid, 0);
                 String[] user_ = userid.split(",");
                 System.out.println(user_);
                 if(userid != null){
                     System.out.println("创建新用户的userid为：------------->" + userid);
                     System.out.println("创建新用户的weixin_name为：------------->" + weixin_name);
                     setAttr("weixinname",weixin_name);
                     setAttr("userid",user_[0]);
                     setAttr("weixinid",user_[1]);

                 }else{
                     System.out.println("创建新用户失败的userid为：------------->" + userid);
                     setAttr("userid",0);
                 }

                 //不是首次登录的游客或会员
             }else{

                 int id = wx.getInt("userid");
                 T_weixinuser wu = T_weixinuser.dao.updateUserdetail(weixin_name, id);

                 if(id != 0){
                     T_weixinuser wxu = T_weixinuser.dao.findWeixinuser(id);

                     System.out.println("非首次登录的游客userid为：------------->" + id);
                     System.out.println("创建新用户的weixin_name为：------------->" + weixin_name);

                     //setAttr("departid", wxu.getInt("departid"));
                     setAttr("8userid",wu.getInt("userid"));
                     setAttr("weixinname",weixin_name);
                     setAttr("weixinid",wu.getInt("userid"));

                 }else {
                     System.out.println("非首次登录失败的游客userid为：------------->" + id);
                     setAttr("userid",0);
                 }

             }
             renderTemplate("/WEB-INF/weixin/index.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/


}
