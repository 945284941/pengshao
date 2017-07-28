$f("df",__,function(_scope,_page,_data){
/*    $f("tools.replyDateFormat",_data.data.list);*/

    var html=$f("TPPL",_page,{id:_scope, data:_data.data});
    $("#note-reply-list").html(html);
    chatView.router.load({pageName:'chats-info'})
});