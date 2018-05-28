from Service.i_service import IService
from Utils import user_utils, locatii_utils


class ServiceDonator(IService):
    def __init__(self, repo_manager, db):
        super().__init__(repo_manager, db)

    def insert_formular_user(self, formular, username):
        '''
        Insereaza datele din formular in BD pentru un user deja existent
        :param formular: Model.FormularDonare
        :param user: Utils.orm.User
        :return: status, message
        '''
        user = self.db.select("User", ["username"], [username], True)
        if user is None:
            return 1, "User not found"

        donator = self.db.select("Donator", ["id_user"], [user.id], True)
        if donator is None:
            return 2, "Donator not found"

        # 1. Insereaza in formular ce e de inserat
        user_utils.insert_formular(self.db, formular, donator.id_donator)

        #2. Fa update pe datele userului daca e cazul
        return self.__update_user(formular, user)



    def __update_user(self, formular, user):
        '''
        Verifica daca formularul contine date diferite despre user fata de cele din DB
        Daca da, face update
        :param formular: Model.FormularDonare
        :param user Utils.orm.User
        :return: status, message
        '''
        # 1 Vezi ce difera
        donator_info = user_utils.get_info_donator(self.db, user.id)

        coloane_noi = []  # denumirile coloanelor care trebuie modifiate
        valori_noi = []  # valorile coloanelor coresp denumirilor din coloane_noi
        denumiri = ['nume', 'prenume', 'telefon', 'sex']
        valori_formular = [formular.nume, formular.prenume, formular.telefon, formular.sex]
        # pentru fiecare coloana, vezi daca difera. Daca da, adaug-o in coloane_noi si valori_noi
        for i in range(0, len(denumiri)):
            if donator_info[denumiri[i]] != valori_formular[i]:
                coloane_noi.append(denumiri[i])
                valori_noi.append(valori_formular[i])

        # Vezi daca difera domiciliul
        if donator_info['domiciliu_localitate'] != formular.domiciliu_localitate or \
                donator_info['domiciliu_judet'] != formular.domiciliu_judet:  # daca domiciliul difera
            # nu conteaza daca e un judet nou sau e acelasi
            id_judet = locatii_utils.get_id_judet(self.db, formular.domiciliu_judet)  # cauta sau insereaza judetul nou

            id_localitate = locatii_utils.get_id_localitate(self.db, formular.domiciliu_localitate,
                                                            id_judet)  # cauta/ins
            coloane_noi.append('id_domiciliu')  # va trebui schimbat si in Donator
            valori_noi.append(id_localitate)
        if donator_info['domiciliu_adresa'] != formular.domiciliu_adresa:  # daca difera adresa
            coloane_noi.append('adresa_domiciliu')
            valori_noi.append(formular.domiciliu_adresa)

        # la fel pentru resedinta
        if donator_info['resedinta_localitate'] != formular.resedinta_localitate or \
                donator_info['resedinta_judet'] != formular.resedinta_judet:  # daca resedinta difera
            # nu conteaza daca e un judet nou sau e acelasi
            id_judet = locatii_utils.get_id_judet(self.db, formular.resedinta_judet)  # cauta sau insereaza judetul nou

            id_localitate = locatii_utils.get_id_localitate(self.db, formular.resedinta_localitate,
                                                            id_judet)  # cauta/ins
            coloane_noi.append('id_localitate_resedinta')  # va trebui schimbat si in Donator
            valori_noi.append(id_localitate)
        if donator_info['resedinta_adresa'] != formular.resedinta_adresa:  # daca difera adresa
            coloane_noi.append('adresa_resedinta')
            valori_noi.append(formular.resedinta_adresa)

        # 2 Fa modificarile efectiv
        if coloane_noi:
            self.db.update("Donator", columns_where=["id_user"], values_where=[user.id], columns=coloane_noi,
                           values=valori_noi)

        return 0, "Formular inregistrat cu succes"
