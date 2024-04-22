// DecodeMessage.cpp

#include "DecodeMessage.h"

// Default constructor
DecodeMessage::DecodeMessage() {
    // Nothing specific to initialize here
}

// Destructor
DecodeMessage::~DecodeMessage() {
    // Nothing specific to clean up
}


std::string DecodeMessage::decodeFromImage(const ImageMatrix& image, const std::vector<std::pair<int, int>>& edgePixels) {
    std::string binaryString;
    for (const auto & edgePixel : edgePixels) {
        int row= edgePixel.first;
        int col= edgePixel.second;
        double pixel = image.get_data(row,col);
        int lsb = static_cast<int>(pixel) & 1;
        binaryString += std::to_string(lsb);
    }
    int paddingLength = 7 - (binaryString.length() % 7);
    std::string zeros(paddingLength, '0');
    if (paddingLength < 7) {
        binaryString = zeros + binaryString;
    }
    std::string decodedMessage;
    decodedMessage.reserve(binaryString.size() / 7);
    for (int i =0;i<binaryString.size();i+=7){
        std::string segment = binaryString.substr(i,7);
        int asciiNumber = std::stoi(segment, 0, 2);
        if(asciiNumber <= 32){asciiNumber+=33;}
        if(asciiNumber >=127){asciiNumber=126;}
        char asciiChar = static_cast<char>(asciiNumber);
        decodedMessage += asciiChar;
    }
    return decodedMessage;

}

