import json
import logging
import datetime

from flask import Flask, request
from flask_socketio import SocketIO

from Controller.back_controller import BackController
from Model.account_type import AccountType
from Model.cerere_sange import CerereSange
from Model.analiza import Analiza
from Model.register_info import RegisterInfo
from Model.formular_donare import FormularDonare


class FlaskServer:
    flask_app = Flask(__name__)
    app = SocketIO(flask_app)

    def __init__(self, config_data, controller: BackController):
        self.controller = controller
        self.port = config_data["flask_http_port"]
        self.host = '0.0.0.0'
        self.request_data = {}
        self.logger = logging.getLogger()

        self.init_requests()

    def update_wrapper(self):
        self.app.emit("update")

    def run(self):
        self.app.run(self.flask_app, self.host, self.port)

    def shutdown_server(self):
        func = request.environ.get('werkzeug.server.shutdown')
        if func is None:
            raise RuntimeError('Not running with the Werkzeug Server')
        func()

    def stop(self):
        self.shutdown_server()

    def init_requests(self):
        self.flask_app.add_url_rule("/test", "test_request",
                              self.test_request,
                              methods=["GET", "POST"])
        self.flask_app.add_url_rule("/login", "login_request",
                              self.login_request,
                              methods=["POST"])
        self.flask_app.add_url_rule("/register", "register_request",
                              self.register_request,
                              methods=["POST"])
        self.flask_app.add_url_rule("/add_pacient", "add_pacient_request",
                              self.add_pacient_request,
                              methods=["POST"])
        self.flask_app.add_url_rule("/user_trimite_formular_donare", "user_trimite_formular_donare",
                              self.user_trimite_formular_donare,
                              methods=["POST"])
        self.flask_app.add_url_rule("/staff_trimite_formular_donare", "staff_trimite_formular_donare",
                              self.staff_trimite_formular_donare,
                              methods=["POST"])
        self.flask_app.add_url_rule("/staff_cere_formulare_donari", "staff_cere_formulare_donari",
                              self.staff_cere_formular_donari,
                              methods=["POST"])
        self.flask_app.add_url_rule("/staff_update_formular_donare", "staff_update_fomular_donare",
                              self.staff_update_formular_donare,
                              methods=["POST"])
        self.flask_app.add_url_rule("/staff_trimite_analiza", "staff_trimite_analiza",
                              self.staff_trimite_analiza,
                              methods=["POST"])
        self.flask_app.add_url_rule("/staff_get_stoc_curent", "staff_get_stoc_curent",
                              self.staff_get_stoc_curent,
                              methods=["POST"])
        self.flask_app.add_url_rule("/get_analize", "get_analize",
                              self.get_analize,
                              methods=["POST"])
        self.flask_app.add_url_rule("/desavarsire_cerere_medic", "desavarsire_cerere_medic",
                              self.trimite_pungi,
                              methods=["POST"])
        self.app.add_url_rule("/trimite_cerere_sange", "trimite_cerere_sange", self.trimite_cerere_sange,
                              methods=["POST"])
        self.flask_app.add_url_rule("/get_cereri_sange", "get_cereri_sange", self.get_cereri_sange,
                              methods=["POST"])
        self.flask_app.add_url_rule("/anulare_cerere", "anulare_cerere", self.anulare_cerere,
                              methods=["POST"])
        self.flask_app.add_url_rule("/getIstoricDonare", "get_istoric_donare", self.get_istoric_donare,
                              methods=["POST"])
        self.flask_app.add_url_rule("/valid_donation", "valid_donation_request",
                              self.valid_donation_request,
                              methods=["POST"])
        self.app.add_url_rule("/get_centru_home_screen_data", "get_centru_home_screen_data",
                              self.get_centru_home_screen_data, methods=["POST"])

    def trimite_pungi(self):
        self.request_data = request.get_json()
        self.logger.debug("Got request trimite pungi JSON: {}".format(self.request_data))

        id_locatie_curenta = self.request_data["id_locatie_curenta"]

        # Update on production
        id_cerere = self.request_data["id_cerere"]

        grupa = self.request_data["grupa"]
        rh = self.request_data["rh"]
        numar_plasma = self.request_data["plasma"]
        numar_globule = self.request_data["globule"]
        numar_trombocite = self.request_data["trombocite"]
        lista = [numar_plasma, numar_trombocite, numar_globule]
        self.logger.debug(lista)
        status, mesaj = self.controller.send_pungi(id_locatie_curenta,
                                                   id_cerere,
                                                   grupa,
                                                   rh,
                                                   plasma=numar_plasma,
                                                   globule_rosii=numar_globule,
                                                   tromobocite=numar_trombocite)
        if status == 0:
            # pungile au fost trimise
            id_cerere = self.request_data["id_cerere"]

        return_dict = {"status": status, "message": mesaj}

        self.logger.debug("Returning response for trimite pungi: {}".format(return_dict))

        self.update_wrapper()

        return json.dumps(return_dict)

    def get_analize(self):
        self.request_data = request.get_json()
        self.logger.debug("Req data: {}".format(self.request_data))

        dictionar = {
            "entities": self.controller.get_analize(self.request_data["cnp"])
        }
        self.logger.debug(dictionar)

        return json.dumps(dictionar)

    def test_request(self):
        self.request_data = request.get_json()
        self.logger.debug("Req data: {}".format(self.request_data))
        return json.dumps(self.request_data)

    def valid_donation_request(self):
        self.request_data = request.get_json()
        self.logger.debug("Valid donation request JSON: {}".format(self.request_data))
        cnp_donator = self.request_data["cnpDonator"]

        status, message = self.controller.is_a_valid_donation(cnp_donator)

        return_dict = {"status" : str(status), "message" : message}

        return json.dumps(return_dict)

    def add_pacient_request(self):
        self.request_data = request.get_json()
        self.logger.debug("Add pacient request JSON: {}".format(self.request_data))
        id_medic = self.request_data["idMedic"]
        nume_pacient = self.request_data["numePacient"]
        cnp_pacient = self.request_data["cnpPacient"]
        grupa_sange_pacient = self.request_data["grupaSangePacient"]
        rh_pacient = self.request_data["rhPacient"]

        status, message = self.controller.add_pacient(id_medic, nume_pacient, cnp_pacient, grupa_sange_pacient, rh_pacient)

        return_dict = {"status": str(status), "message": message}

        self.update_wrapper()

        return json.dumps(return_dict)

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
                                     self.request_data["judet"], self.request_data["localitate"],
                                     self.request_data["address"], self.request_data["phone"],
                                     AccountType[self.request_data["accountType"]], self.request_data["license"])

        status_code, status_message = self.controller.register(register_info)

        return_dict = {"status": str(status_code), "message": status_message}

        return json.dumps(return_dict)

    def user_trimite_formular_donare(self):
        self.request_data = request.get_json()
        self.logger.debug("Got trimiteFormularDonare request JSON: {}".format(self.request_data))

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

        self.update_wrapper()

        return json.dumps(return_dict)

    def staff_get_stoc_curent(self):
        self.request_data = request.get_json()
        self.logger.debug("Got register request JSON: {}".format(self.request_data))
        id_locatie = self.request_data["id_locatie"]

        return_dict = self.controller.get_stoc_curent(id_locatie)
        self.logger.debug("Returning response: {}".format(return_dict))

        return json.dumps(return_dict)

    def staff_cere_formular_donari(self):
        self.request_data = request.get_json()
        self.logger.debug("Got register request JSON: {}".format(self.request_data))
        id_locatie = self.request_data["id_locatie"]

        lista = self.controller.staff_cerere_formulare_donari(id_locatie)
        lista_dictionare =[]
        for x in lista:
            dict_list = {"id": x[0],
                         "nume": x[1],
                         "prenume": x[2],
                         "sex": x[3],
                         "telefon": x[4],
                         "domiciliuLocalitate": x[5],
                         "domiciliuJudet": x[6],
                         "domiciliuAdresa": x[7],
                         "resedintaLocalitate": x[8],
                         "resedintaJudet": x[9],
                         "resedintaAdresa": x[10],
                         "beneficiar_full_name": x[11],
                         "beneficiar_cnp": x[12],
                         "grupa": x[13],
                         "rh": x[14],
                         "status": x[15]}
            self.logger.debug("Entity in all cereri list for location {}: {}".format(id_locatie, x))
            lista_dictionare.append(dict_list)

        return_dict = {"entities": lista_dictionare}

        return json.dumps(return_dict)

    def staff_trimite_analiza(self):
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
                                         self.request_data["zile_disponibil"],
                                         self.request_data["status"],
                                         self.request_data["id"])

        id_locatie = self.request_data["id_locatie"]

        analiza = Analiza(
                        -1,
                        self.request_data["alt"],
                        self.request_data["sif"],
                        self.request_data["htlv"],
                        self.request_data["htcv"],
                        self.request_data["hiv"],
                        self.request_data["hb"]

        )

        self.logger.debug(analiza)
        status, message = self.controller.staff_update_formular_donare(formular_donare, id_locatie, analiza=analiza)
        return_dict = {"status": str(status), "message": message}

        self.update_wrapper()

        return json.dumps(return_dict)

    def staff_update_formular_donare(self):
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
                                         self.request_data["zile_disponibil"],
                                         self.request_data["status"],
                                         self.request_data["id"])

        id_locatie = self.request_data["id_locatie"]

        staff_full_name = self.request_data["staff_full_name"]

        status, message = self.controller.staff_update_formular_donare(formular_donare, id_locatie, staff_full_name=staff_full_name)
        return_dict = {"status": str(status), "message": message}

        self.update_wrapper()

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

        self.update_wrapper()

        return json.dumps(return_dict)

    def trimite_cerere_sange(self):
        self.request_data = request.get_json()
        self.logger.debug("Got /trimiteCereeSange request JSON {}".format(self.request_data))

        cerere = CerereSange( self.request_data["nume_pacient"],
                              self.request_data["cnp_pacient"],
                              self.request_data["grupa_sange"],
                              self.request_data["rh"],
                              self.request_data["numar_pungi_trombocite"],
                              self.request_data["numar_pungi_globule_rosii"],
                              self.request_data["numar_pungi_plasma"],
                              datetime.datetime.today().strftime('%Y-%m-%d'),
                              self.request_data["importanta"])

        status, message = self.controller.trimite_cerere_sange(cerere, self.request_data["cnp_medic"])
        return_dict = {"status": status, "message": message}

        self.update_wrapper()

        return json.dumps(return_dict)

    def get_istoric_donare(self):
        self.request_data = request.get_json()
        self.logger.debug("Got /get_istoric_donare request JSON {}".format(self.request_data))

        username = self.request_data["username"]

        return_dict = {
            "entities": self.controller.get_istoric_donari(username)
        }

        return json.dumps(return_dict)

    def get_cereri_sange(self):
        self.request_data = request.get_json()
        self.logger.debug("Got /get cereri sange request JSON {}".format(self.request_data))

        id_locatie = self.request_data["id_locatie"]
        status = self.request_data["status"]
        from_spital = self.request_data["from_spital"]

        list_dict = {"entities": self.controller.get_cereri_sange(id_locatie, status, from_spital)}

        self.logger.debug(list_dict)

        return json.dumps(list_dict)

    def anulare_cerere(self):
        self.request_data = request.get_json()
        self.logger.debug("Got /anulare request JSON {}".format(self.request_data))

        id_cerere = self.request_data["id_cerere"]

        status, mesaj = self.controller.anulare_cerere(id_cerere)

        list_dict = {"status": status,
                     "messsage": mesaj}

        self.logger.debug(list_dict)

        self.update_wrapper()

        return json.dumps(list_dict)

    def get_centru_home_screen_data(self):
        self.request_data = request.get_json()
        id_locatie = int(self.request_data["id_locatie"])

        self.logger.debug("Got request get_centru_home_screen_data for id_locatie: {}".format(id_locatie))
        return_dict = self.controller.get_centru_home_screen_data(id_locatie)
        self.logger.debug("Returning data for request get_centru_home_screen_data {}".format(return_dict))

        return json.dumps(return_dict)
