package xin.allview.schoolcms.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import xin.allview.schoolcms.Entity.RoleAuthor;
import xin.allview.utils.Uitls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Samous
 */
public class T_role extends Model<T_role> {
    public static final T_role dao = new T_role();

    /**
     * 角色表 单表 新增
     *
     * @param name
     * @return
     */
    public boolean addRole(String name, Integer[] ids){
        T_role role = new T_role();
        role.set("name",name);
        boolean flag = role.save();
        if(ids == null || ids.length == 0) {
            return flag;
        }
        else {
            T_role r = T_role.dao.findFirst("SELECT id FROM t_role where name = ?", name);
            int roleId = r.getInt("id");
            for(Integer i : ids){
                L_roleauthor ra = new L_roleauthor();
                ra.set("createtime", Uitls.currTime());
                ra.set("roleid", roleId);
                ra.set("authorid", i);
                if(!ra.save()){
                    return false;
                }
            }
            return true;
        }
    }


    /**
     * 角色表 单表 更新
     *      -- created By Samous
     * @param name
     * @return
     */
    public boolean update(Integer id, String name){
        T_role role = dao.findFirst("SELECT * FROM t_role where id = ?", id);
        role.set("name", name);
        return role.update();
    }

    /**
     * 分页 角色表 单表查询 列表
     *      -- created By Samous
     *
     * @return
     */
    public Page<Record> findRoleByUserId(Integer id, Integer page, Integer pageSize) {
        String sql1 = "SELECT r.* ";
        String sql2 = "from l_upesnuserrole u LEFT JOIN t_role r on u.roleid = r.id where u.upesnuserid = ?";
        Page<Record> list = Db.paginate(page, pageSize, sql1, sql2, id);
        return list;
    }

    /**
     * 分页 角色表 单表查询 列表
     *      -- created By Samous
     *
     * @return
     */
    public Page<Record> findPageRoles(Integer page, Integer pageSize) {
        String sql1 = "SELECT r.name, r.id ";
        String sql2 = "from t_role r";
        Page<Record> list = Db.paginate(page, pageSize, sql1, sql2);
        return list;
    }

    /**
     * 不分页 角色表全部查询
     *
     *      -- KLP
     * @return
     */

    public List findRole() {
        String sql = "SELECT * from t_role ";
        List<T_role> list = T_role.dao.find(sql);
        return list;
    }


    /**
     * 分页 用户表 单表查询 列表
     * 参数: 角色id
     *
     *      -- created By Samous
     * @param roleid
     * @return
     *
     */
    public Page<Record> findUpUserByRoleId(Integer page, Integer size, Integer roleid){
        String sql1 = "SELECT ur.upesnuserid, u.nick_name name, u.phone, u.email ";
        String sql2 = "FROM l_upesnuserrole ur LEFT JOIN t_upesnuser u ON ur.upesnuserid = u.userid WHERE ur.roleid = ?";
        try{
            Page<Record> userList = Db.paginate(page, size, sql1, sql2, roleid);
            System.out.println(userList);
            if(userList != null){
                return userList;
            }
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    /**
     * 分页 功能权限 单表查询 列表
     * 参数：角色id
     *
     *      -- created By Samous
     *
     * @param roleid
     * @return
     */
    public Page<Record> findFunctionsByRoleId(Integer page, Integer size, Integer roleid){
        String sql1 = "SELECT a.name, ra.authorid ";
        String sql2 = "FROM l_roleauthor ra LEFT JOIN t_author a ON ra.authorid = a.id WHERE ra.roleid = ?";
        try{
            Page<Record> functionList = Db.paginate(page, size, sql1, sql2, roleid);
            if(functionList != null){
                return functionList;
            }
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 列出所有功能权限
     *      -- created By Samous
     * @return
     */

    public List<Record> findAllAuthor() {
        return Db.find("SELECT * FROM t_author");
    }



}
