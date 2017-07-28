package xin.allview.schoolcms.model;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Page;

import xin.allview.utils.Uitls;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class L_user extends Model<L_user> {
	public static final L_user dao = new L_user();


	/**通过添加t_weixinUser添加用户
	 * KLP
	 * @param userid：t_weixinuser的主键userid
	 * @param pName:家长姓名
	 * @return
	 */
	public boolean addUserByUserid(int userid,String pName) {
		try {

			L_user user= L_user.dao.findById(userid);
			user.set("nick_name",pName);
			boolean a = user.update();
			if(a){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



	public  boolean  addUser(String nick_name,String account, String creater,String img){

		L_user user = new L_user();
		user.set("nick_name", nick_name);
		user.set("account", account);
		user.set("creater", creater);
		user.set("img",img);

		//版本控制
		user.set("creater", 1);
		user.set("create_time", Uitls.currTime());
		user.set("version", 1);
		boolean a=user.save();
		if(a){

			return true;
		}else{

			return false;
		}
	}
	public String peopleName(int id){
          L_user l=L_user.dao.findFirst("select * from l_user where id= ?",id);
		String name=l.get("nick_name");
		return name;
	}
	public boolean deleteUser(Integer[] ids){

		int j = 0;
		List list = new ArrayList();
		for (int i = 0; i < ids.length; i++) {
			boolean flag = L_user.dao.deleteById(ids[i]);
			if (flag) {
				j++;
			} else {
				list.add(ids[i]);
			}
		}
		if (j < ids.length) {
			return false;
		} else {
			return true;
		}

	}
	public Record queryById(int id){

		Record record=Db.findFirst("SELECT u.id,u.nick_name,u.account,u.img,u.expand,u.img\n"
				+ "FROM l_user u\n"
				+ "WHERE 1=1\n"
				+ "AND u.id=?",id);


		return record;
	}

	public Map<String,Object> findUserMapList(int pid){

		L_user user=L_user.dao.findFirst("select * from user where id ="+pid+"");
		String expands=user.get("expand");
		Map<String,Object> map=new HashMap<String,Object>();
		if(expands==null){
			map.put("phone","");
			map.put("address","");
			map.put("email","");
			String namenull=user.get("nick_name");
			int idnull=user.get("id");
			String imgnull=user.get("img");
			String accountnull=user.get("account");
			String createtimenull=user.get("create_time");
			map.put("nick_name",namenull);
			map.put("id",idnull);
			map.put("img",imgnull);
			map.put("account",accountnull);
			map.put("create_time",createtimenull);
			return map;
		}
		JSONArray jsa= JSON.parseArray(expands);

		for( int i=0;i<jsa.size();i++){
			JSONObject js=jsa.getJSONObject(i);
			String  phone=js.get("phone").toString();
			String address=js.get("address").toString();
			String email=js.get("email").toString();
			map.put("phone",phone);
			map.put("address",address);
			map.put("email",email);
		}
		String name=user.get("nick_name");
		int id=user.get("id");
		String img=user.get("img");
		String account=user.get("account");
		String createtime=user.get("create_time");
		map.put("nick_name",name);
		map.put("id",id);
		map.put("img",img);
		map.put("account",account);
		map.put("create_time",createtime);
		return  map;



	}

	public boolean updateUser(int id,String nick_name,String account,String img){
		try{
			L_user user = L_user.dao.findById(id);
			user.set("nick_name",nick_name);
			user.set("account",account);
			user.set("img",img);
			boolean a=user.update();
			return a;
		}catch (Exception e){
			return false;
		}
	}

	//获取用户的详细信息
	public Page<Record> getUserList(int page,int pageSize) {


		String sql1="select u.id, u.creater,u.create_time,u.version,u.account,u.nick_name,u.img ";
		String sql2="from l_user u ";


		String sql3 = " ORDER BY u.create_time DESC";
		sql2 += sql3;
		Page<Record> list= null;
		try {
			list = Db.paginate(page,pageSize,sql1,sql2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
/*
		List<User> list = User.dao.find("select u.id, u.creater,u.create_time,u.version,u.account,u.password,u.nick_name" +
				" from user u");
		return list;*/
	}

	public List<L_user> getAllUser(){
		List<L_user> list = L_user.dao.find("select u.id, u.creater,u.img,u.create_time,u.version,u.account,u.nick_name" +
				" from l_user u order by convert(nick_name using gbk) ASC");
		return list;
	}

}
