### free mes open source mes Manufacturing Execution System
# 市面上超级好用的开源MES，支持二开
### 技术架构：Springboot2+ VUE3 + Mysql8 + Redis + Minio，也有SpringCloud版本
### 开源不易，努力坚持！记得 Star，不要白嫖~

### 目录介绍：
#### 部署和文档：基于docker的各种部署方式和使用手册
#### source-code：核心框架、前、后端源代码


## MES系统流程图
<img width="1360" alt="image" src="./images/系统流程图.jpg">

## MES功能架构图
<img width="1360" alt="image" src="./images/MES功能架构图.jpg">

## MES排产功能图
<img width="1360" alt="image" src="./images/排产功能图.jpg">

## 排班日历
<img width="1360" alt="image" src="./images/排班日历.png">

## 排班计划
<img width="1309" alt="image" src="./images/排班计划.png">

## 生产订单
<img width="1368" alt="image" src="./images/生产订单.png">

## 生产排产
<img width="1383" alt="image" src="./images/生产排产.png">

## 生产任务
<img width="1320" alt="image" src="./images/生产任务.png">

## 生产报工
<img width="1389" alt="image" src="./images/生产报工.png">

## 数据大屏1
<img width="1360" alt="image" src="./images/数据大屏1.jpg">

## 数据大屏2
<img width="1360" alt="image" src="./images/数据大屏2.jpg">


### 建议安装在Ubuntu Server 22.04服务器上，需要懂技术的哦

### 执行步骤：
### 1、安装docker
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -  
apt-get install -y software-properties-common  
add-apt-repository    "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"  
apt-get install docker-ce  -y
### 2、安装docker-compose
sudo curl -L https://github.com/docker/compose/releases/download/v2.20.2/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose  
sudo chmod +x /usr/local/bin/docker-compose
### 3、下载源码后，执行 docker-compose up
### 4、访问：http://localhost:48081
用户名：metaxk  
密码：111111

## 完整代码包含前端、后端、数据大屏、报表系统

## DEMO：
## 演示账号：
https://mesv2.cloudmes.io/
### 账号：test001   密码：123456
### 账号：test002   密码：123456

## 联系我们：17898898894