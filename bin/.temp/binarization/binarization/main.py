import numpy as np
import cv2
from skimage import data_dir,io,transform,color,filters

def convert_Gray(fileRead):
     rgbRead = io.imread(fileRead)

     grayRead = cv2.cvtColor(rgbRead, cv2.COLOR_RGB2GRAY)
     localBinary = cv2.adaptiveThreshold(grayRead, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 25, 10)

     return localBinary

files = 'inputFilePath/*.jpg'
coll = io.ImageCollection(files, load_func = convert_Gray)

for i in range(len(coll)):
    io.imsave('outputFilePath/' + np.str(i) + '.png', coll[i])