#include "ImageProcessor.h"
#include <iostream>

ImageProcessor::ImageProcessor() {

}

ImageProcessor::~ImageProcessor() {

}


std::string ImageProcessor::decodeHiddenMessage(const ImageMatrix &img) {
    // Sharpen the image
    ImageSharpening sharpening;
    ImageMatrix sharpenedMatrix = sharpening.sharpen(img,2.0);

    //Detect the edges in the sharpened image
    EdgeDetector edgeDetector;
    std::vector<std::pair<int,int>> pixels;
    pixels = edgeDetector.detectEdges(sharpenedMatrix);
    edges=pixels;

    //Decode the message
    DecodeMessage decodeMessage;
    std::string message = decodeMessage.decodeFromImage(sharpenedMatrix,pixels);

    return message;
}

ImageMatrix ImageProcessor::encodeHiddenMessage(const ImageMatrix &img, const std::string &message) {
    ImageSharpening sharpening;
    ImageMatrix sharpenedMatrix = sharpening.sharpen(img,2.0);

    //Detect the edges in the sharpened images

    EncodeMessage encodeMessage;
    ImageMatrix embeddedMatrix=encodeMessage.encodeMessageToImage(img,message,edges);
    return  embeddedMatrix;

}
