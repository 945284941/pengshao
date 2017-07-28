#!/bin/bash
# 编译
gradle_war=`gradle war`
# 找到文件
warfiles=`ls build/libs`
if [ -f "build/libs/$warfiles" ]
then
    # 复制到复制到docker目录
    cp "build/libs/$warfiles" "docker/schoolcms.war"
    # 覆盖远程测试服务器版本
    scp docker/* docker@172.16.248.40:~/schoolcms-docker
    # 更新docker
    ssh -t docker@172.16.248.40 bash -c 'true;sudo docker stop schoolcms;sudo docker rm schoolcms;sudo docker rmi schoolcms;cd ~/schoolcms-docker;sudo docker build -t schoolcms .;sudo docker run --name=schoolcms -d -p 8000:8080 schoolcms'
else
    echo "build 失败"
fi
