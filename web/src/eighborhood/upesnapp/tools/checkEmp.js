$f("df",__,function(str){
    var result = "";
    // if(str instanceof Array){
    //     if(str.length == 0){
    //         result = true;
    //     }else {
    //         result = false;
    //     }
    // }


        if(str == undefined || str == null || str == ""){
            result = true;
        }else{
            result = false;
        }


    return result;
});
