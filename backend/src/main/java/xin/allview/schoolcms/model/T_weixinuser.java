package xin.allview.schoolcms.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Page;
import xin.allview.utils.Uitls;

/**
 * Created by KLP on 2017/05/03.
 */
public class T_weixinuser extends Model<T_weixinuser> {

    public static final T_weixinuser dao = new T_weixinuser();


    /**
     * KLP
     * 根据userid查询微信用户信息
     * @param userid:t_weixinuser的userid
     * @return
     */
    public Record findOneByUserid(int userid) {

        Record record = Db.findFirst("select tw.*,td.name as departname,tu.upesnid from t_weixinuser tw LEFT JOIN t_department td ON tw.juweihui_id=td.id left join t_upesnuser tu on tu.upesnid = tw.userid where 1=1 AND tw.userid = ?" ,userid);
        return record;
    }



    /**
     * KLP
     * 根据userid查询绑定孩子的sid
     *
     * @param userid
     * @return
     */
    public int findSid(int userid) {
        try {
            Record record = Db.findFirst("select * from t_weixinuser where userid = ? and stuid is not null ",userid);
            if (record != null) {
                int stuid = record.getInt("stuid");
                return stuid;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 查询所有的微信端的人员
     */
     public Page<Record> findWeiXinPeopleList(int page,int pageSize){
         String sql1="select * from ";
         String sql2="t_weixinuser ";

         try{
             return Db.paginate(page,pageSize,sql1,sql2);
         }catch (Exception e){
             e.printStackTrace();
             return null;
         }
     }
    /**
     * KLP
     * 实名认证，绑定家长学生信息
     *
     * @param stuid
     * @param userid
     * @param pName
     * @param phone
     * @return
     */
    public boolean bindWxUser(int stuid, int userid, String pName, String phone) {
        try {
            Record record = Db.findFirst("select * from t_weixinuser where userid = ?",userid);
            if (record != null) {

                T_weixinuser wu = T_weixinuser.dao.findById(userid);
                wu.set("name", pName);
                wu.set("phone", phone);
                wu.set("identity", 1);
                wu.set("stuid", stuid);
                wu.set("status", 3);
                boolean a = wu.update();
                if (a) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addAndUpdateInfo(int userid, String pName, String phone,int departid,String sfzh) {
        try {
            Record record = Db.findFirst("select * from t_weixinuser where userid = ?",userid);
            Record record1 = Db.findFirst("select * from l_user where id = ?",userid);
            if (record != null) {
                T_weixinuser wu = T_weixinuser.dao.findById(userid);
                L_user lu = L_user.dao.findById(userid);
                wu.set("name", pName);
                wu.set("phone", phone);

                wu.set("juweihui_id", departid);
                wu.set("card_id", sfzh);
                wu.set("status", "0");
                wu.set("identity", 0);
                lu.set("nick_name", pName);
                boolean a = wu.update();
                boolean b = lu.update();
                if (a&&b) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }





        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addDepartidYouke(int userid,int departid) {
        try {
            Record record = Db.findFirst("select * from t_weixinuser where userid = ?",userid);
            if (record != null) {

                T_weixinuser wu = T_weixinuser.dao.findById(userid);


                wu.set("juweihui_id", departid);

                wu.set("identity", 0);

                boolean a = wu.update();
                if (a) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }






    /**
     * created wechat User
     *     --  created by Samous
     *
     * @param weixin_name
     * @param openid
     * @param identity
     * @return
     */

    public String add(String weixin_name, String openid, int identity) {
        String id = "";

        try {
            //user表创建用户
            L_user user = new L_user();
            user.set("create_time", Uitls.currTime());
            user.set("nick_name", weixin_name);
            user.save();

            String userid = user.getInt("id").toString();
            id += userid + ",";
            //wxUser家长表创建关联
            T_weixinuser wxUser = new T_weixinuser();
            wxUser.set("userid", user.getInt("id"));
            wxUser.set("weixin_name", weixin_name);
            wxUser.set("openid", openid);
            wxUser.set("identity", identity);

            boolean flag = wxUser.save();
            id += wxUser.getInt("userid").toString();
            if (flag) {
                System.out.println("添加用户时的id逗号隔开的玩意儿为" + id);
                return id;

            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * created By Samous
     *
     * @param weixin_name
     * @param id
     * @return
     */

    public T_weixinuser updateUserdetail(String weixin_name, Integer id) {
        T_weixinuser wu = T_weixinuser.dao.findById(id);
        wu.set("weixin_name", weixin_name);
        wu.update();
        return wu;
    }

    public T_weixinuser findWeixinuser(int userId){
        T_weixinuser wxu = T_weixinuser.dao.findFirst("select * from t_weixinuser where userid = ? ",userId);
        return wxu;
    }

    /**
     * creater by pyy
     * @param name
     * @param status 审核状态
     */
    public Page<Record> findWeiXinUserAll(int page,int pageSize,String name,String status){
        String sql1="select tw.*,td.name as departname";
        String sql2="from t_weixinuser tw LEFT JOIN t_department td ON tw.juweihui_id=td.id where tw.status !='-1'";
        if (name == null || name == "") {
        } else {
            sql2 += " and tw.name LIKE '%" + name + "%' ";
        }
       if(status=="5" || status.equals("5")){

       }else{
           sql2 += " and tw.status =" + status ;
       }

        Page<Record> list=null;
        try {
            /*list=Db.paginate(page,pageSize,sql1,sql2,name,status);*/
            list=Db.paginate(page,pageSize,sql1,sql2);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;

    }
    /**
     *
     *creater by peng
     */

    public boolean changStatusSuccess(int id,String status){
        boolean flag=false;
        int identity=-1;
        if(status.equals("1")){
            identity=1;
        }else if(status.equals("2")){
            identity=0;
        }
        try {
            T_weixinuser weixinuser=T_weixinuser.dao.findById(id);
            weixinuser.set("status",status);
            weixinuser.set("identity",identity);
             flag=weixinuser.update();
        }catch(Exception e){
            e.printStackTrace();
        }
       return flag;

    }





}
