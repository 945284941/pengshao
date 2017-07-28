package xin.allview.schoolcms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import xin.allview.schoolcms.model.T_upesnUser;
import xin.allview.schoolcms.model.L_user;
import xin.allview.schoolcms.tool.UpesnTools;
import xin.allview.utils.Uitls;


import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by neo on 29/01/2017.
 */
public class UpesnAppController extends Controller {

    UpesnTools upesnTools = new UpesnTools();
    public void index(){

    }

    @Before(GET.class)
    public void main(){

        try {
            //获得图标链接标识
            String action = getPara("action");
            //获得友空间code
            String code = getPara("code");
            System.out.println(action);
            String appid="3699a867c7a739a1";
            String secret="d3de938225da21600429b2db4055d789133aef322071be613007828224e5";
            JSONObject jsonObject = JSON.parseObject(upesnTools.getUserInfo(appid, secret, code));
            JSONObject data = jsonObject.getJSONObject("data");
            String access_token=upesnTools.getAcessToken(appid,secret);
            String upesn_id= data.getString("member_id");
            String name= data.getString("name");
            String mobile= data.getString("mobile");
            String email= data.getString("email");

            T_upesnUser upensuser = T_upesnUser.dao.findFirst("SELECT * FROM t_upesnuser where upesnid = ?", upesn_id);
            int userid;

            if(upensuser==null){
                String time = Uitls.currTime();
                boolean flag = new L_user().set("create_time", time).set("nick_name", name).save();
                if(flag){
                    L_user u= L_user.dao.findFirst("SELECT * FROM l_user where create_time = ? and nick_name =?", time, name);
                    int usetid=u.getInt("id");
                    String nick_name=u.getStr("nick_name");
                    boolean flag1=new T_upesnUser().set("upesnid", upesn_id).set("userid", usetid).set("phone", mobile).set("email", email).set("nick_name", name).save();

                    if(flag1){
                        userid= usetid;
                        setAttr("action", action);
                        setAttr("userid",userid);
                        setAttr("name",nick_name);
                        setAttr("access_token",access_token);
                    }
                }
            }else{
                userid=upensuser.getInt("userid");
                setAttr("action", action);
                setAttr("userid",userid);
                setAttr("name",name);
                setAttr("access_token",access_token);
            }

            renderTemplate("/WEB-INF/upesnapp/index.html");

        } catch (Exception e) {
            e.printStackTrace();

        }


    }
    @Before(GET.class)
    public void mainLogin(){

        try {
            //获得图标链接标识
            String action = "agency,0";
            //获得友空间code
            String code = getPara("code");
            System.out.println(action);
            String appid="3699a867c7a739a1";
            String secret="d3de938225da21600429b2db4055d789133aef322071be613007828224e5";
            JSONObject jsonObject = JSON.parseObject(upesnTools.getUserInfo(appid, secret, code));
            JSONObject data = jsonObject.getJSONObject("data");
            String access_token=upesnTools.getAcessToken(appid,secret);
            String upesn_id= data.getString("member_id");
            String name= data.getString("name");
            String mobile= data.getString("mobile");
            String email= data.getString("email");

            T_upesnUser upensuser = T_upesnUser.dao.findFirst("SELECT * FROM t_upesnuser where upesnid = ?", upesn_id);
            int userid;

            if(upensuser==null){
                String time = Uitls.currTime();
                boolean flag = new L_user().set("create_time", time).set("nick_name", name).save();
                if(flag){
                    L_user u= L_user.dao.findFirst("SELECT * FROM l_user where create_time = ? and nick_name =?", time, name);
                    int usetid=u.getInt("id");
                    String nick_name=u.getStr("nick_name");
                    boolean flag1=new T_upesnUser().set("upesnid", upesn_id).set("userid", usetid).set("phone", mobile).set("email", email).set("nick_name", name).save();

                    if(flag1){
                        userid= usetid;
                        setAttr("action", action);
                        setAttr("userid",userid);
                        setAttr("name",nick_name);
                        setAttr("access_token",access_token);
                    }
                }
            }else{
                userid=upensuser.getInt("userid");
                setAttr("action", action);
                setAttr("userid",userid);
                setAttr("name",name);
                setAttr("access_token",access_token);
            }

            renderTemplate("/WEB-INF/upesnapp/index.html");

        } catch (Exception e) {
            e.printStackTrace();

        }


    }
    @Before(GET.class)
    public void everydayWork(){

        try {
            //获得图标链接标识
            String action = "schedule,0";
            //获得友空间code
            String code = getPara("code");
            System.out.println(action);
            String appid="3699a867c7a739a1";
            String secret="d3de938225da21600429b2db4055d789133aef322071be613007828224e5";
            JSONObject jsonObject = JSON.parseObject(upesnTools.getUserInfo(appid, secret, code));
            JSONObject data = jsonObject.getJSONObject("data");
            String access_token=upesnTools.getAcessToken(appid,secret);
            String upesn_id= data.getString("member_id");
            String name= data.getString("name");
            String mobile= data.getString("mobile");
            String email= data.getString("email");

            T_upesnUser upensuser = T_upesnUser.dao.findFirst("SELECT * FROM t_upesnuser where upesnid = ?", upesn_id);
            int userid;

            if(upensuser==null){
                String time = Uitls.currTime();
                boolean flag = new L_user().set("create_time", time).set("nick_name", name).save();
                if(flag){
                    L_user u= L_user.dao.findFirst("SELECT * FROM l_user where create_time = ? and nick_name =?", time, name);
                    int usetid=u.getInt("id");
                    String nick_name=u.getStr("nick_name");
                    boolean flag1=new T_upesnUser().set("upesnid", upesn_id).set("userid", usetid).set("phone", mobile).set("email", email).set("nick_name", name).save();

                    if(flag1){
                        userid= usetid;
                        setAttr("action", action);
                        setAttr("userid",userid);
                        setAttr("name",nick_name);
                        setAttr("access_token",access_token);
                    }
                }
            }else{
                userid=upensuser.getInt("userid");
                setAttr("action", action);
                setAttr("userid",userid);
                setAttr("name",name);
                setAttr("access_token",access_token);
            }

            renderTemplate("/WEB-INF/upesnapp/index.html");

        } catch (Exception e) {
            e.printStackTrace();

        }


    }




    public void getUserList() {
        int page=getParaToInt("page",1);
        int pageSize=getParaToInt("pageSize",10);
        Page<Record> list = L_user.dao.getUserList(page,pageSize);
        renderJson(Uitls.Ajax.success("查询成功", list));
    }

    public void getAllUser(){
        List<L_user> list = L_user.dao.getAllUser();
        renderJson(Uitls.Ajax.success("查询成功", list));
    }

    @Before(GET.class)
    public void add() {

        String nick_name = getPara("nick_name", "默认名字");//用户名字
        String account = getPara("account", "baby");//用户账号
        String creater=getPara("creater","1");//创建人
        String img=getPara("img","back2");

        boolean flag = L_user.dao.addUser(nick_name, account, creater,img);

        if (flag) {
            renderJson(Uitls.Ajax.success("保存成功", ""));
        } else {
            renderJson(Uitls.Ajax.failure("保存失败", ""));
        }


    }
    @Before(GET.class)
    public void delete(){

        Integer[] ids = getParaValuesToInt("id[]");
        boolean flage= L_user.dao.deleteUser(ids);
        if(flage){
            renderJson(Uitls.Ajax.success("删除成功",""));
        }else{
            renderJson(Uitls.Ajax.failure("删除失败",""));
        }

    }



    @Before(GET.class)
    public void  findUserOne(){
        int  pid=getParaToInt("id");
        Map<String,Object> map= L_user.dao.findUserMapList(pid);
        renderJson(Uitls.Ajax.success("查询成功",map));
    }

    @Before(GET.class)
    public void update() {
        String nick_name=getPara("nick_name","默认昵称");//昵称
        String account=getPara("account","22");//账号
        int id=getParaToInt("id",1);
        String img=getPara("img");

        boolean flag= L_user.dao.updateUser(id,nick_name,account,img);
        if(flag){
            renderJson(Uitls.Ajax.success("修改成功",""));
        }
        else{
            renderJson( Uitls.Ajax.failure("修改失败",""));
        }

    }

    public void admin() {
        renderJson("{\"admin\":true}");
    }



}
