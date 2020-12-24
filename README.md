# Tennis_Stats_App

This tennis app provides head to head stats between two players belonging to the ATP (men) or WTA (women) circuits. This app can be helpful to pick players in your Draft King roster. I would personally spend an hour to look up stats online and mathematically caclulate the necessary parameters. But now I can directly look at them on the go. Future plan is to integrate more players from other sports/leagues. 

Stats include- matches won, sets won, games won, break points won, clean sets (without losing a single game ex. 6-0), straight sets (without losing a single set ex 6-1 6-2 for best of 3), aces, double faults.  

Python script derives the essential information from the spreadsheet database and returns the final value which gets displayed in the UI screen.  

Libraries used:
1) Chaqoupy (Integrates python scripts in Android Studio IDE)
2) Python Community Plugin (Editing .py files)

Data provided courtesy of https://github.com/JeffSackmann/tennis_atp, https://github.com/JeffSackmann/tennis_wta
