package xin.allview.schoolcms.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

/**
 * Created by KLP on 2017/5/5.
 */
public class L_upesnuserrole extends Model<L_upesnuserrole> {
    public static final L_upesnuserrole dao = new L_upesnuserrole();

    public boolean addRole(int id,int userid){
        try {


            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteRole(int userid){
        try {

            boolean a = L_upesnuserrole.dao.deleteById("upesnuserid",userid);
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean bindingRole(int userId,int id){
        L_upesnuserrole lu=new L_upesnuserrole();
        lu.put("roleid",id);
        lu.put("upesnuserid",userId);
        boolean flag=lu.save();
        return flag;

    }
    public Record selectExixt(int userId,int id){
        Record record= Db.findFirst("select * from l_upesnuserrole where roleid=? and upesnuserid=?",id,userId);
        return record;
    }
    public void deleteUserId(int userId){
        Db.update("delete from l_upesnuserrole where upesnuserid=?",userId);
    }

}
