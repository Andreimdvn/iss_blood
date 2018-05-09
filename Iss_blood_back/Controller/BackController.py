from Repository.repository_manager import RepoManager
from Service.service_common import ServiceCommon
from Service.service_donator import ServiceDonator
from Service.service_medic import ServiceMedic
from Utils.orm import ORM


class BackController:
    def __init__(self):
        self.repo_manager = None
        self.service_common = None
        self.service_donator = None
        self.service_medic = None
        self.service_transfuzie = None
        self.service_administrator = None

    def init_services(self, db_config):
        orm = ORM(db_config)
        self.repo_manager = RepoManager(orm)
        self.service_common = ServiceCommon(self.repo_manager, orm)
        self.service_donator = ServiceDonator(self.repo_manager, orm)
        self.service_medic = ServiceMedic(self.repo_manager, orm)

    def login(self, user, password):
        return self.service_common.login(user, password)

    def register(self, registerInfo):
        return self.service_common.register(registerInfo)
