#ifndef PA2_GAMECONTROLLER_H
#define PA2_GAMECONTROLLER_H
#define occupiedCellChar "██"
#define unoccupiedCellChar "▒▒"
#include "BlockFall.h"
#include <iostream>
#include <iostream>

using namespace std;
class GameController {

public:
    void write(string message);
    void printGrid(BlockFall& game);
    void moveRight(BlockFall& game);
    void moveLeft(BlockFall& game);
    void moveDown(BlockFall& game);
    void rotateRight(BlockFall& game);
    void rotateLeft(BlockFall& game);
    void drop(BlockFall& game, bool gravity);
    bool play(BlockFall &game, const string &commands_file); // Function that implements the gameplay
};



#endif //PA2_GAMECONTROLLER_H
