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

    def update_cerere(self, id_cerere, status):
        table_name = 'CereriSange'
        specific_vals = [status]

        specific_col_names = ['status']

        columns_where = ['id']
        values_where = [id_cerere]

        try:
            self.db.update(table_name, columns_where=columns_where,
                           values_where=values_where, columns=specific_col_names,
                           values=specific_vals)
        except...:
            return 2, "Database error"

        return 0, "Updated successfully"

    def get_all_by_id_status(self, id_locatie, status, from_spital):

        specific_cols = ['status']
        cereri_sange = self.db.select('CereriSange', specific_cols, values=[status])

        lista_returnata = []

        for cerere in cereri_sange:
            id_medic = cerere.id_medic
            medic = self.get_medic(id_medic)
            comp1 = medic.id_locatie
            comp2 = id_locatie

            if not from_spital:
                locatie_centru = self.db.select('Locatie',['id'], [id_locatie], first=True)
                comp1 = self.db.select('Localitate', ['id'],
                                       [locatie_centru.id_localitate], first=True).id_judet

                locatie_medic = self.db.select('Locatie', ['id'], [medic.id_locatie], first=True)
                comp2 = self.db.select('Localitate', ['id'],
                                       [locatie_medic.id_localitate], first=True).id_judet

            if comp1 == comp2:
                pacient = self.get_pacient(cerere.id_pacient)
                spital = self.db.select('Locatie', ['id'], [id_locatie], first=True)

                dictionar = {"id": cerere.id,
                             "numar_pungi_trombocite": cerere.numar_pungi_trombocite,
                             "numar_pungi_globule_rosii": cerere.numar_pungi_globule_rosii,
                             "numar_pungi_plasma": cerere.numar_pungi_plasma,
                             "grupa": cerere.grupa_sange,
                             "rh": cerere.rh,
                             "nume_pacient":pacient.nume,
                             "data": str(cerere.date),
                             "importanta": cerere.importanta,
                             "nume_medic": medic.nume + ' ' + medic.prenume,
                             "spital": spital.nume,
                             "cnp_pacient": pacient.cnp,
                             "status": cerere.status
                             }
                lista_returnata.append(dictionar)

        return lista_returnata

    def get_medic(self,id_medic):
        return self.db.select('Medic', ['id_user'], [id_medic], first=True)

    def get_pacient(self,id_pacient):
        return self.db.select('Pacient', ['id'], [id_pacient], first=True)

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
