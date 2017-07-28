package xin.allview.schoolcms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Record;
import xin.allview.schoolcms.model.*;
import xin.allview.utils.Uitls;
import com.jfinal.plugin.activerecord.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KLP on 2017/5/03.
 */
public class WeixinuserController extends Controller {

    /**
     * 我的页面,查询t_weixinuser用户的信息，主要做微信昵称和手机号展示
     * KLP
     */
    @Before(GET.class)
    public void findOneUser() {
        try {
            int userid = getParaToInt("userid");
            Record user = T_weixinuser.dao.findOneByUserid(userid);
            int departid = user.getInt("juweihui_id");
            T_department dt = T_department.dao.findDepartmentById(departid);
            Map<String,Object> map = new HashMap<>();
            map.put("user",user);
            map.put("dt",dt);
            renderJson(Uitls.Ajax.success("查询成功", map));
        } catch (Exception e) {
            renderJson(Uitls.Ajax.success("查询失败", ""));
        }
    }


    /**
     * 查询认证信息
     * pyy
     */
    public void findAuthenInfo() {
        int userid = getParaToInt("userid");

            Record weixinuser = T_weixinuser.dao.findOneByUserid(userid);

            if ( weixinuser != null) {
                renderJson(Uitls.Ajax.success("查询成功", weixinuser));
            } else {
                renderJson(Uitls.Ajax.success("无数据", ""));
            }

    }





    /**
     * 居委会  添加/修改认证信息
     * pyy
     */
    public void addAndUpdateInfo() {

        String sfzh = getPara("sfzh");
        String name = getPara("name");
        int departid = getParaToInt("departid");

        String phone = getPara("phone");
        int userid = getParaToInt("userid");

        boolean flag = T_weixinuser.dao.addAndUpdateInfo(userid, name, phone,departid,sfzh);
        if(flag){
            renderJson(Uitls.Ajax.success("操作成功", ""));
        }else{
            renderJson(Uitls.Ajax.failure("操作失败", ""));
        }

    }

    /**
     * 居委会  添加/修改认证信息
     * pyy
     */
    public void addDepartidYouke() {
        int departid = getParaToInt("departid");
        int userid = getParaToInt("userid");

        boolean flag = T_weixinuser.dao.addDepartidYouke(userid, departid);
        if(flag){
            renderJson(Uitls.Ajax.success("操作成功", ""));
        }else{
            renderJson(Uitls.Ajax.failure("操作失败", ""));
        }

    }



    /**
     * 添加聊天关系
     * KLP
     */
    public void addMessagePeople(){
        try{
            int yourId = getParaToInt("yourId");
            int myId = getParaToInt("myId");
            String who = getPara("who");
            TB_message_people tb1 = TB_message_people.dao.haveSmae(yourId,myId);
            TB_message_people tb2 = TB_message_people.dao.haveSmae(myId,yourId);
            int uuid;
            Map<String,Object> map = new HashMap<>();
            if(tb1!=null||tb2!=null){
                uuid = tb1.get("uuid");
                String yourName = "";
                if(who.equals("1")){
                    Record tw = T_weixinuser.dao.findOneByUserid(yourId);
                    yourName = tw.getStr("name");
                }else if(who.equals("2")){
                    Record tu = T_upesnUser.dao.findOneByUserid(yourId);
                    yourName = tu.getStr("nick_name");
                }
                map.put("yourName",yourName);
                map.put("uuid",uuid);
                renderJson(Uitls.Ajax.failure("关系已存在",map));
            }else{
                int classId = T_classification.dao.haveClassId(myId);
                boolean a = false;
                String yourName = "";
                if(who.equals("1")){
                    a = TB_message_people.dao.addMessagePeopleYezhu(myId,yourId,classId);
                    Record tw = T_weixinuser.dao.findOneByUserid(yourId);
                    yourName = tw.getStr("name");
                }else if(who.equals("2")){
                    a = TB_message_people.dao.addMessagePeople(myId,yourId,classId);
                    Record tu = T_upesnUser.dao.findOneByUserid(yourId);
                    yourName = tu.getStr("nick_name");
                }
                map.put("yourName",yourName);
                map.put("uuid",classId);
                if(a){
                    renderJson(Uitls.Ajax.success("添加关系表成功",map));
                }else{
                    renderJson(Uitls.Ajax.failure("添加失败",""));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("添加失败",""));
        }



    }




    /**
     *微信用户审核
     *
     * creater by peng
     */
    @Before(GET.class)
    public void findWeiXinUserAll(){
        try {
            int page = getParaToInt("page", 1);
            int pageSize = getParaToInt("pageSize", 10);
            String name = getPara("name",null);
            String status = getPara("status","5");
            Page<Record> pageList = T_weixinuser.dao.findWeiXinUserAll(page, pageSize, name, status);
            renderJson(Uitls.Ajax.success("查询成功",pageList));
        }catch (Exception e){
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败",""));
        }

    }

    /**
     * 审核通过or不通过
     * creater by pyy
     */
    @Before(POST.class)
    public void changStatus(){
        try {
            int id = getParaToInt("userid");
            String status = getPara("status");
            boolean flag=T_weixinuser.dao.changStatusSuccess(id,status);
            if(flag){
                renderJson(Uitls.Ajax.success("操作成功",""));
            }else{
                renderJson(Uitls.Ajax.failure("操作失败",""));
            }


        }catch(Exception e){
            e.printStackTrace();

        }

    }


    /**
     * 查询微信端的人员
     */
    public void findWeiXinPeopleList(){
        try{
            int page=getParaToInt("page",1);
            int pageSize=getParaToInt("pageSize",10);
            Page<Record> pageList=T_weixinuser.dao.findWeiXinPeopleList(page,pageSize);
            renderJson(Uitls.Ajax.success("查询成功",pageList));
        }catch (Exception e){
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("系统出现异常!!!",""));
        }
    }
    /**
     * 查询关注的人
     */
    public void myConcern(){
        try{
            int userId=getParaToInt("userId");
            int page=getParaToInt("page",1);
            int pageSize=getParaToInt("pageSize",20);
            Page<Record> pageList= TB_message_people.dao.myConcern(page,pageSize,userId);
            List<Map> list=new ArrayList<>();
            for(int i=0;i<pageList.getList().size();i++){
                Map map=new HashMap();
                int id=pageList.getList().get(i).getInt("addressee_id");
                String position=T_department.dao.position(id);
                map.put("position",position);
                map.put("id",pageList.getList().get(i).getInt("id"));
                map.put("sender_id",pageList.getList().get(i).getInt("sender_id"));
                map.put("addressee_id",pageList.getList().get(i).getInt("addressee_id"));
                map.put("classid",pageList.getList().get(i).get("uuid"));
                map.put("last_time",pageList.getList().get(i).get("last_time"));
                map.put("num",pageList.getList().get(i).get("num"));
                map.put("weixin_name",pageList.getList().get(i).get("weixin_name"));
                map.put("img",pageList.getList().get(i).get("img"));
                map.put("nick_name",pageList.getList().get(i).get("nick_name"));

                map.put("who", Integer.parseInt(String.valueOf(pageList.getList().get(i).getLong("who"))));
                list.add(map);
            }

            renderJson(Uitls.Ajax.success("查询成功",list));
        }catch (Exception e){
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("系统出现异常，请联系管理人员",""));
        }
    }


    /*
     *和居委会的人形成关系
     * lxp
     *
     */

    public void formation(){
        try {
            int userId = getParaToInt("userId");
            int departId = getParaToInt("departId");
            List<T_department> list = T_department.dao.findPeopleByDepartId(departId);//查询居委会下的分类
            if(list.size()==0){
                System.out.println("组织?不存在的");
            }else {
                for (int i = 0; i < list.size(); i++) {
                    int id = list.get(i).getInt("id");  //街道id
                    if (id == departId) {
                        System.out.println("避免死循环");
                    } else {
                        List<L_departuser> departList = L_departuser.dao.departuserPeopleId(id);//居委会下的人
                        for (int j = 0; j < departList.size(); j++) {
                            int yourId = departList.get(j).get("userid");
                            if (TB_message_people.dao.haveSmae(userId, yourId) == null) {
                                int classId = T_classification.dao.haveClassId(userId);
                                boolean flage = TB_message_people.dao.addMessagePeople(userId, yourId, classId);
                                if (flage) {
                                    System.out.println("成功Id" + yourId);
                                } else {
                                    System.out.println("失败Id" + yourId);
                                }
                            } else {
                                System.out.println("数据已存在不会再添加");
                            }//ss
                        }
                        int sa = formationSed(userId,id);
                    }

                }
            }
            renderJson(Uitls.Ajax.success("添加成功", ""));
        }catch (Exception e){
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("系统异常",""));
        }
    }

    public int formationSed(int userId,int departId){
        List<T_department> list=T_department.dao.findPeopleByDepartId(departId);//查询居委会下的分类
        int sa=0;
        if(list.size()==0){
            System.out.println("组织?不存在的");
        }else {
            for (int i = 0; i < list.size(); i++) {
                int id = list.get(i).getInt("id");  //街道id
                if (id == departId) {
                    System.out.println("避免死循环");
                } else {
                    List<L_departuser> departList = L_departuser.dao.departuserPeopleId(id);//居委会下的人
                    for (int j = 0; j < departList.size(); j++) {
                        int yourId = departList.get(j).get("userid");
                        if (TB_message_people.dao.haveSmae(userId, yourId) == null) {
                            int classId = T_classification.dao.haveClassId(userId);
                            boolean flage = TB_message_people.dao.addMessagePeople(userId, yourId, classId);
                            if (flage) {
                                System.out.println("成功Id" + yourId);
                            } else {
                                System.out.println("失败Id" + yourId);
                            }
                        } else {
                            System.out.println("数据已存在不会再添加");
                        }
                    }
                    int op=formationSed(userId,id);
                }
            }
        }
        return sa;
    }



}
