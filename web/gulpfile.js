var gulp = require('gulp');
var url = require('url');
var proxy   = require('proxy-middleware');
var connect=require('gulp-connect');
var proxyOptions = url.parse('http://localhost:8080/backend');
proxyOptions.route = '/proxy';
proxyOptions.changeOrigin=true;
proxyOptions.pathRewrite={'/proxy/':''};



gulp.task('hrframe',function(){
    gulp.src(["web/*","web/**/*"])
        .pipe(gulp.dest('dist/'));
    gulp.src(["src/*","src/**/*"])
        .pipe(gulp.dest('dist/app'));
    gulp.src(["doc/*","doc/**/*"])
        .pipe(gulp.dest('dist/doc'));
});

gulp.task('watch',function() {
    gulp.watch(['src/*','src/**/*','web/*','web/**/*','doc/*',"doc/**/*"], ['hrframe']);
});

gulp.task('server', function(done) {
    connect.server({
        port:'3000',
        root:'dist',
        livereload:false,
        middleware: function(connect, opt) {
            var middleware = proxy(proxyOptions);
            return [middleware];
        }
    });
});

gulp.task("default",['hrframe','watch','server']);