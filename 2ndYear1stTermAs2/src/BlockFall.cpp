#include "BlockFall.h"
#include <vector>
#include <string>
#include <algorithm>
#include <fstream>
#include <sstream>
#include <iostream>
#include "Block.h"
void BlockFall::printBlock( Block* temp) {
    if (!temp) {
        std::cerr << "Block is null." << std::endl;
        return;
    }

    std::cout << "Contents of the block:" << std::endl;
    for (const auto& row : temp->shape) {
        for (bool cell : row) {
            std::cout << (cell ? '1' : '0');
        }
        std::cout << std::endl;
    }
}
BlockFall::BlockFall(string grid_file_name, string blocks_file_name, bool gravity_mode_on, const string &leaderboard_file_name, const string &player_name) : gravity_mode_on(
        gravity_mode_on), leaderboard_file_name(leaderboard_file_name), player_name(player_name) {
    initialize_grid(grid_file_name);
    read_blocks(blocks_file_name);
    rightRotations();
    leaderboard.read_from_file(leaderboard_file_name);
}

Block * BlockFall::rotateRight(Block* block){
    int c = block->shape[0].size();
    int r = block->shape.size();
    std::vector<std::vector<bool>> transposed(c, std::vector<bool>(r, false));
    for (int i = 0; i < r; ++i) {
        for (int j = 0; j < c; ++j) {
            transposed[j][r - 1 - i] = block->shape[i][j];
        }
    }
    Block* newBlock = new Block;
    newBlock->shape=transposed;
    return newBlock;

}

Block *BlockFall::rotateLeft(Block *block) {
    int c = block->shape[0].size();
    int r = block->shape.size();
    std::vector<std::vector<bool>> transposed(c, std::vector<bool>(r, false));
    for (int i = 0; i < r; ++i) {
        for (int j = 0; j < c; ++j) {
            transposed[j][i] = block->shape[i][j];
        }
    }

    Block* newBlock = new Block;
    newBlock->shape=transposed;

    return newBlock;
}

bool BlockFall::areTheySame(Block* original, Block* rotated){
    return original->shape==rotated->shape;
}

void BlockFall::read_blocks(const string &input_file) {
    //initial_block = blocksLinkedList(input_file);

    std::ifstream file(input_file);
    if (!file.is_open()) {
        std::cout << "File Cannot Open" << input_file << std::endl;
    }


    Block* head = nullptr;
    std::string line;
    Block* current = nullptr;
    Block* newBlock = new Block();
    while (getline(file, line)) {
        if(!line.empty()) {
            if (newBlock == nullptr ) {
                newBlock = new Block();
            }
            std::vector<bool> newRow;
            for (char c: line) {
                if (c == '1') {
                    newRow.push_back(true);
                } else if (c == '0') {
                    newRow.push_back(false);
                }
            }
            if(!newRow.empty()){
                newBlock->shape.push_back(newRow);
            }
            if (line.find(']')!=std::string::npos) {
                if (head == nullptr) {
                    head = newBlock;
                    current = head;
                } else {
                    current->next_block = newBlock;
                    current = newBlock;
                }
                newBlock = nullptr;
            }

        }
    }
    file.close();

    initial_block =  head;
    active_rotation = initial_block;

      Block* temp1 = initial_block;
      Block* powerUp;
      while (temp1->next_block->next_block != nullptr) {
          temp1=temp1->next_block;
    }
      powerUp = temp1->next_block;
      temp1->next_block= nullptr;
    std::vector<std::vector<bool>> matrix(powerUp->shape.size(), std::vector<bool>(powerUp->shape[0].size(), false));
    for (int i = 0; i < powerUp->shape.size(); ++i) {
        for (int j = 0; j < powerUp->shape[0].size(); ++j) {
            matrix[i][j]=powerUp->shape[i][j];
        }
    }

    // TODO: Read the blocks from the input file and initialize "initial_block" and "active_rotation" member variables
    // TODO: For every block, generate its rotations and properly implement the multilevel linked list structure
    //       that represents the game blocks, as explained in the PA instructions.
    // TODO: Initialize the "power_up" member variable as the last block from the input file (do not add it to the linked list!)
}

/*void BlockFall::rotateAll(){
    Block* temp=initial_block;
    int m =1;
    while(temp->next_block != nullptr){
        int right =0;
        int left=0;
        bool l = areTheySame(temp, rotateLeft(temp));
        if(!l){ // if I can create a left rotation different from original
            left++;
            temp->left_rotation = rotateLeft(temp);
            temp->left_rotation->right_rotation=temp;
            bool v = areTheySame(temp, rotateRight(temp));
            bool lr= areTheySame(rotateRight(temp),temp->left_rotation);
            if(!v &&  !lr){ //if

            }

        }
        bool v = areTheySame(temp, rotateRight(temp));

        if(!v){ // if I can create the first right rotation
            temp->right_rotation= rotateRight(temp);
           // printBlock(rotateRight(temp));
            temp->right_rotation->left_rotation=temp;
            temp->right_rotation->next_block=temp->next_block;
            right++;

            bool r = areTheySame(temp->right_rotation, rotateRight(temp->right_rotation));
            if( !r ){ // if I can create the second right rotation
                temp->right_rotation->right_rotation = rotateRight(temp->right_rotation);
                temp->right_rotation->right_rotation->left_rotation= temp->right_rotation;
                temp->right_rotation->right_rotation->next_block = temp->next_block;
                if(left==0){ // If creating left rotation isn't possible
                    temp->right_rotation->right_rotation->right_rotation=temp;

                }
                if(left==1){ // If creating left rotation is possible
                    temp->right_rotation->right_rotation->right_rotation = temp->left_rotation;
                    temp->right_rotation->right_rotation->right_rotation->right_rotation=temp;
                }

                right++;

            }else{ //if I cant then I should do circle
                if(left==0){ // If creating left rotation isn't possible
                    temp->right_rotation->right_rotation=temp;
                }
                if(left==1){ // If creating left rotation is possible
                    temp->right_rotation->right_rotation = temp->left_rotation;
                }
            }

        }else { // if I cant create any right rotation
            if(left==0){ // If creating left rotation isn't possible
                temp->right_rotation=temp;
                temp->left_rotation=temp;
            }
            if(left==1){ // If creating left rotation is possible
                temp->right_rotation = temp->left_rotation;
            }
        }


        if(!l){ // if I can create the left rotation
            temp->left_rotation = rotateLeft(temp);
            temp->left_rotation->right_rotation = temp;
            temp->left_rotation->next_block= temp->next_block;

            // how many right rotation do I have?
            if(right==0){
                temp->left_rotation->left_rotation =temp;
                temp->right_rotation=temp->left_rotation;
            }
            if(right==1){
                temp->left_rotation->left_rotation =temp->right_rotation;
                temp->right_rotation->right_rotation=temp->left_rotation;
            }
            if(right==2){
                temp->left_rotation->left_rotation =temp->right_rotation->right_rotation;
                temp->right_rotation->right_rotation->right_rotation=temp->left_rotation;
            }
        }else{ //If there is no left rotation
            if(right==0){
                temp->left_rotation =temp;
                temp->right_rotation=temp;
            }
            if(right==1){
                temp->left_rotation =temp->right_rotation;
                temp->right_rotation->right_rotation=temp;
            }
            if(right==2){
                temp->left_rotation =temp->right_rotation->right_rotation;
                temp->right_rotation->right_rotation->right_rotation=temp;
            }
        }
        m++;
        temp=temp->next_block;
    }

}*/
void BlockFall::rightRotations(){
    Block* block = initial_block;
    while(block!= nullptr) {
        int right =0;
        bool sameL = areTheySame(block, rotateLeft(block));
        if (!sameL) { //if there is a left rotation different from original
            block->left_rotation = rotateLeft(block);
            block->left_rotation->right_rotation = block;
            block->left_rotation->next_block = block->next_block;

            bool same = areTheySame(block, rotateRight(block));
            bool areLeftRightSame = areTheySame(rotateRight(block), rotateLeft(block));

            if (areLeftRightSame) { // Then there are only 2 blocks left and original
                block->right_rotation = block->left_rotation;
            }
            if (!same && !areLeftRightSame) { // If there is a right rotation diff than original and left one.
                block->right_rotation = rotateRight(block);
                block->right_rotation->left_rotation = block;
                block->right_rotation->next_block = block->next_block;
                right++;
                bool sameR = areTheySame(block->right_rotation, rotateRight(block->right_rotation));
                if (!sameR) { // If there is second right rotation
                    block->right_rotation->right_rotation = rotateRight(block->right_rotation);
                    block->right_rotation->right_rotation->left_rotation = block->right_rotation;
                    block->right_rotation->right_rotation->right_rotation=block->left_rotation;
                    block->right_rotation->right_rotation->right_rotation->next_block = block->next_block;
                    right++;
                }
                else{ // if there is one right and one left
                    block->right_rotation->right_rotation=block->left_rotation;
                }
            }
            if(right==0){
                block->left_rotation->left_rotation=block;
            } else if(right==1){
                block->left_rotation->left_rotation=block->right_rotation;
            }else if(right==2){
                block->left_rotation->left_rotation=block->right_rotation->right_rotation;
            }
        } else { // if there is no left rotation
            block->right_rotation=block;
            block->left_rotation=block;

        }
        block=block->next_block;
    }
}

void BlockFall::initialize_grid(const string &input_file) {

    std::ifstream input(input_file);
    std::cout<<input_file;

    if(!input.is_open())
    {
        exit(EXIT_FAILURE);
    }
    std::string line;
    while (std::getline(input, line)) {
        std::istringstream iss(line);
        std::vector<int> row;
        int number;
        while (iss >> number) {
            row.push_back(number);
        }
        grid.push_back(row);
    }
    rows=grid.size();
    cols=grid[0].size();


    // TODO: Initialize "rows" and "cols" member variables
    // TODO: Initialize "grid" member variable using the command-line argument 1 in main
}

BlockFall::~BlockFall() {
    Block* current = initial_block;
    while (current != nullptr) {
        // Delete the rotation linked list for each block
      /*  Block* rotation = current->right_rotation;
        while (rotation != nullptr) {
            Block* nextRotation = rotation->right_rotation;
            delete rotation;
            rotation = nextRotation;
        }*/

        // Move to the next block in the main linked list
        Block* nextBlock = current->next_block;
        delete current;
        current = nextBlock;
    }
    // TODO: Free dynamically allocated memory used for storing game blocks
}