import datetime

def data_nasterii_din_cnp(cnp):
    '''
    Extrage data nasterii din CNP
    Arunca ValueError daca rezulta o data invalida
    :param cnp:
    :return: datetime.datetime
    '''
    # -yymmdd---
    year = int(cnp[1:3]) + 2000
    if year > datetime.datetime.now().year:
        year -= 100
    month = cnp[3:5]
    day = cnp[5:7]
    date = datetime.datetime.strptime("{0}-{1}-{2}".format(str(year), month, day), "%Y-%m-%d")
    return date

def get_info_donator(db, user, donator = None):
    '''
    Returneaza toate informatiile despre donator, inclusiv cele din alte tabele(localitate, judet...)
    :param db: Utils.orm.ORM - Baza de date care va fi interogata
    :param donator: Utils.orm.Donator - Donatorul al carui informatie va fi cautata
    :param user: Utils.orm.User - Intrarea din tabelul de baza 'User'
    :return: Un dictionar cu: username, nume, prenume, cnp, telefon, domiciliu_localitate, domiciliu_judet,
                domiciliu_adresa, resedinta_localitate, resedinta_judet, resedinta_adresa
    '''

    if donator is None:
        donator = db.select("Donator", ["id_user"], [user.id], True)

    if donator is None: #daca inca e None, nu exista inregistrarea
        raise ValueError("Userul nu apare in tabela de donatori")

    #id_domiciliu te duce la o localitate care apartine de un judet
    #cauta localitatea
    localitate_domiciliu = db.select('Localitate', ['id'], [donator.id_domiciliu], True)
    judet_domiciliu = db.select('Judet', ['id'], [localitate_domiciliu.id_judet], True)

    #la fel pentru resedinta
    localitate_resedinta = db.select('Localitate', ['id'], [donator.id_localitate_resedinta], True)
    judet_resedinta = db.select('Judet', ['id'], [localitate_resedinta.id_judet], True)

    return {'nume': donator.nume,
            'prenume': donator.prenume,
            'cnp': donator.cnp,
            'telefon': donator.telefon,
            'domiciliu_localitate': localitate_domiciliu.nume,
            'domiciliu_judet': judet_domiciliu.nume,
            'domiciliu_adresa': donator.adresa_domiciliu,
            'resedinta_localitate': localitate_resedinta.nume,
            'resedinta_judet': judet_resedinta.nume,
            'resedinta_adresa': donator.adresa_resedinta,
            'sex': donator.sex}


def get_info_medic(db, user, medic = None):
    '''
    Nume, prenume, ID locatie
    :param db:
    :param user:
    :param medic:
    :return:
    '''
    if medic is None:
        medic = db.select("Medic", ["id_user"], [user.id])

    if medic is None: #daca inca e None, nu exista inregistrarea
        raise ValueError("Userul nu apare in tabela de medici")

    locatie = db.select("Locatie", ["id"], [medic.id_locatie], True)

    return {"nume": medic.nume,
            "prenume": medic.prenume,
            "id_locatie": medic.id_locatie,
            "nume_locatie": locatie.nume}


def get_info_staff(db, user, staff = None):
    '''
    Nume, prenume, ID locatie
    :param db:
    :param user:
    :param staff:
    :return:
    '''
    if staff is None:
        staff = db.select("StaffTransfuzii", ["id_user"], [user.id])

    if staff is None: #daca inca e None, nu exista inregistrarea
        raise ValueError("Userul nu apare in tabela de StaffTransfuzii")

    locatie = db.select("Locatie", ["id"], [staff.id_locatie], True)


    return {"nume": staff.nume,
            "prenume": staff.prenume,
            "id_locatie": staff.id_locatie,
            "nume_locatie": locatie.nume}
