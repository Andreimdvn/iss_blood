import logging

from Repository.repository_manager import RepoManager
from Utils.orm import ORM


class IService:
    def __init__(self, repo_manager : RepoManager, db: ORM):
        """
        :param: repo_manager: RepoManager, singletone object that contains all repos
        :param: db: ORM object
        """
        self.repo_manager = repo_manager
        self.db = db
        self.logger = logging.getLogger()
