# -*- coding: UTF-8 -*-
from flask import Flask, g, current_app
from config import Config
from controller import gitHooks, Mapping
from utils.logUtils import printlog


# git hooks github、gitee
def create_app():
    app = Flask(__name__)
    app.config.from_object(Config)
    Config.init_app(app)
    # 路由规则
    app.register_blueprint(gitHooks, url_prefix=Mapping.VIEW_PREFIX)
    # app.config["DBSession"] = DBSession

    # 接口的统一入口
    @app.before_request
    def before_request():
        # 定义访问数据库的对象
        # g.db = current_app._get_current_object().config["DBSession"]()
        printlog("before_request")

    # 在接口返回之后进行调用（出错也会调用）
    @app.teardown_request
    def admin_teardown(exception=None):
        # 断连接
        g.db.close()

    return app


if __name__ == '__main__':
    app = create_app()
    printlog("start server")
    app.run(host='0.0.0.0', port=Config.PORT, debug=Config.DEBUG)
