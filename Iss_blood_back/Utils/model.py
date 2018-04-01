#!/usr/bin/env python3

from sqlalchemy import Table


class MODEL:

    def __init__(self, metadata):
        for t in metadata.tables:
            setattr(self, t, Table(t, metadata, autoload=True))
        print('[*] All tables where added to the model...')
