import datetime

from Model.analiza import Analiza
from Model.sange_brut import SangeBrut
from Model.sange_prelucrat import SangePrelucrat
from Service.i_service import IService


class ServiceSange(IService):
    def __init__(self, repo_manager, db):
        super().__init__(repo_manager, db)

    def create_sange_brut(self, id_donator, id_locatie):
        """
        Sangele a fost recoltat =>
            status: recoltata
            rh si grupa unknown deoarece nu s-a facut analiza
        :param id_donator:
        :param id_locatie:
        :return:
        """
        current_date = self.get_current_date()
        status = 'Recoltata'
        rh = 'unknown'
        grupa = 'unknown'
        sange_brut = SangeBrut(id_donator, id_locatie, current_date, status, grupa, rh, id_locatie)
        status = self.repo_manager.repo_sange_brut.insert(sange_brut)

        # to do / check status

    def create_sange_prelucrat(self, id_donator):
        status = 'Recoltata'
        sange_brut = self.repo_manager.repo_sange_brut.get_first_element(id_donator, status)

        status_punga = 'Prelucrat'
        punga_trombocite = SangePrelucrat(sange_brut.id, 'Trombocite', sange_brut.id_locatie_recoltare, status_punga)
        punga_globule = SangePrelucrat(sange_brut.id, 'Globule_rosii', sange_brut.id_locatie_recoltare, status_punga)
        punga_plasma = SangePrelucrat(sange_brut.id, 'Plasma', sange_brut.id_locatie_recoltare, status_punga)

        self.repo_manager.repo_sange_prelucrat.insert(punga_trombocite)
        self.repo_manager.repo_sange_prelucrat.insert(punga_globule)
        self.repo_manager.repo_sange_prelucrat.insert(punga_plasma)

        sange_brut.status = 'Prelucrata'
        self.repo_manager.repo_sange_brut.update(sange_brut)

    def create_analiza(self, id_donator,grupa,rh, alt, sif, antihtlv, antihtcv, antihiv, hb):
        status = 'Prelucrata'
        sange_brut = self.repo_manager.repo_sange_brut.get_first_element(id_donator, status)
        sange_brut.grupa = grupa
        sange_brut.rh = rh
        analiza = Analiza(sange_brut.id, alt, sif, antihtlv, antihtcv, antihiv, hb)

        self.repo_manager.repo_analiza.insert(analiza)

        if self.check_analiza(analiza):
            sange_brut.status = 'Impartita'
            self.update_sange_prelucrat(sange_brut.id, 'Depozitat')
        else:
            sange_brut.status = 'Aruncata'
            self.delete_sange_prelucrat(sange_brut.id)

        self.logger.debug(sange_brut.status)
        self.repo_manager.repo_sange_brut.update(sange_brut)

    def check_analiza(self, analiza):
        return not(analiza.alt or analiza.sif or analiza.antihiv or analiza.antihtlv or analiza.antihtcv or analiza.hb)

    def update_sange_prelucrat(self, id_sange_brut, status):
        self.repo_manager.repo_sange_prelucrat.update_status_by_id_sange_brut(id_sange_brut, status)

    def delete_sange_prelucrat(self, id_sange_brut):
        self.repo_manager.repo_sange_prelucrat.delete(id_sange_brut)

    def get_analize(self, cnp):
        return self.repo_manager.repo_analiza.get_analize(cnp)

    def send_pungi(self, id_locatie_curenta, id_locatie_noua, grupa, rh, plasma, tromobocite, globule_rosii):
        self.repo_manager.repo_sange_prelucrat.\
            send_pungi(id_locatie_curenta, id_locatie_noua, grupa, rh, plasma, tromobocite, globule_rosii)


    def get_stoc_curent(self, id_locatie):
        repo = self.repo_manager.repo_sange_prelucrat
        stoc_curent = {
            'O1_pozitiv': repo.get_stoc_curent_by_grupa_rh(id_locatie, 'O1', 'Pozitiv'),
            'A2_pozitiv': repo.get_stoc_curent_by_grupa_rh(id_locatie, 'A2', 'Pozitiv'),
            'B3_pozitiv': repo.get_stoc_curent_by_grupa_rh(id_locatie, 'B3', 'Pozitiv'),
            'AB4_pozitiv': repo.get_stoc_curent_by_grupa_rh(id_locatie, 'AB4', 'Pozitiv'),
            'O1_negativ': repo.get_stoc_curent_by_grupa_rh(id_locatie, 'O1', 'Negativ'),
            'A2_negativ': repo.get_stoc_curent_by_grupa_rh(id_locatie, 'A2', 'Negativ'),
            'B3_negativ': repo.get_stoc_curent_by_grupa_rh(id_locatie, 'B3', 'Negativ'),
            'AB4_negativ': repo.get_stoc_curent_by_grupa_rh(id_locatie, 'AB4', 'Negativ'),
        }

        self.logger.debug(stoc_curent)

        return stoc_curent

    def get_current_date(self):
        now = datetime.datetime.now()
        return str(now.year)+'-'+str(now.month)+'-'+str(now.day)
