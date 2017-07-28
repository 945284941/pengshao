package xin.allview.schoolcms.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import xin.allview.schoolcms.model.T_functionshop;
import xin.allview.schoolcms.model.T_note;
import xin.allview.utils.Uitls;

import java.util.List;

public class FunctionShopController extends Controller {
    public void index(){
        renderText("hi");
    }
    /**
     * pyy
     * 功能商店列表
     */
    @Before(GET.class)
    public void findFunctionList(){

        String type=getPara("type");//种类
        List<T_functionshop> list= T_functionshop.dao.findFunctionList(type);

        renderJson(Uitls.Ajax.success("查询成功",list));
    }
    /**
     * pyy
     * 商品种类
     */
    @Before(GET.class)
    public void findTypeList(){
        List<T_functionshop> list= T_functionshop.dao.findTypeList();
        renderJson(Uitls.Ajax.success("查询成功",list));
    }
    /**
     * 查看商品详情
     * pyy
     *
     */
    @Before(GET.class)
    public void findOneById() {
        try {
            int id=getParaToInt("id");//主键id
            Record functionshop= T_functionshop.dao.findOneById(id);
            renderJson(Uitls.Ajax.success("查询成功",functionshop));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson( Uitls.Ajax.failure("查询失败",""));
        }
    }


}
