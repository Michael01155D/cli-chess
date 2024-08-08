CLI version of chess in java. The goal of this project is to serve as a starting point for what will become a Spring Boot web app 

////Current Progress///

- Board initializes with proper pieces
- Pieces movement logic seems correct
- kings cannot move into check
- king must be moved if in check
- game ends if king in check and has no valid moves


///Future Todos//

-capturing pieces

-promoting pawn at edge of board

- distinguish between black and white pieces visually

- castling (if king and rook havent moved, if no pieces between them, if no spaces betwen them in danger, if king not in check)

-en passant (need to read the rules on this to determine logic)