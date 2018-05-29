from Repository.i_repository import IRepository


class RepositoryAnaliza(IRepository):
    def __init__(self, db):
        super().__init__(db)

    def insert(self, analiza):

        table_name = 'Analize'

        specific_vals = [analiza.id_sange_brut, analiza.alt, analiza.sif, analiza.antihtlv, analiza.antihtcv,
                         analiza.antihiv, analiza.hb]

        specific_col_names = ['id_sange_brut', 'alt', 'sif', 'antihtlv', 'antihtcv', 'antihiv', 'hb']

        try:
            self.db.insert(table_name, specific_col_names, specific_vals)
        except...:
            return 2, "Database error"

        return 0, "Added successfully"

    def get_analize(self, id_sange_brut):
        """
        Get first element based on id_donator and status
        :param id_donator:
        :param status:
        :return:
        """
        specific_col_names = ['id_sange_brut']
        cols_values = [id_sange_brut]
        analiza = self.db.select('SangeBrut', columns=specific_col_names, values=cols_values)
        return analiza
