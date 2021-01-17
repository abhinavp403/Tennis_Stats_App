import csv
from os.path import dirname, join

def isPlayerExistMale(name):
    with open(join(dirname(__file__), 'atp_players.csv'), mode='r') as csv_file:
        csv_reader = csv.DictReader(csv_file)
        name = name.split(" ")
        if len(name) == 1:
            return False
        first = name[0]
        last = name[1]
        for row in csv_reader:
            if first == row["firstname"] and last == row["lastname"]:
                return True
    return False


def isPlayerExistFemale(name):
    with open(join(dirname(__file__), 'wta_players.csv'), mode='r') as csv_file:
        csv_reader = csv.DictReader(csv_file)
        name = name.split(" ")
        if len(name) == 1:
            return False
        first = name[0]
        last = name[1]
        for row in csv_reader:
            if first == row["firstname"] and last == row["lastname"]:
                return True
    return False