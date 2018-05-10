import sys

from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String, ForeignKey, Date, SmallInteger, Enum, Float, create_engine, Boolean
from sqlalchemy.orm import relationship, sessionmaker


MYSQL_CON_STRING = 'mysql://%s:%s@%s/%s'

DB = declarative_base()


class User(DB):
    __tablename__ = 'User'

    id = Column(Integer, autoincrement=True, primary_key=True)
    username = Column(String(100), nullable=False, unique=True)
    email = Column(String(100), nullable=False, unique=True)
    password = Column(String(100), nullable=False)

    donatori = relationship('Donator', back_populates='user')
    staff_transfuzii = relationship('StaffTransfurzii', back_populates='user')
    staff_recoltare = relationship('StaffRecoltare', back_populates='user')
    medici = relationship('Medic', back_populates='user')


class Judet(DB):
    __tablename__ = 'Judet'

    id = Column(Integer, autoincrement=True, primary_key=True)
    nume = Column(String(50), nullable=False)

    localitati = relationship('Localitate', back_populates='judet')


class Localitate(DB):
    __tablename__ = 'Localitate'

    id = Column(Integer, autoincrement=True, primary_key=True)
    id_judet = Column(Integer, ForeignKey('Judet.id'))
    nume = Column(String(50), nullable=False)

    judet = relationship('Judet', back_populates='localitati')
    locatii = relationship('Locatie', back_populates='localitate')


class Donator(DB):
    __tablename__ = 'Donator'

    id_user = Column(Integer, ForeignKey('User.id'), primary_key=True)
    prenume = Column(String(50), nullable=False)
    nume = Column(String(50), nullable=False)
    cnp = Column(String(13), nullable=False)
    id_domiciliu = Column(Integer, ForeignKey('Localitate.id'))
    adresa_domiciliu = Column(String(100), nullable=False)
    data_nasterii = Column(Date, nullable=False)
    telefon = Column(String(20), nullable=False)
    id_localitate_resedinta = Column(Integer, ForeignKey('Localitate.id'))
    adresa_resedinta = Column(String(100), nullable=False)

    user = relationship('User', back_populates='donatori')
    sange_brut = relationship('SangeBrut', back_populates='donator')


class Locatie(DB):
    __tablename__ = 'Locatie'

    id = Column(Integer, autoincrement=True, primary_key=True)
    nume = Column(String(50), nullable=False)
    adresa = Column(String(100), nullable=False)
    id_localitate = Column(Integer, ForeignKey('Localitate.id'))
    tip_locatie = Column(SmallInteger, nullable=False)

    localitate = relationship('Localitate', back_populates='locatii')
    staff_transfuzii = relationship('StaffTransfurzii', back_populates='locatie')
    staff_recoltare = relationship('StaffRecoltare', back_populates='locatie')
    medici = relationship('Medic', back_populates='locatie')
    sange_prelucrat = relationship('SangePrelucrat', back_populates='locatie')


class StaffTransfurzii(DB):
    __tablename__ = 'StaffTransfurzii'

    id_user = Column(Integer, ForeignKey('User.id'), primary_key=True)
    id_locatie = Column(Integer, ForeignKey('Locatie.id'))
    telefon = Column(String(20), nullable=False)
    prenume = Column(String(50), nullable=False)
    nume = Column(String(50), nullable=False)
    cnp = Column(String(13), nullable=False)

    user = relationship('User', back_populates='staff_transfuzii')
    locatie = relationship('Locatie', back_populates='staff_transfuzii')
    analize = relationship('Analize', back_populates='staff_transfuzii')


class StaffRecoltare(DB):
    __tablename__ = 'StaffRecoltare'

    id_user = Column(Integer, ForeignKey('User.id'), primary_key=True)
    id_locatie = Column(Integer, ForeignKey('Locatie.id'))
    prenume = Column(String(50), nullable=False)
    nume = Column(String(50), nullable=False)
    cnp = Column(String(13), nullable=False)

    user = relationship('User', back_populates='staff_recoltare')
    locatie = relationship('Locatie', back_populates='staff_recoltare')


class Medic(DB):
    __tablename__ = 'Medic'

    id_user = Column(Integer, ForeignKey('User.id'), primary_key=True)
    id_locatie = Column(Integer, ForeignKey('Locatie.id'))
    prenume = Column(String(50), nullable=False)
    nume = Column(String(50), nullable=False)
    cnp = Column(String(13), nullable=False)
    telefon = Column(String(20), nullable=False)
    specializare = Column(String(50), nullable=False)

    user = relationship('User', back_populates='medici')
    locatie = relationship('Locatie', back_populates='medici')

class Licente(DB):
    __tablename__ = 'Licente'

    id = Column(Integer, autoincrement=True, primary_key=True)
    tip_licenta = Column(Enum('StaffTransfuzie', 'Medic'), nullable=False)
    cod_licenta = Column(String(20), nullable=False)
    folosita = Column(Boolean, nullable=False)

class Analize(DB):
    __tablename__ = 'Analize'

    id = Column(Integer, autoincrement=True, primary_key=True)
    id_staff_transfuzii = Column(Integer, ForeignKey('StaffTransfurzii.id_user'))

    staff_transfuzii = relationship('StaffTransfurzii', back_populates='analize')
    sange_brut = relationship('SangeBrut', back_populates='analize')


class SangeBrut(DB):
    __tablename__ = 'SangeBrut'

    id = Column(Integer, autoincrement=True, primary_key=True)
    id_donator = Column(Integer, ForeignKey('Donator.id_user'))
    id_locatie_recoltare = Column(Integer, ForeignKey('Locatie.id'))
    data_donare = Column(Date, nullable=False)
    id_analize = Column(Integer, ForeignKey('Analize.id'))
    stadiu = Column(Enum('Recoltata', 'Analizata', 'Impartita', 'Aruncata'), nullable=False)
    id_locatie_curenta = Column(Integer, ForeignKey('Locatie.id'))

    donator = relationship('Donator', back_populates='sange_brut')
    analize = relationship('Analize', back_populates='sange_brut')
    sange_prelucrat = relationship('SangePrelucrat', back_populates='sange_brut')


class SangePrelucrat(DB):
    __tablename__ = 'SangePrelucrat'

    id = Column(Integer, autoincrement=True, primary_key=True)
    id_sange_brut = Column(Integer, ForeignKey('SangeBrut.id'))
    tip = Column(Enum('Plasma', 'Trombocite', 'Globule_rosii'))
    id_locatie = Column(Integer, ForeignKey('Locatie.id'))
    gramaj = Column(Float, nullable=False)
    status = Column(Enum('Depozitat', 'Folosit', 'Expirat'))
    sange_brut = relationship('SangeBrut', back_populates='sange_prelucrat')
    locatie = relationship('Locatie', back_populates='sange_prelucrat')


class ORM:

    def __init__(self, config):
        con_string = MYSQL_CON_STRING % (config['mysql_username'], config['mysql_password'], config['mysql_server'],
                                         config['mysql_database'])

        engine = create_engine(con_string)
        self.session = sessionmaker(bind=engine)
        # DB.metadata.drop_all(engine) # if you want to delete all tables in database
        DB.metadata.create_all(engine)
        self.ses = self.session()

    def columns_objects(self, table, columns):
        cols = []
        for col in columns:
            try:
                c = getattr(table, col)
            except AttributeError:
                c = None
            if not c:
                raise ValueError('[!] Column [%s] not present in table [%s]!' % (col, table))
            cols.append(c)
        return cols

    def table_object(self, table):
        try:
            tb = getattr(sys.modules[__name__], table)
        except AttributeError:
            tb = None
        if not tb:
            raise ValueError('[!] Table [%s] couldn\'t be found!' % table)
        return tb

    def insert(self, table, columns=None, values=None):
        """
        Insert into table, into specified columns values provided.
        :param table:
        :param columns:
        :param values:
        :return:
        """
        if columns:
            if type(columns) not in (list, tuple):
                raise ValueError('[!] Type [%s] for columns are not allowed!' % type(columns))
        if values:
            if type(values) not in (list, tuple):
                raise ValueError('[!] Type [%s] for values are not allowed!' % type(values))
        tb = self.table_object(table)
        if columns:
            cols = self.columns_objects(tb, columns)
            if len(cols) != len(values):
                raise ValueError('[!] Columns and values are not equal!')
            col_val = {e[0]: e[1] for e in zip(columns, values)}
            self.ses.add(tb(**col_val))
            self.ses.commit()
            self.ses.flush()
        else:
            cols = [c.key for c in tb.__table__.columns if c.key != 'id']
            if len(cols) != len(values):
                raise ValueError('[!] Columns and values number are not equal!')
            col_val = {e[0]: e[1] for e in zip(cols, values)}
            self.ses.add(tb(**col_val))
            self.ses.commit()
            self.ses.flush()

    def select(self, table, columns=None, values=None, first=False):
        """
        Execute a query on all rows in a table and returns the results with all the columns.
        :param table: table name to be queried.
        :param columns: list with required columns for the WHERE clause.
        :param values: list with values corresponding to the given columns
        :param first: only the first item is returned.
        :return: if first==False, a list with: objects of table type if no columns were specified, tuples<Column> otherwise.
                 if first==True, a single object is returned instead of a list
        """
        res = None
        if columns:
            if type(columns) not in (list, tuple):
                raise ValueError('[!] Type [%s] for columns are not allowed! Use list or tuple.' % type(columns))
        if values:
            if type(values) not in (list, tuple):
                raise ValueError('[!] Type [%s] for values are not allowed! Use list or tuple.' % type(values))
        tb = self.table_object(table)
        if not columns:
            res = self.ses.query(tb)
        elif not values:
            cols = [getattr(tb, c) for c in columns]
            res = self.ses.query(*cols)
        else:
            cols = [getattr(tb, c) for c in columns]
            if len(cols) != len(values):
                raise ValueError('[!] There are not enough values/columns!')
            col_val = {e[0].key: e[1] for e in zip(cols, values)}
            res = self.ses.query(tb).filter_by(**col_val)
        if first:
            return res.first()
        return res.all()

    def update(self):
        pass

    def delete(self, table, columns=None, values=None):
        """
        Execute a delete on a table with specific columns and values.
        :param table:
        :param columns:
        :param values:
        :return:
        """
        if columns:
            if type(columns) not in (list, tuple):
                raise ValueError('[!] Type [%s] for columns are not allowed!' % type(columns))
            if len(columns) < 1:
                raise ValueError('[!] Specify some columns!')
        else:
            raise ValueError('[!] Specify columns for where clause!')
        if values:
            if type(values) not in (list, tuple):
                raise ValueError('[!] Type [%s] for values are not allowed!' % type(values))
            if len(values) < 1:
                raise ValueError('[!] Specify some values!')
        else:
            raise ValueError('[!] Specify values for where clause!')
        if len(values) != len(columns):
            raise ValueError('[!] There are not enough values/columns!')
        item = self.select(table, columns, values, first=True)
        if not item:
            raise ValueError('[!] Item with specified values doesn\'t exists!')
        self.ses.delete(item)
        self.ses.commit()
        self.ses.flush()
