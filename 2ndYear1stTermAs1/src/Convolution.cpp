#include "Convolution.h"

// Default constructor
Convolution::Convolution() {
}

// Parametrized constructor for custom kernel and other parameters
Convolution::Convolution(double** customKernel, int kh, int kw, int stride_val, bool pad):kernel(customKernel),kh(kh),kw(kw),stride(stride_val),pad(pad){
}

// Destructor
Convolution::~Convolution() {


}


// Copy constructor
Convolution::Convolution(const Convolution &other): kh(other.kh), kw(other.kw){
        kernel = new double*[kh];
        for (int i = 0; i < kh; ++i) {
            kernel[i]=new double[kw];
        }
        for (int i = 0; i < kh; ++i) {
            for (int j = 0; j < kw; ++j) {
                kernel[i][j]=other.kernel[i][j];
            }
        }
    }



// Copy assignment operator
Convolution& Convolution::operator=(const Convolution &other) {
    if(this==&other){
        return *this;
    }
    //de-allocation
    for (int i = 0; i < kh; ++i) {
        delete [] kernel[i];
    }
    delete [] kernel;

    // new allocation
    kh = other.kh;
    kw = other.kw;
    kernel = new double*[kh];
    for (int i = 0; i < kh; ++i) {
        kernel[i]=new double[kw];
    }
    for (int i = 0; i < kh; ++i) {
        for (int j = 0; j < kw; ++j) {
            kernel[i][j]=other.kernel[i][j];
        }
    }
    return *this;
}

int Convolution::get_kh() const {
    return kh;
}
int Convolution::get_kw() const {
    return kw;
}

// Convolve Function: Responsible for convolving the input image with a kernel and return the convolved image.
ImageMatrix Convolution::convolve(const ImageMatrix& input_image) const {
    int h = input_image.get_height();
    int w = input_image.get_width();
    int rows = (h - kh) / stride + 1;;
    int cols =(w - kw) / stride + 1;

    ImageMatrix convolvedImage(rows,cols);
    double** inputImageData = input_image.get_data();

    if (pad) {
        int padded_h = h + kh - 1;
        int padded_w = w + kw - 1;
        ImageMatrix paddedInputImage(padded_h,  padded_w);
        double** paddedInputData = paddedInputImage.get_data();


        // Copying data
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                paddedInputData[i + kh/2][j + kw/2] = inputImageData[i][j];
            }
        }

        int out_h =(padded_h - kh) / stride + 1;
        int out_w = (padded_w - kw) / stride + 1;
        ImageMatrix paddedImage(out_h,out_w);
        // Perform convolution using the padded image
        for (int i = 0; i < out_h; i++) {
            for (int j = 0; j < out_w; j++) {
                double sum = 0.0;
                for (int k = 0; k < kh; k++) {
                    for (int l = 0; l < kw; l++) {
                        sum += (kernel[k][l] * paddedInputData[i*stride + k][j*stride + l]);
                    }
                }
                paddedImage.get_data()[i][j] = sum;
            }
        }
        return paddedImage;
    }

    else {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double sum = 0.0;
                for (int k = 0; k < kh; k++) {
                    for (int l = 0; l < kw; l++) {
                        sum += (kernel[k][l] * input_image.get_data()[i*stride + k][j*stride + l]);
                    }
                }
                convolvedImage.get_data()[i][j] = sum;
            }
        }
        return convolvedImage;
    }
}

