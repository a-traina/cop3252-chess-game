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

---

## Interface

- Click a piece to select it (only works on the current player's turn).  
- Click a valid destination square to move the piece.  
- Invalid moves are automatically prevented by the game logic.  
- Highlights indicate selected pieces and possible moves.

---

## Running the Project

1. Ensure that you are running Java 24 JDK
2. 


---

# Extra Features

Look for these extra features in this application:

**Game Play Features**
- Mouse cursor changes to hand cursor when hovering over clickable content (sprite buttons and board pieces)
- A timer for competitive chess game play
- An evaluation bar for mid-game player material advantage visuals
- Option to toggle timer and evaluation bar on/off in the general settings menu
- Formatted move history according to SAN notation rules
    - Specifically note that ambiguities are clarified when necessary
- Player banner dynamically displays captured pieces as they occur during the game
- Game board maintains square dimensions during resizing of the application for customizability
- Draw button provided to end game prematurely if both players consent to a draw
- Resign button provided to end game prematurely if a player desires to resign from the game

**Visual Features**
- All graphic elements are custom designed by Ethan Broome
- Evaluation bar implements smooth animations for a clean look and feel
- Player banner highlights on player's turn
- Game board colors are fully customizable in the appearance settings (with an option to reset to default if desired)
- Menu screen and game over screen has sprite buttons that expand on hover for interactivity

**Audio Features**
- Sound effects are implemented as pieces are moved throughout the game
- Background music adds ambience when playing the game
- Option to toggle music/ sound effects on/off are provided in the sound settings menu
