CLI version of chess in java. The goal of this project is to serve as a starting point for what will become a Spring Boot web app 

////Current Progress///

- Board initializes with proper pieces
- Pieces movement logic seems correct
- Pieces cannot make a move if it results in their king in check
- game ends if player has no valid moves

///Future Todos//

- castling (if king and rook havent moved, if no pieces between them, if no spaces betwen them in danger, if king not in check)

-en passant (need to read the rules on this to determine logic)

-Stalemates

-Basic AI (could randomly pick a move in validMove, for example)