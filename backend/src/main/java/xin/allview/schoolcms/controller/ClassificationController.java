package xin.allview.schoolcms.controller;


import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import xin.allview.schoolcms.model.L_userClassification;
import xin.allview.schoolcms.model.T_classification;
import xin.allview.utils.Uitls;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class ClassificationController extends Controller {

    /**
     * 获取用户的联系人列表
     * creater by peng
     */
    @Before(GET.class)
    public void findConactByUserId() {
        try {
            int userId = getParaToInt("userId", 1);
            List<L_userClassification> list = L_userClassification.dao.findConactByUserId(userId);
            renderJson(Uitls.Ajax.success("查询成功", list));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }

    /**
     * 新增分类
     * -- created By Samous
     */
    @Before(POST.class)
    public void add() {
        String name = getPara("name");
        String intro = getPara("intro");
        int userId = getParaToInt("userId",1);
        int pid = getParaToInt("pid",0);
        boolean flage=T_classification.dao.findNoOne(pid,name);
        if(flage){
            try {
                boolean flag = T_classification.dao.add(name, intro, pid, userId);
                if(flag){
                    renderJson(Uitls.Ajax.success("添加成功",""));
                }else {
                    renderJson(Uitls.Ajax.success("添加失败",""));
                }
            } catch (Exception e) {
                e.printStackTrace();
                renderJson(Uitls.Ajax.failure("系统异常！", ""));
            }

        }else{
            renderJson(Uitls.Ajax.failure("名字重复！！请重新输入",""));

        }

    }

    /**
     * 修改分类
     * -- created By Samous
     */
    @Before(POST.class)
    public void update() {
        String name = getPara("name");
        String intro = getPara("intro");
        int id = getParaToInt("id");
        try {
            boolean flag = T_classification.dao.updateById(name, intro, id);
            if (flag) {
                renderJson(Uitls.Ajax.success("修改成功", ""));
            } else {
                renderJson(Uitls.Ajax.success("修改失败", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("系统异常！", ""));
        }
    }


    /**
     * 删除子分类并查看是否有帖子
     */

    @Before(GET.class)
    public void delete() {
        int ids = getParaToInt("id");
        boolean x = false;

        x = T_classification.dao.checkNoteUnderClass(ids);

        if(x){
            renderJson( Uitls.Ajax.failure("分类下有帖子！请联系管理人员！！","DON"));
        }else{
            boolean flage=T_classification.dao.deleteClassification(ids);
            if(flage){
                renderJson(Uitls.Ajax.success("删除成功",""));
            }
            else{
                renderJson( Uitls.Ajax.failure("删除失败",""));
            }
        }

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 首页展示分类小组
     * -- created By Samous
     */
    @Before(GET.class)
    public void findListForHome() {
        int page = getParaToInt("page", 1);
        int pageSize = getParaToInt("pageSize");
        Page<Record> list = T_classification.dao.getClassificationList(page, pageSize);
        renderJson(Uitls.Ajax.success("查询成功", list));

    }


    /**
     * 查询父类下的子类
     * pyy
     */
    @Before(GET.class)
    public void findClassificationByParent() {
        int pid = getParaToInt("pid", 1);
        List<T_classification> list = T_classification.dao.getClassificationByParent(pid);
        renderJson(Uitls.Ajax.success("查询成功", list));
    }

    /**
     * 根据父分类获取子分类列表 分页
     *
     * -- created by Samous
     */
    @Before(GET.class)
    public void findListByPid() {
        int pid = getParaToInt("pid");
        int page = getParaToInt("page",1);
        int pageSize = getParaToInt("pageSize", 50);
        String context=getPara("context","");
        try {
            Page<Record> list = T_classification.dao.findListByPid(pid, page, pageSize,context);
            if (list != null) {
                renderJson(Uitls.Ajax.success("查询成功", list));
            } else {
                renderJson(Uitls.Ajax.failure("查询失败", ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
            renderText("程序异常，查询失败");
        }
    }
    /**
     * 根据所传的ID查询出分类的信息
     * peng
     */
    public void findClassificationInf(){
        try {
            int id = getParaToInt("id");
            Record r=T_classification.dao.findClassificationInf(id);
            renderJson(Uitls.Ajax.success("查询成功",r));
        }catch(Exception e){
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败",""));
        }

    }


    /**
     * 查看分类详情
     * pyy
     */
    @Before(GET.class)
    public void findOneById() {
        try {
            int id = getParaToInt("id");//主键
            Record classification = T_classification.dao.findOneById(id);
            renderJson(Uitls.Ajax.success("查询成功", classification));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }


}
