import json

from flask import Flask, request
import logging

from Controller.back_controller import BackController
from Model.account_type import AccountType
from Model.register_info import RegisterInfo
from Model.FormularDonare import FormularDonare


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
        self.app.add_url_rule("/userTrimiteFormularDonare", "user_trimite_formular_donare", self.user_trimite_formular_donare,
                              methods=["POST"])
        self.app.add_url_rule("/staffTrimiteFormularDonare", "staff_trimite_formular_donare", self.staff_trimite_formular_donare,
                              methods=["POST"])

    def test_request(self):
        self.request_data = request.get_json()
        self.logger.debug("Req data: {}".format(self.request_data))
        return json.dumps(self.request_data)

    def login_request(self):
        self.request_data = request.get_json()
        self.logger.debug("Got Login Request JSON: {}".format(self.request_data))
        user = self.request_data["username"]
        password = self.request_data["password"]

        status, user_type, user_info = self.controller.login(user, password)

        if status == 0:
            return_dict = user_info  # refolosim user_info care deja e dictionar
            return_dict["status"] = 0
            return_dict["message"] = "Login cu success!"
            return_dict["user_type"] = user_type
        else:
            return_dict = {"status": status, "message": "Username sau parola invalide"}

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

        status_code, status_message = self.controller.register(register_info)

        return_dict = {"status": str(status_code), "message": status_message}

        return json.dumps(return_dict)

    def user_trimite_formular_donare(self):
        self.request_data = request.get_json()
        self.logger.debug("Got register request JSON: {}".format(self.request_data))

        formular_donare = FormularDonare(self.request_data["nume"],
                                         self.request_data["prenume"],
                                         self.request_data["sex"],
                                         self.request_data["telefon"],
                                         self.request_data["domiciliu_localitate"],
                                         self.request_data["domiciliu_judet"],
                                         self.request_data["domiciliu_adresa"],
                                         self.request_data["resedinta_localitate"],
                                         self.request_data["resedinta_judet"],
                                         self.request_data["resedinta_adresa"],
                                         self.request_data["beneficiar_full_name"],
                                         self.request_data["beneficiar_CNP"],
                                         self.request_data["grupa"],
                                         self.request_data["rh"],
                                         self.request_data["zile_disponibil"])

        status, message = self.controller.user_trimite_formular(formular_donare, self.request_data["username"])

        return_dict = {"status": str(status), "message": message}
        return json.dumps(return_dict)

    def staff_trimite_formular_donare(self):
        self.request_data = request.get_json()
        self.logger.debug("Got register request JSON: {}".format(self.request_data))

        formular_donare = FormularDonare(self.request_data["nume"],
                                         self.request_data["prenume"],
                                         self.request_data["sex"],
                                         self.request_data["telefon"],
                                         self.request_data["domiciliu_localitate"],
                                         self.request_data["domiciliu_judet"],
                                         self.request_data["domiciliu_adresa"],
                                         self.request_data["resedinta_localitate"],
                                         self.request_data["resedinta_judet"],
                                         self.request_data["resedinta_adresa"],
                                         self.request_data["beneficiar_full_name"],
                                         self.request_data["beneficiar_CNP"],
                                         self.request_data["grupa"],
                                         self.request_data["rh"],
                                         self.request_data["zile_disponibil"])

        status, message = self.controller.staff_trimite_formular(formular_donare)

        return_dict = {"status": str(status), "message": message}

        return json.dumps(return_dict)