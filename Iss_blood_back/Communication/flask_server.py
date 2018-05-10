import json

from flask import Flask, request
import logging

from Controller.BackController import BackController
from Model.AccountType import AccountType
from Model.RegisterInfo import RegisterInfo


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
        self.app.add_url_rule("/register", "register_request", self.register_request, methods=["POST"])

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
            return_dict["message"] = "Login cu success!"
        else:
            return_dict["message"] = "Username sau parola invalide"

        self.logger.debug("Returning response for Login Request: {}".format(return_dict))

        return json.dumps(return_dict)

    def register_request(self):
        self.request_data = request.get_json()
        self.logger.debug("Got register request JSON: {}".format(self.request_data))

        register_info = RegisterInfo(self.request_data["username"], self.request_data["password"],
                                    self.request_data["email"], self.request_data["nume"],
                                    self.request_data["prenume"], self.request_data["cnp"],
                                    self.request_data["localitate"], self.request_data["judet"],
                                    self.request_data["address"], self.request_data["phone"],
                                    AccountType[self.request_data["accountType"]], self.request_data["license"])

        #TO DO: server side validation: sa nu fie medici cu licenta = "", telefonul sa aiba 10 char...

        status_code, status_message = self.controller.register(register_info)

        return_dict = {"status": str(status_code), "message": status_message}

        return json.dumps(return_dict)
