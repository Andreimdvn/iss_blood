from Service.i_service import IService
from Utils import user_utils, locatii_utils


class ServiceTransfuzie(IService):
    def __init__(self, repo_manager, db):
        super().__init__(repo_manager, db)

    def get_cereri(self,id_locatie):
        """
        Get all pula lui Ciprian
        """
        lista = self.repo_manager.repo_formular_donare.get_all(id_locatie)
        for x in lista:
            self.logger.debug(x)

        return lista
