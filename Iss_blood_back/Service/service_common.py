from Model.AccountType import AccountType
from Service.i_service import IService
from Utils.orm import User


class ServiceCommon(IService):
    def __init__(self, repo_manager, db):
        super().__init__(repo_manager, db)

    def login(self, username, password):
        return 0

    def register(self, register_info):
        '''
        Adds a new user to the database
        :param register_info: RegisterInfo;
        :return: Tuple<int, string> = (status code, status message); status code = 0 on success or >= 1 otherwise
        '''


        # 0. Verifica daca exista deja userul. Username si email trebuie sa fie unice
        duplicate_user = self.db.select('User', ['username'], [register_info.username])
        if len(duplicate_user) != 0:
            return 1, "Username already taken"
        duplicate_user = self.db.select('User', ['email'], [register_info.email])
        if len(duplicate_user) != 0:
            return 1, "email already in use"

        # 1. Creeaza obiectul de baza - User
        new_user_object = User()
        new_user_object.username = register_info.username
        new_user_object.password = register_info.password
        new_user_object.email = register_info.email

        # 2. Vezi daca exista judetul in BD sau trebuie adaugat
        judet = self.db.select('Judet', ['nume'], [register_info.judet], True)
        if judet is None:
            self.db.insert('Judet', ['nume'], [register_info.judet])
            judet = self.db.select('Judet', ['nume'], [register_info.judet], True)
        id_judet = judet.id

        # 3. La fel pentru localitate
        localitate = self.db.select('Localitate', ['nume', 'id_judet'], [register_info.localitate, id_judet], True)
        if localitate is None:  # trebuie adaugata
            self.db.insert('Localitate', ['nume', 'id_judet'], [register_info.localitate, id_judet])
            localitate = self.db.select('Localitate', ['nume', 'id_judet'], [register_info.localitate, id_judet], True)
        id_localitate = localitate.id

        # 4. Numele tabelului si coloanele in functie de tipul de cont
        table_name = None
        specific_col_names = []
        specific_vals = []
        if register_info.account_type == AccountType['Donator']:
            table_name = 'Donator'
            specific_col_names = ['prenume', 'nume', 'cnp', 'id_domiciliu', 'adresa_domiciliu', 'data_nasterii',
                                'telefon', 'id_localitate_resedinta', 'adresa_resedinta', 'user']
            specific_vals = [register_info.fullname, register_info.fullname, register_info.cnp, id_localitate, register_info.address, '-',
                             register_info.phone, id_localitate, register_info.address, new_user_object]
        elif register_info.account_type == AccountType['Medic']:
            table_name = 'Medic'
        elif register_info.account_type == AccountType['StaffRecoltare']:
            table_name = 'StaffRecoltare'
        elif register_info.account_type == AccountType['StaffTransfuzie']:
            table_name = 'StaffTransfurzii'
        # else ramane None


        # 5. Adauga in tabel
        try:
            self.db.insert(table_name, specific_col_names, specific_vals)
        except...:
            return 2, "Database error"
        return 0, "Added successfully"
