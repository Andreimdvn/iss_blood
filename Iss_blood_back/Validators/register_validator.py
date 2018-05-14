from Model.account_type import AccountType
from Utils.user_utils import data_nasterii_din_cnp


class register_validator:
    def validate(self, register_info):
        '''
        Verifica daca informatia data e valida
        O parte din validari se intampla si in service(e nevoie de baza de date)
        :param register_info:
        :return: Tuple>bool, string) -> valid/invalid + mesaj cu eventualele erori
        '''

        erori = ""

        if register_info.username == "" or register_info.password == "" or register_info.email == "" or \
                register_info.nume == "" or register_info.prenume == "" or register_info.cnp == "" or \
                register_info.judet == "" or register_info.localitate == "" or register_info.address == "" or \
                register_info.phone == "" or register_info.account_type == "": \
                erori += "Toate campurile sunt obligatorii\n"

        if register_info.account_type is None:
            erori += "Eoare interna: tipul de cont nu a fost gasit\n"
        elif register_info.account_type != AccountType.Donator and register_info.license == "":
            # verificare separata: donatorii nu au licenta
            erori += "Toate campurile sunt obligatorii\n"

        if len(register_info.phone) != 10:
            erori += "Numarul de telefon nu e valid\n"
        else:
            for c in register_info.phone:
                if c < '0' or c > '9':
                    erori += "Numarul de telefon nu e valid\n"

        if "@" not in register_info.email or "." not in register_info.email:
            erori += "Adresa de email nu e valida\n"

        try:
            register_info.data_nasterii = data_nasterii_din_cnp(register_info.cnp)
        except ValueError:
            erori += "CNP-ul nu e valid\n"

        if erori == "":
            return True, ""
        return False, erori
