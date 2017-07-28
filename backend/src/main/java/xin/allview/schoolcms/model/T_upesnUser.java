package xin.allview.schoolcms.model;



import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import java.util.List;

import xin.allview.utils.Uitls;



public class T_upesnUser extends Model<T_upesnUser> {
	public static final T_upesnUser dao = new T_upesnUser();


	/**
	 * 查看更多老师列表
	 * pyy
	 * @param
	 * @return
	 */
	public Page<Record> findUser(int page, int pageSize) {
		String sql1="select * ";
		String sql2="from t_upesnuser";
		Page<Record> list= null;
		try {
			list = Db.paginate(page,pageSize,sql1,sql2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * KLP
	 * 根据userid查询友空间用户信息
	 */
	public Record findOneByUserid(int userid) {

		Record record = Db.findFirst("select * from t_upesnuser where 1=1 AND userid = ?" ,userid);
		return record;
	}


	/**
	 * 查询角色管理
	 *
	 * @param page
	 * @param pageSize
     * @return
	 * KLP
     */
	public Page<Record> findUpesnUser(String userName,int page, int pageSize) {
        String sql="select *";
		String sql1=" from t_upesnuser tu ";
		if (userName == null || userName=="") {
		}else{
			sql1 += "where tu.nick_name like '%"+userName+"%' ";
		}
		Page<Record> list= null;
		try {

			list = Db.paginate(page,pageSize,sql,sql1);
			for(int i=0;i<list.getList().size();i++){

			  int id=list.getList().get(i).get("userid");
			  List<T_role> roleList=T_role.dao.find("select * from l_upesnuserrole l LEFT JOIN t_role e on l.roleid=e.id where l.upesnuserid=?",id);
				if(roleList.size()==0||roleList==null){
					return list ;
				}
				String newName="";
				 for (int j=0;j<roleList.size();j++){
					 String name=roleList.get(j).get("name")+"，";
					 newName+=name;
				 }
				 newName=newName.substring(0,newName.lastIndexOf("，"));
				 list.getList().get(i).set("role",newName);
				}
		}catch (Exception e){
			e.printStackTrace();
		}

		return list;

	}

	/**
	 * 查看老师详情
	 * pyy
	 * @param id
	 * @return
	 */

	public Record findTeacherdetails(int id) {
		Record record = Db.findFirst("select * from t_upesnuser tu where tu.userid=?", id);
		return record;
	}



	/**
	 *  分页 单表查询 用户列表
	 *
	 * 		-- created By Samous
	 *
	 * @param page
	 * @param pageSize
     * @return
     */

	public Page<Record> findListforPage(int page, int pageSize) {

		String sql1 = "SELECT u.userid, u.nick_name, u.createtime, u.img, u.expand";
		String sql2 = "FROM t_upesnuser u ORDER BY u.createtime DESC";

		Page<Record> list= null;
		try {
			list = Db.paginate(page,pageSize,sql1,sql2);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 根据姓名查询用户对象
	 *
	 * 		-- created By Samous
	 */
	public T_upesnUser findUserByName(String name) {
		String sql = "SELECT * FROM t_upesnuser WHERE nick_name = ?";
		return T_upesnUser.dao.findFirst(sql, name);

	}
	/**
	 * 查询用户对象
	 *
	 * 		-- created By pyy
	 */
	public List<T_upesnUser> findUserByPar(String name) {
		String sql = "SELECT * FROM t_upesnuser WHERE nick_name like '%"+name+"%'";
		List<T_upesnUser> list = T_upesnUser.dao.find(sql);
		return list;

	}



	/**
	 * 根据权限查询用户列表 用以通知开会参会人
	 * 		-- created by Samous
	 *
	 */
	public Page<Record> findMeetingUser(int page, int pageSize) {
		String sql1="select * ";
		String sql2="from l_user";
		Page<Record> list= null;
		try {
			list = Db.paginate(page,pageSize,sql1,sql2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}