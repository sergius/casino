This is an exercise which one of my friends had to implement as a test. After several months of only
coding in Scala, I decided to go back to Java and play a while, seeing if I can apply any
functional and/or reactive principles to it. Unfortunately, Java 8 wasn't available yet, so it was
pretty much a torture... Definitely improvable :)

Requirements:
------------


Roulette is a popular casino game. The aim of the exercise is to create a simple command line
multiplayer version of the game.

On start-up, load a file which contains a list of player's names:
```
Tiki_Monkey
Barbara
```

Once started, it should read lines from the console, each line will contain the player's name, what
they want to bet on (either a number from 1-36, EVEN or ODD), and how much they want to bet:

`Tiki_Monkey 2 1.0`

Or

`Barbara EVEN 3.0`

Every 30 seconds the game should choose a random number between 0 and 36 (inclusive) for
the ball to land on.

• If the number is 1-36 then any bet on that number wins, and the player wins 36x the bet's amount.
• If the number is even, any bet on EVEN wins, and the player wins 2x the bet's amount.
• If the number is odd, any bet on ODD wins, and the player wins 2x the bet's amount.
• All other bets lose.

The game should print to the console, the number, and for each bet, the player's name, the bet,
whether they won or lost, and how much they won:

```
Number: 4
Player      Bet     Outcome Winnings
---
Tiki_Monkey 2       LOSE    0.0

Barbara     EVEN    WIN     6.0
```

**Optional Bonus Question**

We'd like to print out the total amount a player has won and bet. To do this make the following

changes. The file should have optional win and total bet columns:
```
Tiki_Monkey,1.0,2.0

Barbara,2.0,1.0
```

After each number is chosen, also print out the totals in a tabular format:
```
Player      Total Win   Total Bet

Tiki_Monkey 1.0         3.0

Barbara     8.0         4.0
```

---

**The solution**:

* Must use Java.
* Should build from the command line with Maven.
* Must provide a class with a main method (no need to provide a stand alone jar). 
* Should be thread-safe.

---


How it works:
------------

1. It should be started running the class Casino.
2. If the initial file is not found, it is possible to insert the file path from the console on prompt
3. If a bet is not valid (not having the format described in the requirements), it is ignored.
4. If the name of the player who makes the bet corresponds to none from the initial list,
the bet is considered invalid and is ignored.

