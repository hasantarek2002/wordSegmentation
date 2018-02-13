package image;

import java.util.ArrayList;
import java.util.Stack;

public class HistogramProjection {

	int[][] matrix;
	int row, col;

	public HistogramProjection(int[][] matrix) {
		this.matrix = matrix;
		row = matrix.length;
		col = matrix[0].length;
	}
	
	//this method separate each line of text image and put the position of the line in a stack 
	public Stack<int[]> horizontalProjection() {
		int[] horizontalPixelCount = new int[row];
		Stack<int[]> Hstack = new Stack<int[]>();
		for (int i = 0; i < row; i++) {
			int count = 0;
			for (int j = 0; j < col; j++) {
				if (matrix[i][j] == 0)
					count++;
			}
			horizontalPixelCount[i] = count;
		}


		int enterToSecondLoop = 0;
		for (int j = 1; j < row - 1; j++) {
			int maxRow = 0;
			int minRow = 0;
			if (horizontalPixelCount[j - 1] >0) {
				minRow = j - 1;
				enterToSecondLoop = 1;
				for (int i = j; i < row - 1; i++) {

					if (horizontalPixelCount[i] > 100) {

						for (int k = i; k > minRow; k--) {
							// int value1 = horizontalPixelCount[i + 1] -
							// horizontalPixelCount[i];
							int value2 = horizontalPixelCount[k + 1] + horizontalPixelCount[k - 1]
									- (2 * horizontalPixelCount[k]); // Second
																		// derivative
																		// value
							if (value2 < 0) {
								maxRow = k - 1;
								int[] arr = { minRow, maxRow };
								//System.out.println("max value :"+maxRow+" miin row value "+maxRow-minRow);
								Hstack.push(arr);
								minRow = k;
								i = k + 1;
								break;
							}
						}
					}
				}
				maxRow=row-1;
				int[] arr = { minRow, maxRow };
				Hstack.push(arr);
				//break;
			}
			if (enterToSecondLoop == 1)
				break;
		}
		return Hstack;
	}


	//this method separate each word of each line of text image and put the position of the word in a stack 
	public Stack<int[]> verticalProjection(int minRow, int maxRow) {
		int[] verticalPixelCount = new int[col];
		Stack<int[]> Vstack = new Stack<int[]>();
		for (int j = 0; j < col; j++) {
			int count = 0;
			for (int i = minRow; i < maxRow; i++) {
				if (matrix[i][j] == 0)
					count++;
			}
			verticalPixelCount[j] = count;
		}

		for (int j = 0; j < col; j++) {
			int flag = 0;
			int minCol = 0;
			int maxCol = 0;
			while (verticalPixelCount[j] > 0) {
				if (flag == 0) {
					minCol = j;
					flag = 1;
				}
				j++;
				if (j == col)
					break;
			}
			if (flag == 1) {
				maxCol = j;
				flag = 0;
				int[] arr = { minCol, maxCol };
				Vstack.push(arr);
			}
		}
		return Vstack;

	}

	public int mode(int[] input) {

		int[] count = new int[200];
		for (int i = 0; i < input.length; i++) {
			count[input[i]]++;
		}
		// go backwards and find the count with the most occurrences
		int index = count.length - 1;
		for (int i = count.length - 2; i >= 0; i--) {
			if (count[i] >= count[index])
				index = i;
		}

		return index;
	}

}