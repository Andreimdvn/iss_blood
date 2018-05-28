from Repository.i_repository import IRepository


class RepositoryFormularDonari(IRepository):
    def __init__(self, db):
        super().__init__(db)

    def update(self, formular_donare):
        table_name = "FormularDonare"
        coloane_noi = ['beneficiar_full_name', 'beneficiar_CNP', 'grupa',
                              'rh', 'status']
        value_noi = [formular_donare.beneficiar_full_name,
                     formular_donare.beneficiar_CNP,
                     formular_donare.grupa,
                     formular_donare.rh,
                     formular_donare.status]

        coloane_where = ['id']
        value_where = [formular_donare.id]

        try:
            self.db.update(table_name, columns_where=coloane_where,
                         values_where=value_where, columns=coloane_noi, values=value_noi)
        except...:
            return 2, "Database error"

        return 0, "Updated successfuly"

    def get_all(self,id_locatie):
        """
        Get all blood needed from the database
        :param
        :return: all elements from table
        """
        table_name = 'FormularDonare'
        specific_col_names = ['id', 'id_donator', 'beneficiar_full_name', 'beneficiar_CNP', 'grupa',
                              'rh', 'zile_disponibil', 'status']

        localitateCentru = self.get_localitate(id_locatie)
        judetCentruCod = self.get_judet(localitateCentru.id_judet).id
        try:
            rezultat = []

            for formular in self.db.select(table_name, columns=specific_col_names, values=[]):

                donator = self.get_donator(formular.id_donator)
                localitate = self.get_localitate(donator.id_domiciliu)
                judet = self.get_judet(localitate.id_judet)

                self.logger.debug(localitate.id_judet)
                self.logger.debug(judetCentruCod)

                if localitate.id_judet == judetCentruCod:
                    id = formular.id
                    beneficiar_full_name = formular.beneficiar_full_name
                    beneficiar_cnp = formular.beneficiar_CNP
                    grupa = formular.grupa
                    rh = formular.rh
                    status = formular.status
                    nume = donator.nume
                    prenume = donator.prenume
                    sex = donator.sex
                    telefon = donator.telefon
                    domiciliuLocalitate = localitate.nume
                    domiciliuJudet = judet.nume
                    domiciliuAdresa = donator.adresa_domiciliu

                    localitate = self.get_localitate(donator.id_localitate_resedinta)
                    judet = self.get_judet(localitate.id_judet)

                    resedintaLocalitate = localitate.nume
                    resedintaJudet = judet.nume
                    resedintaAdresa = donator.adresa_resedinta

                    rezultat.append(
                        [id, nume, prenume, sex, telefon,
                         domiciliuLocalitate, domiciliuJudet, domiciliuAdresa,
                         resedintaLocalitate, resedintaJudet, resedintaAdresa,
                         beneficiar_full_name, beneficiar_cnp, grupa, rh, status]
                    )
            return rezultat
        except...:
            return "Database error"

    def get_donator(self, id_donator):
        """
        Pula lui Alin
        :param: id_domiciliu:
        :return:
        """
        table_name = 'Donator'
        specific_col_names = ['id_donator']
        values = [id_donator]
        donator = self.db.select(table_name, columns=specific_col_names, values=values, first=True)
        return None if not donator else donator

    def get_localitate(self,id_localitate):
        table_name = 'Localitate'
        specific_col_names = ['id']
        values = [id_localitate]
        return self.db.select(table_name, columns=specific_col_names, values=values, first=True)

    def get_judet(self,id_judet):
        table_name = 'Judet'
        specific_col_names = ['id']
        values = [id_judet]
        return self.db.select(table_name, columns=specific_col_names, values=values, first=True)
