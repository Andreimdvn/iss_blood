
class FormularDonare:
    def __init__(self, nume, prenume, sex, telefon, domiciliu_localitate, domiciliu_judet, domiciliu_adresa,
                 resedinta_localitate, resedinta_judet, resedinta_adresa, beneficiar_full_name,
                 beneficiar_CNP, grupa, rh, zile_disponibil, status="IN_ASTEPTARE", id=-1):
        self.zile_disponibil = zile_disponibil
        self.rh = rh
        self.grupa = grupa
        self.beneficiar_CNP = beneficiar_CNP
        self.beneficiar_full_name = beneficiar_full_name
        self.resedinta_adresa = resedinta_adresa
        self.resedinta_judet = resedinta_judet
        self.resedinta_localitate = resedinta_localitate
        self.domiciliu_adresa = domiciliu_adresa
        self.domiciliu_judet = domiciliu_judet
        self.domiciliu_localitate = domiciliu_localitate
        self.telefon = telefon
        self.sex = sex
        self.prenume = prenume
        self.nume = nume
        self.status = status
        self.id = id
