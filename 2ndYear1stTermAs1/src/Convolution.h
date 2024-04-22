// Convolution.h

#ifndef CONVOLUTION_H
#define CONVOLUTION_H


#include "ImageMatrix.h"
#include <vector>

// Class `Convolution`: Provides the functionality to convolve an image with
// a kernel. Padding is a bool variable, indicating whether to use zero padding or not.
class Convolution {
public:
    // Constructors and destructors
    Convolution(); // Default constructor
    Convolution(double** customKernel, int kernelHeight, int kernelWidth, int stride, bool padding); // Parametrized constructor for custom kernel and other parameters
    ~Convolution(); // Destructor

    Convolution(const Convolution &other); // Copy constructor
    Convolution& operator=(const Convolution &other); // Copy assignment operator

    ImageMatrix convolve(const ImageMatrix& input_image) const; // Convolve Function: Responsible for convolving the input image with a kernel and return the convolved image.
    int get_kh() const;
    int get_kw() const;

private:
    double** kernel;
    int kh;
    int kw;
    int stride;
    bool pad;


};

#endif // CONVOLUTION_H