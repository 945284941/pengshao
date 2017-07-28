$f("df",__,function(_url){
    var IP="localhost:8080/backend/";
    var server="";
    if(this.ISTEST){
        return server+_url;
    }else{
        //return "/proxy/"+_url;
        return "http://106.14.93.174/eighborhood/"+_url;
    }
});
