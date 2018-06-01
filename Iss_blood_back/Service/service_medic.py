from Model.cerere_sange import CerereSange
from Repository.repository_manager import RepoManager
from Service.i_service import IService
from Utils.orm import ORM


class ServiceMedic(IService):
    def __init__(self, repo_manager : RepoManager, db: ORM):
        super().__init__(repo_manager, db)


    def add_pacient(self, id_medic, nume_pacient, cnp_pacient, grupa_sange_pacient, rh_pacient):
        '''
            Add a new pacient to database
        :param id_medic:
        :param nume_pacient:
        :param cnp_pacient:
        :param grupa_sange_pacient:
        :param rh_pacient:
        :return: int 0(succes) 1(error) + a message successfully or error type
        '''

        duplicate_pacient = self.db.select('Pacient', columns=['cnp'], values=[cnp_pacient])
        if duplicate_pacient:
            return 1, "Pacientul exista deja in baza de date"

        table_name = 'Pacient'
        columns_to_add = ['nume', 'cnp', 'rh', 'grupa', 'id_medic']
        id_medic_from_db = self.get_id_medic_for_cnp(id_medic)
        values_to_add = [nume_pacient, cnp_pacient, rh_pacient, grupa_sange_pacient, id_medic_from_db]
        try:
            self.db.insert(table=table_name, columns=columns_to_add, values=values_to_add)
        except:
            return 1, "Database error"

        return  0, "Pacient added successfully"

    def get_id_medic_for_cnp(self, cnp_medic):
        """
            Get id_user of a medic for a given cnp
        :param cnp_medic: represents cnp of medic
        :return:
        """

        id_medic = self.db.select('Medic', ['cnp'], [cnp_medic])

        return id_medic[0].id_user

    def trimite_cerere_sange(self, cerere: CerereSange, cnp_medic):
        """
        Folosind repo_cereri adauga o cerere noua in baza de date
        Daca nu exista pacientul respectiv il creeaza
        :param cerere: CerreSange
        :return: tuple(status: int, message: string)
        0 - success
        1 - error
        """
        added_pacient = False
        pacient = self.db.select("Pacient", ["cnp"], [cerere.cnp_pacient], True)
        if not pacient:
            medic = self.db.select("Medic", ["cnp"], [cnp_medic], True)
            if not medic:
                self.logger.error("Invalid cnp medic {}".format(cnp_medic))
                return 2, "Invalid cnp medic {}".format(cnp_medic)
            self.logger.debug("Adaugare pacient nou: cnp {}, id_medic {}".format(cerere.cnp_pacient, medic.id_user))
            self.db.insert("Pacient", ["nume", "cnp", "rh", "grupa", "id_medic"],
                           [cerere.nume_pacient, cerere.cnp_pacient, cerere.rh, cerere.grupa_sange, medic.id_user])
            added_pacient = True

        status= self.repo_manager.repo_cereri.add(cerere)
        if status:
            return 1, "Database Error"

        if added_pacient:
            return 0, "Cerere adaugata cu succes.\n " \
                      "A fost adaugat si un pacient nou."
        else:
            return 0, "Cerere adaugata cu succes."

