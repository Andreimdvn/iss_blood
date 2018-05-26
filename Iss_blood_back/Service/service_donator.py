from Service.i_service import IService
from Utils import user_utils, locatii_utils


class ServiceDonator(IService):
    def __init__(self, repo_manager, db):
        super().__init__(repo_manager, db)

    def trimite_formular(self, formular):
        '''
        Inregistreaza formularul in BD.
        Face update la datele utilizatorului daca e cazul.
        :param formular: Model.FormularDonare
        :return: status_code, message
                status_code = 0 pentru succes, != 0 altfel
        '''
        user = self.db.select("User", ["username"], [formular.username], True)
        if user is None:
            return 1, "User not found"

        user_id = user.id

        # 1. Insereaza in DB
        nume_coloane = ["id_donator", "sex", "zile_disponibil"]
        valori_coloane = [user.id, formular.sex, formular.zile_disponibil]
        if formular.beneficiar_full_name != "":
            nume_coloane.append("beneficiar_full_name")
            valori_coloane.append(formular.beneficiar_full_name)

        if formular.beneficiar_CNP != "":
            nume_coloane.append("beneficiar_CNP")
            valori_coloane.append(formular.beneficiar_CNP)

        if formular.grupa != "":
            nume_coloane.append("grupa")
            valori_coloane.append(formular.grupa)

        if formular.rh != "":
            nume_coloane.append("rh")
            valori_coloane.append(formular.rh)

        self.db.insert("FormularDonare", nume_coloane, valori_coloane)

        # 2. Update pe user cu valorile noi(nume, tel, locuinta)
        # 2.1 Vezi ce difera
        donator_info = user_utils.get_info_donator(self.db, user)

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
        if donator_info['domiciliu_localitate'] != formular.domiciliu_localitate:  # daca localitatea domiciliu difera
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
        if donator_info['resedinta_localitate'] != formular.resedinta_localitate:  # daca localitatea resedinta difera
            # nu conteaza daca e un judet nou sau e acelasi
            id_judet = locatii_utils.get_id_judet(self.db, formular.resedinta_judet)  # cauta sau insereaza judetul nou

            id_localitate = locatii_utils.get_id_localitate(self.db, formular.resedinta_localitate,
                                                            id_judet)  # cauta/ins
            coloane_noi.append('id_localitate_resedinta')  # va trebui schimbat si in Donator
            valori_noi.append(id_localitate)
        if donator_info['resedinta_adresa'] != formular.resedinta_adresa:  # daca difera adresa
            coloane_noi.append('adresa_resedinta')
            valori_noi.append(formular.resedinta_adresa)

        # 2.2 Fa modificarile efectiv
        if coloane_noi:
            self.db.update("Donator", columns_where=["id_user"], values_where=[user_id], columns=coloane_noi,
                           values=valori_noi)

        return 0, "Formular inregistrat cu succes"


    def __insert_formular_user(self, formular, user):
        '''
        Insereaza datele din formular in BD pentru un user deja existent
        :param formular: Model.FormularDonare
        :param user: Utils.orm.User
        :return:
        '''


