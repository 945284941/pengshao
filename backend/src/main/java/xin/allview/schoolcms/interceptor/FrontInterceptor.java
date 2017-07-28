package xin.allview.schoolcms.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import xin.allview.utils.Log;

/**
 * Created by neo on 29/01/2017.
 */
public class FrontInterceptor implements Interceptor {

  @Override
  public void intercept(Invocation inv) {
    Log.d("front intercept in");
    
    inv.invoke();

//    inv.getController().redirect("/clear");
    Log.d("front intercept out");
  }
}
