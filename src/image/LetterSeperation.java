package image;

import java.awt.image.BufferedImage;
import java.util.Stack;

public class LetterSeperation {

	private int col, row;
	private int[][] matrix;

	public LetterSeperation(int[][] matrix) {
		this.matrix = matrix;
		row = matrix.length;
		col = matrix[0].length;
		// System.out.println(" width " + col + " height " + matrix.length);
		// printMatrix(matrix);
	}
	// this method separate each character from word and put the position in a stack 
	public Stack<int[]> letterSeperationFromWord() {
		int[] verticalPixelCount = new int[col];
		Stack<int[]> Vstack = new Stack<int[]>();

		for (int j = 0; j < col; j++) {
			int count = 0;
			for (int i = 0; i < row; i++) {
				// System.out.println(i + " " + j);
				if (matrix[i][j] == 0)
					count++;
			}
			verticalPixelCount[j] = count;
		}

		for (int j = 0; j < col; j++) {
			int flag = 0;
			int minColIndex = 0;
			int maxColIndex = 0;
			while (verticalPixelCount[j] > 0) {
				if (flag == 0) {
					minColIndex = j;
					flag = 1;
				}
				j++;
				if (j == col)
					break;
			}
			if (flag == 1) {
				maxColIndex = j;
				flag = 0;
				int[] arr = { minColIndex, maxColIndex };
				Vstack.push(arr);
			}
		}
		return Vstack;

	}

	public void printMatrix(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}

}
