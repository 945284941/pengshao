package xin.allview.schoolcms.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import xin.allview.schoolcms.Entity.RoleAuthor;
import xin.allview.schoolcms.model.L_roleauthor;
import xin.allview.schoolcms.model.T_role;
import xin.allview.schoolcms.model.T_upesnUser;
import xin.allview.utils.Uitls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created By Samous
 */
public class RoleAuthorController extends Controller {

    /**
     * 新增角色并赋予功能权限
     *
     * created by samous
     */
    @Before(POST.class)
    public void add() {
        String name = getPara("name");
        Integer[] ids = getParaValuesToInt("ids[]");
        try {
            boolean flag = T_role.dao.addRole(name, ids);
            if (flag) {
                renderJson(Uitls.Ajax.success("添加成功", ""));
            } else {
                renderJson(Uitls.Ajax.failure("添加失败", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据角色修改功能权限
     *
     *      -- created By Samous
     */
    @Before(GET.class)
    public void updateAuthorForRole() {
        Integer roleid = getParaToInt("roleid");
        String name = getPara("name");
        Integer[] authorIds = getParaValuesToInt("ids[]");

        try{
            int number = L_roleauthor.dao.delByRoleId(roleid);
            boolean update = T_role.dao.update(roleid, name);

            if(authorIds == null || authorIds.length == 0){
                renderJson(Uitls.Ajax.success("成功删除当前角色的所有功能权限", ""));
                return;
            }

            boolean flag = L_roleauthor.dao.updateAuthorForRole(roleid, authorIds);
            if(flag){
                renderJson(Uitls.Ajax.success("成功修改当前角色的功能权限！", ""));
            }
            else {
                renderJson(Uitls.Ajax.failure("对不起，操作失败！",""));
            }

        }catch(Exception e){
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("系统错误！", ""));
        }

    }


    /**
     * 角色管理列表
     *
     *      -- created By Samous
     */
    @Before(GET.class)
    public void findPageRoles() {
        Integer page = getParaToInt("page", 1);
        Integer pageSize = getParaToInt("pageSize", 10);
        HashMap<String, Object> map = new HashMap<String ,Object>();
        try {
            Page<Record> pages = T_role.dao.findPageRoles(page, pageSize);
            map.put("pageNumber", pages.getPageNumber());
            map.put("pageSize", pages.getPageSize());
            map.put("totalPage", pages.getTotalPage());
            map.put("firstPage", pages.isFirstPage());
            map.put("lastPage", pages.isLastPage());

            List<RoleAuthor> item = new ArrayList<RoleAuthor>();
            List<Record> list = pages.getList();
            for (int i = 0; i < list.size(); i++) {
                int roleid = list.get(i).getInt("id");
                String name = list.get(i).getStr("name");
                RoleAuthor ra = new RoleAuthor();
                ra.setId(roleid);
                ra.setName(name);
                ra.setUsername(T_role.dao.findUpUserByRoleId(1, 6, roleid).getList());
                ra.setFunctionname(T_role.dao.findFunctionsByRoleId(1, 6, roleid).getList());
                item.add(ra);
            }
            map.put("list", item);
            renderJson(Uitls.Ajax.success("查询成功", map));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }

    /**
     * 权限列表 无分页
     *
     * -- created By Samous
     */
    @Before(GET.class)
    public void findAllAuthor() {

        try {
            List<Record> list = T_role.dao.findAllAuthor();
            renderJson(Uitls.Ajax.success("查询成功", list));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }

    /**
     * 用户列表 分页
     * 参数：角色id
     *
     *      -- created By Samous
     */
    @Before(GET.class)
    public void findUpUserByRoleId() {
        Integer roleid = getParaToInt("roleid");
        Integer page = getParaToInt("page", 1);
        Integer pageSize = getParaToInt("pageSize", 10);
        try {
            Page<Record> list = T_role.dao.findUpUserByRoleId(page, pageSize, roleid);
            renderJson(Uitls.Ajax.success("查询成功", list));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }

    /**
     * 分页 根据用户名称搜索角色
     * 参数：角色id
     *      -- created By Samous
     */
    @Before(GET.class)
    public void findRolesByUserName() {
        Integer page = getParaToInt("page", 1);
        Integer pageSize = getParaToInt("pageSize", 10);
        String name = getPara("name");

        HashMap<String, Object> map = new HashMap<String ,Object>();

        try {
            T_upesnUser uu = T_upesnUser.dao.findUserByName(name);
            if(uu == null){
                renderJson("查无此人", "");
                return;
            }
            Integer userId = uu.get("userid");
            Page<Record> pages = T_role.dao.findRoleByUserId(userId, page, pageSize);
            map.put("pageNumber", pages.getPageNumber());
            map.put("pageSize", pages.getPageSize());
            map.put("totalPage", pages.getTotalPage());
            map.put("firstPage", pages.isFirstPage());
            map.put("lastPage", pages.isLastPage());

            List<RoleAuthor> item = new ArrayList<RoleAuthor>();
            List<Record> list = pages.getList();
            for (int i = 0; i < list.size(); i++) {
                int roleid = list.get(i).getInt("id");
                String roleName = list.get(i).getStr("name");
                RoleAuthor ra = new RoleAuthor();
                ra.setId(roleid);
                ra.setName(roleName);
                ra.setUsername(T_role.dao.findUpUserByRoleId(1, 6, roleid).getList());
                ra.setFunctionname(T_role.dao.findFunctionsByRoleId(1, 6, roleid).getList());
                item.add(ra);
            }
            map.put("list", item);
            renderJson(Uitls.Ajax.success("查询成功", map));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }

    }

}
