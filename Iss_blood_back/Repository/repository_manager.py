from Repository.repo_analiza import RepositoryAnaliza
from Repository.repo_cereri import RepositoryCereri
from Repository.repo_formular_donare import RepositoryFormularDonari
from Repository.repo_sange_brut import RepositorySangeBrut
from Repository.repo_sange_prelucrat import RepositorySangePrelucrat


class Singleton(type):
    _instances = {}

    def __call__(cls, *args, **kwargs):
        if cls not in cls._instances:
            cls._instances[cls] = super(Singleton, cls).__call__(*args, **kwargs)
        return cls._instances[cls]


class RepoManager(metaclass=Singleton):
    def __init__(self, db):
        """
        Singleton class that holds all the available repositories
        :param db: Orm
        """
        self.repo_cereri = RepositoryCereri(db)
        self.repo_analiza = RepositoryAnaliza(db)
        self.repo_sange_brut = RepositorySangeBrut(db)
        self.repo_sange_prelucrat = RepositorySangePrelucrat(db)
        self.repo_formular_donare = RepositoryFormularDonari(db)

    def __get_repository_formular(self):
        return self.repo_formular_donare