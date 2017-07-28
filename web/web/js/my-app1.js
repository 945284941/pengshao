/*
// Initialize your app
AppFramework = new Framework7();
// Export selectors engine
var $$ = Dom7;
*/

/*
$(function () {
    homeView = AppFramework.addView(".view-home",{domCache:true});
    personView = AppFramework.addView(".view-person");
    //$f("plugin", "home", homeView);
});
*/
// Initialize your app
AppFramework = new Framework7();
// Export selectors engine
AppFramework.pullToRefreshDone()
var $$ = Dom7;

function changepage(_page) {
    /*AppFramework.showIndicator();*/

    if (_page == 'home') {


        var a= document.getElementsByName("weixinhome").length;
        var b= document.getElementsByClassName("page").length;
       /* alert(a+","+b)*/
        /*alert(a+","+b);*/
        if(a==0){
            $f('plugin','eighborhood.weixin.home',0);
        }if(a==1){
            if(b==1){

            }
            /*  if()*/
if(b>1) {
    if (b == 2) {
        $("div[name='weixinhome']").remove();

        $f('plugin', 'eighborhood.weixin.home', 0);
    }
    else{
        $('.view-main-home-page').html(" ");
        $f('plugin', 'eighborhood.weixin.home', 0);
    }
}

        }



    }
    if (_page == 'people') {
       /* $("div[name='myconcern']").*/

     /*   $('div[name="myconcern"]').each(function(){

        });*/


        var a= document.getElementsByName("myconcern").length;
        var b= document.getElementsByClassName("page").length;
/*alert(a+","+b);*/
        if(a==0){
            $f('plugin','eighborhood.weixin.myconcern',0); 
        }if(a==1){
         /*  if()*/

         if(b>1) {
             if (b == 2) {

             } else {

             /* alert(1)*/
             $('.view-main-people-page').html(" ");
             /*   $("div[name='myconcern']").remove();*/
             $f('plugin', 'eighborhood.weixin.myconcern', 0);
         }
         }else{

         }

        }
        /*$("div[name='myconcern']").remove();*/
   

    }
    if (_page == 'person.person') {

        $f("plugin", 'eighborhood.weixin.person.person', 0);
    }

}

$(function() {

    homeView = AppFramework.addView(".view-main",{
        domCache: true //enable inline pages
    });
    peopleView = AppFramework.addView(".view-main-people",{
        domCache: true //enable inline pages
    });

    personView = AppFramework.addView(".view-main-person",{
        domCache: true //enable inline pages
    });

    $f('plugin','eighborhood.weixin.home',0)

});



