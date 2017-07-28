package xin.allview.schoolcms.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import xin.allview.utils.Uitls;


public class L_classificationnote extends Model<L_classificationnote> {
	public static final L_classificationnote dao = new L_classificationnote();

	/**
	 * 添加关系
	 * pyy
	 * @param noteId
	 * @param type
	 */
	public boolean addClassifcationnote(int noteId, int type, int userId) {
		L_classificationnote tc=new L_classificationnote();
		tc.set("creater", userId);
		tc.set("version", 1);
		tc.set("createtime", Uitls.currTime());
		tc.set("classid",type);
		tc.set("noteid",noteId);
		boolean b=tc.save();
		return  b;



	}

	/**
	 * 删除关系

	 */
	public int deleteClassifcationnote(int id) {

		int count=Db.update("delete from l_classificationnote where noteid=?",id);

		return  count;
	}
}
