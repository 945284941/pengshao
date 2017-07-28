package xin.allview.schoolcms.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;



public class T_student extends Model<T_student> {
	public static final T_student dao = new T_student();



	/**
	 * 根据学生的学号和姓名查询学生的id
	 * @param no：学号
	 * @param name：姓名
	 * @return
	 */
	public int findStuId(String no, String name) {
		try{
			Record record = Db.findFirst("select * from t_student where 1=1 and no = ? and name = ?",no,name);
			if(record != null){
				int stuid = record.getInt("id");
				return stuid;
			}else {
				return 0;
			}


		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}finally {

		}

	}


	/**根据t_weixinuser的stuid查询学生信息
	 * KLP
	 * @param stuid：学生的id
	 * @return
	 */
	public Record findOneStu(int stuid){
		try {
			Record t_student = Db.findFirst("select * from t_student where id = ?",stuid);
			if (t_student!=null) {
				return t_student;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**根据学生的学号，姓名看是否能查出学生
	 * KLP
	 * @param no
	 * @param name
	 * @return
	 */
	public boolean findStu(String no, String name) {
		try {
			Record record = Db.findFirst("select * from t_student where 1=1 and no = ? and name = ?",no,name);

			if (record.equals("")) {
				return false;
			} else {
				return true;
			}


		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}