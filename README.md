非Maven的基本环境SSM+hadoop+hbase框架idea项目
==============================
基本的SSM框架的非Maven idea 项目
用于给想一步步导包helloworld开始学习 而不想用Maven建项目的我们

包括了junit,mock,mockMVC测试,输入校验,数据库操作
包括了基本的一些例子
包括了spring-data-hadoop的相关 hbase+hadoop
包括了2个封装的类用于操作hdfs和hbase result生成实体类

现有输入校验，拦截器，异常处理
数据库操作（增删查改，1对多的生成，Mybatis自动生成）
测试（controller测试，将service层利用Mock模拟，service层测试）
hdfs的增删查改上传下载
hbase的result映射成实体类

这个项目的环境是 
idea 2017.1.5  
mysql->schema是wwt_db->user,book 2个表 可以用项目中的wwt_db.sql 导入
spring 4.3.9
mybatis 3.4.1
spring-data-hadoop
