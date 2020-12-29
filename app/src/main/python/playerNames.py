import csv
from os.path import dirname, join

def malePlayers():
    list = []
    with open(join(dirname(__file__), 'atp_players.csv'), mode='r') as csv_file:
        csv_reader = csv.DictReader(csv_file)
        for row in csv_reader:
            list.append(row["firstname"] + " " + row["lastname"])
    return list


def femalePlayers():
    list = []
    with open(join(dirname(__file__), 'wta_players.csv'), mode='r') as csv_file:
        csv_reader = csv.DictReader(csv_file)
        for row in csv_reader:
            list.append(row["firstname"] + " " + row["lastname"])
    return list