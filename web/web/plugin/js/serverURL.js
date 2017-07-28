
//var host1 = 'http://192.168.1.98:8080/sms';
/*var host1 = 'http://127.0.0.1:8080/schoolcms';*/
//host1 = 'http://192.168.54.110:8080/SchoolMS';

//var host1='http://127.0.0.1:8080/schoolcms';

var host1 = 'http://172.16.248.40:8000/schoolcms';

//var host1 = 'http://172.16.248.40:8000/schoolcms';



window.serverURL = {
    host1 :host1,
    host:"http://127.0.0.1/schoolcms",

    //帖子、回复
    noteAdd:host1+"/archive/note/add",
    noteDelete:host1+"/archive/note/delete",
    noteUpdate:host1+"/archive/note/update",
    replyUpdate:host1+"/archive/note/updateMessage" ,
    noteQuery:host1+"/archive/note/queryById",
    noteAddReply:host1+"/archive/note/Reply",
    noteList:host1+"/archive/note/getNoteList",
    replyDelete:host1+"/archive/note/deleteMessage",
    noteReplyList:host1+"/archive/note/getReplyList",
    noteOne:host1+"/archive/note/findOneNote",
    findnoteOne:host1+"/archive/note/findNoteOne",

    userList:host1+"/archive/upesn/getUserList",
    userListAdd:host1+"/archive/upesn/add",
    userListDelete:host1+"/archive/upesn/delete",
    userFindOne:host1+"/archive/upesn/findUserOne",
    userUpdate:host1+"/archive/upesn/update",
    //分类
    classList:host1+"/archive/classification/getList",


    classAdd: host1+"/archive/classification/add",
    classUpdate: host1+"/archive/classification/update",
    classDelete: host1+"/archive/classification/delete",
    classfuList:host1+"/archive/classification/findFlClass",
    classsmallList:host1+"/archive/classification/findSmallClass",

    //家长
    paretList:host1+"/archive/parent/getNoteList",
    deleteParetList:host1+"/archive/parent/delete",
    updateParetList:host1+"/archive/parent/update",
    finddateParetOne:host1+"/archive/parent/findParentOne",



    //家长
    findAllwxList:host1+"/archive/upsenWeb/findAllList",


    weiXinUser:host1+"/archive/weixinUser/findWeiXinUserAll",
    changStatusSuccess:host1+"/archive/weixinUser/changStatusSuccess",
    changStatusFailure:host1+"/archive/weixinUser/changStatusFailure",
    changStatus:host1+"/archive/weixinUser/changStatus",




    //友空间老师列表
    findUpesnUser:host1+"/archive/upesnUser/findUpesnUser",

    //友空间添加角色
    addRole:host1+"/archive/upesnUser/addRole",
    //友空间查询角色列表
    findRole:host1+"/archive/upesnUser/findRole",
    //友空间绑定角色
    updateRole:host1+"/archive/upesnUser/updateRole",


    changMessage:host1+"/archive/upsenWeb/changMessgae",
    changNoMessage:host1+"/archive/upsenWeb/changNoMessage",
    changOrMessage:host1+"/archive/upsenWeb/goBackMessage",
    //商店
    functionShopList:host1+"/archive/functionShop/findFunctionList",
    functionShopDatil:host1+"/archive/functionShop/findOneById",
    functionShopseach:host1+"/archive/functionShop/findTypeList",


    //今日任务
    todayTaskList:host1+"/archive/note/findNoteList",//今日任务列表
    addNoteTake:host1+"/archive/note/addNote",//添加今日任务
    deleteNoteById:host1+"/archive/note/deleteNoteById",//删除今日任务
    findOneById:host1+"/archive/note/findOneById",//查看详情
    updataNote:host1+"/archive/note/updateNote",//修改

    sendClassid:host1+"/archive/note/sendClassid",//专门传classid的值的方法

    //友空间新增模板相同的分类模板
    addClassification:host1+"/archive/classification/addClassification",//友空间新增分类

    //友空间查询模板相同的分类列表
    findClassification:host1+"/archive/classification/findClassification",
    //分类根据父类查询子类
    findClassificationByParent:host1+"/archive/classification/findClassificationByParent",




};
