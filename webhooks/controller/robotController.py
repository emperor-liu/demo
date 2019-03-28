# coding:utf-8
from . import gitHooks
from utils.logUtils import printlog


@gitHooks.route("createTask", methods=["post"])
def create_task():
    printlog("request test")
    return "success"
