$f("df",__,function(_scope,_page,_data){


  var co = $f("TPPL",_page,_data);
  $("#chats-info-content").html(co);

  chatView.router.load({pageName:'chats-info'});
});
