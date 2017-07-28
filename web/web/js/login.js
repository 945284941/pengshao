// Initialize your app
AppFramework = new Framework7();
// Export selectors engine
var $$ = Dom7;

$(function(){
    AppFramework.showIndicator();
    setInterval(function(){
        $("#loginSystem").submit();
    },500);


});










