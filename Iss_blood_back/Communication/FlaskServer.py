from flask import Flask, request


class FlaskServer:
    app = Flask(__name__)

    def __init__(self, config_data):
        self.port = config_data["flask_http_port"]
        self.host = '0.0.0.0'
        self.request_data = {}

        self.init_requests()

    def run(self):
        self.app.run(self.host, self.port)

    def init_requests(self):
        self.app.add_url_rule("/test", "test_request", self.test_request, methods=["GET", "POST"])

    def test_request(self):
        print("inside test")
        self.request_data = request.get_json()
        print(self.request_data)
        return "ok"