from Repository.i_repository import IRepository


class RepositorySangePrelucrat(IRepository):
    def __init__(self, db):
        super().__init__(db)
