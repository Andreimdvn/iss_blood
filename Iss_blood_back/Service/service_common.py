from Service.i_service import IService


class ServiceCommon(IService):
    def __init__(self, repo_manager, db):
        super().__init__(repo_manager, db)

    def login(self, username, password):
        """
        Checks the databse for the username and password entry
        :return: int 0(success) 1(error)
        """
        lst = self.db.select("User", ["username", "password"], [username, password])
        if len(lst) == 0:
            return 1
        return 0
