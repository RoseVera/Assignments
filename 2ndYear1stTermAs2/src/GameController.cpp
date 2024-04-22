#include "GameController.h"
#include <iostream>

ofstream MyFile("out.txt");
void GameController::write(std::string message){
    MyFile<<message;
}

void GameController::printGrid(BlockFall& game) {
    Block* active= game.active_rotation;
    int topLeftRow =active->topLeftCoor.first;
    int topLeftCol = active->topLeftCoor.second;
    for (int i = 0; i < active->shape.size(); ++i) {
        for (int j = 0; j < active->shape[0].size(); ++j) {
            if(active->shape[i][j]){
                game.grid[i+topLeftRow][j+topLeftCol]=1;
            }else{
                game.grid[i][j];
            }
        }
    }
    for (int i = 0; i <game.grid.size(); ++i) {
        std::cout<<"i "<<i<<endl;
        for (int j = 0; j < game.grid[0].size(); ++j) {
            if(game.grid[i][j]==1){
                MyFile<<occupiedCellChar;
            }else if(game.grid[i][j]==0){
                MyFile<<unoccupiedCellChar;
            }
            MyFile<<" ";
        }
        MyFile<<"\n";
    }
    MyFile<<"\n";

}

void GameController::moveRight(BlockFall& game) {

    Block *active = game.active_rotation;
    // Get Coordinates of True (1) cells of active block
    int r = active->topLeftCoor.first;
    int c = active->topLeftCoor.second;

    for (int i = 0; i < active->shape.size(); ++i) {
        for (int j = 0; j < active->shape[0].size(); ++j) {
            if (active->shape[i][j]) {  // if it is 1
                active->onesCoor.push_back(std::make_pair(i + r, j + c));
            }
        }
    }

    bool canMove = true;
    // Checking if there are occupied cells on the way
    for (int i = 0; i < active->onesCoor.size(); ++i) {
        int a = active->onesCoor[i].first;
        int b = active->onesCoor[i].second + 1;

        bool different = true;
        for (int k = 0; k < active->onesCoor.size(); ++k) {
            if (active->onesCoor[k].second == b && active->onesCoor[k].first == a) {
                different = false;
                break;
            }
        }
        if (game.grid[a][b] == 1 && different) {
            canMove = false;
        }

        if (b == game.cols) {
            canMove = false;
        }
    }
    if (canMove) {
        for (int i = r; i < r + active->shape.size(); ++i) {
            for (int j = c + active->shape[0].size() - 1; j >= c; j--) {
                game.grid[i][j + 1] = game.grid[i][j];
                game.grid[i][j] = 0;
            }
        }
        active->topLeftCoor.second++;
        active->onesCoor.clear();
    }
}

void GameController::moveDown(BlockFall& game) {
    Block *active = game.active_rotation;
    // Get Coordinates of True (1) cells of active block
    int r = active->topLeftCoor.first;
    int c = active->topLeftCoor.second;

    for (int i = 0; i < active->shape.size(); ++i) {
        for (int j = 0; j < active->shape[0].size(); ++j) {
            if (active->shape[i][j]) {  // if it is 1
                active->onesCoor.push_back(std::make_pair(i + r, j + c));
            }
        }
    }

    bool canMove = true;
    bool atLast = false;

    // Checking if there are occupied cells on the way
    for (int i = 0; i < active->onesCoor.size(); ++i) {

        int a = active->onesCoor[i].first + 1;
        int b = active->onesCoor[i].second;



        bool different = true;
        for (int k = 0; k < active->onesCoor.size(); ++k) {
            if (active->onesCoor[k].second == b && active->onesCoor[k].first == a) {
                different = false;
                break;
            }
        }
       // std::cout << "Checking cell (" << a << ", " << b << ") - game.grid[" << a << "][" << b << "] = " << game.grid[a][b] << std::endl;

        if (game.grid[a][b] == 1 && different) {
            canMove = false;
        }

        if (a==game.rows-1) {
            std::cout<<"we are at th end<<"<< endl;
            canMove = false;


        }
    }
            if (canMove) {
               // std::cout<<"yes"<<endl;
                for (int i = r + active->shape.size() - 1; i >= r; i--) {
                    for (int j = c; j < c + active->shape[0].size(); j++) {
                       // std::cout<<"iii: "<<i<<endl;

                        game.grid[i + 1][j] = game.grid[i][j];
                        game.grid[i][j] = 0;
                    }
                }
                active->topLeftCoor.first++;
                active->onesCoor.clear();
            }
    std::cout<<"sol ust row  "<< active->topLeftCoor.first+active->shape.size()<<endl;
            if(active->topLeftCoor.first+active->shape.size()==game.rows){

                for (int i = r + active->shape.size() - 1; i >= r; i--) {
                    for (int j = c; j < c + active->shape[0].size(); j++) {
                       // std::cout<<"iii: "<<i<<endl;

                        game.grid[i + 1][j] = game.grid[i][j];
                        game.grid[i][j] = 0;
                    }
                }
                active->topLeftCoor.first++;
                active->onesCoor.clear();
            }
    //std::cout << "topleft "<<active->topLeftCoor.first<<active->topLeftCoor.second;
    }



void GameController::moveLeft(BlockFall& game){
    Block *active = game.active_rotation;
    // Get Coordinates of True (1) cells of active block
    int r = active->topLeftCoor.first;
    int c = active->topLeftCoor.second;

    for (int i = 0; i < active->shape.size(); ++i) {
        for (int j = 0; j < active->shape[0].size(); ++j) {
            if (active->shape[i][j]) {  // if it is 1
                active->onesCoor.push_back(std::make_pair(i + r, j + c));
            }
        }
    }
    bool canMove = true;
    // Checking if there are occupied cells on the way
    for (int i = 0; i < active->onesCoor.size(); ++i) {
        int a = active->onesCoor[i].first;
        int b = active->onesCoor[i].second - 1;

        bool different = true;
        for (int k = 0; k < active->onesCoor.size(); ++k) {
            if (active->onesCoor[k].second == b && active->onesCoor[k].first == a) {
                different = false;
                break;
            }
        }
        if (game.grid[a][b] == 1 && different) {
            canMove = false;
            break;

        }
        if (b == 0) {
            canMove = false;
            break;
        }
    }
    // move left
    if (canMove) {
        for (int i = r; i < r + active->shape.size(); ++i) {
            for (int j = c ; j < c + active->shape[0].size(); j++) {
                game.grid[i][j - 1] = game.grid[i][j];
                game.grid[i][j] = 0;
            }
        }
        active->topLeftCoor.second--;
    }


}

void GameController::rotateRight(BlockFall& game){
    bool rotatable =true ;
    if(rotatable){
        // game.active_rotation=game.active_rotation->right_rotation;
        Block* rotated = game.active_rotation->right_rotation;
        rotated->topLeftCoor.first = game.active_rotation->topLeftCoor.first;
        rotated->topLeftCoor.second = game.active_rotation->topLeftCoor.second;
        for (int i = 0; i < game.active_rotation->shape.size(); ++i) {
            for (int j = 0; j < game.active_rotation->shape[0].size(); ++j) {
                game.grid[i+game.active_rotation->topLeftCoor.first][j+game.active_rotation->topLeftCoor.second]=0;
            }
        }
        game.active_rotation = game.active_rotation->right_rotation;

    }

}

void GameController::rotateLeft(BlockFall& game){
    bool rotatable =true;
    if(rotatable){
        // game.active_rotation=game.active_rotation->right_rotation;
        Block* rotated = game.active_rotation->left_rotation;
        rotated->topLeftCoor.first = game.active_rotation->topLeftCoor.first;
        rotated->topLeftCoor.second = game.active_rotation->topLeftCoor.second;
        for (int i = 0; i < game.active_rotation->shape.size(); ++i) {
            for (int j = 0; j < game.active_rotation->shape[0].size(); ++j) {
                game.grid[i+game.active_rotation->topLeftCoor.first][j+game.active_rotation->topLeftCoor.second]=0;
            }
        }
        game.active_rotation = game.active_rotation->left_rotation;

    }
}
void GameController::drop(BlockFall& game, bool gravity){
        Block* active= game.active_rotation;
        int topLeftRow =active->topLeftCoor.first;
        int topLeftCol = active->topLeftCoor.second;
        for (int i = 0; i < active->shape.size(); ++i) {
            for (int j = 0; j < active->shape[0].size(); ++j) {
                game.grid[i + topLeftRow][j + topLeftCol] = 0;
            }
        }
    if(!gravity){
        bool collision =false;
        for (int i = active->shape.size(); i < game.grid.size(); ++i) {
            for (int j = 0; j < active->shape[0].size(); ++j) {
                if(game.grid[topLeftRow+i][topLeftCol+j]==1){
                    std::cout<<"cells "<< topLeftRow+i<<" "<<game.grid[topLeftRow+i][topLeftCol+j]<<endl;
                    active->topLeftCoor.first += (i-active->shape.size());
                    collision= true;
                    break;
                }
            }
        }
        if(!collision){
            std::cout<<"nocollisions"<<endl;
            active->topLeftCoor.first += game.grid.size()- active->shape.size();
        }
        // print dropped blocked

        for (int i = 0; i < active->shape.size(); ++i) {
            for (int j = 0; j < active->shape[0].size(); ++j) {
                if(active->shape[i][j]){
                    game.grid[i+active->topLeftCoor.first][j+active->topLeftCoor.second]=1;
                }else{
                    game.grid[i][j];
                }
            }
        }
        // go next block
        game.active_rotation=game.active_rotation->next_block;

    }
}



bool GameController::play(BlockFall& game, const string& commands_file){
    std::ifstream file(commands_file);
    if(!file.is_open()){
        {
            exit(EXIT_FAILURE);
        }
    }
    std::string line;
    std::cout<<"sonn hucre "<<game.grid.size();

   while(getline(file,line)){
        if(line=="PRINT_GRID"){
            printGrid(game);
            write("\n");

        }else if(line=="ROTATE_RIGHT"){
           // active =active->right_rotation;
            rotateRight(game);

        }else if(line=="ROTATE_LEFT"){
            //active =active->left_rotation;
            rotateLeft(game);

        }else if(line=="MOVE_RIGHT"){
            moveRight(game);

        }else if(line=="MOVE_DOWN"){
             moveDown(game);


        }else if(line=="MOVE_LEFT"){
            moveLeft(game);

        }else if(line=="GRAVITY_SWITCH"){

        }else if(line=="DROP"){
            drop( game, false);

        }
    }

    // TODO: Implement the gameplay here while reading the commands from the input file given as the 3rd command-line
    //       argument. The return value represents if the gameplay was successful or not: false if game over,
    //       true otherwise.

    return false;
}



