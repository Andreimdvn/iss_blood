from Service.i_service import IService
from Utils import user_utils, locatii_utils


class ServiceStaffTransfuzie(IService):
    def __init__(self, repo_manager, db):
        super().__init__(repo_manager, db)

    def get_id_donator(self, formular):
        return self.repo_manager.repo_formular_donare.db.select(
            'FormularDonare', ['id'], [formular.id], first=True).id_donator

    def insert_formular_staff(self, formular):
        '''
        Insereaza datele din formular in BD pentru o persoana care nu are cont
        :param formular: Model.FormularDonare
        :return: status, message
        '''
        # 1. Insereaza un donator nou

        # 1.1 Vezi cu resedinta si domiciliul
        id_judet_domiciliu = locatii_utils.get_id_judet(self.db, formular.domiciliu_judet)
        id_localitate_domiciliu = locatii_utils.get_id_localitate(self.db, formular.domiciliu_localitate,
                                                                  id_judet_domiciliu)

        id_judet_resedinta = locatii_utils.get_id_judet(self.db, formular.resedinta_judet)
        id_localitate_resedinta = locatii_utils.get_id_localitate(self.db, formular.resedinta_localitate,
                                                                  id_judet_resedinta)

        # resedinta, domiciliu, telefon, sex, nume, prenume
        nume_coloane = ['nume', 'prenume', 'sex', 'telefon', 'id_domiciliu', 'adresa_domiciliu',
                        'id_localitate_resedinta', 'adresa_resedinta']
        valori_coloane = [formular.nume, formular.prenume, formular.sex, formular.telefon, id_localitate_domiciliu,
                          formular.domiciliu_adresa, id_localitate_resedinta, formular.resedinta_adresa]

        self.db.insert('Donator', nume_coloane, valori_coloane)

        donator = self.db.select('Donator', ['nume', 'prenume', 'telefon'],
                                 [formular.nume, formular.prenume, formular.telefon], True)

        # 2. Insereaza un formular nou cu foreign key catre donator
        user_utils.insert_formular(self.db, formular, donator.id_donator)

        return 0, "Formular inregistrat cu succes"

    def get_cereri(self, id_locatie):
        lista = self.repo_manager.repo_formular_donare.get_all(id_locatie)
        return lista

    def update_formular(self, formular_donare):
        return self.repo_manager.repo_formular_donare.update(formular_donare)
