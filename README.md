# -YYX
从LoginUI开始运行文件即可。
## 环境
1.mysql-connector
2.javaFX环境
## 数据库
数据库YunYuXuan
需要手动建立
表admin(adminId int primary key, adminName varchar(20), adminAccount varchar(20), adminPassword varchar(20))
不需要插入任何数据，在登录界面注册即可插入数据并且建立该用户的friend表，request表

！！！！！！！以下表不需要手动建立
当A用户向B用户发起好友请求后，会在B用户的request表中插入A用户数据，B用户如果接受则两人成为好友，并建立message表。

