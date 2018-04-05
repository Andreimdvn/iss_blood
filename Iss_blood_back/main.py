import argparse
import os
import json

from Communication.FlaskServer import FlaskServer


def parse_input_file(config_file):
    flask_config = {}
    db_config = {}

    with open(config_file) as fin:
        j = json.loads(fin.read())
        for key in j.keys():
            if "mysql" in key:
                db_config[key] = j[key]
            elif "flask" in key:
                flask_config[key] = j[key]
            else:
                print("Invalid Json key '{}' in config file.".format(key))

    return flask_config, db_config


def init_flask_server(flask_config):
    flask = FlaskServer(flask_config)
    flask.run()

def main():


    parser = argparse.ArgumentParser(description="ISS Blood Server")
    parser.add_argument('-c', metavar='--config_file', type=str,
                        help='Config file for flask server and database connection')
    args = parser.parse_args()

    if not args.c:
        parser.print_help()
        return

    config_file = args.c

    # init_loger()

    if not os.path.exists(config_file):
        print("Error: Path '{}' does not exist".format(config_file))
        return

    flask_config, db_config = parse_input_file(config_file)

    print(flask_config)
    print(db_config)

    init_flask_server(flask_config)

# run with:  py -3 main.py -c config.json
if __name__ == "__main__":
    main()
