import datetime

from Model.account_type import AccountType
from Service.i_service import IService
from Utils.orm import User
from Validators.register_validator import register_validator
from Utils.user_utils import get_info_donator


class ServiceCommon(IService):
    def __init__(self, repo_manager, db):
        super().__init__(repo_manager, db)

    def login(self, username, password):
        """
        Checks the databse for the username and password entry
        :return: int 0(success) 1(error) +
                type of user: 1. donator , 2. medic 3. staff , 4. administrator +
                Model.donator_info pentru donator
        """
        # uita-te mai intai in tabela de useri si dupa cauta dupa foreign key in tabelele specifice
        user = self.db.select("User", ["username", "password"], [username, password], True)
        if user is None:
            return 1, None, None
        else:
            id = user.id
            donator = self.db.select("Donator", ["id_user"], [id], True)
            if donator is not None:
                return 0, 1, get_info_donator(self.db, donator, user)
            medic = self.db.select("Medic", ["id_user"], [id], True)
            if medic is not None:
                return 0, 2
            staff = self.db.select("StaffTransfurzii", ["id_user"], [id], True)
            if staff is not None:
                return 0, 3
            # administrator select

        return 1, None

    def register(self, register_info):
        '''
        Adds a new user to the database
        :param register_info: RegisterInfo;
        :return: Tuple<int, string> = (status code, status message); status code = 0 on success or >= 1 otherwise
        '''

        validator = register_validator()
        valid, msg = validator.validate(register_info)
        if not valid:
            return 1, msg

        use_license_callback = None

        # 1. Daca are licenta, valideaz-o
        if register_info.license != "":
            license_is_valid, message = self.check_license(register_info.account_type.name, register_info.license)
            if not license_is_valid:
                return 1, message

            # licenta e buna, la sfarsit seteaz-o la folosita
            use_license_callback = lambda: self.db.update('Licente', ['tip_licenta', 'cod_licenta'],
                                                          [register_info.account_type.name, register_info.license],
                                                          ['folosita'], [True])

        # 2. Verifica daca exista deja userul. Username si email trebuie sa fie unice
        duplicate_user = self.db.select('User', ['username'], [register_info.username], True)
        if duplicate_user is not None:
            return 1, "Username already taken"
        duplicate_user = self.db.select('User', ['email'], [register_info.email], True)
        if duplicate_user is not None:
            return 1, "email already in use"

        # 3. Creeaza obiectul de baza - User
        new_user_object = User()
        new_user_object.username = register_info.username
        new_user_object.password = register_info.password
        new_user_object.email = register_info.email

        # 4. Vezi daca exista judetul in BD sau trebuie adaugat
        id_judet = self.get_id_judet(register_info.judet)

        # 5. La fel pentru localitate
        id_localitate = self.get_id_localitate(register_info.localitate, id_judet)

        # 6. Numele tabelului si coloanele in functie de tipul de cont
        table_name = None
        specific_col_names = []
        specific_vals = []
        if register_info.account_type == AccountType['Donator']:
            table_name = 'Donator'
            specific_col_names = ['prenume', 'nume', 'cnp', 'id_domiciliu', 'adresa_domiciliu', 'data_nasterii',
                                  'telefon', 'id_localitate_resedinta', 'adresa_resedinta', 'user']
            specific_vals = [register_info.prenume, register_info.nume, register_info.cnp, id_localitate,
                             register_info.address, register_info.data_nasterii,
                             register_info.phone, id_localitate, register_info.address, new_user_object]
        elif register_info.account_type == AccountType['Medic']:
            table_name = 'Medic'
            specific_col_names = ['prenume', 'nume', 'cnp', 'telefon', 'specializare', 'user']  # id_locatie ramane null
            specific_vals = [register_info.prenume, register_info.nume, register_info.cnp, register_info.phone,
                             'pl', new_user_object]
        elif register_info.account_type == AccountType['StaffTransfuzie']:
            table_name = 'StaffTransfurzii'
            specific_col_names = ['prenume', 'nume', 'cnp', 'telefon', 'user']  # id_locatie ramane null
            specific_vals = [register_info.prenume, register_info.nume, register_info.cnp, register_info.phone,
                             new_user_object]
        # else ramane None

        # 7. Adauga in tabel
        try:
            self.db.insert(table_name, specific_col_names, specific_vals)
        except...:
            return 2, "Database error"

        if use_license_callback is not None:
            use_license_callback()

        return 0, "Added successfully"

    def check_license(self, license_type, license_code):
        '''

        :param license_type:
        :param license_code:
        :return: Tuple<bool, string> daca licenta poate fi folosita si un mesaj
        '''
        license_row = self.db.select('Licente', ['tip_licenta', 'cod_licenta'],
                                     [license_type, license_code], True)
        if license_row is None:
            return False, "Licenta nu este valida"
        if license_row.folosita:
            return False, "Licenta este deja folosita"

        return True, "Ok"

    def get_id_judet(self, nume):
        '''
        Cauta ID-ul unui judet. Daca nu exista, il adauga in BD si returneaza ID-ul creat
        :param nume: string, numele judetului
        :return: int, ID-ul
        '''
        judet = self.db.select('Judet', ['nume'], [nume], True)
        if judet is None:  # trebuie adaugat
            self.db.insert('Judet', ['nume'], [nume])
            judet = self.db.select('Judet', ['nume'], [nume], True)
        return judet.id

    def get_id_localitate(self, nume, id_judet):
        '''
        Cauta ID-ul unei localitati. Daca nu exista, o adauga in BD si returneaza ID-ul creat
        :param nume: string, numele localitatii
        :return: int, ID-ul
        '''
        localitate = self.db.select('Localitate', ['nume', 'id_judet'], [nume, id_judet], True)
        if localitate is None:  # trebuie adaugata
            self.db.insert('Localitate', ['nume', 'id_judet'], [nume, id_judet])
            localitate = self.db.select('Localitate', ['nume', 'id_judet'], [nume, id_judet], True)
        return localitate.id
