from Service.i_service import IService


class ServiceMedic(IService):
    def __init__(self, repo_manager, db):
        super().__init__(repo_manager, db)