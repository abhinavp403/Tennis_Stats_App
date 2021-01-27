import csv
import re
import playerExist
from os.path import dirname, join


def breakdownscore(score):
    winner = {"sets-won": 0, "games-won": 0, "clean-sets": 0, "straight-sets": 0}
    loser = {"sets-won": 0, "games-won": 0, "clean-sets": 0, "straight-sets": 0}
    sets = score.split(' ')
    for s in sets:
        c = s.split('-')
        if c.__contains__('RETD') or c.__contains__('RET'):
            break
        c[0] = int(re.sub(r'\(\d\)', '', c[0]))
        c[1] = int(re.sub(r'\(\d\)', '', c[1]))
        if c[0] > c[1]:
            winner["sets-won"] += 1
            winner["games-won"] += c[0]
            loser["games-won"] += c[1]
            if c[1] == 0:
                winner["clean-sets"] += 1
        elif c[0] < c[1]:
            loser["sets-won"] += 1
            loser["games-won"] += c[1]
            winner["games-won"] += c[0]
            if c[0] == 0:
                loser["clean-sets"] += 1
    if loser["sets-won"] == 0:
        winner["straight-sets"] += 1
    return winner["sets-won"], winner["games-won"], winner["clean-sets"], winner["straight-sets"], loser["sets-won"], loser["games-won"], loser["clean-sets"], loser["straight-sets"]


def readfile(p1, p2, input_path, line_count, player1, player2, win, lose, court):
    with open(join(dirname(__file__), input_path), mode='r') as csv_file:
        csv_reader = csv.DictReader(csv_file)
        for row in csv_reader:
            if (row["winner_name"] == p1 and row["loser_name"] == p2) or (row["winner_name"] == p2 and row["loser_name"] == p1):
                line_count += 1
                if row["surface"] == court or "" == court:
                    if row["score"] == "W/O":
                        if row["winner_name"] == p1:
                            player1["matches-won"] += 1
                        if row["winner_name"] == p2:
                            player2["matches-won"] += 1
                        break
                    if row["winner_name"] == p1:
                        player1["matches-won"] += 1
                        win["sets-won"], win["games-won"], win["clean-sets"], win["straight-sets"], lose["sets-won"], lose["games-won"], lose["clean-sets"], lose["straight-sets"] = breakdownscore(row["score"])
                        player1["sets-won"] += win["sets-won"]
                        player1["games-won"] += win["games-won"]
                        player1["clean-sets"] += win["clean-sets"]
                        player1["straight-sets"] += win["straight-sets"]
                        player2["sets-won"] += lose["sets-won"]
                        player2["games-won"] += lose["games-won"]
                        player2["clean-sets"] += lose["clean-sets"]
                        player2["straight-sets"] += lose["straight-sets"]
                        if row["w_ace"] != '':
                            player1["aces"] += int(row["w_ace"])
                        if row["w_df"] != '':
                            player1["doublefaults"] += int(row["w_df"])
                        if row["l_bpFaced"] != '':
                            player1["breakpts-won"] += (int(row["l_bpFaced"]) - int(row["l_bpSaved"]))
                    if row["winner_name"] == p2:
                        player2["matches-won"] += 1
                        win["sets-won"], win["games-won"], win["clean-sets"], win["straight-sets"], lose["sets-won"], lose["games-won"], lose["clean-sets"], lose["straight-sets"] = breakdownscore(row["score"])
                        player2["sets-won"] += win["sets-won"]
                        player2["games-won"] += win["games-won"]
                        player2["clean-sets"] += win["clean-sets"]
                        player2["straight-sets"] += win["straight-sets"]
                        player1["sets-won"] += lose["sets-won"]
                        player1["games-won"] += lose["games-won"]
                        player1["clean-sets"] += lose["clean-sets"]
                        player1["straight-sets"] += lose["straight-sets"]
                        if row["w_ace"] != '':
                            player2["aces"] += int(row["w_ace"])
                        if row["w_df"] != '':
                            player2["doublefaults"] += int(row["w_df"])
                        if row["l_bpFaced"] != '':
                            player2["breakpts-won"] += (int(row["l_bpFaced"]) - int(row["l_bpSaved"]))
                    if row["loser_name"] == p1:
                        if row["l_ace"] != '':
                            player1["aces"] += int(row["l_ace"])
                        if row["l_df"] != '':
                            player1["doublefaults"] += int(row["l_df"])
                        if row["w_bpFaced"] != '':
                            player1["breakpts-won"] += (int(row["w_bpFaced"]) - int(row["w_bpSaved"]))
                    if row["loser_name"] == p2:
                        if row["l_ace"] != '':
                            player2["aces"] += int(row["l_ace"])
                        if row["l_df"] != '':
                            player2["doublefaults"] += int(row["l_df"])
                        if row["w_bpFaced"] != '':
                            player2["breakpts-won"] += (int(row["w_bpFaced"]) - int(row["w_bpSaved"]))

        return player1, player2, line_count


def main(entry1, entry2, year, court):
    player1 = {"matches-won": 0, "sets-won": 0, "games-won": 0, "breakpts-won": 0, "clean-sets": 0, "straight-sets": 0, "aces": 0, "doublefaults": 0}
    player2 = {"matches-won": 0, "sets-won": 0, "games-won": 0, "breakpts-won": 0, "clean-sets": 0, "straight-sets": 0, "aces": 0, "doublefaults": 0}
    win = {"sets-won": 0, "games-won": 0, "clean-sets": 0, "straight-sets": 0}
    lose = {"sets-won": 0, "games-won": 0, "clean-sets": 0, "straight-sets": 0}
    if entry1 == "" or entry2 == "":
        return "Enter Both Player Names"
    if playerExist.isPlayerExistMale(entry1) is False or playerExist.isPlayerExistMale(entry2) is False:
        return "Player Not Found"
    if year != "" and (int(year) < 1968 or int(year) > 2020):
        return "No Record"
    if court != "" and (court != "Hard" and court != "Clay" and court != "Grass"):
        return "No Such Surface"
    line_count = 0
    if year != "" and int(year) in range(1968, 2021):
        input_path = 'atp_matches_' + year + '.csv'
        player1stats, player2stats, line_count = readfile(entry1, entry2, input_path, line_count, player1, player2, win, lose, court)
    else:
        for i in range(1968, 2021):
            input_path = 'atp_matches_' + str(i) + '.csv'
            player1stats, player2stats, line_count = readfile(entry1, entry2, input_path, line_count, player1, player2, win, lose, court)
    entries = [player1stats, player2stats]
    return entries