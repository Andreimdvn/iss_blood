import logging


class IService:
    def __init__(self, repo_manager, db):
        """
        :param: repo_manager: RepoManager, singletone object that contains all repos
        :param: db: ORM object
        """
        self.repo_manager = repo_manager
        self.db = db
        self.logger = logging.getLogger()
