from Service.i_service import IService


class ServiceDonator(IService):
    def __init__(self, repo_manager, db):
        super().__init__(repo_manager, db)

    def trimite_formular(self, formular):
        #TO DO: daca datele din formular difera fata de alea din BD, update
        return True
