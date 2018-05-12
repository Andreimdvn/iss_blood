import datetime

from Model.AccountType import AccountType


class validator_register:
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

        if register_info.account_type == None:
            erori+= "Eoare interna: tipul de cont nu a fost gasit\n"
        elif register_info.account_type != AccountType.Donator and register_info.license == "":
            # verificare separata: donatorii nu au licenta
            erori += "Toate campurile sunt obligatorii\n"

        if len(register_info.phone) != 10:
            erori += "Numarul de telefon nu e valid\n"
        else:
            for c in register_info.phone:
                if c < '0' or c > '9':
                    erori+="Numarul de telefon nu e valid\n"

        if "@" not in register_info.email or "." not in register_info.email:
            erori+= "Adresa de email nu e valida\n"



        try:
            register_info.data_nasterii = self.data_nasterii_din_cnp(register_info.cnp)
        except ValueError:
            erori+= "CNP-ul nu e valid\n"

        if erori == "":
            return True, ""
        return False, erori


    def data_nasterii_din_cnp(self, cnp):
        '''
        Extrage data nasterii din CNP
        Arunca ValueError daca rezulta o data invalida
        :param cnp:
        :return: datetime.datetime
        '''
        #-yymmdd---
        year = int(cnp[1:3]) + 2000
        if year > datetime.datetime.now().year:
            year-=100
        month = cnp[3:5]
        day = cnp[5:7]
        date = datetime.datetime.strptime("{0}-{1}-{2}".format(str(year), month, day), "%Y-%m-%d")
        return date