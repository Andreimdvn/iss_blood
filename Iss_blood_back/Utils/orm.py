#!/usr/bin/env python3

from sqlalchemy import MetaData, create_engine

from Utils import libsql
from Utils.model import MODEL


class ORM:

    def __init__(self, config_file):
        self.__conf = libsql.load_config(config_file)
        con_string = libsql.MYSQL_CON_STRING % (self.__conf['username'], self.__conf['password'], self.__conf['host'],
                                                self.__conf['database'])

        # create engine for ORM
        self.eng = create_engine(con_string)

        # create metadata object to map all tables from the engine
        self.metadata = MetaData(self.eng)
        # get in metadata object all tables from engine
        self.metadata.reflect()

        self.model = MODEL(self.metadata)

    def select(self, table, columns=None):
        """
        Execute a query for all the fields in a table and returnes the results.
        :param table: table name to be queried.
        :param columns: list with required columns.
        :return: a tuple for each row in table. e.g. (field1, field2, ...)
        """
        tb = getattr(self.model, table)
        if not columns:
            return tb.select().execute().fetchall()
        else:
            rows = []
            for r in tb.select().execute():
                rows.append(tuple(r[col] for col in columns))
            return rows
