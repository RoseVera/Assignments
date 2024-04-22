#include "EncodeMessage.h"
#include <cmath>
#include <iostream>



// Default Constructor
EncodeMessage::EncodeMessage() {

}

// Destructor
EncodeMessage::~EncodeMessage() {

}
bool EncodeMessage::isPrime(int n) {
    bool is_prime = true;
    if (n == 0 || n == 1) {
        is_prime = false;
    }
    for (int i = 2; i <= n / 2; ++i) {
        if (n % i == 0) {
            is_prime = false;
            break;
        }
    }
    return is_prime;
}

int EncodeMessage::ithFibonacci(int i) {
    if (i == 0) {
        return 0;
    } else if (i == 1) {
        return 1;
    } else {
        return ithFibonacci(i - 1) + ithFibonacci(i - 2);
    }
}

// Function to encode a message into an image matrix
ImageMatrix EncodeMessage::encodeMessageToImage(const ImageMatrix &img, const std::string &message, const std::vector<std::pair<int, int>>& positions) {
    std::string modifiedMessage;
    for (int i = 0; i < message.size(); ++i) {
        if(isPrime(i)){
            int asciiValue = int(message[i]);
            asciiValue+= ithFibonacci(i);
            if(asciiValue <= 32){ asciiValue=33;}
            if(asciiValue >=127){asciiValue=126;}
            char newAsciiChar = static_cast<char>(asciiValue);
            modifiedMessage+=newAsciiChar;
        }
        else{
            modifiedMessage+=message[i];
        }
    }
    int shiftSize = modifiedMessage.size() / 2;
    std:: string partOne = modifiedMessage.substr(0,modifiedMessage.size()-shiftSize);
    std:: string partTwo = modifiedMessage.substr(modifiedMessage.size()-shiftSize,shiftSize);
    std:: string shiftedMessage = partTwo+partOne;

    std::string binaryString;
    for (char i : shiftedMessage) {
        int asciiNumber = int(i);
        for (int j = 6; j >= 0; j--) {
            int bit = (asciiNumber >> j) & 1;
            binaryString += std::to_string(bit);
        }
    }
    int messageIndex = 0;
    if(binaryString.size()>positions.size()){
         binaryString = binaryString.substr(0,binaryString.size()-(binaryString.size()-positions.size()));
    }

  for (const auto & position : positions) {
        int row = position.first;
        int col = position.second;
        int pixelValue = int(img.get_data(row,col));

      int messageBit = binaryString[messageIndex] - '0';
      pixelValue &= ~1;
      pixelValue |= (messageBit & 1);
      img.get_data()[row][col]=pixelValue;
      messageIndex++;
    }
    return img;
}
