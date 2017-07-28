$f("df",__,function(timeParam){
  var timeNow = parseInt(new Date().getTime() / 1000);
  var publishTime;
  var d;
  var time;
  $.each(timeParam, function (i, n){
     publishTime = parseInt(new Date(n.reply_time).getTime() / 1000);
     d = timeNow - publishTime;
     d_days = parseInt(d / (60 * 60 * 24));
     d_hours = parseInt(d / (60 * 60));
     d_minutes = parseInt(d / 60);
     if(d_days>0 && d_days<30){
       time = d_days + "天前";
     }else if(d_hours<=24 && d_hours > 0){
       time = d_hours + "小时前";
     }else if(d_minutes < 60 && d_minutes>0){
       time = d_minutes + "分钟前";
     }
     n.reply_time = time;
  });
});
