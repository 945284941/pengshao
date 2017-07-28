package xin.allview.schoolcms.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by lxh on 2017/7/25.
 */
public class L_departuser extends Model<L_departuser> {

    public static  final L_departuser dao=new L_departuser();

    public List<L_departuser> departuserPeopleId(int id){
        return L_departuser.dao.find("select * from l_departuser d where d.departid=?",id);
    }
}
