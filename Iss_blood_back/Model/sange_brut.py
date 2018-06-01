class SangeBrut:
    def __init__(self, id_donator, id_locatie_recoltare, data_recoltare, status, grupa,
                 rh,id_locatie_curenta, staff_full_name, id=-1):
        self.staff_full_name = staff_full_name
        self.id = id
        self.id_donator = id_donator
        self.id_locatie_recoltare = id_locatie_recoltare
        self.data_recoltare = data_recoltare
        self.status = status
        self.grupa = grupa
        self.rh = rh
        self.id_locatie_curenta = id_locatie_curenta
