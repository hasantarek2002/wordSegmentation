package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MatraClipOfWord {
	public static int count = 1;
	private int rowIndex, columnIndex, row, col, bottomLine, upperLine;
	private int[][] matrix;

	//////////////////// my way to seperate letter from word///////////////

	public MatraClipOfWord(int[][] matrix) {
		this.matrix = matrix;
		rowIndex = 0;
		columnIndex = 0;
		row = matrix.length;
		col = matrix[0].length;
		// System.out.println("matraclip class: height -> " + row + " width -> "
		// + col);

	}
	
	//this method separate each character and put the position of the separated character in 2D array 
	public int[][] clippingOfMatra() throws IOException {
		int maxnumberOfPixelRowIndex = findMaximumNumberOfPixelInImageWithAnewArray();
		upperLine = maxnumberOfPixelRowIndex;
		rowIndex = upperLine;
		bottomLine = findBottomLineOfWord(upperLine);
		int firstBlackPixelInMatra = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < col; i++) {
			if (matrix[upperLine][i] == 0) {
				firstBlackPixelInMatra = i;
				// System.out.println("firstBlackPixelInMatra at position : " +
				// i);
				break;
			}
		}

		for (int i = firstBlackPixelInMatra; i < col; i++) {
			rowIndex = upperLine;
			columnIndex = firstBlackPixelInMatra;
			int outerFound = 0;
			int thirdLoopExit = 0;
			for (; rowIndex < bottomLine; rowIndex++) {
				for (; columnIndex < col; columnIndex++) {
					if (rowIndex + 1 != bottomLine && columnIndex + 1 != col) {
						if (matrix[rowIndex + 1][columnIndex + 1] == 0) {
							// check for bottom corner or down
							while ((rowIndex + 1) != bottomLine && (columnIndex + 1) != col
									&& matrix[rowIndex + 1][columnIndex + 1] == 0) {
								// check for right-down corner
								rowIndex += 1; // assign new position
								columnIndex += 1;
								firstBlackPixelInMatra = columnIndex;
								i = columnIndex;
							}

							int temp1 = rowIndex + 1;
							int flag1 = 0;
							if (temp1 != bottomLine) { // boundary condition
								for (; temp1 < bottomLine; temp1++) {
									// check for another intersect point like
									// jho
									// letter towards down
									if (matrix[temp1][columnIndex] == 0) {
										flag1 = 1;
										rowIndex = temp1; // assign new position
										break;
									}
								}
							}

							if (flag1 == 1) {
								int f = 0;
								// System.out.println("flag1 if condition main
								// execution " + rowIndex + " " + columnIndex);
								while (rowIndex + 1 != bottomLine && columnIndex + 1 != col) {

									while ((rowIndex + 1) != bottomLine && (columnIndex + 1) != col
											&& matrix[rowIndex][columnIndex + 1] == 0) {
										// check for right-down corner
										rowIndex += 1; // assign new position
										columnIndex += 1;
										i = columnIndex;
										// System.out.println("right-down " +
										// columnIndex);
										firstBlackPixelInMatra = columnIndex;

										while ((rowIndex - 1) != upperLine && (columnIndex + 1) != col
												&& matrix[rowIndex - 1][columnIndex + 1] == 0) {
											// check for right-top corner
											rowIndex -= 1; // assign new
															// position
											columnIndex += 1;
											i = columnIndex;
											// System.out.println("right up " +
											// columnIndex);
											firstBlackPixelInMatra = columnIndex;
										}
										f = 1;
									}

									if (f == 0 && rowIndex + 1 != bottomLine) {
										rowIndex += 1;
										// System.out.println("straight down");
										continue;
									}
									if (f == 1 || rowIndex + 1 == bottomLine) {
										firstBlackPixelInMatra = columnIndex;
										i = columnIndex;
										// System.out.println("add to list " +
										// columnIndex);
										list.add(columnIndex);
										thirdLoopExit = 1;
										break;
									}
								}
								if (thirdLoopExit == 1) {
									break;
								}
							}

							int temp2 = rowIndex - 1, flag2 = 0;
							if (temp2 != upperLine) { // boundary condition

								for (; temp2 > upperLine; temp2--) {
									// check for another intersect point
									// like
									// pho letter towards up
									if (matrix[temp2][columnIndex] == 255) {
										flag2 = 1;
										rowIndex = temp2 + 1; // assign new
																// position
										break;
									}
								}
							}

							if (flag2 == 1) {
								while ((rowIndex - 1) != upperLine && (columnIndex + 1) != col
										&& matrix[rowIndex - 1][columnIndex + 1] == 0) {
									// check for right-top corner
									rowIndex -= 1; // assign new position
									columnIndex += 1;
									i = columnIndex;
									// System.out.println("right up " +
									// columnIndex);
									firstBlackPixelInMatra = columnIndex;
								}

								while ((rowIndex + 1) != bottomLine && (columnIndex + 1) != col
										&& matrix[rowIndex + 1][columnIndex + 1] == 0) {
									// check for right-down corner
									rowIndex += 1; // assign new position
									columnIndex += 1;
									i = columnIndex;
									// System.out.println("right-down " +
									// columnIndex);
									firstBlackPixelInMatra = columnIndex;
								}
								// System.out.println("add to list second if
								// execution " + columnIndex);
								list.add(columnIndex);
								thirdLoopExit = 1;
								break; // break third loop
							}

							if (columnIndex + 1 != col && rowIndex + 1 != bottomLine) {
								continue;
							} else {
								// System.out.println("boundary row or colum " +
								// columnIndex);
								list.add(columnIndex);
								thirdLoopExit = 1;
								break; // break third loop
							}
						}
					}
				} // Third loop ending bracket
				if (thirdLoopExit == 1) {
					outerFound = 1;
					break;
				}

			} // Second Loop ending bracket
		} // First Loop ending bracket

		for (int i = 0; i < list.size(); i++) {
			// System.out.println(list.get(i));
			if (list.get(i) + 1 != col) {
				matrix[upperLine][list.get(i) + 1] = 255;
			} else {
				matrix[upperLine][list.get(i)] = 255;
			}

		}

		BufferedImage image = matrixToImageConversion(matrix);
		//ImageIO.write(image, "png", new File("output_image_word_withSeperate_letter\\" + count + ".png"));
		count += 1;
		return matrix;

	}

	private int findMaximumNumberOfPixelInImageWithAnewArray() {
		int positionOfRowIndex = 0;
		int maxPixelOfImage = 0;
		for (int i = 0; i < row; i++) {
			int countOfPixelInEachRow = 0;
			for (int j = 0; j < col; j++) {
				if (matrix[i][j] == 0) {
					countOfPixelInEachRow += 1;
				}
			}
			if (maxPixelOfImage < countOfPixelInEachRow) {
				maxPixelOfImage = countOfPixelInEachRow;
				positionOfRowIndex = i;
			}
		}
		// System.out.println("maximum pixel row index is : " +
		// positionOfRowIndex);
		return positionOfRowIndex;

	}

	private int findBottomLineOfWord(int upper) {
		int bottom = upper + 1;

		for (int i = upper + 1; i < row; i++) {
			int countOfPixelInEachRow = 0;
			for (int j = 0; j < col; j++) {
				if (matrix[i][j] == 0) {
					countOfPixelInEachRow += 1;
				}
			}
			if (countOfPixelInEachRow == 0) {
				bottom = i;
				break;
			}

		}
		// System.out.println("bottom row index is : " + bottom);
		return bottom;

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
