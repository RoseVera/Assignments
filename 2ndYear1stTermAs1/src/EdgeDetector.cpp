// EdgeDetector.cpp

#include "EdgeDetector.h"
#include <cmath>
#include <iostream>

// Default constructor
EdgeDetector::EdgeDetector() {

}

// Destructor
EdgeDetector::~EdgeDetector() {

}

// Detect Edges using the given algorithm
std::vector<std::pair<int, int>> EdgeDetector::detectEdges(const ImageMatrix& input_image) {
    double* gX[3] = {
            new double[3]{-1.0, 0.0, 1.0},
            new double[3]{-2.0, 0.0, 2.0},
            new double[3]{-1.0, 0.0, 1.0}
    };
    double* gY[3] = {
            new double[3]{-1.0, -2.0, -1.0},
            new double[3]{0.0, 0.0, 0.0},
            new double[3]{1.0, 2.0, 1.0}
    };
    Convolution convolution1(gX,3,3,1,true);
    ImageMatrix imageX = convolution1.convolve(input_image);

    Convolution convolution2(gY,3,3,1,true);
    ImageMatrix imageY = convolution2.convolve(input_image);

    int height = input_image.get_height();
    int width = input_image.get_width();

    auto ** magnitudes = new double * [height];
    for (int i = 0; i < height; ++i) {
        magnitudes[i]= new double [width];
        }
    double sum = 0.0;
    int a=0;
    for (int i = 0; i < height; ++i) {
        for (int j = 0; j < width; ++j) {
            double result = sqrt(pow(imageX.get_data()[i][j],2)+pow(imageY.get_data()[i][j],2));
            magnitudes[i][j] = result;
            sum+=result;
        }
    }
    double threshold = sum / (height*width);
    std::vector<std::pair<int,int>> edgePixels;

    for (int i = 0; i < height; ++i) {
        for (int j = 0; j < width; ++j) {
        if(magnitudes[i][j]>threshold){
            edgePixels.emplace_back(i,j);
            }
        }
    }
    for (int i = 0; i < height; ++i) {
        delete[] magnitudes[i];
    }
    delete[] magnitudes;

    for (int i = 0; i < 3; ++i) {
        delete[] gX[i];
    }

    for (int i = 0; i < 3; ++i) {
        delete[] gY[i];
    }

    return edgePixels;
}



