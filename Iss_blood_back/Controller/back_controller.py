from Repository.repository_manager import RepoManager
from Service.service_common import ServiceCommon
from Service.service_donator import ServiceDonator
from Service.service_medic import ServiceMedic
from Service.service_sange import ServiceSange

from Service.service_staff_transfuzie import ServiceStaffTransfuzie
from Utils.orm import ORM


class BackController:
    def __init__(self, db_config):
        self.repo_manager = None
        orm = ORM(db_config)
        self.repo_manager = RepoManager(orm)
        self.service_common = ServiceCommon(self.repo_manager, orm)
        self.service_donator = ServiceDonator(self.repo_manager, orm)
        self.service_medic = ServiceMedic(self.repo_manager, orm)
        self.service_transfuzie = ServiceStaffTransfuzie(self.repo_manager, orm)
        self.service_sange = ServiceSange(self.repo_manager, orm)
        self.service_administrator = None

    def login(self, user, password):
        return self.service_common.login(user, password)

    def register(self, register_info):
        return self.service_common.register(register_info)

    def user_trimite_formular(self, formular, username):
        return self.service_donator.insert_formular_user(formular, username)

    def staff_trimite_formular(self, formular):
        return self.service_transfuzie.insert_formular_staff(formular)

    def staff_cerere_formulare_donari(self,id_locatie):
        return self.service_transfuzie.get_cereri(id_locatie)

    def staff_update_formular_donare(self, formular_donare, id_locatie, analiza=None, staff_full_name=None):
        self.manage_request(formular_donare, id_locatie, analiza=analiza, staff_full_name=staff_full_name)
        return self.service_transfuzie.update_formular(formular_donare)

    def create_sange_brut(self, id_donator, id_locatie, staff_full_name):
        self.service_sange.create_sange_brut(id_donator, id_locatie, staff_full_name)

    def create_sange_prelucrat(self,id_donator):
        self.service_sange.create_sange_prelucrat(id_donator)

    def create_analiza(self, id_donator,grupa, rh, alt, sif, antihtlv, antihtcv, antihiv, hb, id_formular):
        self.service_sange.create_analiza(id_donator, grupa, rh, alt, sif, antihtlv, antihtcv, antihiv, hb, id_formular)

    def get_stoc_curent(self,id_locatie):
        return self.service_sange.get_stoc_curent(id_locatie)

    def send_pungi(self, id_locatie_curenta, id_locatie_noua, grupa, rh, plasma, tromobocite, globule_rosii):

        stoc_curent = self.get_stoc_curent(id_locatie_curenta)

        stoc_specific = stoc_curent[str(grupa).upper()+'_'+str(rh).lower()]

        numar_plasma = stoc_specific["Plasma"]
        numar_trombocite = stoc_specific["Trombocite"]
        numar_globule = stoc_specific["Globule_rosii"]

        if numar_plasma - plasma >= 0 and numar_trombocite - tromobocite >= 0 and numar_globule - globule_rosii >= 0:
            self.service_sange.send_pungi(
                id_locatie_curenta, id_locatie_noua, grupa, rh, plasma, tromobocite, globule_rosii)
            return 0, "Pungile au fost trimise"

        return 2, "Mesaj dragut de eroare"

    def get_analize(self, cnp):
        return self.service_sange.get_analize(cnp)

    def manage_request(self, formular_donare, id_locatie, analiza=None, staff_full_name=None):
        status = formular_donare.status
        id_donator = self.get_id_donator(formular_donare)

        if status.upper() == 'PRELEVARE':
            self.create_sange_brut(id_donator, id_locatie, staff_full_name)
        elif status.upper() == 'PREGATIRE':
            self.create_sange_prelucrat(id_donator)
        elif analiza is not None:
            alt = analiza.alt
            sif = analiza.sif
            antihtlv = analiza.antihtlv
            antihtcv = analiza.antihtcv
            antihiv = analiza.antihiv
            hb = analiza.hb
            grupa = formular_donare.grupa
            rh = formular_donare.rh
            self.create_analiza(id_donator, grupa, rh, alt, sif, antihtlv, antihtcv, antihiv, hb, formular_donare.id)

    def get_id_donator(self, formular_donare):
        return self.service_transfuzie.get_id_donator(formular_donare)

    def trimite_cerere_sange(self, cerere, cnp_medic):
        return self.service_medic.trimite_cerere_sange(cerere, cnp_medic)

    def get_istoric_donari(self, username):
        return self.service_donator.get_istoric_donari(username)
