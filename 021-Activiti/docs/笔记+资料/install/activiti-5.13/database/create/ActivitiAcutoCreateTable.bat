@echo off
title Activiti5-CreateTableProcess
color a
@echo Activiti5数据库导入中...

mysql -uroot -pmikesirius activiti < activiti.mysql.create.engine.sql
mysql -uroot -pmikesirius activiti < activiti.mysql.create.history.sql
mysql -uroot -pmikesirius activiti < activiti.mysql.create.identity.sql

@rem pause

if "%OS%"=="Windows_NT" ENDLOCAL
