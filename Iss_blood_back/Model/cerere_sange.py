from Model.status_cerere_sange import StatusCerereSange


class CerereSange:
    def __init__(self, nume_pacient, cnp_pacient, grupa_sange, rh, numar_pungi_trombocite, numar_pungi_globule_rosii,
                 numar_pungi_plasma, data, importanta, status = StatusCerereSange.in_asteptare.name):
        self.nume_pacient = nume_pacient
        self.cnp_pacient = cnp_pacient
        self.grupa_sange = grupa_sange
        self.rh = rh
        self.numar_pungi_trombocite = numar_pungi_trombocite
        self.numar_pungi_globule_rosii = numar_pungi_globule_rosii
        self.numar_pungi_plasma = numar_pungi_plasma
        self.data = data
        self.importanta = importanta
        self.status = status