# schoolcms

## 更新日志

1. 配置 charisma UI

## 特性
## 原理说明
## 下载安装

IntelliJ IDEA 导入

1. Import/Project 选择 existing sources
2. 在 external model 选择 `Gradle`
3. 选择项目目录以及依赖目录
4. 开始编码

命令行打包

```sh
gradle appRun //启动内置jetty服务器运行项目 ---Gretty
```

## 使用说明

1. `db.sql`: MySQL 数据库实例，含创建用户和数据库
2. `intellij-java-google-style.xml`: Google 的 IntelliJ IDEA 格式化模板
3. `src/main/resources/developer.properties`: 系统配置文件

## 注意事项

6. mysql-connector version (6.x) **不兼容** JFinal Generator 但是旧版本不支持最新版本的 Mariadb server (10.1.x)
7. Gretty 服务器自带热部署。可以在后台持续编辑代码

## TODO

