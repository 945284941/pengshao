package xin.allview.schoolcms.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;


public class T_department extends Model<T_department> {
	public static final T_department dao = new T_department();

	/**

	通过父类id查询子类
	pyy
	 */
	public List<T_department> findDepartmentByPid(int pid){
		String sql = "select * from t_department where pid=?";
		try{
			List<T_department> list = T_department.dao.find(sql,pid);
			if(list != null){
				return list;
			}
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 *通过pid查询人员
	 * @param pid
	 * @param page
	 * @param pageSize
     * @return
     */
	public Page<Record> findUserByDepartid(int pid,int page,int pageSize){
		String sql1 = "SELECT lu.id,lu.nick_name,lu.img ";
		String sql2="FROM l_user lu LEFT JOIN l_departuser ld ON lu.id = ld.userid LEFT JOIN t_department td ON ld.departid = td.id WHERE td.id = ?";
		Page<Record> list= null;
		try {
			list = Db.paginate(page,pageSize,sql1,sql2,pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public T_department findDepartmentById(int juweihui_id){
		T_department td = T_department.dao.findFirst("select * from t_department where id = ? ",juweihui_id);
		return td;
	}
    /*
    查询部门
     */
	public T_department findDepartmentPid(int id){
		String sql = "select * from t_department where id=?";

		try{
			T_department list = T_department.dao.findFirst(sql,id);

			return list;

		} catch (Exception e){
			e.printStackTrace();
			return null;


		}
	}
	/**
	 * 查询居委会下的组织
	 * @param id
	 * @return
	 */
	public List<T_department> findPeopleByDepartId(int id){
		return T_department.dao.find("select d.id from t_department d  where d.id =? or d.pid=?",id,id);
	}
    public String position(int id){
    	T_department d= T_department.dao.findFirst("select * from l_departuser d left join t_department t on d.departid=t.id  where d.userid=?",id);
        if(d!=null){
        	return d.get("name");
		}else{
			return null;
		}
	}
	/*
    查询部门通过userid
     */
	public T_department findDepartmentByUser(int id){
		String sql = "select * from t_department td LEFT JOIN l_departuser ld ON td.id=ld.departid where ld.userid=?";

		try{
			T_department list = T_department.dao.findFirst(sql,id);

			return list;

		} catch (Exception e){
			e.printStackTrace();
			return null;


		}
	}

}