package xin.allview.schoolcms.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import xin.allview.schoolcms.model.*;

import xin.allview.utils.Uitls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lxh on 2017/4/19.
 */
public class UpesnUserController extends Controller {
    T_upesnUser upesnUser = new T_upesnUser();

    /**
     * 查看更多老师列表
     * pyy
     */
    @Before(GET.class)
    public void findUser() {
        int page = getParaToInt("page", 1);
        int pageSize = getParaToInt("pageSize", 16);
        Page<Record> list = T_upesnUser.dao.findUser(page, pageSize);
        renderJson(Uitls.Ajax.success("查询成功", list));
    }

    /**
     * 我的页面,查询upesnUser用户的信息，主要做微信昵称和手机号展示
     * KLP
     */
    @Before(GET.class)
    public void findOneUser() {
        try {
            int userid = getParaToInt("userid");
            Record user = T_upesnUser.dao.findOneByUserid(userid);
            int departid = getParaToInt("juweihui_id");
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
     * 友空间用户管理老师列表
     * KLP
     */
    @Before(GET.class)
    public void findUpesnUser() {
        String userName = getPara("name");//用户名
        int page = getParaToInt("page", 1);
        int pageSize = getParaToInt("pageSize");
        Page<Record> list = T_upesnUser.dao.findUpesnUser(userName, page, pageSize);
        renderJson(Uitls.Ajax.success("查询成功", list));
    }


    /**
     * 友空间查询角色列表
     * KLP
     */

    public void findRole() {

        int userId = getParaToInt("userId");
        List<T_role> list = T_role.dao.findRole();
        Map map = new HashMap();
        map.put("list", list);
        map.put("userId", userId);
        try {
            renderJson(Uitls.Ajax.success("查询成功", map));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }

    /**
     * 友空间人与角色绑定
     */
    public void bindingRole() {
        String[] roles = getParaValues("ids[]");
        int userId = getParaToInt("userId", 89);
        boolean flag = false;
        for (int i = 0; i < roles.length; i++) {
            String pid = roles[i];
            int id = Integer.parseInt(pid);
            Record record = L_upesnuserrole.dao.selectExixt(userId, id);
            if (record == null) {
                flag = L_upesnuserrole.dao.bindingRole(userId, id);
                if (flag) {

                } else {
                    renderJson(Uitls.Ajax.failure("添加失败", ""));
                }
            } else {
                renderJson(Uitls.Ajax.failure("重复的角色不可再次添加", ""));
            }
        }
        if (flag) {
            renderJson(Uitls.Ajax.success("添加成功", ""));
        } else {
            renderJson(Uitls.Ajax.failure("添加失败", ""));
        }
    }


    /**
     * 查看老师详情
     * pyy
     */
    @Before(GET.class)
    public void findTeacherdetails() {
        try {
            int id = getParaToInt("id");//老师id
            Record note = T_upesnUser.dao.findTeacherdetails(id);
            renderJson(Uitls.Ajax.success("查询成功", note));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }

    /**
     * 首页老师列表
     * -- created By Samous
     */

    @Before(GET.class)
    public void findUpesnUserListForHome() {
        int page = 1;
        int pageSize = getParaToInt("pageSize");
        try {
            Page<Record> list = T_upesnUser.dao.findListforPage(page, pageSize);
            renderJson(Uitls.Ajax.success("查询成功", list));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }

    }


    /**
     * 人对角色的增删操作
     */
    public void updateRole() {
        try {
            int userId = getParaToInt("userId", 89);
            String[] roleList = getParaValues("ids[]");
            //通过userId删除 他的所有的角色
            L_upesnuserrole.dao.deleteUserId(userId);
            boolean flage = false;
            //循环遍历传过来的数组
            for (int i = 0; i < roleList.length; i++) {
                //拿到角色的Id
                String pid = roleList[i];
                int id = Integer.parseInt(pid);
                //开始添加操作
                flage = L_upesnuserrole.dao.bindingRole(userId, id);

                if (flage) {

                } else {
                    renderJson(Uitls.Ajax.failure("操作失败", ""));
                }
            }
            if (flage) {
                renderJson(Uitls.Ajax.success("操作成功", ""));
            } else {
                renderJson(Uitls.Ajax.failure("操作失败", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("操作失败", ""));
        }

    }

    @Before(GET.class)
    public void findMeetingUser() {
        int page = getParaToInt("page", 1);
        int pageSize = getParaToInt("pageSize", 10);
        Page<Record> list = T_upesnUser.dao.findMeetingUser(page, pageSize);
        renderJson(Uitls.Ajax.success("查询成功", list));

    }
    @Before(GET.class)
    public void findUserByName() {
        String userName = getPara("name");//用户名

        List<T_upesnUser> list = T_upesnUser.dao.findUserByPar(userName);
        renderJson(Uitls.Ajax.success("查询成功", list));

    }


}
