// Initialize your app
AppFramework = new Framework7();
// Export selectors engine
var $$ = Dom7;

function changepage(_page) {
    AppFramework.showIndicator();

    if (_page == 'home') {

        $f("plugin", _page, 0);
    }
    if (_page == 'person') {

        $f("plugin", 'person.person', 0);
    }

}

$(function () {
    homeView = AppFramework.addView(".view-home",{domCache:true});
    personView = AppFramework.addView(".view-person");
    $f("plugin", "home", homeView);
});



