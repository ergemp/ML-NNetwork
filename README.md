# ML-NNetwork

ML-NNetwork is a Neural Network implementation for Matrix classification. 

There is two variant of this implementation 1D convolution network for one dimensional arrays and 2D convolution network for two dimensional arrays (matrices)

## WindowInterest

In order to apply the filter on the different part of the larger matrix, a smaller window (ex: 3x3 matrix) should flow over the broader matrix (ex: an image). To implement the window over the matrix windowInterest class is used. 

```declarative

        // create window interest
        WindowInterest windowInterest = new WindowInterest();
        
        // initalize the windowInterest
        // for ConvNet1D window size represents the one dimensional 3 elements (3 values for each iteration)
        // fpr ConvNet2D window size represents the two dimentional 3x3 matrix (9 elements for each iteration)
        windowInterest.initialize(3);
```

## Kernel

Kernel (also known as filter) is the values to be applied on the windowInterest. 

Kernel should be initialized with a kernel size. For ConvNet1D kernel size represents three elements for the size, for ConvNet2D kernel size represents nine elements for the size. 

The following example is for a ConvNet2D kernel implementation. 

```declarative
        // create kernel
        Kernel kernel = new Kernel(3);
        List<List<Double>> kernelVals = new ArrayList<>();

        kernelVals.add(Arrays.asList(-0.25, 0.0, 0.25));
        kernelVals.add(Arrays.asList(0.0, 0.0, 0.0));
        kernelVals.add(Arrays.asList(0.25, 0.0, -0.25));

        kernel.setKernel(kernelVals);
```

## NNetwork 

NNetwork class combines WindowInterest, Kernel and data to be applied on. In order to work with the NNetwork, WindowInterest and Kernel should be created and the data (matrix for 2D network, array for 1D network) should be prepared and set. 

NNetwork has the iteration parameter which defines the window interest to slide.  

```declarative
        // create neural network
        // and set the data, window and kernel of the network
        NNetwork nnetwork = new NNetwork();
        nnetwork.setData(redPixList);
        nnetwork.setWindowInterest(windowInterest);
        nnetwork.setKernel(kernel);

        // set the iteration of the window
        nnetwork.setIteration(1);
        nnetwork.adjustSize();
```

The following examples are the complete implementations for a custom kernel applied on a 1D array or 2D matrix. 

[ConvNet2D Custom Kernel Example](https://github.com/ergemp/ML-NNetwork/blob/main/src/main/java/org/ergemp/ConvNet2D/examples/ConvNet2DKernelExample.java)

[ConvNet1D Custom Kernel Example](https://github.com/ergemp/ML-NNetwork/blob/main/src/main/java/org/ergemp/ConvNet1D/examples/ConvNet1DKernelExample.java)

## Pooling

Reducing the size of the original matrix before or after the applied filter. ML-NNetwork supports Min, Max and Average Pooling options. To apply any of the pooling there is no need to create and initialize a kernel, but the windowInterest should be initiated. Default pool sizes are 3x3 for 2D Convolution and 3 for 1D Convolution. 

The sample code for a 1D and 2D ConvNet can be found as follows. 

### 2D ConvNet Max pooling Example
```declarative
        // create window interest
        WindowInterest windowInterest = new WindowInterest();
        windowInterest.initialize(3);

        NNetwork nnetwork = new NNetwork();
        nnetwork.setData(data);
        nnetwork.setWindowInterest(windowInterest);

        nnetwork.setIteration(3);
        nnetwork.adjustSize();

        List<List<Double>> kernelApplied = nnetwork.applyMaxPooling();
        System.out.println(kernelApplied.toString());
```
[Complete Max Pooling Example for 2D](https://github.com/ergemp/ML-NNetwork/blob/main/src/main/java/org/ergemp/ConvNet2D/examples/ConvNet2DMaxPoolingExample.java)


### 1D ConvNet Max pooling Example
```declarative
        // create window interest
        WindowInterest windowInterest = new WindowInterest();
        windowInterest.initialize(3);

        NNetwork nnetwork = new NNetwork();
        nnetwork.setData(data);
        nnetwork.setWindowInterest(windowInterest);

        nnetwork.setIteration(1);
        nnetwork.adjustSize();

        List<Double> poolApplied = nnetwork.applyMaxPooling();
        System.out.println(poolApplied.toString());
```
[Complete Max Pooling Example for 1D](https://github.com/ergemp/ML-NNetwork/blob/main/src/main/java/org/ergemp/ConvNet1D/examples/ConvNet1DMaxPoolingExample.java)


## Filtering an image with ML-NNetwork

### Reading an image to array

```declarative
        // read the image file with ImageIO
        BufferedImage bufferedImage = ImageIO.read(new File("data/image/dog3.jpg"));

        int redPix[][] = getMatrixOfImage(bufferedImage, color.red);
        int greenPix[][] = getMatrixOfImage(bufferedImage, color.green);
        int bluePix[][] = getMatrixOfImage(bufferedImage, color.blue);
```

### Applying Filter in image 

After reading the image (RGB) data to a two-dimensional array (matrix) the rest of the procedure is the same with ConvNet2D implementation. 

In this example, emboss filter for edge detection. 

Complete example of the image filtering is as follows. 

[Complete Image Filtering Example](https://github.com/ergemp/ML-NNetwork/blob/main/src/main/java/org/ergemp/ConvNet2DImageExample.java)

Before Filter

![image](data/image/dog3.jpg)

After Filter

![image](data/saved.png)
