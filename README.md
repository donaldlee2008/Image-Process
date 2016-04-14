# Image-Process
Using Image Processing algorithms to process images. Include the following methods:


1. Using erosion and topHat operators to get the skeleton of an image. 

  The algorithm makes n successive erosions of objects until the objects are completely eroded (stop condition). The number n of iterations is variable and depends of the size of the objects to erode. In Java, this is coded by a while loop.
  The final skeleton is the union of n partial skeletons as skel = U_{i ∈[…n]} [ e_i - (e_i ο b) ]. Each partial skeleton is obtained by a Top Hat on an eroded image e_i using the structuring element b = Square (3x3). And the eroded image is obtained with a structuring element Cross (3x3).
