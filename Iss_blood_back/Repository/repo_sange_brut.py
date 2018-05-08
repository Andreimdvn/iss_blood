from Repository.i_repository import IRepository


class RepositorySangeBrut(IRepository):
    def __init__(self, db):
        super().__init__(db)
