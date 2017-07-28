/////////////////////////////////  加载messenger组件，重写alert  /////////////////////////////////

$._messengerDefaults = {
    extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
};

window.alert = function(message){
    $.globalMessenger().post({
        message: message,
        hideAfter: 3,
        showCloseButton: true
    });
};

/////////////////////////////////  加载messenger组件，重写alert  /////////////////////////////////

