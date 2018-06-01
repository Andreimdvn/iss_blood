from Model.cerere_sange import CerereSange
from Repository.repository_manager import RepoManager
from Service.i_service import IService
from Utils.orm import ORM


class ServiceMedic(IService):
    def __init__(self, repo_manager : RepoManager, db: ORM):
        super().__init__(repo_manager, db)

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
