# Isola_Game
Isola is a turned based 2 - player game implemented using Java RMI (Remote Method Invocation) and Swings.

Goal: 
- Understand the working of Java RMI
- Understand and implement MVC (Model View Controller) pattern

Note: You need to comiple the code before you run it.

To run the code:

1) If running on same machine
  - You will need 3 terminals (1 for server, 2 for player#1 and player#2)
  - For Server:
    1. prompt$ rmiregistry &
    2. prompt$ java ServerImplementation
  - For Player#1:
    1. prompt$ java myIsolaController 1
    (This will start the application for Player 1)
  - For Player#2:
    1. prompt$ java myIsolaController 2
    (This will start the application for Player 2)
  
2) If running on different machines
  - You will need to have all the machines on same network; rest steps are same as above.

Understanding the code:
  All the files are well commented for you to get the idea what the code snippet or a variable is doing.

Rules of the Game (The game's window also populates all the rules as a reference)

- The Game starts with Black.
- A move consists of two subsequent actions:
  1. Moving one's gem to a neighboring (horizontally, vertically, or diagonally) field that contains a square but not the opponent's gem and also should be undestroyed field (which is not marked with X); only one square movement allowed.
  2. Destroying any square with no gem on it. Try blocking the opponent.
- Missing on rule is losing your move.
- If a player cannot move any more, he/she loses the game.



Note: This code was implemented just for learning, usage of this code anywhere should have citation. Feel free to report bugs; we will try to fix it.
