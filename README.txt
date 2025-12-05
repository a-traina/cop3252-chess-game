## COP3252 Chess Term Project

- Homework X
- Code Authors: Alyssa Traina & Ethan Broome
- Game Implemented: Chess

---

# Game Rules

User should follow standard chess rules when running this application.

Note certain special moves from the standard chess rules are supported:
- En Passant
- Castling
- Pawn Promotion

Other things to note:
- If a player decides to offer a draw or resign from the game, options are provided in the application.
- If the players decide to play with a timer and the time expires on a player's turn, the game is ended and the other player is awarded the win.

---

## Interface

- Click a piece to select it (only works on the current player's turn).  
- Click a valid destination square to move the piece.  
- Invalid moves are automatically prevented by the game logic.  
- Highlights indicate selected pieces and possible moves.
- To offer a draw, select the "Offer Draw" button and ensure that both players consent to a draw before selecting the "Yes" option.
- To resign from the game, select the "Resign" button and confirm player resignation.

---

## Running the Project

1. Ensure that you are running Java 24 JDK
2. Run "java -jar hwx.jar" or double-click on the application to run.

**If you must create the runnable jar from this repository**
1. Compile the src code:
    find src -name "*.java" > sources.txt               
    javac -d bin @sources.txt
2. Create the runnable jar using the provided manifest.txt file:
    jar cfm hwx.jar manifest.txt README.txt src -C bin .
3. Add the assets to the jar file:
    jar uf hwx.jar assets .
4. Run the jar
    java -jar hwx.jar


---

# Extra Features

Look for these extra features in this application:

**Game Play Features**
- A timer for competitive chess game play
- An evaluation bar for mid-game player material advantage visuals
- Option to toggle timer and evaluation bar on/off in the general settings menu
- Formatted move history table according to SAN notation rules
    - Specifically note that potential ambiguous notation is clarified when necessary.
    - This means that if a player's pieces of the same type can reach the same cell, additional information is provided
- Player banner dynamically displays captured pieces as they occur during the game
- Game board maintains square dimensions during resizing of the application for customizability
- Draw button provided to end game prematurely if both players consent to a draw
- Resign button provided to end game prematurely if a player desires to resign from the game

**Visual Features**
- All graphic elements are custom designed by Ethan Broome
    - Includes: Menu Screen, Sprite Buttons, Pieces, and Icons
- Evaluation bar implements smooth animations for a clean look and feel
- Player banner highlights on player's turn
- Game board colors are fully customizable in the appearance settings (with an option to reset to default if desired)
- Menu screen and game over screen have sprite buttons that expand on hover for interactivity
- Mouse cursor changes to hand cursor when hovering over clickable content (sprite buttons and board pieces)

**Audio Features**
- All sound effects and music are produced by Ethan Broome
- Sound effects implemented include:
    - Piece moves (standard move, invalid move, and piece capturing move)
    - Sprite button clicks on the menu screen
    - Game over victory sound
- Background music adds ambience when playing the game
- Option to toggle music/ sound effects on/off are provided in the sound settings menu

