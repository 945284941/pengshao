$f("df",__,function(_scope,_page,_data){
  console.log(_page);
  var co = $f("TPPL",_page,_data);
  $('#chats-content').html(co);
  AppFramework.hideIndicator();
  chatView.router.load({pageName:"chats"});
});
