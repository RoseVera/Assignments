#include "ImageSharpening.h"

// Default constructor
ImageSharpening::ImageSharpening() {

}

ImageSharpening::~ImageSharpening(){

}

ImageMatrix ImageSharpening::sharpen(const ImageMatrix& input_image, double k) {
    double* blurringKernel[3] = {
            new double[3]{1.0, 1.0, 1.0},
            new double[3]{1.0, 1.0, 1.0},
            new double[3]{1.0, 1.0, 1.0}
    };

    Convolution convolution1(blurringKernel,3,3,1, true);

    ImageMatrix noisyImage = convolution1.convolve(input_image)*(1/9.0);
    ImageMatrix m1 =input_image - noisyImage;
    ImageMatrix m2 =m1*k;
    ImageMatrix sharpenedImage = input_image + m2;

    for (int i = 0; i < 3; ++i) {
        delete[] blurringKernel[i];
    }

     // Clipping the Values
    for (int i = 0; i < input_image.get_height(); ++i) {
        for (int j = 0; j < input_image.get_width(); ++j) {
            if(sharpenedImage.get_data(i,j)<0.0){
                sharpenedImage.get_data()[i][j]=0.0;
            }
            else if (sharpenedImage.get_data(i,j)>255.0){
                sharpenedImage.get_data()[i][j]=255.0;
            }
        }
    }
    return sharpenedImage;

}
