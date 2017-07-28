package xin.allview.schoolcms.validator;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.validate.Validator;
import xin.allview.utils.Log;

/**
 * Created by neo on 16/02/2017.
 */
public class NameValidator extends Validator {

  @Override
  protected void validate(Controller c) {
    Log.d("name validator in");

    // [Neo] ignore error when validate
    if (null == c.getPara("error")) {
      String name = c.getPara("name");

      // [Neo] same as validateRequired("name", "validate-name", "404");
      if (StrKit.isBlank(name)) {
        addError("validate-name", "no name found");
        return;
      }

      if (name.length() < 3) {
        addError("validate-name", "length must >= 3");
        return;
      }
    }

    Log.d("name validator out");
  }

  @Override
  protected void handleError(Controller c) {
    c.redirect("/welcome?error=" + c.getAttr("validate-name"));
  }
}
