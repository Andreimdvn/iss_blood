from Model.cerere_sange import CerereSange
from Repository.i_repository import IRepository


class RepositoryCereri(IRepository):
    def __init__(self, db):
        super().__init__(db)

    def add(self, cerere_sange : CerereSange):
        """
        Adds a new blood needed to the database
        :param cerere_sange: CerereSange;
        :return: Tuple<int, string> = (status code, status message); status code = 0 on success or >= 1 otherwise
        """

        table_name = 'CereriSange'
        specific_col_names = ['id_medic', 'id_pacient', 'grupa_sange', 'rh', 'numar_pungi_trombocite',
                              'numar_pungi_globule_rosii', 'numar_pungi_plasma', 'date', 'importanta', 'status']

        id_medic = self.get_id_medic(cerere_sange.cnp_pacient)
        id_pacient = self.get_id_pacient(cerere_sange.cnp_pacient)

        specific_vals = [id_medic, id_pacient, cerere_sange.grupa_sange, cerere_sange.rh,
                         cerere_sange.numar_pungi_trombocite, cerere_sange.numar_pungi_globule_rosii,
                         cerere_sange.numar_pungi_plasma, cerere_sange.data, cerere_sange.importanta,
                         cerere_sange.status]
        try:
            self.db.insert(table_name, specific_col_names, specific_vals)
        except Exception as ex:
            self.logger.error("Error executing insert query : {}".format(ex))
            return 1

        return 0

    def delete(self, cerere_sange):
        """
        Delete a blood needed from the database
        :param cerere_sange: CerereSange;
        :return: Tuple<int, string> = (status code, status message); status code = 0 on success or >= 1 otherwise
        """
        id_pacient = self.get_id_pacient(cerere_sange.nume_pacient)
        try:
            self.db.delete('CereriSange', columns=['id_pacient'], values=[id_pacient])
        except:
            return 2,"Database error"

        return 0, "Deleted successfully"

    def get_all(self):
        """
        Get all blood needed from the database
        :param
        :return: all elements from table
        """
        specific_col_names = ['id_medic', 'id_pacient', 'grupa_sange', 'rh', 'numar_pungi_trombocite',
                              'numar_pungi_globule_rosii', 'numar_pungi_plasma', 'date', 'importanta', 'status']
        try:
            return self.db.select('CereriSange', columns=specific_col_names, values=[])
        except:
            return "Database error"
        
    def get_id_pacient(self, cnp_pacient):
        """
        Cauta ID-ul unui pacient.
        :param nume: string, cnp-ul pacientului
        :return: int, ID-ul
        """
        pacient = self.db.select('Pacient', columns=['cnp'], values=[cnp_pacient], first=True)
        if pacient is None:
            return None
        return pacient.id

    def get_id_medic(self, cnp_pacient):
        """
        Cauta ID-ul unui medicului.
        :param nume: string, cnp-ul pacientului
        :return: int, ID-ul
        """
        pacient = self.db.select('Pacient', columns=['cnp'], values=[cnp_pacient], first=True)
        if pacient is None:
            return None
        return pacient.id_medic
