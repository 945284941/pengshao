package xin.allview.schoolcms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import xin.allview.schoolcms.interceptor.FrontInterceptor;
import xin.allview.schoolcms.model.*;
import xin.allview.utils.Uitls;
import xin.allview.schoolcms.tool.UpesnTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 帖子
 */
@Before(FrontInterceptor.class)
public class NoteController extends Controller {


/////////////////////////////////////////            手机端首页展示（start）            ////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 每日一篇好文章 | 每日金句 | 今日热点  三个模块的接口
     * <p>
     * -- created By Samous
     */

    @Before(GET.class)
    public void findOneDailyNote() {
        int id = getParaToInt("classId");
        try {
            Record record = T_note.dao.findOneNoteByClassId(id);
            renderJson(Uitls.Ajax.success("查询成功", record));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }

    }

    /**
     * 首页学校动态列表
     * <p>
     * -- created By Samous
     */

/*    public void findDynamicList() {
        int id = getParaToInt("classId");
        int pageSize = getParaToInt("pageSize");
        int page = getParaToInt("page", 1);
        String context = getPara("context", "");

        try {
            Page<Record> list = T_note.dao.findNoteListByClassId(id, page, pageSize, context);
            renderJson(Uitls.Ajax.success("查询成功", list));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }

    }*/

    /**
     * 首页今日任务列表
     * -- created By Samous
     */
/*    @Before(GET.class)
    public void findDailyTask() {
        int id = 71;
        int page = 1;
        int pageSize = getParaToInt("pageSize");
        String context = getPara("context");
        try {
            Page<Record> list = T_note.dao.findNoteListByClassId(id, page, pageSize, context);
            renderJson(Uitls.Ajax.success("查询成功", list));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }

    }*/


//////////////////////////////////////            手机端首页展示（End）           //////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////      PC端 帖子基本操作 （start）         //////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 分页 根据分类 帖子列表 时间倒序
     * -- created By Samous
     */

    @Before(GET.class)
    public void findNoteListByClassId() {
        int page = getParaToInt("page", 1);
        int pageSize = getParaToInt("pageSize", 10);
        int id = getParaToInt("id", 1);//分类id()
        int departId=getParaToInt("departId",0);
        try {
            Page<Record> list = T_note.dao.findNotesByClassId(page, pageSize, id,departId);
            if (list != null) {
                renderJson(Uitls.Ajax.success("查询成功", list));
            } else {
                renderJson(Uitls.Ajax.failure("列表不存在", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("系统异常", ""));
        }


    }


    /**
     * 删除帖子
     * pyy
     */
    @Before(GET.class)
    public void deleteNoteById() {
        int id = getParaToInt("id");//帖子id;
        boolean b = T_note.dao.deleteNoteById(id);
        if (b) {
            int bb = L_classificationnote.dao.deleteClassifcationnote(id);
            renderJson(Uitls.Ajax.success("删除帖子成功", ""));

        } else {
            renderJson(Uitls.Ajax.failure("删除帖子失败", ""));
        }


    }

    /**
     * 修改帖子
     */
    @Before(POST.class)
    public void updateNote() {
        int id = getParaToInt("id");//帖子id;
        String title = getPara("title");//标题
        String content = getPara("content");//内容
        String imgurl = getPara("imgurl");//图片
        String startTime = getPara("startTime", "");//开始时间
        String endTime = getPara("endTime", "");//结束时间
        String type = getPara("type", "");
        int departid = getParaToInt("departId", 1);//发帖单位id
        boolean flage = T_note.dao.updateNote(id, title, content, imgurl, startTime, endTime, type,departid);
        if (flage) {

            renderJson(Uitls.Ajax.success("帖子修改成功", ""));

        } else {
            renderJson(Uitls.Ajax.failure("帖子修改失败", ""));
        }


    }


////////////////////////////////////      PC端 帖子基本操作 （end）           ///////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 查看帖子有关的所有分类
     * pyy
     */
    @Before(GET.class)
    public void findClassificationByNotes() {
        int id = getParaToInt("id");//帖子id()
        List<T_classification> list = T_note.dao.findClassificationByNotes(id);
        renderJson(Uitls.Ajax.success("查询成功", list));
    }


    /**
     * 查看分类下的帖子 -- 手机端
     * pyy
     */

    @Before(GET.class)
    public void findNotesByClassId() {
        int page = getParaToInt("page", 1);
        int pageSize = getParaToInt("pageSize", 5);
        int departId=getParaToInt("departId",0);
        int id = getParaToInt("id", 1);//分类id()
        Page<Record> list = T_note.dao.findNotesByClassId(page, pageSize, id,departId);
        Record r = T_classification.dao.findIntrduce(id);

        Map map = new HashMap();
        map.put("list", list);
        map.put("classification", r);
        renderJson(Uitls.Ajax.success("查询成功", map));
    }

    /**
     * 查看某个用户发的帖子 -- 微信端
     * KLP
     */

    @Before(GET.class)
    public void findNotesByUserId() {
        int page = getParaToInt("page", 1);
        int pageSize = getParaToInt("pageSize", 5);
        int juweihui =getParaToInt("juweihui_id");
        String juweihui_id = juweihui+"";
        int userId = getParaToInt("userId", 1);//分类id()
        Page<Record> list = T_note.dao.findNotesByUserId(page, pageSize, userId,juweihui_id);
        renderJson(Uitls.Ajax.success("查询成功", list));
    }

    /**
     * 查看帖子详情
     * pyy
     */
    @Before(GET.class)
    public void findOneById() {
        try {
            int id = getParaToInt("id");//帖子详情
            Record note = T_note.dao.findOneById(id);
            renderJson(Uitls.Ajax.success("查询成功", note));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }


    /**
     * 查看帖子的回复列表
     * pyy
     */
    @Before(GET.class)
    public void findReplyByNoteId() {
        int page = getParaToInt("page", 1);
        int pageSize = getParaToInt("pageSize", 10);

        int noteId = getParaToInt("noteId");//帖子id
        Page<Record> list = T_note.dao.findReplyByNoteId(page, pageSize, noteId);
        renderJson(Uitls.Ajax.success("查询成功", list));
    }


    /**
     * 友空间电脑端查询拆解步骤
     * KLP
     */
    @Before(GET.class)
    public void findReplyByNoteIdASC() {
        int noteId = getParaToInt("noteId");//帖子id
        List<T_reply> list = T_note.dao.findReplyByNoteIdASC(noteId);
        renderJson(Uitls.Ajax.success("查询成功", list));
    }


    /**
     * pyy
     * 查询老师发的帖子
     */
    @Before(GET.class)
    public void findNoteByUserId() {

        int userId = getParaToInt("userId", 1);
        List<T_note> list = T_note.dao.findNoteByUserId(userId);
        renderJson(Uitls.Ajax.success("查询成功", list));
    }

    /**
     * pyy
     * 通过查询条件查询帖子列表
     */

    @Before(GET.class)
    public void findNoteList() {
        int id = getParaToInt("classId");//类型id;
        String title = getPara("title", null);//标题
        String name = getPara("name", null);//发帖人姓名
        int departid = getParaToInt("departid", 0);//发帖单位
        int pageSize = getParaToInt("pageSize", 10);
        int page = getParaToInt("page", 1);
        try {
            Page<Record> list = T_note.dao.findNoteListBySearch(id, title, name,departid, page, pageSize);
            renderJson(Uitls.Ajax.success("查询成功", list));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }

    }

    /**
     * KLP
     * 通过查询条件查询投诉和建议帖子列表，用户只能看到自己的
     */

    @Before(GET.class)
    public void findNotes() {
        int userId = getParaToInt("userId", 1);
        int id = getParaToInt("classId");//类型id;
        String title = getPara("title");//标题
        String name = getPara("name");//发帖人姓名
        int pageSize = getParaToInt("pageSize");
        int page = getParaToInt("page", 1);
        try {
            Page<Record> list = T_note.dao.findNotes(id, title, userId, name, page, pageSize);
            renderJson(Uitls.Ajax.success("查询成功", list));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }

    }
    /**
     * 获取工作中心的数据 查询newMessage 字段 来判断为新增加没查看的or 新增加查看状态的
     * peng
     */
    public void  messageReminder(){
        int id=getParaToInt("id",327);
        int userId=getParaToInt("userId",7);
        List list=T_note.dao.messageReminder(id,userId);
        int messageId=0;
        if(list.size()==0){
            messageId=0;
            renderJson(Uitls.Ajax.success("没有新的消息",messageId));

        }else{
            messageId=1;
            renderJson(Uitls.Ajax.success("您有新消息,请查看",messageId));

        }

    }

    /**
     * json 解析 map、放进 list 获取开始结束时间 imp
     * peng
     */
    public void findNoteListJSON() {


        int id = getParaToInt("id", 86);//类型id;
        String title = getPara("title");//标题
        String name = getPara("name");//发帖人姓名
        int pageSize = getParaToInt("pageSize", 10);
        int page = getParaToInt("page", 1);
        int userId = getParaToInt("userId", 7);
        String time = getPara("time", "");//时间
        String state = getPara("state", "");//状态

        String brff = getPara("brff", "我负责");//负责和派生
        String startTime = "";
        String endTime = "";
        String worKStatus = "";
        String fuze = "1"; //我负责
        String daytime = getPara("daytime", "");//日历时间

        String weekStartTime = "";//当前周的开始时间 开始时间
        String weekEndTime = "";//当前周的结束时间 结束时间
        String EndWeekStartTime = "";//当前周的开始时间 截止时间
        String EndWeekEndTime = "";//当前周的结束时间 截止时间
        String status = "";
        String startTimee = "";//开始时间
        String endTimee = "";//结束时间
        String location = "";//会议地点


        String NoDate = "1";
        Page<Record> list = null;
        if (time.equals("全部")) {
            time = "";
        }
        if (state.equals("全部状态")) {
            worKStatus = "";
        }
        if (brff.equals("负责和派生")) {
            fuze = "";
        }
        if (time.equals("无日程")) {
            NoDate = "";
            daytime = "";
        }

        if (state.equals("待办")) {
            worKStatus = "2";


        }if(state.equals("未读")){
            worKStatus="1";
        }if(state.equals("完成")){
            worKStatus="4";
        }
        if(state.equals("待审")){
            worKStatus="3";
        }
        if(state.equals("指派")){
            worKStatus="5";
        }


        if (brff.equals("我派生")) {
            fuze = "3";
        }
        if (brff.equals("我负责")) {
            fuze = "1";
        }
        if (brff.equals("我开始")) {
            fuze = "2";
        }

        if (time.equals("今日待办")) {
            startTime = Uitls.currTimeDay();
        }
        if (time.equals("今日截止")) {
            endTime = Uitls.currTimeDay();
        }
        if (time.equals("开始")) {
            startTime = daytime;
        }
        if (time.equals("截止")) {
            endTime = daytime;
        }
        if (time.equals("本周待办")) {
            weekStartTime = Uitls.getBeginDayOfWeek();
            weekEndTime = Uitls.getEndDayOfWeek();

        }
        if (time.equals("本周截止")) {
            EndWeekStartTime = Uitls.getBeginDayOfWeek();
            EndWeekEndTime = Uitls.getEndDayOfWeek();
        }


        list = T_note.dao.findNoteListWorkSelect(id, userId, page,
                pageSize, startTime, endTime, worKStatus, fuze, weekStartTime,

                weekEndTime, EndWeekStartTime, EndWeekEndTime, NoDate, daytime);

        /*     list = T_note.dao.findNoteListWork(id,userId, page, pageSize);*/
        List<Map<String, Object>> listt = new ArrayList<Map<String, Object>>();

        if (list.getList().size() == 0) {
            renderJson(Uitls.Ajax.success("查询成功", ""));
        }


        for (int i = 0; i < list.getList().size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String expands = list.getList().get(i).get("expand");
            if (expands.equals("") || expands == null) {
                int classid = list.getList().get(i).get("classid");
                String classname = list.getList().get(i).get("classname");
                String context = list.getList().get(i).get("context");
                String creater = list.getList().get(i).get("creater");
                String createtime = list.getList().get(i).get("createtime");
                String titlee = list.getList().get(i).get("title");
                String nick_name = list.getList().get(i).get("nick_name");
                int idd = list.getList().get(i).get("id");
                map.put("classid", classid);
                map.put("classname", classname);
                map.put("context", context);
                map.put("creater", creater);
                map.put("createtime", createtime);
                map.put("id", idd);
                map.put("title", titlee);
                map.put("nick_name", nick_name);
                map.put("list", list);
                listt.add(map);
            } else {
                expands = "[" + expands + "]";
                JSONArray jsa = JSON.parseArray(expands);
                for (int j = 0; j < jsa.size(); j++) {
                    JSONObject js = jsa.getJSONObject(j);


                    boolean findEndTime = js.containsKey("endTime");
                    boolean findStartTime = js.containsKey("startTime");
                    boolean statusBoal = js.containsKey("status");
                    boolean locationflage = js.containsKey("location");
                    status = "";
                    if (findEndTime) {
                        endTimee = js.get("endTime").toString();
                    }
                    if (findStartTime) {
                        startTimee = js.get("startTime").toString();
                    }
                    if (statusBoal) {
                        status = js.get("status").toString();
                    }
                    if (locationflage) {
                        location = js.get("location").toString();
                    }
                    map.put("location", location);
                    map.put("status", status);
                    map.put("startTime", startTimee);
                    map.put("endTime", endTimee);

                }
                int classid = list.getList().get(i).get("classid");
                String classname = list.getList().get(i).get("classname");
                String context = list.getList().get(i).get("context");
                String creater = list.getList().get(i).get("creater");
                String createtime = list.getList().get(i).get("createtime");
                String titlee = list.getList().get(i).get("title");
                String nick_name = list.getList().get(i).get("nick_name");
                int idd = list.getList().get(i).get("id");
                map.put("classid", classid);
                map.put("classname", classname);
                map.put("context", context);
                map.put("creater", creater);
                map.put("createtime", createtime);
                map.put("id", idd);
                map.put("title", titlee);
                map.put("nick_name", nick_name);
                map.put("list", list);
                listt.add(map);
            }
        }
        renderJson(Uitls.Ajax.success("查询成功", listt));


    }

    public void findNoteListJSONPC() {


        int id = getParaToInt("id", 86);//类型id;
        String title = getPara("title");//标题
        String name = getPara("name");//发帖人姓名
        int pageSize = getParaToInt("pageSize", 10);
        int page = getParaToInt("page", 1);
        int userId = getParaToInt("userId", 7);
        String time = getPara("time", "");//时间
        String state = getPara("state", "");//状态

        String brff = getPara("brff", "我负责");//负责和派生
        String startTime = "";
        String endTime = "";
        String worKStatus = "";
        String fuze = "1"; //我负责
        String daytime = getPara("daytime", "");//日历时间

        String weekStartTime = "";//当前周的开始时间 开始时间
        String weekEndTime = "";//当前周的结束时间 结束时间
        String EndWeekStartTime = "";//当前周的开始时间 截止时间
        String EndWeekEndTime = "";//当前周的结束时间 截止时间
        String status = "";
        String startTimee = "";//开始时间
        String endTimee = "";//结束时间
        String location = "";//会议地点


        String NoDate = "1";
        Page<Record> list = null;
        if (time.equals("全部")) {
            time = "";
        }
        if (state.equals("全部状态")) {
            worKStatus = "";
        }
        if (brff.equals("负责和派生")) {
            fuze = "";
        }
        if (time.equals("无日程")) {
            NoDate = "";
            daytime = "";
        }

        if (state.equals("待办")) {
            worKStatus = "2";


        }if(state.equals("未读")){
            worKStatus="1";
        }if(state.equals("完成")){
            worKStatus="4";
        }
        if(state.equals("待审")){
            worKStatus="3";
        }
        if(state.equals("指派")){
            worKStatus="5";
        }


        if (brff.equals("我派生")) {
            fuze = "3";
        }
        if (brff.equals("我负责")) {
            fuze = "1";
        }
        if (brff.equals("我开始")) {
            fuze = "2";
        }

        if (time.equals("今日待办")) {
            startTime = Uitls.currTimeDay();
        }
        if (time.equals("今日截止")) {
            endTime = Uitls.currTimeDay();
        }
        if (time.equals("开始")) {
            startTime = daytime;
        }
        if (time.equals("截止")) {
            endTime = daytime;
        }
        if (time.equals("本周待办")) {
            weekStartTime = Uitls.getBeginDayOfWeek();
            weekEndTime = Uitls.getEndDayOfWeek();

        }
        if (time.equals("本周截止")) {
            EndWeekStartTime = Uitls.getBeginDayOfWeek();
            EndWeekEndTime = Uitls.getEndDayOfWeek();
        }


        list = T_note.dao.findNoteListWorkSelect(id, userId, page,
                pageSize, startTime, endTime, worKStatus, fuze, weekStartTime,

                weekEndTime, EndWeekStartTime, EndWeekEndTime, NoDate, daytime);


        renderJson(Uitls.Ajax.success("查询成功", list));

    }

    /**
     * 添加今日任务
     * pyy
     */
    @Before(POST.class)
    public void addNote() {
        int departid = getParaToInt("departId", 1);//发帖单位id
        int userid = getParaToInt("userId", 1);//发帖人id;
        String title = getPara("title");//标题
        String content = getPara("content");//内容
        String imgurl = getPara("imgurl");//图片地址
        int type = getParaToInt("type");//今日任务分类
        String startTime = getPara("startTime", "");//开始时间
        String endTime = getPara("endTime", "");

        int noteId = T_note.dao.addNote(userid, title, content, imgurl, startTime, endTime,departid);

        if (noteId != 0) {
            boolean b = L_classificationnote.dao.addClassifcationnote(noteId, type, userid);
            if (b) {
                renderJson(Uitls.Ajax.success("发布成功", ""));
            } else {
                renderJson(Uitls.Ajax.failure("发布失败，请稍后再试", ""));
            }
        } else {
            renderJson(Uitls.Ajax.failure("系统出现异常", ""));
        }


    }


    /**
     * 获取单人聊天的信息 or 多人聊天
     * created by  peng
     */
    public void findMessage() {
        try {
            int classId = getParaToInt("classId"); //分类的id
            int page = getParaToInt("page", 1);
            int pageSize = getParaToInt("pageSize", 5);
            Page<Record> pageList = T_note.dao.findMessage(classId, page, pageSize);
            String className = T_classification.dao.findClassName(classId);
            Map map = new HashMap();
            map.put("list", pageList);
            map.put("className", className);
            renderJson(Uitls.Ajax.success("查询成功", map));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }

    /**
     * 通过获取个人所发的学校动态
     * created by peng
     */
    public void findDynamicsbyUserId() {
        try {
            int userId = getParaToInt("userId", 1);
            int page = getParaToInt("page", 1);
            int pageSize = getParaToInt("pageSize", 4);
            Page<Record> pageList = T_note.dao.findDynamicsbyUserId(userId, page, pageSize);
            renderJson(Uitls.Ajax.success("查询成功", pageList));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }

    /**
     * 查询学校动态
     * creater by peng
     */
    public void selectDynamics() {

        String personName = getPara("personName");
        String context = getPara("context");
        int page = getParaToInt("page", 1);
        int pageSize = getParaToInt("pageSize", 4);
        Page<Record> pageList = null;
        try {
            pageList = T_note.dao.selectDynamics(page, pageSize, personName, context);

            renderJson(Uitls.Ajax.success("查询成功", pageList));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }

    /**
     * 查询政策宣传 zhi 工作宣传
     * creater by peng
     */
    public void findPoacy() {
        try {
            int classId = getParaToInt("classId", 1);
            int departid = getParaToInt("departid", 0);//发帖单位
            int page = getParaToInt("page", 1);
            int pageSize = getParaToInt("pageSize", 10);
            String disish = getPara("disish","");
            String context = getPara("context", "");
            Page<Record> pageList = null;
            if (disish.equals("") || disish == null) {
                //工作宣传
                pageList = T_note.dao.findWorkPublicity(classId, page, pageSize, context,departid);
            } else {
                //政策引导
                pageList = T_note.dao.findGuidance(classId, page, pageSize, context,departid);
            }
            renderJson(Uitls.Ajax.success("查询成功", pageList));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }

    /**
     * 查询工作宣传与政策引导的总喝
     * peng
     */
    public void findJobPol() {
        try {
            int id = getParaToInt("classId");
            int pageSize = getParaToInt("pageSize");
            int page = getParaToInt("page", 1);
            String context = getPara("context", "");
            int departid = getParaToInt("departid",0);

            Page<Record> list = T_note.dao.findNoteListByClassId(id, page, pageSize, context,departid);
            renderJson(Uitls.Ajax.success("查询成功", list));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }

    /**
     * 查询工作宣传 与我的工作宣传
     * creater by peng
     */
    public void JobPublicity() {
        try {
            String userId = getPara("userId");
            int pageSize = getParaToInt("pageSize", 4);
            int page = getParaToInt("page", 1);
            int classId = getParaToInt("classId");
            Page<Record> pageList = null;
            if (userId.equals("") || userId == null) {
                pageList = T_note.dao.findJobPublicity(page, pageSize, classId);
            } else {
                int userIdd = Integer.parseInt(userId);
                pageList = T_note.dao.findJobPublicityMe(userIdd, page, pageSize, classId);
            }
            renderJson(Uitls.Ajax.success("查询成功", pageList));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }

    /**
     * 查询政策引导
     * creater by peng
     */
    public void PolicyGuidance() {
        try {
            String userId = getPara("userId");
            int pageSize = getParaToInt("pageSize", 4);
            int page = getParaToInt("page", 1);
            int classId = getParaToInt("classId");
            Page<Record> pageList = null;
            if (userId.equals("") || userId == null) {
                pageList = T_note.dao.findPolicyGuidance(page, pageSize, classId);
            } else {
                int userIdd = Integer.parseInt(userId);
                pageList = T_note.dao.findPolicyGuidanceMe(userIdd, page, pageSize, classId);
            }
            renderJson(Uitls.Ajax.success("查询成功", pageList));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }
    }

    /**
     * 学校动态的添加
     * created  by  PENG
     */
    @Before(POST.class)
    public void addDynamicsByUserId() {
        try {
            int departid = getParaToInt("departId", 1);//发帖单位id
            int userId = getParaToInt("userId", 1);
            String context = getPara("context");
            int classid = getParaToInt("classId", 76);
            String title = getPara("title", "");
            String mode = getPara("mode", "");
            String imgurl = getPara("imgurl", "");
            boolean flage = T_note.dao.findSomeName(classid, title);
            if (flage) {
                int id = T_note.dao.addDynamicsByUserId(userId, context, mode, title, imgurl,departid);
                boolean flag = L_classificationnote.dao.addClassifcationnote(id, classid, userId);
                if (flag) {
                    renderJson(Uitls.Ajax.success("保存成功", ""));
                } else {
                    renderJson(Uitls.Ajax.failure("保存失败", ""));
                }

            } else {
                renderJson(Uitls.Ajax.failure("标题名称不能重复，请重新输入！！", ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("保存失败", ""));
        }

    }

    /**
     * 查询我所发布的任务
     * created by peng
     */
    @Before(GET.class)
    public void findNoteListByUser() {
        try {
            int id = getParaToInt("classId");//类型id;
            int userId = getParaToInt("userId", 1);
            int pageSize = getParaToInt("pageSize");
            int page = getParaToInt("page", 1);

            Page<Record> list = T_note.dao.findNoteListByUser(id, page, pageSize, userId);
            renderJson(Uitls.Ajax.success("查询成功", list));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }

    }

    /**
     * 增加回复
     * creater by peng
     */
    public void addReply() {
        try {
            int noteId = getParaToInt("noteId");
            int userId = getParaToInt("userId", 1);
            String context = getPara("context");
            boolean flag = T_reply.dao.addReply(noteId, userId, context);
            if (flag) {
                renderJson(Uitls.Ajax.success("回复成功", ""));
            } else {
                renderJson(Uitls.Ajax.failure("回复失败", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("回复失败", ""));
        }
    }

    /**
     * 获取回复列表
     */
    public void findReply() {
        try {
            int page = getParaToInt("page", 1);
            int pageSize = getParaToInt("pageSize", 5);
            int noteId = getParaToInt("noteId");
            Page<Record> pageList = T_reply.dao.findReply(page, pageSize, noteId);
            renderJson(Uitls.Ajax.success("获取成功", pageList));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("获取失败", ""));
        }

    }
    /**
     * 获取一条回复详情
     * KLP
     */

   /*     public void findReplyByReplyId() {
        try {

            int replyId = getParaToInt("replyId");
            T_reply reply = T_reply.dao.findReplyByReplyId(replyId);

            renderJson(Uitls.Ajax.success("获取成功", reply));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("获取失败", ""));
        }

    }*/

    public void findReplyByReplyId() {
        try {

            int replyId = getParaToInt("replyId");
            T_reply reply = T_reply.dao.findReplyByReplyId(replyId);
            String expand = reply.get("expand");
            JSONObject js=JSON.parseObject(expand);

            Map<String,Object> map = new HashMap<String ,Object>();
            ArrayList<String> al=new ArrayList<String>();
            String peopleName = "";
            if(js.containsKey("people")){
                String people= js.get("people").toString();
                String[] peopleCup=people.split(",");

                for(int j=0;j<peopleCup.length;j++){


                    int  peopleId=Integer.parseInt(peopleCup[j]) ;
                    peopleName = L_user.dao.peopleName(peopleId);
                    al.add(peopleName);

                }

            }
            map.put("peopleName",al);
            map.put("reply",reply);

            renderJson(Uitls.Ajax.success("获取成功", map));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("获取失败", ""));
        }

    }

    public void findReplyDetails(){
        try {
            int page = getParaToInt("page", 1);
            int pageSize = getParaToInt("pageSize", 5);
            int noteId = getParaToInt("noteId");
            String peopleName="";
            String  relpy_context="";
            Page<Record> pageList = T_reply.dao.findReply(page, pageSize, noteId);

            List list=new ArrayList();

            for(int i=0;i<pageList.getList().size();i++){
                Map<String,Object> map=new HashMap<String,Object>();
                ArrayList<String> al=new ArrayList<String>();
                String expand=pageList.getList().get(i).get("expand");
                if(expand==null){
                    relpy_context=pageList.getList().get(i).get("relpy_context");
                    map.put("context",relpy_context);
                    list.add(map);
                }else{
                 JSONObject js=JSON.parseObject(expand);

                    if(js.containsKey("people")){
                        String people= js.get("people").toString();
                      String[] peopleCup=people.split(",");

                        for(int j=0;j<peopleCup.length;j++){
                           int  peopleId=Integer.parseInt(peopleCup[j]) ;
                             peopleName=L_user.dao.peopleName(peopleId);
                            al.add(peopleName);
                        }

                    }
                    relpy_context=pageList.getList().get(i).get("reply_context");

                }
                map.put("peopleName",al);
                map.put("context",relpy_context);
                list.add(map);
            }

            renderJson(Uitls.Ajax.success("获取成功", list));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("获取失败", ""));
        }
    }



    /**
     * 专门用来传classid
     * KLP
     */
    public void sendClassid() {
        int classid = getParaToInt("classid");
        String name = T_classification.dao.findClassificationName(classid);
        Map map = new HashMap();
        map.put("classid", classid);
        map.put("classname", name);
        renderJson(Uitls.Ajax.success("查询成功", map));
    }

    /**
     * @工作中心
     *
     * @添加会议
     *      -- created by Samous
     */
    @Before(POST.class)
    public void addMeetingNote() {
        int userid = getParaToInt("userId", 1);
        String title = getPara("title");
        int type = getParaToInt("type");
        String startTime = getPara("startTime", "");
        String location = getPara("location", "");
        Integer[] people = getParaValuesToInt("ids[]");
        String content = getPara("content", "");
        int pid=getParaToInt("noteId",0);//父类
        int noteId = T_note.dao.addMeetingNote(userid, title, startTime, location, people, content,pid);

        if (noteId != 0) {
            boolean b = L_classificationnote.dao.addClassifcationnote(noteId, type, userid);
            if (b) {
                renderJson(Uitls.Ajax.success("发布成功", ""));
            } else {
                renderJson(Uitls.Ajax.failure("发布失败", ""));
            }
        } else {
            renderJson(Uitls.Ajax.failure("系统出现异常", ""));
        }

    }



    /**
     *  @事务接口
     *
     *  @所属模块：工作中心
     *
     *  添加拆解工作
     *      -- created by Samous
     */
    @Before({POST.class, Tx.class})
    public void addStepNote() {

        //拆解步骤标题
        String title = getPara("title");
        //所在任务id
        Integer note = getParaToInt("noteId");
        //指派内容
        String content = getPara("content", "");
        //指派人
        Integer[] ids = getParaValuesToInt("ids[]");
        //创建人
        int userid = getParaToInt("userId", 1);
        //所属工作分类
        int type = getParaToInt("type");
        //回复表添加该数据
        boolean flag = T_reply.dao.addAssignedReply(note, userid, content, ids);
        //帖子表添加该数据
        int noteId = T_note.dao.addStepNote(userid, title, ids, content);
        if (noteId != 0 && flag) {
            boolean b = L_classificationnote.dao.addClassifcationnote(noteId, type, userid);
                if (b) {
                    renderJson(Uitls.Ajax.success("指派成功", ""));
                } else {
                    renderJson(Uitls.Ajax.failure("指派成功", ""));
                }
        } else {
            renderJson(Uitls.Ajax.failure("系统出现异常", ""));
        }

    }


    UpesnTools upesnTools = new UpesnTools();
/*    public void shiyan(){

        //获得友空间code
        try{
        String code="";
                String appid="3699a867c7a739a1";
                String secret="d3de938225da21600429b2db4055d789133aef322071be613007828224e5";
                JSONObject jsonObject = JSON.parseObject(upesnTools.addNotice(appid,secret));
                JSONObject data = jsonObject.getJSONObject("data");
                renderJson(Uitls.Ajax.success("成功",data));}
                catch (Exception e){
                    e.printStackTrace();
            renderJson(Uitls.Ajax.failure("失败",""));
        }
    }*/

    public void sendMessageConfig(){

        //获得友空间code
        try{
            String classification=getPara("classification");
            String thirdUrl="http://schoolcms.allview.xin/schoolcms/upesnapp/mainLogin";
            if(classification.equals("我的日程")){
                thirdUrl="http://schoolcms.allview.xin/schoolcms/upesnapp/everydayWork";
            }
            int userId=getParaToInt("userId",1);
            T_upesnUser usenUser= T_upesnUser.dao.findFirst("select * from t_upesnuser where userid = ?",userId);
            String usenUserId =usenUser.get("upesnid");
            String title=getPara("title");
            String content=getPara("content");
            Integer[] people = getParaValuesToInt("ids[]");
            ArrayList<String> al=new ArrayList<String>();
            for(int i=0;i<people.length;i++){
                 T_upesnUser usenid= T_upesnUser.dao.findFirst("select * from t_upesnuser where userid = ?",people[i]);
                if(usenid==null){

                }else{
                 String usenidd =usenid.get("upesnid");

                 al.add(usenidd);
                }
            }
            String [] a=al.toArray(new String[al.size()]);

            StringBuffer sb = new StringBuffer();
            for (int j=0;j< a.length;j++){
                sb.append(a[j]+",");

            }
            if (sb.length() > 0)
                sb.deleteCharAt(sb.length() - 1); //调用 字符串的deleteCharAt() 方法,删除最后一个多余的逗号


            String bs=sb.toString();

            String appid="db90d75eec4c55a9";
            String secret="41a3dcf7701c9552bee0124591c52079f01a952c63cc3b8ed783ced8eaf7";
            String callValue = upesnTools.addNotice(appid,secret,bs,usenUserId,content,title,thirdUrl);
            /*String baby=upesnTools.thirdLogin(appid,secret);*/

            renderJson(Uitls.Ajax.success("成功",callValue));}
        catch (Exception e){
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("失败",""));
        }
    }

    /**
     * 所属模块：工作中心
     *
     * 根据noteid查询带expand字段内容的note详情
     *
     */
    @Before(GET.class)
    public void findNoteExpandById(){
        int id = getParaToInt("id");//帖子详情

        String status = "";
        String startTime = "";//开始时间
        String endTime = "";//结束时间
        String location = "";//会议地点
        String ids = "";//指派人

        Map<String, Object> map = new HashMap<String ,Object>();
        try {
            Record note = T_note.dao.findOneById(id);
            JSONObject js = JSON.parseObject(note.getStr("expand"));

            if (js.containsKey("endTime")) {
                endTime = js.get("endTime").toString();
                map.put("endTime", endTime);
            }
            if (js.containsKey("startTime")) {
                startTime = js.get("startTime").toString();
                map.put("startTime", startTime);
            }
            if (js.containsKey("status")) {
                status = js.get("status").toString();
                map.put("status", status);
            }
            if (js.containsKey("location")) {
                location = js.get("location").toString();
                map.put("location", location);
            }
            if(js.containsKey("people")){
                ids = js.get("people").toString();
                map.put("people", ids);
            }
/*            if(js.containsKey("people")){
                ids = js.get("people").toString();
                String[] users = ids.split(",");
                List list = new ArrayList();
                for(String s : users){
                    Map<String,String> hm = new HashMap<>();
                    if(!s.equals("0")){
                        L_user u = L_user.dao.findById(s);
                        hm.put("name",u.getStr("nick_name"));
                        hm.put("id",s);
                        list.add(hm);

                    }

                }

                map.put("people", list);
            }
            */

            String title = note.getStr("title");
            map.put("id",note.getInt("id"));
            map.put("classid",note.getStr("classid"));
            map.put("title", title);
            map.put("context",note.getStr("context"));
            map.put("creater",note.getStr("creater"));
            map.put("createtime",note.getStr("createtime"));

            renderJson(Uitls.Ajax.success("查询成功", map));

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("查询失败", ""));
        }

    }

    /**
     * @所属模块: 工作中心
     *
     * 更新任务状态
     *      -- created by Samous
     *
     */
    @Before(POST.class)
    public void updateStatus(){
        Integer id = getParaToInt("id");
        Integer status = getParaToInt("status");

        try {
            Integer upStatus = T_note.dao.updateStatus(id, status);
            if(upStatus == 1){
                renderJson(Uitls.Ajax.success("完成任务！", ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("系统异常！", ""));
        }

    }

    /**
     * 两人的谈话
     */
    public void concernMessage(){
        try{
            int page=getParaToInt("page",1);
            int pageSize=getParaToInt("pageSize",10);
            int userId=getParaToInt("userId");
            int yourId=getParaToInt("yourId");
            int classId=TB_message_people.dao.findClassId(userId,yourId);

            Page<Record> pageList=T_note.dao.concernMessage(page,pageSize,classId);
            renderJson(Uitls.Ajax.success("查询成功",pageList));


        }catch (Exception e){
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("系统出现异常,请联系人管理人员",""));
        }
    }

    /**
     * 穿uuid
     */
    public void concernMessageYou(){
        try{
            int page=getParaToInt("page",1);
            int pageSize=getParaToInt("pageSize",10);
            int userId=getParaToInt("userId");
            int yourId=getParaToInt("yourId");
            int classId=TB_message_people.dao.findClassId(userId,yourId);
            renderJson(Uitls.Ajax.success("查询成功",classId));
        }catch (Exception e){
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("系统出现异常,请联系人管理人员",""));
        }
    }
    public void concernMessageGLZ(){
          try{
           int uuId=getParaToInt("uuId");
              int page=getParaToInt("page",1);
              int pageSize=getParaToInt("pageSize",10);
              Page<Record> pageList=TB_message.dao.concernMessageGLZ(page,pageSize,uuId);
              renderJson(Uitls.Ajax.success("查询成功",pageList));
          }catch (Exception e){
              e.printStackTrace();
              renderJson(Uitls.Ajax.failure("系统出现异常,请联系管理人员",""));
          }
    }


    /**
     * 添加会话
     */
    public void  addMessage(){
        try{
            int uuId=getParaToInt("uuId");
            String content=getPara("content");
            int userId=getParaToInt("userId");
            String time=Uitls.currTime();
            boolean flage=TB_message.dao.addMessage(uuId,content,time,userId);
            if(flage){
                TB_message_people.dao.update(uuId,time);
                renderJson(Uitls.Ajax.success("发送成功",""));
            }else{
                renderJson(Uitls.Ajax.failure("保存出现异常,请联系管理人员",""));
            }
        }catch (Exception e){
            e.printStackTrace();
            renderJson(Uitls.Ajax.failure("系统出现异常,请联系管理人员",""));
        }
    }


}


