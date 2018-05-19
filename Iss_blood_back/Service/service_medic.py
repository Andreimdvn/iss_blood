from Service.i_service import IService


class ServiceMedic(IService):
    def __init__(self, repo_manager, db):
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
        values_to_add = [nume_pacient, cnp_pacient, rh_pacient, grupa_sange_pacient, id_medic]
        #try:
        self.db.insert(table=table_name, columns=columns_to_add, values=values_to_add)
        #except:
         #   return 1, "Database error"

        return  0, "Pacient added successfully"
