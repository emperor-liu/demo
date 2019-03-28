# -*- coding: UTF-8 -*-
from os import path
import datetime


# 全局配置项
class Config(object):
    PORT = 8081
    DEBUG = True
    ENV_TEST = False
    # 数据库配置
    db_userName = "root"
    db_userPwd = "root"
    db_address = "127.0.0.1:3306"
    db_name = "demo"
    _MYSQL_url = "mysql://%s:%s@%s/%s" % (db_userName, db_userPwd, db_address, db_name)

    # 日志配置
    logger_level = "DEBUG"
    logfile = True
    import time
    now = time.time()
    logger_logFilename = path.dirname(__file__) + "/../logs/git-hooks.%s.log" % (datetime.date.fromtimestamp(now))

    @staticmethod
    def init_app(app):
        pass
