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

def get_info_donator(db, donator, user):
    '''
    Returneaza toate informatiile despre donator, inclusiv cele din alte tabele(localitate, judet...)
    :param db: Utils.orm.ORM - Baza de date care va fi interogata
    :param donator: Utils.orm.Donator - Donatorul al carui informatie va fi cautata
    :param user: Utils.orm.User - Intrarea din tabelul de baza 'User'
    :return: Un dictionar cu: username, nume, prenume, cnp, telefon, domiciliu_localitate, domiciliu_judet,
                domiciliu_adresa, resedinta_localitate, resedinta_judet, resedinta_adresa
    '''

    #id_domiciliu te duce la o localitate care apartine de un judet
    #cauta localitatea
    localitate_domiciliu = db.select('Localitate', ['id'], [donator.id_domiciliu], True)
    judet_domiciliu = db.select('Judet', ['id'], [localitate_domiciliu.id_judet], True)

    #la fel pentru resedinta
    localitate_resedinta = db.select('Localitate', ['id'], [donator.id_localitate_resedinta], True)
    judet_resedinta = db.select('Judet', ['id'], [localitate_resedinta.id_judet], True)

    return {'username': user.username,
            'nume': donator.nume,
            'prenume': donator.prenume,
            'cnp': donator.cnp,
            'telefon': donator.telefon,
            'localitate_domiciliu': localitate_domiciliu.nume,
            'judet_domiciliu': judet_domiciliu.nume,
            'adresa_domiciliu': donator.adresa_domiciliu,
            'localitate_resedinta': localitate_resedinta.nume,
            'judet_resedinta': judet_resedinta.nume,
            'adresa_resedinta': donator.adresa_resedinta}
