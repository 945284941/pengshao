package xin.allview.schoolcms.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by KLP on 2017/5/10.
 */
public class T_menu extends Model<T_menu> {
    public static final T_menu dao = new T_menu();

    /**
     * KLP
     */
    public List<T_menu> findMenu(){
        String sql = "select * from t_menu order by place";
        try{
            List<T_menu> list = T_menu.dao.find(sql);
            if(list != null){
                return list;
            }
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
