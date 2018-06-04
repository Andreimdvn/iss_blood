from Service.i_service import IService


class ServiceChat(IService):

    def __init__(self, repo_manager, db):
        super().__init__(repo_manager, db)
        self.active_users = []

    def add_active_user(self, user):
        self.active_users.append(user)

        return 0, "User connected!"

    def remove_user(self, user):
        self.active_users.remove(user)

        return 0, "User disconnected!"

    def add_new_message(self, sender, receiver, message, date):
        self.db.insert('Mesaje', columns=['id_sender', 'id_receiver', 'mesaj', 'data'], values=[sender, receiver,
                                                                                                message, date])

        return 0, "Message sent successfully!"

    def get_active_users(self):
        res = []

        for u in self.active_users:
            res.append((self.__get_username_for_cnp(u), u))

        return 0, res


    def __get_username_for_cnp(self, cnp):
        for u in self.db.select('User'):
            if u.donatori and u.donatori[0].cnp == cnp:
                return u.username
            if u.staff_transfuzii and u.staff_transfuzii[0].cnp == cnp:
                return u.username
            if u.medici and u.medici[0].cnp == cnp:
                return u.username

    def __get_id_for_cnp(self, cnp):
        for u in self.db.select('User'):
            if u.donatori and u.donatori[0].cnp == cnp:
                return u.id
            if u.staff_transfuzii and u.staff_transfuzii[0].cnp == cnp:
                return u.id
            if u.medici and u.medici[0].cnp == cnp:
                return u.id

    def __get_cnp_for_id(self, id):
        for u in self.db.select('User'):
            if u.donatori and u.id == id:
                return u.donatori[0].cnp
            if u.staff_transfuzii and u.id == id:
                return u.staff_transfuzii[0].cnp
            if u.medici and u.id == id:
                return u.medici[0].cnp

    def get_messages_for_user(self, user):
        id_user = self.__get_id_for_cnp(user)
        result = []

        res = self.db.select('Mesaje', columns=['id_sender'], values=[id_user])
        for message in res:
            cnp_sender = self.__get_cnp_for_id(message.id_sender)
            cnp_receiver = self.__get_username_for_cnp(message.id_receiver)

            result.append((cnp_sender, cnp_receiver, message.mesaj, message.data))

        res = self.db.select('Mesaje', columns=['id_receiver'], values=[id_user])
        for message in res:
            cnp_sender = self.__get_cnp_for_id(message.id_sender)
            cnp_receiver = self.__get_username_for_cnp(message.id_receiver)

            result.append((cnp_sender, cnp_receiver, message.mesaj, message.data))

        return 0, result
