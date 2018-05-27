from Repository.repository_manager import RepoManager
from Service.service_common import ServiceCommon
from Service.service_donator import ServiceDonator
from Service.service_medic import ServiceMedic
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

        self.service_administrator = None

    def login(self, user, password):
        return self.service_common.login(user, password)

    def register(self, register_info):
        return self.service_common.register(register_info)

    def user_trimite_formular(self, formular, username):
        return self.service_donator.insert_formular_user(formular, username)

    def staff_trimite_formular(self, formular):
        return self.service_transfuzie.insert_formular_staff(formular)
