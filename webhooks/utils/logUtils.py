# !/usr/bin/python
# -*- coding: UTF-8 -*-
import sys
import logging
from config import Config

reload(sys)
sys.setdefaultencoding('utf8')


def printlog(info, is_error=False):
    # Define a Handler and set a format which output to file
    fh = logging.FileHandler(encoding='utf-8', mode='a', filename=Config.logger_logFilename)
    logging.basicConfig(
        handlers=[fh],
        level=Config.logger_level,  # 定义输出到文件的log级别，大于此级别的都被输出
        format='%(asctime)s  %(filename)s : %(levelname)s  %(message)s',  # 定义输出log的格式
        datefmt='%Y-%m-%d %A %H:%M:%S',  # 时间
        filename=Config.logger_logFilename,  # log文件名
        filemode='a')  # 写入模式“w”或“a”
    # Define a Handler and set a format which output to console
    if Config.DEBUG:
        console = logging.StreamHandler()  # 定义console handler
        console.setLevel(logging.INFO)  # 定义该handler级别
        formatter = logging.Formatter('%(asctime)s  %(filename)s : %(levelname)s  %(message)s')  # 定义该handler格式
        console.setFormatter(formatter)
        # Create an instance
        logging.getLogger().addHandler(console)  # 实例化添加handler
    if is_error:
        logging.error('%s' % info)
    else:
        logging.info('%s' % info)
