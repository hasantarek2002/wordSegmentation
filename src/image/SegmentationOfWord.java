package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;

public class SegmentationOfWord {

	int[][] matrix;

	public SegmentationOfWord(int[][] matrix) {
		this.matrix = matrix;
	}

	public void wordSeperationOfImage() throws IOException {
		int wordImageCount = 1;
		int letterImageCount=1;

		HistogramProjection pr = new HistogramProjection(matrix);
		Stack<int[]> newHstack = pr.horizontalProjection();
		Stack<int[]> Hstack = new Stack<int[]>();

		//////// reverse the stack for maintaining input sequence of
		//////// image////////////
		while (!newHstack.empty()) {
			Hstack.push(newHstack.pop());
		}

		while (!Hstack.empty()) {
			int[] hArray = Hstack.peek();
			int minRow = hArray[0];
			int maxRow = hArray[1];
			Stack<int[]> newVstack = pr.verticalProjection(minRow, maxRow);
			Stack<int[]> Vstack = new Stack<int[]>();

			//////// reverse the stack for maintaining input sequence of
			//////// image////////////
			while (!newVstack.empty()) {
				Vstack.push(newVstack.pop());
			}

			while (!Vstack.empty()) {
				int[] vArray = Vstack.peek();
				int minCol = vArray[0];
				int maxCol = vArray[1];

				int height = maxRow - minRow;
				int width = maxCol - minCol;
				int[][] matrixOfWord = new int[height][width];

				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						matrixOfWord[i][j] = matrix[i + minRow][j + minCol];
					}
				}
			

				BufferedImage image = matrixToImageConversion(matrixOfWord);
				//ImageIO.write(image, "png", new File("connected_components_image_full_word\\" + wordImageCount + ".png"));
				wordImageCount += 1;
				MatraClipOfWord mp = new MatraClipOfWord(matrixOfWord);
				int[][] wordClippedMatrix = mp.clippingOfMatra();	//this matrix contains the letter seperation position
				
				LetterSeperation ls=new LetterSeperation(wordClippedMatrix);
				Stack<int[]> rStack=ls.letterSeperationFromWord();
				Stack<int[]> letterStack=new Stack<int[]>();
				
				//////// reverse the stack for maintaining input sequence of
				//////// image////////////
				while (!rStack.empty()) {
					letterStack.push(rStack.pop());
				}
				while(!letterStack.isEmpty()){
					int []letterArray = letterStack.peek();
					int minColIndexOfLetter = letterArray[0];
					int maxColIndexOfLetter = letterArray[1];
					int letterHeight = maxRow - minRow;
					int letterWidth=maxColIndexOfLetter-minColIndexOfLetter;
					int [][]matrixOfLetter=new int[letterHeight][letterWidth];
					
					for (int i = 0; i < letterHeight; i++) {
						for (int j = 0; j < letterWidth; j++) {
							matrixOfLetter[i][j] = wordClippedMatrix[i][j + minColIndexOfLetter];
						}
					}
					
					BufferedImage img = matrixToImageConversion(matrixOfLetter);
					
					// write the image into "output_image_with_letter" folder of this project
					ImageIO.write(img, "png", new File("output_image_with_letter\\" + letterImageCount + ".png"));
					letterImageCount+=1;
					System.out.println("Letter : "+letterImageCount);
					letterStack.pop();
				}
				
				Vstack.pop();
			}
			Hstack.pop();
		}
	}

	private BufferedImage matrixToImageConversion(int[][] arr) {

		int xLength = arr.length;
		int yLength = arr[0].length;
		BufferedImage img = new BufferedImage(yLength, xLength, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < xLength; x++) {
			for (int y = 0; y < yLength; y++) {
				int grey = arr[x][y];
				int argb = (255 << 24) | (grey << 16) | (grey << 8) | grey;
				img.setRGB(y, x, argb);

			}
		}
		return img;
	}

}
