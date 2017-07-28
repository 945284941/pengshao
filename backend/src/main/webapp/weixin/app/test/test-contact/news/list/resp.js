$f("df",__,function(_scope,_page,_data) {
    console.log(_page);
   var content = $f("TPPL",_page,_data);
   $("#chats-info-content").html(content);
    chatView.router.load({pageName:'chats-info'});
 });
