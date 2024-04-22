#ifndef PA2_BLOCK_H
#define PA2_BLOCK_H

#include <vector>

using namespace std;

class Block {
public:

    vector<vector<bool>> shape; // Two-dimensional vector corresponding to the block's shape
    pair<int,int> topLeftCoor=std::make_pair(0,0);
    vector<pair<int,int>> onesCoor;
    Block * right_rotation = nullptr; // Pointer to the block's clockwise neighbor block (its right rotation)
    Block * left_rotation = nullptr; // Pointer to the block's counter-clockwise neighbor block (its left rotation)
    Block * next_block = nullptr; // Pointer to the next block to appear in the game

    bool operator==(const Block& other) const {
        return shape == other.shape;
        // TODO: Overload the == operator to compare two blocks based on their shapes
        /*bool same = true;
        if(shape.size()==other.shape.size() && shape[0].size()==other.shape[0].size()){
            for (int i = 0; i < shape.size(); ++i) {
                for (int j = 0; j < shape[0].size(); ++j) {
                    if(shape[i][j]!=other.shape[i][j]){
                        same= false;
                        break;
                    }
                }
            }
        } else{
            same= false;
        }
        return same;*/
    }

    bool operator!=(const Block& other) const {
        return shape != other.shape;

        // TODO: Overload the != operator to compare two blocks based on their shapes
       /* bool notSame = false;
        if(shape.size()==other.shape.size() && shape[0].size()==other.shape[0].size()){
            for (int i = 0; i < shape.size(); ++i) {
                for (int j = 0; j < shape[0].size(); ++j) {
                    if(shape[i][j]!=other.shape[i][j]){
                        notSame= true;
                        break;
                    }
                }
            }
        } else{
            notSame= true;
        }
        return notSame;*/
    }
};

#endif //PA2_BLOCK_H
