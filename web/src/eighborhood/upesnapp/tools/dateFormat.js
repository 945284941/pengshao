$f("df", __, function (timeParam) {
    if (timeParam === null || timeParam === "" || timeParam === undefined) {
        return;
    }
    var timeNow = parseInt(new Date().getTime() / 1000);
    var publishTime;
    var d;
    var time;
    publishTime = parseInt(new Date(timeParam).getTime() / 1000);
    d = timeNow - publishTime;

    var d_year = parseInt(d / (60 * 60 * 24 * 30 * 12));
    var d_month = parseInt(d / (60 * 60 * 24 * 30));
    var d_days = parseInt(d / (60 * 60 * 24));
    var d_hours = parseInt(d / (60 * 60));
    var d_minutes = parseInt(d / 60);

    if (d < 60) {
        time = "刚刚发布";
    } else if (d_minutes < 60 && d_minutes > 0) {
        time = d_minutes + "分钟前";
    } else if (d_hours <= 24 && d_hours > 0) {
        time = d_hours + "小时前";
    } else if (d_days > 0 && d_days < 30) {
        time = d_days + "天前";
    } else if (d_month > 0 && d_month < 13) {
        if (d_month > 5 && d_month < 7) {
            time = "半年前";
        } else {
            time = d_month + "月前";
        }
    } else if (d_year >= 1) {
        time = d_year + "年前";
    }
    return time;

});
