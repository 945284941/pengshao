package xin.allview.schoolcms.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import xin.allview.schoolcms.model.*;
import xin.allview.utils.Uitls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KLP on 2017/5/10.
 */
public class DepartmentController extends Controller {

    /**
     * 查询父类下的子类
     * pyy
     */
    @Before(GET.class)
    public void findDepartmentByPid(){
        int pid = getParaToInt("pid", 1);
        int page = getParaToInt("page", 1);
        int pageSize = getParaToInt("pageSize", 10);
        try {
            Map map =new HashMap();
            List<T_department> list = T_department.dao.findDepartmentByPid(pid);
            Page<Record> userList = T_department.dao.findUserByDepartid(pid,page,pageSize);
            T_department department=T_department.dao.findDepartmentPid(pid);

            map.put("depart",list);
            map.put("userList",userList);
            map.put("department",department);

            if(list!=null){
                 renderJson(Uitls.Ajax.success("查询成功",map));
            }else{
                renderJson(Uitls.Ajax.failure("查询失败",""));
            }
        }catch (Exception e){
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败",""));
        }
    }

    /**
     * 通过部门查询人
     */
    public void findUserByDepartid() {

        int page = getParaToInt("page", 1);
        int pageSize = getParaToInt("pageSize", 10);
        int departid = getParaToInt("departid");
        Page<Record> pageList = null;
        try {
            pageList = T_department.dao.findUserByDepartid(departid,page,pageSize);

            renderJson(Uitls.Ajax.success("查询成功", pageList));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }


    @Before(GET.class)
    public void findDepartmentByUser(){
        int userid = getParaToInt("userid", 1);
        T_department department=T_department.dao.findDepartmentByUser(userid);
        renderJson(Uitls.Ajax.success("查询成功",department));


    }

    /**
     * 通过父id查询子部门
     */
    @Before(GET.class)
    public void findDepartByPid(){
        int pid = getParaToInt("pid", 1);
        List<T_department> department=T_department.dao.findDepartmentByPid(pid);
        renderJson(Uitls.Ajax.success("查询成功",department));

    }

    // ztree 异步加载树结构分类表
    public void getList() {
        try{
            int pid  = getParaToInt("pid",0);
            List<T_department> list = T_department.dao.find("select * from t_department where pid = " + pid + "");
            List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
            if(list.size()>0) {
                for (int i = 0; i < list.size(); i++) {
                    HashMap<String, Object> hm = new HashMap<String, Object>();   //最外层，父节点
                    String id = list.get(i).get("id").toString();
                    hm.put("id", id);//id属性  ，数据传递
                    hm.put("name", list.get(i).get("name")); //name属性，显示节点名称
                    hm.put("pid", list.get(i).get("pid"));
                    List<T_department> ifList = T_department.dao.find("select * from t_department where pid = " + id + "");
                    if (ifList.size() > 0) {
                        hm.put("isParent", "true");
                    } else {
                        List<L_user> ifpeopleList = L_user.dao.find("select lu.id,lu.nick_name,td.pid from l_user lu LEFT JOIN l_departuser ld ON lu.id=ld.userid LEFT JOIN t_department td ON ld.departid=td.id where ld.departid = " + id );
                        if (ifpeopleList.size() > 0) {
                            hm.put("isParent", "true");
                        } else {

                        }
                    }
                    data.add(hm);
                }
                renderJson(Uitls.Ajax.success("查询成功",data));
            }else {
                int thisId = pid;
                HashMap<String, Object> hm = new HashMap<String, Object>();   //最外层，父节点
                hm.put("id", "");//id属性  ，数据传递
                hm.put("name", "");
                hm.put("isParent", "none");//name属性，显示节点名称
                hm.put("pid", thisId);
                data.add(hm);
                renderJson(Uitls.Ajax.success("查询成功",data));
            }
        }catch (Exception e){
            e.printStackTrace();
            renderJson( Uitls.Ajax.failure("查询失败",""));
        }
    }

}
