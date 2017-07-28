$f("df",__,function(_data){
    console.log("wode>>"+_data);

    return {"url":$f("serverURL","archive/note/findNoteXWOne"),

        "data":{"id":_data}};
 });
