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

    def get_analize(self, cnp):

        id_donator = self.db.select('Donator', columns=['cnp'], values=[cnp], first=True).id_donator
        recoltari = self.db.select('SangeBrut', columns=['id_donator'], values=[id_donator])

        analize = []

        for recoltare in recoltari:
            analiza = self.get_analiza(recoltare.id_sange_brut)
            dict_analiza = {
                "id": analiza.id,
                "alt": self.get_true(analiza.alt),
                "sif": self.get_true(analiza.sif),
                "htcv": self.get_true(analiza.antihtcv),
                "htlv": self.get_true(analiza.antihtlv),
                "hiv": self.get_true(analiza.antihiv),
                "hb": self.get_true(analiza.hb)
            }
            analize.append(dict_analiza)

        return analize

    def get_true(self,numar):
        return numar == 1

    def get_analiza(self,id_sange_brut):
        return self.db.select('Analize', columns=['id_sange_brut'], values=[id_sange_brut], first=True)
