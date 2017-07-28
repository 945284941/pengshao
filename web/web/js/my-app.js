// Initialize your app
AppFramework = new Framework7();
// Export selectors engine
var $$ = Dom7;


$(function() {

    mainView = AppFramework.addView(".view-main",{
        domCache: true
    });

});
