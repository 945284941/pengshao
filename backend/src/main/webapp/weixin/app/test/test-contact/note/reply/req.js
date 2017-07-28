$f("df",__,function(noteId){
    console.log("初始"+noteId);
    return {
        url:$f("serverURL","archive/note/getReplyList"),
        data:{
            "noteId":noteId
        }
    };
});