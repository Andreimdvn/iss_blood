def get_id_judet(db, nume):
    '''
    Cauta ID-ul unui judet. Daca nu exista, il adauga in BD si returneaza ID-ul creat
    :param db: baza de date care va fi interogata
    :param nume: string, numele judetului
    :return: int, ID-ul
    '''
    judet = db.select('Judet', ['nume'], [nume], True)
    if judet is None:  # trebuie adaugat
        db.insert('Judet', ['nume'], [nume])
        judet = db.select('Judet', ['nume'], [nume], True)
    return judet.id


def get_id_localitate(db, nume, id_judet):
    '''
    Cauta ID-ul unei localitati. Daca nu exista, o adauga in BD si returneaza ID-ul creat
    :param db: baza de date care va fi interogata
    :param nume: string, numele localitatii
    :return: int, ID-ul
    '''
    localitate = db.select('Localitate', ['nume', 'id_judet'], [nume, id_judet], True)
    if localitate is None:  # trebuie adaugata
        db.insert('Localitate', ['nume', 'id_judet', 'x_cord', 'y_cord'], [nume, id_judet, 0, 0])
        localitate = db.select('Localitate', ['nume', 'id_judet'], [nume, id_judet], True)
    return localitate.id