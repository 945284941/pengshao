package xin.allview.schoolcms.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class T_functionshop extends Model<T_functionshop> {
	public static final T_functionshop dao = new T_functionshop();

	/**
	 * pyy
	 * 功能商品列表
	 * @param type
     * @return
     */
	public List<T_functionshop> findFunctionList(String type) {
		List<T_functionshop> list=null;
		if(type.equals("0")){
			 list= T_functionshop.dao.find("select * from t_functionshop");
		}else{
			 list= T_functionshop.dao.find("select * from t_functionshop where type= ?",type);
		}

		return list;
	}

	/**
	 * pyy
	 * 查询商品详情
	 * @param id
     * @return
     */
	public Record findOneById(int id) {

		Record record= Db.findFirst("select * from t_functionshop where id=?",id);
		return record;
	}

	/**
	 * pyy
	 * 商品种类
	 * @return
     */

	public List<T_functionshop> findTypeList() {
		List<T_functionshop> list=T_functionshop.dao.find("select DISTINCT(type) from t_functionshop");

		return list;
	}
}