$f("df",__,function (_scope,_page,_data) {
    var typeIdOne=$('#impId').val();
    console.log("impId"+typeIdOne)
    var content = $f("TPPL",_page,{"noteId":typeIdOne});
    $("#chats-content").html(content);
    chatView.router.load({pageName:'chats'})


    $('#baby').click(function () {
        var noteId=$('#xixi').val();

        var context=$('#contact').val();
        console.log("增加+"+noteId,context);



        $.ajax({
            url:$f("serverURL","archive/note/Reply"),
            type:"GET",
            data:{
                "noteId":noteId,
                "context":context,

            },
            success:function(resp){
                alert(resp.message);
                //刷新页面
                console.log("穿"+noteId);

                $f('list','contact.note',noteId);
            }
        });

    })

})
