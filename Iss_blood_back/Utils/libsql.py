#!/usr/bin/env python3

CONFIG_FIELDS = ('host', 'database', 'username', 'password')
MYSQL_CON_STRING = 'mysql://%s:%s@%s/%s'


def load_config(config_file):
    """
    Load a configuration file with CONFIG_FIELDS.
    :param config_file: location for the configuration file.
    :return: dict with CONFIG_FIELDS as keys.
    """
    d = {}
    with open(config_file) as f:
        for ff in CONFIG_FIELDS:
            ln = f.readline().strip()
            if ln != '':
                d[ff] = ln
            else:
                raise ValueError('[!] Field [%s] not present in [%s]!' % (ff, config_file))
    print('[*] Configuration loaded from [%s]...' % config_file)
    return d
