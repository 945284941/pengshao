$f("df",__,function(_scope,_page,_data){
    console.log(_page);
    //$("#page-friend").children()[1].innerHTML=_page;
    //$.router.load("#page-friend");
    $("#home-info-content").html(_page);
    AppFramework.hideIndicator();
    var calendarDefault = AppFramework.calendar({
        input: '#calendar-default',
        dateFormat: 'DD, MM dd, yyyy'
    });
    homeView.router.load({pageName:"home-info"});
});
