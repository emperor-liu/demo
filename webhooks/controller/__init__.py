# coding:utf-8
# request mapping 配置
from flask import Blueprint
gitHooks = Blueprint('robot', __name__)
from . import robotController


class Mapping(object):
    VIEW_PREFIX = '/robot'
