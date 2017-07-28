package xin.allview.schoolcms.model;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import xin.allview.utils.Uitls;

import java.util.List;

/**
 * Created By Samous
 */
public class L_roleauthor extends Model<L_roleauthor> {
    public static final L_roleauthor dao = new L_roleauthor();

    /**
     * 给角色更新权限
     *      -- created By Samous
     * @param roleid
     * @param authorIds
     * @return
     */
    public boolean updateAuthorForRole(Integer roleid, Integer[] authorIds) {

        for(Integer i : authorIds){
            L_roleauthor ra = new L_roleauthor();
            ra.set("createtime", Uitls.currTime());
            ra.set("roleid", roleid);
            ra.set("authorid", i);
            if(!ra.save()){
                return false;
            }
        }
        return true;
    }

    /**
     * 清空角色的功能权限
     *      -- created By Samous
     *
     */
    public Integer delByRoleId(Integer roleid) {
        String sql = "DELETE FROM l_roleauthor where roleid = ?";
        return Db.update(sql, roleid);
    }

}
