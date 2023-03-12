# Dungeon-Game

The Dungeon of Doom is played on a rectangular grid, which serves as the game’s board. A human player, acting as a brave fortune-hunter, can move
and pick up gold. The goal is to get enough gold to meet a win condition and then exit the dungeon. A bot player, acting as a villain, is trying to catch our
hero. The game is played in turns. On each player’s turn, that player (human or bot) sends a command and (if the command is successful) an action takes
place. A full list of the available commands, the game protocol, is under the How to Play heading.

The game ends either:
 - when the human player has collected **enough gold** and calls the EXIT command on the exit square (The player has won)
 - when the **bot catches the human player** (The player has lost)

# Installation
Advised to run on **Java 17** or newer.
```
$ git clone https://github.com/SamB032/Dungeon-Game.git
$ Javac GameLogic.java
$ Java GameLogic
```
# How to play
```Bash
Map name: 'Medium Dungeon of Disaster'
Gold required: 4
To get the list of all possible commands, type 'commands'

> LOOK
#..##
#..##
#.P##
...##
...##

> MOVE N

> LOOK
..###
#..##
#.P##
#..##
...##
```
Each command will take up a players turn. The idea of the game is to move around the map and find gold; when the gold requirement is reach for the map, #
the player can leave the map and win. However, the bot will also spawn in, and be able to pickup gold and attack the player if there is line of sight.
The player only will see a 5x5 grid around them (replicating fog and increasing difficulty). The player can select different maps at the start of the program, 
with smaller ones being easier and larger ones being more difficult.

## Commands
Note: not case sensitive

Command             | Usage                                  | example
:------------------:|----------------------------------------|------------------------------------------
`HELLO`             | Displays gold required to win          | `HELLO` -> `Gold to win: {Number}`
`GOLD`              | Displays current gold owned            | `GOLD` -> `Gold owned : {Number}`
`MOVE <direction>`  | Move a player in direction (N,E,S,W)   | `MOVE S` -> moves player 1 block down
`PICKUP`            | Picks up gold at player location       | `PICKUP` -> picks up gold
`LOOK`              | Displays 5x5 grid around player        | `LOOK` -> prints 5x5 grid to terminal
`QUIT`              | Leaves game if player on exit tile     | `QUIT` -> win if gold requirement reached

## Board Representation
Symbol              | Meaning                            
:------------------:|----------------------------------------
`P`                 | Represents player on the board      
`B`                 | Represents bot on the board            
`.`                 | Empty space  
`#`                 | Wall (cannot be moved onto)      
`G`                 | Gold block     
`E`                 | Exit block   
