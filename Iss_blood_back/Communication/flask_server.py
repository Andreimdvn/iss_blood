import json

from flask import Flask, request
import logging

from Controller.BackController import BackController


class FlaskServer:
    app = Flask(__name__)

    def __init__(self, config_data, controller: BackController):
        self.controller = controller
        self.port = config_data["flask_http_port"]
        self.host = '0.0.0.0'
        self.request_data = {}
        self.logger = logging.getLogger()

        self.init_requests()

    def run(self):
        self.app.run(self.host, self.port)

    def shutdown_server(self):
        func = request.environ.get('werkzeug.server.shutdown')
        if func is None:
            raise RuntimeError('Not running with the Werkzeug Server')
        func()

    def stop(self):
        self.shutdown_server()

    def init_requests(self):
        self.app.add_url_rule("/test", "test_request", self.test_request, methods=["GET", "POST"])
        self.app.add_url_rule("/login", "login_request", self.login_request, methods=["POST"])

    def test_request(self):
        self.request_data = request.get_json()
        self.logger.debug("Req data: {}".format(self.request_data))
        return json.dumps(self.request_data)

    def login_request(self):
        self.request_data = request.get_json()
        self.logger.debug("Got Login Request JSON: {}".format(self.request_data))
        user = self.request_data["username"]
        password = self.request_data["password"]

        status = self.controller.login(user, password)

        return_dict = {"status": status}
        if status == 0:
            return_dict["message"] = "Login successfully"
        else:
            return_dict["message"] = "Invalid username or password"

        self.logger.debug("Returnin response for Login Request: {}".format(return_dict))

        return json.dumps(return_dict)
