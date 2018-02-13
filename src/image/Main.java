package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) {

		try {
			 //File file = new File("C:\\Users\\Hasan\\Desktop\\e.png");
			File file = new File("input_image\\t1.png");
			BufferedImage inputImage = ImageIO.read(file);

			ImageConversion im = new ImageConversion();

			BufferedImage grayScaleImage = im.colorImageToGrayScaleImageConversion(inputImage);


			Threshold th = new Threshold();
			int thresholdValue = th.otsuThresholdMethod(grayScaleImage);
			//System.out.println("Threshold value is " + thresholdValue);

			BufferedImage binaryImage = im.grayScaleToBinaryImageConversion(grayScaleImage, thresholdValue);
			int binaryImageMatrix[][] = im.imageIntoMatrixConvertion(binaryImage);
//			im.printMatrix(binaryImage, binaryImageMatrix);
			
			//word segmentation
			SegmentationOfWord sg = new SegmentationOfWord(binaryImageMatrix);
			sg.wordSeperationOfImage();

			System.out.println("program successfully terminated.");
			//System.out.println("Output shows in connected_components_image folder.");
		} catch (IOException e) {
			System.out.println("File can not open");
		}

	}

}
