#添加基础镜像
FROM openjdk:8

MAINTAINER liang.qfzc@gmail.com

#复制应用程序
COPY lib /data/lib
COPY config /data/config
COPY bin /data/bin

#设置环境变量
ENV LANG C.UTF-8

#定义时区参数
ENV TZ=Asia/Shanghai

#设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone

EXPOSE 8080

WORKDIR /data/bin
RUN sed -i -e 's/\r$//' docker-run.sh
RUN chmod +x ./docker-run.sh
#启动服务
ENTRYPOINT ./docker-run.sh
