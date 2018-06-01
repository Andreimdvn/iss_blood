from Repository.i_repository import IRepository


class RepositorySangeBrut(IRepository):
    def __init__(self, db):
        super().__init__(db)

    def insert(self, sange_brut):

        table_name = 'SangeBrut'

        sange_brut.status = "Recoltata"
        specific_vals = [sange_brut.id_donator,
                         sange_brut.id_locatie_recoltare,
                         sange_brut.data_recoltare,
                         sange_brut.status,
                         sange_brut.grupa,
                         sange_brut.rh,
                         sange_brut.id_locatie_curenta]

        specific_col_names = ['id_donator', 'id_locatie_recoltare', 'data_recoltare',
                              'status', 'grupa', 'rh', 'id_locatie_curenta']

        try:
            self.db.insert(table_name, specific_col_names, specific_vals)
        except...:
            return 2, "Database error"

        return 0, "Added successfully"

    def update(self,sange_brut):
        table_name = 'SangeBrut'

        specific_vals = [sange_brut.id_donator,
                         sange_brut.id_locatie_recoltare,
                         sange_brut.data_recoltare,
                         sange_brut.status,
                         sange_brut.grupa,
                         sange_brut.rh,
                         sange_brut.id_locatie_curenta]

        specific_col_names = ['id_donator', 'id_locatie_recoltare', 'data_recoltare',
                              'status', 'grupa', 'rh', 'id_locatie_curenta']

        columns_where = ['id']
        values_where = [sange_brut.id]

        try:
            self.db.update(table_name, columns_where=columns_where,
                           values_where=values_where, columns=specific_col_names,
                           values=specific_vals)
        except...:
            return 2, "Database error"

        return 0, "Updated successfully"

    def get_first_element(self, id_donator, status):
        """
        Get first element based on id_donator and status
        :param id_donator:
        :param status:
        :return:
        """
        specific_col_names = ['id_donator', 'status']
        cols_values = [id_donator, status]
        sange_brut = self.db.select('SangeBrut', columns=specific_col_names, values=cols_values, first=True)
        return sange_brut

    def get_count_for_location_and_date(self, id_locatie, date):
        pungi_sange_brut = self.db.select('SangeBrut',
                                          columns=["id_locatie_recoltare", "data_recoltare"],
                                          values=[id_locatie, date])

        if not pungi_sange_brut:
            return_value = 0
        else:
            return_value = len(pungi_sange_brut)

        self.logger.debug("Am gasit {} pungi de sange pentru locatia {} recoltate in data {}".format
                          (return_value, id_locatie, date))
        return return_value
