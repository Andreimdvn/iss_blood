import logging


class IRepository:
    def __init__(self, db):
        """
        :param db: Orm
        """
        self.db = db
        self.logger = logging.getLogger()

