$f("df",__,function(_scope,_page,_data) {
var a;
    var c=$('#qq').val();
    var b=$('#xixi').val();
    if(c==null){
         a=b;
    }else {
        a=c;
    }
    console.log("a是》"+a+"oo"+_data.data.id );
    var content = $f("TPPL",_page,{"data":_data.data,"a":a});

    $("#chats-info-content").html(content);

    chatView.router.load({pageName:'chats-info'});



    $f("render","contact.note.reply",a)
});
