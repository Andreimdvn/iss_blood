import os
from datetime import datetime

from Utils.orm import ORM
from main import parse_input_file, WORKING_DIRECTORY


def run():
    _, db_configuration = parse_input_file(os.path.join(WORKING_DIRECTORY, 'config.json'))
    orm = ORM(db_configuration)
    orm.drop_databse()
    orm.create_database()
    insert_dumy_data(orm)


def insert_dumy_data(orm):
    # judet
    orm.insert('Judet', columns=('nume',), values=('Judet1',))
    orm.insert('Judet', columns=('nume',), values=('Judet2',))

    # localitati
    orm.insert('Localitate', columns=('id_judet', 'nume', 'x_cord', 'y_cord'), values=(1, 'Localitate1', 46.77, 23.59))
    orm.insert('Localitate', columns=('id_judet', 'nume', 'x_cord', 'y_cord'), values=(2, 'Localitate2', 46.75, 23.39))
    orm.insert('Localitate', columns=('id_judet', 'nume', 'x_cord', 'y_cord'), values=(1, 'Localitate3', 43.06, 23.57))
    orm.insert('Localitate', columns=('id_judet', 'nume', 'x_cord', 'y_cord'), values=(2, 'Localitate4', 46.18, 21.31))

    # locatii
    orm.insert('Locatie', columns=('nume', 'adresa', 'id_localitate', 'tip_locatie'), values=('Spital1', 'Str. Spital1',
                                                                                              1, 'Spital'))
    orm.insert('Locatie', columns=('nume', 'adresa', 'id_localitate', 'tip_locatie'), values=('Spital2', 'Str. Spital2',
                                                                                              2, 'Spital'))
    orm.insert('Locatie', columns=('nume', 'adresa', 'id_localitate', 'tip_locatie'), values=('Spital3', 'Str. Spital3',
                                                                                              1, 'Spital'))
    orm.insert('Locatie', columns=('nume', 'adresa', 'id_localitate', 'tip_locatie'), values=('Recoltare1', 'Str. rec1',
                                                                                              1, 'CentruTransfuzie'))
    orm.insert('Locatie', columns=('nume', 'adresa', 'id_localitate', 'tip_locatie'), values=('Recoltare2', 'Str. rec2',
                                                                                              2, 'CentruTransfuzie'))
    orm.insert('Locatie', columns=('nume', 'adresa', 'id_localitate', 'tip_locatie'), values=('Recoltare3', 'Str. rec3',
                                                                                              3, 'CentruTransfuzie'))

    # medic
    orm.insert('User', columns=('username', 'email', 'password'), values=('medic1', 'medic1@gmail.com', 'medic1'))
    orm.insert('User', columns=('username', 'email', 'password'), values=('medic2', 'medic2@gmail.com', 'medic2'))

    orm.insert('Medic', columns=('id_user', 'id_locatie', 'prenume', 'nume', 'cnp', 'telefon', 'specializare'),
               values=(1, 1, 'PrenumeMedic1', 'NumeMedic1', '1234567891234', '1231231231', 'Oncologie'))
    orm.insert('Medic', columns=('id_user', 'id_locatie', 'prenume', 'nume', 'cnp', 'telefon', 'specializare'),
               values=(2, 2, 'PrenumeMedic2', 'NumeMedic2', '1234567891235', '1231231232', 'Boli infectioase'))

    # donator
    orm.insert('User', columns=('username', 'email', 'password'), values=('donator1', 'donator1@gmail.com', 'donator1'))
    orm.insert('User', columns=('username', 'email', 'password'), values=('donator2', 'donator2@gmail.com', 'donator2'))

    orm.insert('Donator', columns=('id_user', 'nume', 'prenume', 'cnp', 'id_domiciliu', 'adresa_domiciliu',
                                   'data_nasterii', 'telefon', 'id_localitate_resedinta', 'adresa_resedinta'),
               values=(3, 'NumeDonator1', 'PrenumeDonator1', '1234567891236', 1, 'Domiciliu1', datetime(1997, 1, 21),
                       '1231233211', 1, 'Resedinta1'))
    orm.insert('Donator', columns=('id_user', 'nume', 'prenume', 'cnp', 'id_domiciliu', 'adresa_domiciliu',
                                   'data_nasterii', 'telefon', 'id_localitate_resedinta', 'adresa_resedinta'),
               values=(4, 'NumeDonator2', 'PrenumeDonator2', '1234569871236', 3, 'Domiciliu2', datetime(1991, 5, 2),
                       '1221233211', 1, 'Resedinta2'))

    # staff transfurzii
    orm.insert('User', columns=('username', 'email', 'password'), values=('transfuzie1', 'transfuzie1@gmail.com',
                                                                          'transfuzie1'))
    orm.insert('User', columns=('username', 'email', 'password'), values=('transfuzie2', 'transfuzie2@gmail.com',
                                                                          'transfuzie2'))
    orm.insert('StaffTransfuzii', columns=('id_user', 'id_locatie', 'telefon', 'prenume', 'nume', 'cnp'),
               values=(5, 4, '0987654321', 'PrenumeTransfuzie1', 'NumeTransfuzie1', '1122334455667'))
    orm.insert('StaffTransfuzii', columns=('id_user', 'id_locatie', 'telefon', 'prenume', 'nume', 'cnp'),
               values=(6, 6, '0987651234', 'PrenumeTransfuzie2', 'NumeTransfuzie2', '1122734455667'))

    # licente
    orm.insert('Licente', columns=('tip_licenta', 'cod_licenta', 'folosita'), values=('StaffTransfuzie', 'transf1', 0))
    orm.insert('Licente', columns=('tip_licenta', 'cod_licenta', 'folosita'), values=('StaffTransfuzie', 'transf2', 0))
    orm.insert('Licente', columns=('tip_licenta', 'cod_licenta', 'folosita'), values=('StaffTransfuzie', 'transf3', 0))

    orm.insert('Licente', columns=('tip_licenta', 'cod_licenta', 'folosita'), values=('Medic', 'medic1', 0))
    orm.insert('Licente', columns=('tip_licenta', 'cod_licenta', 'folosita'), values=('Medic', 'medic2', 0))
    orm.insert('Licente', columns=('tip_licenta', 'cod_licenta', 'folosita'), values=('Medic', 'Medic3', 0))

    # sange brut


    # analize


if __name__ == '__main__':
    run()
