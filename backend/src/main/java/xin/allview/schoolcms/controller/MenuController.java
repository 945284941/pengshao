package xin.allview.schoolcms.controller;

import com.jfinal.core.Controller;

import xin.allview.schoolcms.model.T_classification;
import xin.allview.schoolcms.model.T_menu;
import xin.allview.utils.Uitls;

import java.util.List;

/**
 * Created by KLP on 2017/5/10.
 */
public class MenuController extends Controller {

    /**
     * 友空间菜单按钮（升序排列）
     * KLP
     */

    public void findMenu(){
        try {
            int userId = getParaToInt("userid", 1);
            List<T_menu> list = T_menu.dao.findMenu();
            if(list!=null){
                renderJson(Uitls.Ajax.success("查询成功",list));
            }else{
                renderJson(Uitls.Ajax.failure("查询失败",""));
            }
        }catch (Exception e){
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败",""));
        }
    }
}
