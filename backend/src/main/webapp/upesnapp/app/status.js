$f("df","status",function(_data){
  if(_data!=undefined && _data!=null && _data != "0"){
    return _data;
  }else{
    //判断客户端类型
    alert("登陆过期");
    return {};
  }
});
