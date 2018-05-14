import datetime


def data_nasterii_din_cnp(cnp):
    '''
    Extrage data nasterii din CNP
    Arunca ValueError daca rezulta o data invalida
    :param cnp:
    :return: datetime.datetime
    '''
    # -yymmdd---
    year = int(cnp[1:3]) + 2000
    if year > datetime.datetime.now().year:
        year -= 100
    month = cnp[3:5]
    day = cnp[5:7]
    date = datetime.datetime.strptime("{0}-{1}-{2}".format(str(year), month, day), "%Y-%m-%d")
    return date