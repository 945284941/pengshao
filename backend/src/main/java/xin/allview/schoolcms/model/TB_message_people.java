package xin.allview.schoolcms.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import xin.allview.utils.Uitls;

/**
 * Created by lxh on 2017/7/24.
 */
public class TB_message_people extends Model<TB_message_people> {

    public static final TB_message_people dao=new TB_message_people();


    public Page<Record> myConcern(int page,int pageSize,int userId){

        String sql="select m.id,m.sender_id,m.addressee_id,m.uuid,m.last_time,m.expand->'$.num' as num ,cast(m.expand->'$.who' as SIGNED) as who, w.weixin_name,w.img,\n" +
                "\n" +
                "u.nick_name ";
        String sql1="from tb_message_people m left join t_weixinuser w on m.addressee_id=w.userid left join\n" +
                "\n" +
                "t_upesnuser u on u.userid=m.addressee_id\n" +
                "\n" +
                "where m.sender_id=?  and m.expand->'$.num'!='0' order by m.last_time DESC";
        try{
             return Db.paginate(page,pageSize,sql,sql1,userId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public int findClassId(int userId,int yourId){
        TB_message_people m=TB_message_people.dao.findFirst("select * from tb_message_people where sender_id=? and addressee_id=? order by last_time desc ,expand->'$.who' DEsC ",userId,yourId);
        return m.getInt("uuid");
    }
    public void update(int uuId,String time){

        Db.update("update tb_message_people set last_time=?,expand = JSON_REPLACE(expand, \"$.num\", ?) where uuid=?",time,10,uuId);

    }

    public TB_message_people haveSmae(int userId,int yourId){
       return TB_message_people.dao.findFirst("select * from tb_message_people where sender_id =? and addressee_id=? ",userId,yourId);
    }
    public boolean addMessagePeople(int userId,int yourId,int classId){

        TB_message_people m=new TB_message_people();
        m.set("sender_id",userId);
        m.set("addressee_id",yourId);
        m.set("last_time",Uitls.currTime());
        m.set("expand","{\"num\":\"1\",\"who\":\"2\"}");
        m.set("uuid",classId);
        m.save();
        TB_message_people me=new TB_message_people();
        me.set("sender_id",yourId);
        me.set("addressee_id",userId);
        me.set("last_time",Uitls.currTime());
        me.set("expand","{\"num\":\"1\",\"who\":\"2\"}");
        me.set("uuid",classId);
        return me.save();

    }

    public boolean addMessagePeopleYezhu(int userId,int yourId,int classId){

        TB_message_people m=new TB_message_people();
        m.set("sender_id",userId);
        m.set("addressee_id",yourId);
        m.set("last_time",Uitls.currTime());
        m.set("expand","{\"num\":\"0\",\"who\":\"1\"}");
        m.set("uuid",classId);
        m.save();
        TB_message_people me=new TB_message_people();
        me.set("sender_id",yourId);
        me.set("addressee_id",userId);
        me.set("last_time",Uitls.currTime());
        me.set("expand","{\"num\":\"0\",\"who\":\"1\"}");
        me.set("uuid",classId);
        return me.save();

    }
}
