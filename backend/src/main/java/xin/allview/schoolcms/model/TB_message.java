package xin.allview.schoolcms.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * Created by lxh on 2017/7/25.
 */
public class TB_message extends Model<TB_message> {
    public static  final TB_message dao=new TB_message();

    /**
     * 查询两人的对话
     * @param page
     * @param pageSize
     * @param uuId
     * @return
     */
    public Page<Record> concernMessageGLZ(int page,int pageSize,int uuId){
        String sql="select tb.*,u.nick_name,w.img as image,w.weixin_name  ";
        String sql2=" from tb_message tb left join t_upesnuser u on tb.creater =u.userid\n" +
                "left join  t_weixinuser w  on w.userid=tb.creater\n" +
                "where tb.uuid=? order by tb.sendtime DESC";
        try{
            return Db.paginate(page,pageSize,sql,sql2,uuId);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * 添加聊天记录
     * @param uuId
     * @param context
     * @param time
     * @return
     */
    public boolean addMessage(int uuId,String context,String time,int userId){
        TB_message m=new TB_message();
        m.set("uuid",uuId);
        m.set("sendcontent",context);
        m.set("sendtime",time);
        m.set("creater",userId);
        return m.save();
    }
}
