import csv
from os.path import dirname, join

def isPlayerExistMale(name):
    with open(join(dirname(__file__), 'atp_players.csv'), mode='r') as csv_file:
        csv_reader = csv.DictReader(csv_file)
        for row in csv_reader:
        if name == row["firstname"] + " " + row["lastname"]:
            return True
    return False


def isPlayerExistFemale(name):
    with open(join(dirname(__file__), 'wta_players.csv'), mode='r') as csv_file:
        csv_reader = csv.DictReader(csv_file)
        for row in csv_reader:
            if name == row["firstname"] + " " + row["lastname"]:
                return True
    return False