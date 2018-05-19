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
        self.app.add_url_rule("/trimiteFormularDonare", "trimite_formular_donare", self.trimite_formular_donare,
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

        status, user_type = self.controller.login(user, password)

        return_dict = {"status": status, "user_type": user_type}
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

        status_code, status_message = self.controller.register(register_info)

        return_dict = {"status": str(status_code), "message": status_message}

        return json.dumps(return_dict)

    def trimite_formular_donare(self):
        self.request_data = request.get_json()
        self.logger.debug("Got register request JSON: {}".format(self.request_data))

        # nume, prenume, sex, telefon, domiciliu_localitate, domiciliu_judet, domiciliu_adresa, resedinta_localitate,
        # resedinta_judet, resedinta_adresa, beneficiar_full_name, beneficiar_CNP, grupa, rh, zile_disponibil
        formular_donare = FormularDonare(self.request_data["nume"], self.request_data["prenume"],
                                         self.request_data["sex"], self.request_data["telefon"],
                                         self.request_data["domiciliuLocalitate"],
                                         self.request_data["domiciliuJudet"],
                                         self.request_data["domiciliuAdresa"],
                                         self.request_data["resedintaLocalitate"],
                                         self.request_data["resedintaJudet"],
                                         self.request_data["resedintaAdresa"],
                                         self.request_data["beneficiarFullName"],
                                         self.request_data["beneficiarCNP"],
                                         self.request_data["grupa"],
                                         self.request_data["rh"],
                                         self.request_data["zileDisponibil"])

        self.controller.trimite_formular(formular_donare)

        return_dict = {"status": "0", "message": "Success"}
        return json.dumps(return_dict)
