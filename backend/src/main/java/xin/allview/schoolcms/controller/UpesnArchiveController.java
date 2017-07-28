package xin.allview.schoolcms.controller;

import com.jfinal.core.Controller;


/**
 * Created by neo on 29/01/2017.
 */
public class UpesnArchiveController extends Controller {

//    UpesnTools upesnTools = new UpesnTools();
//    public void index(){
//    }
//

   /* UpesnTools upesnTools = new UpesnTools();
    public void index(){
    }

    @Before(GET.class)
    public void loginUser(){
        String account=getPara("account","18764418601");//帖子标题
        String password=getPara("password","123456");
        boolean user= L_user.dao.addNote(account,password);
        Record r= L_user.dao.findBySessionId(account,password);
        *//*HttpSession session = getRequest().getSession();*//*


*//*      if(r.equals(null)||r.equals("")){
          redirect("/login");

      }else {
          int userId = r.getInt("id");

          session.setAttribute("id", userId);
      }*//*

          if(user){

              int userId = r.getInt("id");
              setSessionAttr("id", userId);

              redirect("/login");

      }else{


              redirect("/up");

             *//* session.setAttribute("erro","登录失败");*//*
      }
    }
   //用友的自动登录
    @Before(GET.class)
    public void indexLogin() {
        try {

            String code = getPara("code");
            String appid="db90d75eec4c55a9";
            String secret="41a3dcf7701c9552bee0124591c52079f01a952c63cc3b8ed783ced8eaf7";
            JSONObject jsonObject = JSON.parseObject(upesnTools.getUserInfo(appid, secret, code));
            JSONObject data = jsonObject.getJSONObject("data");
            String id = data.getString("member_id");

          *//*  render("/WEB-INF/view/index.html");*//*
            redirect("/login");

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
        String password=getPara("password","123");//用户密码
        String creater=getPara("creater","1");//创建人
        String img=getPara("img","back2");
        boolean flag = L_user.dao.addUser(nick_name, account, password, creater,img);

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


//    @Before(GET.class)
//    public void loginUser(){
//        String account=getPara("account","18764418601");//帖子标题
//        String password=getPara("password","123456");
//        boolean user= L_user.dao.addNote(account,password);
//        Record r= L_user.dao.findBySessionId(account,password);
//        /*HttpSession session = getRequest().getSession();*/
//
//
///*      if(r.equals(null)||r.equals("")){
//          redirect("/login");
//
//      }else {
//          int userId = r.getInt("id");
//
//          session.setAttribute("id", userId);
//      }*/
//
//          if(user){
//
//              int userId = r.getInt("id");
//              setSessionAttr("id", userId);
//
//              redirect("/login");
//
//      }else{
//
//
//              redirect("/up");
//
//             /* session.setAttribute("erro","登录失败");*/
//      }
//    }

//   //用友的自动登录
//    @Before(GET.class)
//    public void indexLogin() {
//        try {
//
//            String code = getPara("code");
//            String appid="db90d75eec4c55a9";
//            String secret="41a3dcf7701c9552bee0124591c52079f01a952c63cc3b8ed783ced8eaf7";
//            JSONObject jsonObject = JSON.parseObject(upesnTools.getUserInfo(appid, secret, code));
//            JSONObject data = jsonObject.getJSONObject("data");
//            String id = data.getString("member_id");
//
//          /*  render("/WEB-INF/view/index.html");*/
//            redirect("/login");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void getUserList() {
//        int page=getParaToInt("page",1);
//
//        int pageSize=getParaToInt("pageSize",10);
//
//        Page<Record> list = L_user.dao.getUserList(page,pageSize);
//
//        renderJson(Uitls.Ajax.success("查询成功", list));
//    }
//
//    public void getAllUser(){
//        List<L_user> list = L_user.dao.getAllUser();
//        renderJson(Uitls.Ajax.success("查询成功", list));
//    }
//
//    @Before(GET.class)
//    public void add() {
//
//        String nick_name = getPara("nick_name", "默认名字");//用户名字
//        String account = getPara("account", "baby");//用户账号
//        String password=getPara("password","123");//用户密码
//        String creater=getPara("creater","1");//创建人
//        String img=getPara("img","back2");
//        boolean flag = L_user.dao.addUser(nick_name, account, password, creater,img);
//
//        if (flag) {
//            renderJson(Uitls.Ajax.success("保存成功", ""));
//        } else {
//            renderJson(Uitls.Ajax.failure("保存失败", ""));
//        }
//
//
//    }
//    @Before(GET.class)
//    public void delete(){
//
//        Integer[] ids = getParaValuesToInt("id[]");
//        boolean flage= L_user.dao.deleteUser(ids);
//        if(flage){
//            renderJson(Uitls.Ajax.success("删除成功",""));
//        }else{
//            renderJson(Uitls.Ajax.failure("删除失败",""));
//        }
//
//    }
//
//
////    @Before(GET.class)
////    public void  findUserOne(){
////
////        int  pid=getParaToInt("id");
////        int  myid=getParaToInt("myid");
////        boolean b= T_classification.dao.queryClassification(pid,myid);
////        Map<String,Object> map= L_user.dao.findUserMapList(pid);
////        map.put("jsguanzhu",b);
////        renderJson(Uitls.Ajax.success("查询成功",map));
////
////
////    }
//
//    @Before(GET.class)
//    public void update() {
//        String nick_name=getPara("nick_name","默认昵称");//昵称
//
//        String account=getPara("account","22");//账号
//
//        String password=getPara("password","22");//密码
//
//        int id=getParaToInt("id",1);
//
//        String img=getPara("img");
//
//        boolean flag= L_user.dao.updateUser(id,nick_name,account,password,img);
//        if(flag)
//        {
//            renderJson(Uitls.Ajax.success("修改成功",""));
//        }
//        else
//        {
//            renderJson( Uitls.Ajax.failure("修改失败",""));
//        }
//
//    }
//
//
//
//
//
//  public void admin() {
//    renderJson("{\"admin\":true}");
//  }


//    @Before(GET.class)
//    public void update() {
//        String nick_name=getPara("nick_name","默认昵称");//昵称
//
//        String account=getPara("account","22");//账号
//
//        String password=getPara("password","22");//密码
//
//        int id=getParaToInt("id",1);
//
//        String img=getPara("img");
//
//        boolean flag= L_user.dao.updateUser(id,nick_name,account,password,img);
//        if(flag)
//        {
//            renderJson(Uitls.Ajax.success("修改成功",""));
//        }
//        else
//        {
//            renderJson( Uitls.Ajax.failure("修改失败",""));
//        }
//
//    }
//
//
//
//
//
//  public void admin() {
//    renderJson("{\"admin\":true}");
//  }*/

}
