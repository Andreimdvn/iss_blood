from Service.i_service import IService


class ServiceCommon(IService):
    def __init__(self, repo_manager, db):
        super().__init__(repo_manager, db)

    def login(self, username, password):
        a = self.db.select("User")
        for i in a:
            self.logger.debug(i.username)
        return 0
