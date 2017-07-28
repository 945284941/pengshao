package xin.allview.schoolcms.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import xin.allview.utils.Uitls;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class T_classification extends Model<T_classification> {
    public static final T_classification dao = new T_classification();

       public String findClassificationName(int id){
           Record record=Db.findFirst("select * from t_classification where id=?",id);
           String name=record.get("classname");
           return name;

       }

    /**
     * created By Samous
     *
     * @return
     */

    public Page<Record> getClassificationList(int page, int pageSize) {
        String sql1 = "select * ";

        String sql2 = "from t_classification c ORDER BY createtime ";


        try {
            Page<Record> list = Db.paginate(page, pageSize, sql1, sql2);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 根据id查询出当前分类的所有信息
     * @param id
     * @return
     */
    public Record findClassificationInf(int id){
        Record r=Db.findFirst("select t.*,u.nick_name from t_classification t left join t_upesnuser u on t.creater=u.userid where t.id=?",id);
        return r;
    }
    public boolean deleteClassification(int id){
        T_classification tc=new T_classification();
        boolean flage=tc.deleteById(id);
        return flage;
    }

    public Record findIntrduce(int id) {
        Record record = Db.findFirst("select classname,introduce,img from t_classification where id=?", id);
        return record;
    }

    public String findClassName(int classId) {
        Record record = Db.findFirst("select * from t_classification where id=?", classId);
        String className = record.getStr("classname");
        return className;
    }


    /**
     * 根据pid查询分类列表
     *      -- created by Samous
     *
     * @param pid
     * @param page
     * @param pageSize
     * @return
     */

    public Page<Record> findListByPid(int pid, int page, int pageSize,String context) {
        try{
            String sql1 = "SELECT t.*,u.nick_name ";
            String sql2 = "FROM t_classification t left join l_user u on t.creater =u.id where pid = ? ";
            if(context.equals("")||context==null){
                return Db.paginate(page, pageSize, sql1, sql2, pid);
            }else{
                String sql3=" and t.classname like '%"+context+"%' or u.nick_name like '%"+context+"%' ";
                sql2=sql2+sql3;
                return Db.paginate(page, pageSize, sql1, sql2, pid);
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
    public boolean findNoOne(int pid,String name){
        boolean a=false;
        Record e=Db.findFirst("select * from t_classification where pid=? and classname =? ",pid,name);
        if(e==null){
           a=true;
        }else{
            a=false;
        }
        return a;
    }

    /**
     * 新增分类
     *      -- created By Samous
     *
     * @param name
     * @param intro
     * @param pid
     * @return
     */
    public boolean add(String name, String intro, int pid, Integer userId) {
        T_classification c = new T_classification();
        c.set("classname",name);
        c.set("introduce",intro);
        c.set("pid", pid);
        c.set("createtime",Uitls.currTime());
        c.set("version",1);
        c.set("creater",userId);
        return c.save();
    }

    /**
     * 修改分类
     *      -- created by Samous
     *
     *
     * @param name
     * @param intro
     * @param id
     * @return
     */

    public boolean updateById(String name, String intro, int id) {
        T_classification c = dao.findFirst("SELECT * FROM t_classification where id = ?", id);
        c.set("createtime", Uitls.currTime());
        c.set("classname", name);
        c.set("introduce", intro);

        return c.update();
    }

    /**
     *
     * 查询父类下的子类
     * @param pid
     * @return
     */
    public List<T_classification> getClassificationByParent(int pid) {
        List<T_classification> list = T_classification.dao.find("select * from t_classification where pid=?", pid);
        return  list;

    }
    /**
     * 查询分类下有没有帖子
     */
    public boolean checkNoteUnderClass(int id){
        Record r=Db.findFirst("select * from l_classificationnote where classid=?",id);
        boolean flage=false;
        if(r!=null){
            flage=true;
        }else{
            flage=false;
        }
        return flage;
    }

    /**
     * 查询详情
     * pyy
     * @param id
     * @return
     */
    public Record findOneById(int id) {
        Record record = Db.findFirst("select tc.*,lu.nick_name from t_classification tc LEFT JOIN l_user lu ON lu.id=tc.creater  where tc.id=?", id);

        return record;
    }

    /**
     * 新建一个类获取id
     * @param userId
     * @return
     */

 public int haveClassId(int userId){
      T_classification c=new T_classification();
     c.set("creater",userId);
     c.set("createtime",Uitls.currTime());
     c.set("version",1);
     c.set("pid",0);
     c.set("classname","聊天");
     c.save();
     return c.getInt("id");


 }





   /* *//**
     * KLP
     *
     * @param className
     * @param introduce
     * @param img
     * @return
     *//*
    public int addClassification(String className, String introduce, String img) {
        try {
            T_classification c = new T_classification();
            c.set("classname", className);
            c.set("introduce", introduce);
            c.set("img", img);
            boolean flag = c.save();
            if (flag) {
                return c.get("id");
            } else {
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
*/

    /**
     * 根据classid查询分类列表
     * KLP
     */

   /* public List<T_classification> findClassification(List<T_classid> classids) {

        List<T_classification> list = new ArrayList<T_classification>();

        try {
            for (int i = 0; i < classids.size(); i++) {
                int id = classids.get(i).getInt("classid");
                String sql = "SELECT * FROM t_classification where id = ?";
                T_classification t_classification = T_classification.dao.findFirst(sql,id);
                list.add(t_classification);

            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/
}

