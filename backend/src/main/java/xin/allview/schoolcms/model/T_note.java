package xin.allview.schoolcms.model;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import xin.allview.utils.Uitls;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class T_note extends Model<T_note> {

    public static final T_note dao = new T_note();


    /**
     * 查看帖子有关的所有分类
     * pyy
     *
     * @param id
     * @return
     */
    public List<T_classification> findClassificationByNotes(int id) {
        String sql = "select tc.* from t_classification tc LEFT JOIN l_classificationnote lc ON tc.id=lc.classid where lc.noteid= ?";
        List<T_classification> list = T_classification.dao.find(sql, id);
        return list;
    }


    /**
     * 查看分类下的帖子 按时间倒序
     * pyy
     *
     * @param page
     * @param pageSize
     * @param id
     * @return
     */
    public Page<Record> findNotesByClassId(int page, int pageSize, int id,int departId) {
        String sql = "SELECT tn.*,lu.nick_name,lu.img as image ,lc.classid,tl.name as departname,tup.upesnid as upesnid  ";
        String sql1 = "FROM t_note tn LEFT JOIN l_classificationnote lc ON tn.id = lc.noteid LEFT JOIN l_user lu on lu.id =tn.creater \n" +
                "\n" +
                "left join t_department tl  on tn.expand->'$.departid'=concat(tl.id,\"\") left join t_upesnuser tup on tup.upesnid = tn.creater \n" +
                "\n" +
                " WHERE lc.classid= ? ";
        if(departId==0){
            sql1 += "ORDER BY tn.createtime DESC";
        }else{
            sql1+="and  cast(tn.expand->'$.departid' as UNSIGNED) ="+departId+" ";
            sql1 += "ORDER BY tn.createtime DESC";
        }


        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql, sql1, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }


    /**
     * 查看某个用户发的帖子 -- 微信端 按时间倒序
     *KLP
     */
    public Page<Record> findNotesByUserId(int page, int pageSize, int userId,String juweihui_id) {
        String sql = "select tn.*,tc.classname,tup.upesnid as upesnid ,lu.nick_name as name  ";
        String sql1 = " from t_note tn left join l_classificationnote lc ON tn.id = lc.noteid left join t_classification tc on lc.classid = tc.id  left join t_upesnuser tup on tup.upesnid = tn.creater left join l_user lu on lu.id = tn.creater where tn.creater = ? ";
        if(juweihui_id==""){
            sql1 += " ORDER BY tn.createtime DESC";
        }else{
            sql1+=" and cast(tn.expand->'$.departid' as UNSIGNED) = ? ";
            sql1 += " ORDER BY tn.createtime DESC";
        }


        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql, sql1, userId ,juweihui_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }



    /**
     * 查看帖子详情
     * pyy
     *
     * @param id
     * @return
     */
    public Record findOneById(int id) {
        Record record = Db.findFirst("select tn.* ,lu.nick_name,l.classid,lu.img as image,td.name as departname,tc.classname as classname from t_note tn LEFT JOIN l_user lu ON tn.creater=lu.id left join l_classificationnote l on l.noteid=tn.id  left join t_department td on  concat(td.id,\"\") =tn.expand->'$.departid' left join l_classificationnote lc on lc.noteid = tn.id left join t_classification tc on tc.id = lc.classid WHERE tn.id=?", id);

        return record;

    }


    /**
     * 查询帖子详情及所属分类
     * <p>
     * find note details which include the details data of itself
     * and classification info of it
     * -- created by Samous
     *
     * @param noteId
     * @return Record
     */

    public Record findNoteById(int noteId) {
        String sql = "SELECT a.*,b.* ,c.* FROM l_classificationnote a " +
                " LEFT JION t_classification b on a.classid=b.id " +
                " LEFT JION t_note c on a.note_id=c.id " +
                " WHERE a.note_id = ?";
        Record record = Db.findFirst(sql, noteId);
        System.out.println(record);

        return record;


    }


    /**
     * 查询帖子的回复列表
     * pyy
     *
     * @param page
     * @param pageSize
     * @param noteId
     * @return
     */
    public Page<Record> findReplyByNoteId(int page, int pageSize, int noteId) {
        String sql1 = "select tr.*,lu.nick_name,lu.img as image ";
        String sql2 = "from t_reply tr LEFT JOIN l_user lu on tr.creater=lu.id where tr.reply_note=? order by tr.create_time DESC";
        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql1, sql2, noteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 友空间电脑端查询拆解步骤
     * KLP
     */
    public List<T_reply> findReplyByNoteIdASC(int noteId) {
        String sql = "select tr.*,lu.nick_name,lu.img as image from t_reply tr LEFT JOIN l_user lu on tr.creater=lu.id where tr.reply_note = "+noteId+" order by tr.create_time ASC";
        List<T_reply> list = new ArrayList<T_reply>();
        try {
           list = T_reply.dao.find(sql);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }


    /**
     * 查询帖子列表，内容包含帖子本身的全部信息，及关联的分类信息，以及创建人信息
     * <p>
     * select note list by these classid which the list info includes
     * the details data of themselves, classifications info and the creator info
     * -- created By Samous
     *
     * @param classId
     * @param page
     * @param pageSize
     * @return Page<Record>
     */

    public Page<Record> findNoteListByClassId(int classId, int page, int pageSize, String context,int departid) {
        String sql1 = "select * ";

        String sql2 = " FROM (SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg,td.name as departname\n" +
                " FROM l_classificationnote cn \n" +
                " LEFT JOIN t_note n ON cn.noteid = n.id\n" +
                " LEFT JOIN t_classification c ON cn.classid = c.id\n" +
                " LEFT JOIN l_user u ON n.creater = u.id \n" +
                "left join t_department td on  concat(td.id,\"\") =n.expand->'$.departid' WHERE cn.classid = ?) o where  1=1 ";
        String sql3 = " ORDER BY o.createtime DESC";

        /*if (context.equals("") || context == null) {

        }else{
            sql2+=" and o.nick_name like '%" + context + "%' or o.title like '%" + context + "%'";
        }
        if (departid!=0) {
            sql2+=" and o.expand->'$.departid' like '%" + departid + "%'";
        }
        sql2=+sql3;*/
        if (context.equals("")) {
            if(departid==0){

                sql2 = sql2  + sql3;
            }else{

                sql2 = sql2  + " and o.expand->'$.departid' like '%" + departid + "%'   "+sql3;
            }
        } else {

            if(departid==0){
                String sql4 = "  and o.nick_name like '%" + context + "%' or o.title like '%" + context + "%'";
                sql2 = sql2 + sql4 + sql3;
            }else{
                String sql4 = "  and  o.nick_name like '%" + context + "%' or o.title like '%" + context + "%'";
                sql2 = sql2 + sql4 + "and o.expand->'$.departid' like '%" + departid + "%'   "+sql3;
            }


        }
        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql1, sql2, classId);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public Page<Record> findJobPol(int classId, int page, int pageSize, String context,int departid) {
        String sql1 = "SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg ";

        String sql2 = "FROM l_classificationnote cn " +
                "LEFT JOIN t_note n ON cn.noteid = n.id " +
                "LEFT JOIN t_classification c ON cn.classid = c.id " +
                "LEFT JOIN l_user u ON n.creater = u.id " +
                "WHERE cn.classid = ? and n.type is not null";
        String sql3 = "ORDER BY n.createtime DESC";
        if (context.equals("") || context == null) {
            sql2 = sql2 + sql3;
        } else {
            if(departid==0){
                String sql4 = "  where o.nick_name like '%" + context + "%' or o.title like '%" + context + "%'";
                sql2 = sql2 + sql4 + sql3;
            }else{
                String sql4 = "  where o.nick_name like '%" + context + "%' or o.title like '%" + context + "%'";
                sql2 = sql2 + sql4 + "and o.expand->'$.departid' like '%\" + departid + \"%'   "+sql2;
            }

        }
        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql1, sql2, classId);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    /**
     * select one single note by its' classid which its' info includes
     * the details data of itself, classification info and the creator info also
     * -- created By Samous
     *
     * @param classId
     * @return Page<Record>
     */

    public Record findOneNoteByClassId(int classId) {
        String sql = "SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg ";

        sql += "FROM l_classificationnote cn " +
                "LEFT JOIN t_note n ON cn.noteid = n.id " +
                "LEFT JOIN t_classification c ON cn.classid = c.id " +
                "LEFT JOIN l_user u ON cn.creater = u.id ";

        if (classId != 0) {
            sql += "WHERE cn.classid = " + classId;
        }
        sql += " ORDER BY n.createtime DESC";
        Record note = null;
        try {
            note = Db.findFirst(sql);
            return note;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 查看老师发的帖子列表
     *
     * @param userid
     * @return
     */
    public List<T_note> findNoteByUserId(int userid) {
        List<T_note> list = T_note.dao.find("select * from t_note where creater= ?", userid);
        T_note.dao.deleteById("a", 1);
        return list;

    }


    /**
     * creater by peng
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public Page<Record> findDynamicsbyUserId(int userId, int page, int pageSize) {
        String sql = "select u.img,n.createtime,n.title,n.context,n.id,n.creater,n.version,u.nick_name";
        String sql1 = "from t_classification t LEFT JOIN l_classificationnote l on t.id=l.classid\n" +
                "LEFT JOIN t_note n on n.id=l.noteid LEFT JOIN l_user u on n.creater=u.id\n" +
                "where t.id=76 or t.pid=76 order by n.createtime DESC";
        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql, sql1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * creater by peng
     *
     * @param page
     * @param pageSize
     * @param personName
     * @param context
     * @return
     */
    public Page<Record> selectDynamics(int page, int pageSize, String personName, String context) {
        String sql = "select u.img,n.createtime,n.title,n.context,n.id,n.creater,n.version,u.nick_name";
        String sql1 = "from t_classification t LEFT JOIN l_classificationnote l on t.id=l.classid\n" +
                "LEFT JOIN t_note n on n.id=l.noteid LEFT JOIN l_user u on n.creater=u.id" +
                " where n.context like '%" + context + "%' or u.nick_name like '%" + context + "%' order by n.createtime DESC ";
        Page<Record> pageList = null;
        try {
            pageList = Db.paginate(page, pageSize, sql, sql1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageList;
    }

    /**
     * creater by peng
     * 保存一条帖子并获取其id
     *
     * @param userId
     * @param context
     * @return
     */
    public int addDynamicsByUserId(int userId, String context, String type, String title, String imgurl,int departid) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        T_note tn = new T_note();


        tn.set("creater", userId);
        tn.set("context", context);
        tn.set("createtime", time);
        tn.set("version", 1);
        if (type.equals("") || type == null) {

        } else {

            tn.set("type", type);
        }
        if (title.equals("") || title == null) {

        } else {
            tn.set("title", title);
        }
        if (imgurl.equals("")) {

        } else {
            tn.set("img", imgurl);
        }
        if(departid!=0){
            tn.set("expand", "{\"departid\":\"" + departid + "\"}");
        }

        tn.save();
        return tn.getInt("id");

    }


    public Page<Record> findNoteListBySearch(int classId, int page, int pageSize) {
        String sql1 = "SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg ";


        String sql2 = "FROM l_classificationnote cn " +
                "LEFT JOIN t_note n ON cn.noteid = n.id " +
                "LEFT JOIN t_classification c ON cn.classid = c.id " +
                "LEFT JOIN l_user u ON n.creater = u.id " +
                "WHERE cn.classid = ? " +
                "ORDER BY n.createtime DESC";
        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql1, sql2, classId);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * CREATER BY PENG
     *
     * @param classId
     * @param page
     * @param pageSize
     * @return
     */
    public Page<Record> findNoteListByUser(int classId, int page, int pageSize, int userId) {
        String sql1 = "select *   ";
        String sql2 = "from (SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg FROM l_classificationnote cn " +
                "LEFT JOIN t_note n ON cn.noteid = n.id " +
                "LEFT JOIN t_classification c ON cn.classid = c.id " +
                "LEFT JOIN l_user u ON n.creater = u.id " +
                "WHERE c.pid= cn.classid  or cn.classid = ?  ) o  where o.creater= ?  " +
                "ORDER BY o.createtime DESC";
        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql1, sql2, classId, userId);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * peng
     * 工作宣传
     *
     * @param classId
     * @param page
     * @param pageSize
     * @return
     */
    public Page<Record> findWorkPublicity(int classId, int page, int pageSize, String context,int departid) {

        String sql = "select *   ";
        String sql1 = "from (select t.classname,n.title,n.context,n.creater,n.id,n.createtime,n.img,n.type,u.nick_name,n.expand \n" +
                "from l_classificationnote l left JOIN\n" +
                "t_note n on l.noteid=n.id LEFT JOIN l_user u on n.creater =u.id left JOIN\n" +
                "t_classification t on t.id=l.classid\n" +
                "where n.type = 2 and l.classid=?) o where 1=1 ";

        String sql2 = " order by o.createtime DESC";
        if (context.equals("")) {

        }else{
            sql1+=" and o.nick_name like '%" + context + "%' or o.title like '%" + context + "%'";
        }
        if (departid!=0) {
            sql1+=" and o.expand->'$.departid' like '%" + departid + "%'";
        }
        sql1+=sql2;


        Page<Record> pageList = null;
        try {
            pageList = Db.paginate(page, pageSize, sql, sql1, classId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageList;
    }

    /**
     * 工作宣传
     *
     * @param page
     * @param pageSize
     * @param classId
     * @return
     */
    public Page<Record> findJobPublicity(int page, int pageSize, int classId) {

        String sql = "select * ";
        String sql1 = " from (SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg\n" +
                "FROM l_classificationnote cn LEFT JOIN t_note n ON cn.noteid = n.id\n" +
                "LEFT JOIN t_classification c ON cn.classid = c.id\n" +
                "LEFT JOIN l_user u ON n.creater = u.id\n" +
                "WHERE c.pid= cn.classid  or cn.classid = ?)o where o.type=2";
        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql, sql1, classId);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 工作宣传 我 peng
     *
     * @param userId
     * @param page
     * @param pageSize
     * @param classId
     * @return
     */
    public Page<Record> findJobPublicityMe(int userId, int page, int pageSize, int classId) {


        String sql1 = "select *   ";


        String sql2 = "from (SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg\n" +
                "FROM l_classificationnote cn LEFT JOIN t_note n ON cn.noteid = n.id\n" +
                "LEFT JOIN t_classification c ON cn.classid = c.id\n" +
                "LEFT JOIN l_user u ON n.creater = u.id\n" +
                "WHERE c.pid= cn.classid  or cn.classid = ?)o where o.type=2 and o.creater =?";
        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql1, sql2, classId, userId);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 政策宣传 creater by peng
     *
     * @param page
     * @param pageSize
     * @param classId
     * @return
     */
    public Page<Record> findPolicyGuidance(int page, int pageSize, int classId) {

        String sql = "select * ";
        String sql1 = " from (SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg\n" +
                "FROM l_classificationnote cn LEFT JOIN t_note n ON cn.noteid = n.id\n" +
                "LEFT JOIN t_classification c ON cn.classid = c.id\n" +
                "LEFT JOIN l_user u ON n.creater = u.id\n" +
                "WHERE c.pid= cn.classid  or cn.classid = ?)o where o.type=1";
        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql, sql1, classId);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 政策宣传 我 peng
     *
     * @param userId
     * @param page
     * @param pageSize
     * @param classId
     * @return
     */

    public Page<Record> findPolicyGuidanceMe(int userId, int page, int pageSize, int classId) {


        String sql1 = "select *   ";


        String sql2 = "from (SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg\n" +
                "FROM l_classificationnote cn LEFT JOIN t_note n ON cn.noteid = n.id\n" +
                "LEFT JOIN t_classification c ON cn.classid = c.id\n" +
                "LEFT JOIN l_user u ON n.creater = u.id\n" +
                "WHERE c.pid= cn.classid  or cn.classid = ?)o where o.type=1 and o.creater =?";
        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql1, sql2, classId, userId);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * peng
     * 政策宣传
     *
     * @param classId
     * @param page
     * @param pageSize
     * @return
     */
    public Page<Record> findGuidance(int classId, int page, int pageSize, String context,int departid) {
        String sql = "select *   ";
        String sql1 = "from (select t.classname,n.title,n.context,n.creater,n.id,n.createtime,n.img,n.type,u.nick_name, n.expand \n" +
                "from l_classificationnote l left JOIN\n" +
                "t_note n on l.noteid=n.id LEFT JOIN l_user u on n.creater =u.id left JOIN\n" +
                "t_classification t on t.id=l.classid\n" +
                "where n.type = 1 and l.classid=?) o where 1=1 ";

        String sql2 = " order by o.createtime DESC";

        if (context.equals("") || context == null) {

        }else{
            sql1+=" and o.nick_name like '%" + context + "%' or o.title like '%" + context + "%'";
        }
        if (departid!=0) {
            sql1+=" and o.expand->'$.departid' like '%" + departid + "%'";
        }

        sql1+=sql2;



       /* if (context.equals("") || context == null) {

            sql1 = sql1 + sql2;
        } else {
            if(departid==0){
                String sql3 = "  where o.nick_name like '%" + context + "%' or o.title like '%" + context + "%'";
                sql1 = sql1 + sql3 + sql2;
            }else{
                String sql3 = "  where o.nick_name like '%" + context + "%' or o.title like '%" + context + "%'";
                sql1 = sql1 + sql3 + "and o.expand->'$.departid' like '%\" + departid + \"%'   "+sql2;
            }
        }*/
        Page<Record> pageList = null;
        try {
            pageList = Db.paginate(page, pageSize, sql, sql1, classId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageList;
    }


    /**
     * 添加帖子
     * pyy
     * <p>
     * 改动
     * peng
     */

    public int addNote(int userid, String title, String context, String imgurl, String startTime, String endTime,int departid) {
        T_note tn = new T_note();
        tn.set("creater", userid);
        String time = "";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        time = df.format(new Date());// new Date()为获取当前系统时间
        if (startTime.equals("")) {
            tn.set("expand", "{\"departid\":\"" + departid + "\"}");

        } else {


            tn.set("expand", "{\"departid\":\"" + departid + "\",\"startTime\":\"" + startTime + "\",\"endTime\":\"" + endTime + "\"}");


        }
        tn.set("createtime", time);


        tn.set("title", title);
        tn.set("context", context);
        tn.set("img", imgurl);
        boolean b = tn.save();

        if (b) {
            return tn.get("id");
        } else {
            return 0;
        }

    }

    /**
     * 刘晓鹏
     *
     * @param classId
     * @param page
     * @param pageSize
     * @return
     */
    public Page<Record> findMessage(int classId, int page, int pageSize) {

        String sql1 = "select t.id,u.nick_name,u.img,t.context,t.createtime,t.version,l.classid,o.classname";
        String sql2 = "from l_classificationnote l " +
                "LEFT JOIN t_note t on l.noteid=t.id " +
                "LEFT JOIN l_user u on t.creater=u.id   LEFT JOIN t_classification o ON o.id=l.classid  where l.classid=? " +
                "order by t.createtime DESC";

        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql1, sql2, classId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public Page<Record> findNoteListWork(int classId, int userId, int page, int pageSize) {
        String sql1 = "select o.* ";
        String sql2 = "from (SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg\n" +
                "FROM l_classificationnote cn\n" +
                "LEFT JOIN t_note n ON cn.noteid = n.id\n" +
                "LEFT JOIN t_classification c ON cn.classid = c.id \n" +
                "LEFT JOIN l_user u ON n.creater = u.id \n" +
                "WHERE cn.classid = ? or c.pid =? )o where o.creater=?";

        sql2 += " ORDER BY o.createtime DESC";

        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql1, sql2, classId, classId, userId);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <<<<<<< HEAD
     *
     * @param classId          分类id
     * @param userId           创建人
     * @param page             页数
     * @param pageSize         页显示
     * @param startTime        开始时间
     * @param endTime          结束时间
     * @param worKStatus       状态
     * @param fuze             负责与派生
     * @param weekEndTime      //当前周的开始时间  开始时间
     * @param weekStartTime    //当前州的结束时间  开始时间
     * @param EndWeekStartTime //当前周的开始时间  截止时间
     * @param NoDate           //无日期
     * @param EndWeekEndTime   //当前周的结束时间 截止时间
     * @return creater by peng
     */

    public Page<Record> findNoteListWorkSelect(int classId, int userId, int page, int pageSize,
                                               String startTime, String endTime, String worKStatus, String fuze,
                                               String weekStartTime, String weekEndTime, String EndWeekStartTime,
                                               String EndWeekEndTime, String NoDate, String daytime) {


        String sql1 = "select o.* ";

        String sql2 = "from (SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg\n" +
                "FROM l_classificationnote cn\n" +
                "LEFT JOIN t_note n ON cn.noteid = n.id\n" +
                "LEFT JOIN t_classification c ON cn.classid = c.id \n" +
                "LEFT JOIN l_user u ON n.creater = u.id \n" +
                "WHERE cn.classid = ? or c.pid =? )o where 1=1  ";

        if (startTime.equals("")) {
        } else {
            sql2 += "and o.expand->'$.startTime' like '%" + startTime + "%' ";
        }
/*            if(fuze.equals("")){
                sql2+=" and  o.creater="+userId+" or o.expand->'$.people' like '%,"+userId+",%' ";
            }*/

        if (!daytime.equals("")) {
            if (!startTime.equals("") && !endTime.equals("")) {
                sql2 += "and o.expand->'$.startTime' < '" + daytime + "' and " + daytime + " < o.expand->'$.endTime'";
            } else {
                sql2 += "and o.expand->'$.startTime' like '%" + daytime + "%'";
            }

            if (fuze.equals("1")) {//我负责
                sql2 += " and  o.expand->'$.people' like '%," + userId + ",%' ";
            }
            if (fuze.equals("2")) {//我开始 也就是我创建的
                sql2 += " and  o.creater=" + userId + " ";//我创建
            }
            if(fuze.equals("3")){
                sql2+="and o.creater ="+userId+" and o.expand->'$.derivation' is not null" ;
            }

            if (!startTime.equals("")) {//开始时间
                sql2 += " and o.expand->'$.startTime' like '%" + startTime + "%' ";
            }
            if (!endTime.equals("")) {//结束时间
                sql2 += " and o.expand->'$.endTime' like '%" + endTime + "%' ";
            }
            if (!worKStatus.equals("")) {//状态
                sql2 += " and o.expand->'$.status'=" + worKStatus + "";
            }

        } else {

            if (fuze.equals("1")) {//我负责
             /*   sql2+="and  o.expand->'$.people' like '%,"+userId+",%'";*/
                sql2 += " and  o.expand->'$.people' like '%," + userId + ",%' ";
            }
            if (fuze.equals("2")) {//我开始 也就是我创建的
                sql2 += " and  o.creater=" + userId + " ";//我创建
            }
            if(fuze.equals("3")){
                sql2+="and o.creater ="+userId+" and o.expand->'$.derivation' is not null" ;
            }
            if (NoDate.equals("")) {//没有时间的
                sql2 += " and o.expand->'$.startTime' is null and o.expand-> '$.endTime' is null ";
            }

            if (!startTime.equals("")) {//开始时间
                sql2 += " and o.expand->'$.startTime' like '%" + startTime + "%' ";
            }
            if (!endTime.equals("")) {//结束时间
                sql2 += " and o.expand->'$.endTime' like '%" + endTime + "%' ";
            }
            if (!worKStatus.equals("")) {//状态
                sql2 += " and o.expand->'$.status'=" + worKStatus + "";
            }
            if (!weekStartTime.equals("") && !weekEndTime.equals("")) {//周开始
                sql2 += " and   o.expand->'$.startTime' > '" + weekStartTime + "' and o.expand->'$.startTime' < '" + weekEndTime + "'";
            }
            if (!EndWeekStartTime.equals("") && !EndWeekEndTime.equals("")) {//周截止
                sql2 += " and   o.expand->'$.endTime' > '" + EndWeekStartTime + "' and o.expand->'$.endTime' < '" + EndWeekEndTime + "'";
            }
        }

        sql2 += " ORDER BY o.createtime DESC";
        System.out.print("sql2");
        Page<Record> list = null;
        try {

            list = Db.paginate(page, pageSize, sql1, sql2, classId, classId);

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Page<Record> findNoteListBySearch(int classId, String title, String name,int departid, int page, int pageSize) {
        String sql1 = "select a.*   ";

        String sql2 = "from  (SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg ,td.name as departname \n" +
                "FROM l_classificationnote cn LEFT JOIN t_note n ON cn.noteid = n.id \n" +
                "LEFT JOIN t_classification c ON cn.classid = c.id \n" +
                "LEFT JOIN l_user u ON n.creater = u.id " +
                "left join t_department td on  concat(td.id,'') = n.expand->'$.departid' WHERE cn.classid = ?   or c.pid=?) a where 1=1 ";
        if (name == null || name == "") {
        } else {
            sql2 += " and a.nick_name like '%" + name + "%' ";
        }
        if (title == null || title == "") {
        } else {
            sql2 += " and a.title LIKE '%" + title + "%' ";
        }
        if (departid!=0) {

            sql2 += " and a.expand->'$.departid' like '%" + departid + "%'  ";

        }
        sql2 += " ORDER BY a.createtime DESC";

        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql1, sql2, classId, classId);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * KLP
     */
    public Page<Record> findNotes(int classId, String title, int userId, String name, int page, int pageSize) {
        String sql1 = "SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg ";

        String sql2 = "FROM l_classificationnote cn " +
                "LEFT JOIN t_note n ON cn.noteid = n.id " +
                "LEFT JOIN t_classification c ON cn.classid = c.id " +
                "LEFT JOIN l_user u ON n.creater = u.id " +
                "WHERE cn.classid = ? and n.creater = ? ";
        if (name == null || name == "") {
        } else {
            sql2 += " and u.nick_name like '%" + name + "%' ";
        }
        if (title == null || title == "") {
        } else {
            sql2 += " and title LIKE '%" + title + "%' ";
        }
        sql2 += " ORDER BY n.createtime DESC";

        Page<Record> list = null;
        try {
            list = Db.paginate(page, pageSize, sql1, sql2, classId, userId);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 通过id 删除帖子
     * pyy
     */
    public boolean deleteNoteById(int id) {
        boolean b = T_note.dao.deleteById(id);
        return b;

    }

    /**
     * pyy
     * 修改帖子内容
     *
     * @param id
     * @param title
     * @param content
     * @param imgurl
     * @return
     */
    public boolean updateNote(int id, String title, String content, String imgurl, String startTime, String endTime, String type,int departid) {

        T_note tn = T_note.dao.findById(id);
        tn.set("title", title);
        tn.set("context", content);
        tn.set("img", imgurl);
        boolean flage = false;
        if (startTime.equals("")) {
            tn.set("expand", "{\"departid\":\"" + departid + "\"}");

        } else {


            tn.set("expand", "{\"departid\":\"" + departid + "\",\"startTime\":\"" + startTime + "\",\"endTime\":\"" + endTime + "\"}");


        }
        if (type.equals("")) {
            flage = tn.update();
        } else {
            int tp = Integer.parseInt(type);
            tn.set("type", tp);
            flage = tn.update();
        }

        return flage;

    }

    public boolean findSomeName(int classId, String title) {
        boolean flage = false;
        Record r = Db.findFirst("select * from l_classificationnote l LEFT JOIN t_note n  on l.noteid =n.id where l.classid=? and n.title=?", classId, title);
        if (r == null) {
            flage = true;
        } else {
            flage = false;
        }
        return flage;
    }

    /**
     * 轮询查 新增加的数据 查看没查看
     *
     * @param id
     * @param userId
     * @return peng
     */
    public List messageReminder(int id, int userId) {


        String sql = "select o.*  from (SELECT n.*, c.classname, c.id classid ,u.nick_name, u.img userImg\n" +
                "FROM l_classificationnote cn\n" +
                "LEFT JOIN t_note n ON cn.noteid = n.id\n" +
                "LEFT JOIN t_classification c ON cn.classid = c.id \n" +
                "LEFT JOIN l_user u ON n.creater = u.id \n" +
                "WHERE cn.classid = ? or c.pid =? )o where 1=1 and o.creater =? and o.expand->'$.newMessage' = 1 ";
        List list = T_note.dao.find(sql, id, id, userId);
        return list;
    }

    /**
     * 添加会议
     * -- created by Samous
     *
     * @param userid
     * @param title
     * @param startTime
     * @param location
     * @param people
     * @return
     */

        public int addMeetingNote(int userid, String title, String startTime, String location, Integer[] people, String content,int pid) {

        //将数组转为字符串保存
        String ids = "";
        for (int i = 0; i < people.length - 1; i++) {
            ids += people[i] + ",";
        }
        ids += people[people.length - 1];

        T_note n = new T_note();

//newMessage 为新增加的信息 1状态是新增加  2状态是以增加
            if(pid==0){
                if (startTime != "" && location != "") {
                    n.set("expand", "{\"startTime\":\"" + startTime + "\",\"location\":\"" + location + "\",\"people\":\"" + ids + "\",\"status\":1,\"newMessage\":1 }");
                } else {
                    n.set("expand", "{\"people\":\"" + ids + "\",\"status\":1,\"newMessage\":1}");
                }
            }else{
                if (startTime != "" && location != "") {
                    n.set("expand", "{\"startTime\":\"" + startTime + "\",\"location\":\"" + location + "\",\"people\":\"" + ids + "\",\"status\":1,\"newMessage\":1,\"derivation\":\"" + pid + "\" }");
                } else {
                    n.set("expand", "{\"people\":\"" + ids + "\",\"status\":1,\"newMessage\":1,\"derivation\":\""+pid+"\"}");
                }
            }




        n.set("creater", userid);
        n.set("createtime", Uitls.currTime());
        n.set("title", title);

        if (content != "") {
            n.set("context", content);
        }

        boolean b = n.save();

        if (b) {
            return n.get("id");
        } else {
            return 0;
        }
    }

    /**
     * 添加拆解任务：
     * -- created By Samous
     *
     * @param userid
     * @param title
     * @param people
     * @param content
     * @return
     */

    public int addStepNote(int userid, String title, Integer[] people, String content) {

        //将数组转为字符串保存
        String ids = "";
        for (int i = 0; i < people.length - 1; i++) {
            ids += people[i] + ",";
        }

        ids += people[people.length - 1];
        T_note n = new T_note();
        n.set("expand", "{\"status\":\"" + 1 + "\",\"people\":\"" + ids + "\"}");
        n.set("creater", userid);
        n.set("createtime", Uitls.currTime());
        n.set("title", title);

        if (content != "") {
            n.set("context", content);
        }

        boolean b = n.save();

        if (b) {
            return n.get("id");
        } else {
            return 0;
        }

    }

    /**
     * 更新状态
     * -- created by Samous
     *
     * @param id
     * @param status
     * @return
     */
    public Integer updateStatus(Integer id, Integer status) {
        Integer upStatus = Db.update("UPDATE t_note SET expand = JSON_REPLACE(expand, \"$.status\", ?) WHERE id = ?", status, id);
        System.out.println(upStatus);
        return upStatus;
    }

    /**
     * 获取两人tanhua
     * @param page
     * @param pageSize
     * @param classId
     * @return
     */
    public Page<Record> concernMessage(int page,int pageSize,int classId){

         String sql="select  n.*,w.weixin_name,w.img as image ";
        String sql1="from l_classificationnote c left join t_note n on c.noteid=n.id left join t_weixinuser w on n.creater=w.userid\n" +
                "where c.classid=? order by n.createtime DESC";
        try{
            return Db.paginate(page,pageSize,sql,sql1,classId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public Page<Record> concernMessageYou(int  page,int pageSize,int classId){
        String sql="select  n.*,w.nick_name as weixin_name ,w.img as image ";
        String sql1="from l_classificationnote c left join t_note n on c.noteid=n.id left join t_upesnuser w on n.creater=w.userid\n" +
                "where c.classid=? order by n.createtime DESC";
        try{
            return Db.paginate(page,pageSize,sql,sql1,classId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int  addMessage(int userId,String content){
        T_note n=new T_note();
        n.set("creater",userId);
        n.set("createtime",Uitls.currTime());
        n.set("version",1);
        n.set("context",content);
        n.save();
        return n.getInt("id");

    }
}
