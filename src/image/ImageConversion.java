package image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageConversion {

	public BufferedImage colorImageToGrayScaleImageConversion(BufferedImage img) {
		int col = img.getWidth();
		int row = img.getHeight();

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Color c = new Color(img.getRGB(j, i));
				int green = (int) c.getGreen();
				int red = (int) c.getRed();
				int blue = (int) c.getBlue();
				int sum = (red + green + blue) / 3;
				Color edited = new Color(sum, sum, sum);
				img.setRGB(j, i, edited.getRGB());
			}
		}
		return img;
	}

	public BufferedImage grayScaleToBinaryImageConversion(BufferedImage img, int value) {
		int col = img.getWidth();
		int row = img.getHeight();
		int newPixelvalue;

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Color c = new Color(img.getRGB(j, i));
				int green = (int) c.getGreen();
				// System.out.println(green);
				if (green < value)
					newPixelvalue = 0;
				else
					newPixelvalue = 255;
				newPixelvalue = (255 << 24) | (newPixelvalue << 16) | (newPixelvalue << 8) | newPixelvalue;
				img.setRGB(j, i, newPixelvalue);
			}
		}
		return img;
	}

	public int[][] imageIntoMatrixConvertion(BufferedImage img) {
		int col = img.getWidth();
		int row = img.getHeight();

		int[][] arr = new int[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Color c = new Color(img.getRGB(j, i));
				int green = (int) c.getGreen();
				arr[i][j] = green;

				// Color c = new Color(img.getRGB(j, i));
				// int alpha =c.getAlpha();
				// int green = (int) c.getGreen();
				// int red = (int) c.getRed();
				// int blue = (int) c.getBlue();
				// //int argb=colorToRGB(alpha, red, green, blue);
				// int argb=img.getRGB(j, i);
				// argb = (255 << 24) | (argb << 16) | (argb << 8) | argb;
				// //argb = (255 << 24) & (argb << 16) & (argb << 8) & argb;
				// //int argb = (alpha << 24) | (red << 16) | (green << 8) |
				// blue;
				// //int pixel=alpha >> 24 | red >> 16 | green >> 8 | blue;
				// //arr[i][j] = green;
				// arr[i][j] = argb;
			}
		}
		return arr;
	}

	public BufferedImage matrixToImageConversion(int[][] matrix) {

		int xLength = matrix.length;
		int yLength = matrix[0].length;
		BufferedImage img = new BufferedImage(yLength, xLength, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < xLength; x++) {
			for (int y = 0; y < yLength; y++) {
				// img.setRGB(x, y, matrix[x][y]);
				int grey = matrix[x][y];
				int argb = (255 << 24) | (grey << 16) | (grey << 8) | grey;
				img.setRGB(y, x, argb);

			}
		}
		return img;
	}

	public void printMatrix(BufferedImage img, int[][] arr) {
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}

	private int colorToRGB(int alpha, int red, int green, int blue) {
		int newPixel = alpha;
		newPixel = newPixel << 8;
		newPixel += red;
		newPixel = newPixel << 8;
		newPixel += green;
		newPixel = newPixel << 8;
		newPixel += blue;

		return newPixel;
	}

}
