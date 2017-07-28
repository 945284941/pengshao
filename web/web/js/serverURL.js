
//var host1 = 'http://192.168.1.98:8080/sms';
var host1 = 'http://127.0.0.1:8080/schoolcms';
// host1 = 'http://192.168.54.110:8080/SchoolMS';

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


    userList:host1+"/archive/upesn/getUserList",
    userListAdd:host1+"/archive/upesn/add",
    userListDelete:host1+"/archive/upesn/delete",
    userFindOne:host1+"/archive/upesn/findUserOne",
    userUpdate:host1+"/archive/upesn/update",
    //分类
    classList:host1+"/archive/classification/getList",


    classAdd: host1+"/archive/classification/add",
    classUpdate: host1+"/archive/classification/update",
    classDelete: host1+"/archive/classification/delete"

};
