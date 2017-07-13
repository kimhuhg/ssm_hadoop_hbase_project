非Maven的基本环境SSM框架idea项目
==============================
基本的SSM框架的非Maven idea 项目 <br/>
用于给想一步步导包helloworld开始学习 而不想用Maven建项目的我们<br/>

包括了junit,mock,mockMVC测试,输入校验,数据库操作<br/>
包括了基本的一些例子 <br/>

比如输入校验，拦截器，异常处理<br/>
数据库操作（增删查改，1对多的生成，Mybatis自动生成）<br/>
测试（controller测试，将service层利用Mock模拟，service层测试）<br/>

今后会再建一个工程 在其中添加spring-data-hadoop的相关，这个项目就不再扩展其他功能<br/>
也欢迎其他人使用<br/>
这个项目的环境是 <br/>
idea 2017.1.5  <br/>
mysql->schema是wwt_db->user,book 2个表 可以用项目中的wwt_db.sql 导入<br/>
spring 4.3.9<br/>
mybatis 3.4.1<br/>
