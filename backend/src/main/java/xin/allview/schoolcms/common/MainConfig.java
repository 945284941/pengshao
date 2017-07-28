package xin.allview.schoolcms.common;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

import xin.allview.schoolcms.controller.*;
import xin.allview.schoolcms.interceptor.FrontInterceptor;
import xin.allview.schoolcms.model._MappingKit;
import xin.allview.utils.Cache;
import xin.allview.utils.Generator;
import xin.allview.utils.Log;

/**
 * Created by neo on 20/01/2017.
 */
public class MainConfig extends JFinalConfig {

  @Override
  public void configConstant(Constants me) {
    Log.init();
    PropKit.use("local.properties");

    Log.d("db_host: " + PropKit.get("db_host"));
    Log.d("redis_host: " + PropKit.get("redis_host"));
    //me.setMainRenderFactory(new BeetlRenderFactory());
    me.setViewType(ViewType.JSP);
    me.setDevMode(PropKit.getBoolean("dev_mode"));
  }

  @Override
  public void configEngine(Engine me) {
  }

  @Override
  public void configRoute(Routes me) {

      //控制层入口
      me.add("/archive/upesnUser", UpesnUserController.class);
      me.add("/archive/roleAuthor", RoleAuthorController.class);
      me.add("/archive/weixinUser", WeixinuserController.class);
      me.add("/archive/classification", ClassificationController.class);
      me.add("/archive/note", NoteController.class);
      me.add("/archive/functionShop", FunctionShopController.class);
      me.add("/archive/menu", MenuController.class);
      me.add("/archive/department", DepartmentController.class);


      //端口
      me.add("/upesnapp", UpesnAppController.class);
      me.add("/weixin", WeiXinController.class);
      me.add("/web", UpesnWebController.class);
      me.add("/",IndexController.class);


  }

  @Override
  public void configPlugin(Plugins me) {
     ActiveRecordPlugin ar = new ActiveRecordPlugin(Generator.dataSourceProvider());
     me.add(ar);
     _MappingKit.mapping(ar);
     ar.setShowSql(PropKit.getBoolean("show_sql"));
  }

  @Override
  public void configInterceptor(Interceptors me) {
    me.addGlobalServiceInterceptor(new FrontInterceptor());
    me.add(new SessionInViewInterceptor(true));

  }

  @Override
  public void configHandler(Handlers me) {
    // [Neo] Redis Cache Class init and first check
    if (Cache.init()) {
      Log.i("redis is online");
    } else {
      Log.w("redis is offline");
    }

    // [Neo] add Druid stat view handler support
    me.add(Generator.statHandler());
  }
}
