package image;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Threshold {
	
	// This method returns the threshold value
	public int otsuThresholdMethod(BufferedImage img) {
		int threshold = 0;
		float maxVariance = 0;

		int[] his = grayscaleTOHistogramConversion(img);
		int totalNumberOfPixel = 0;

		for (int i = 0; i < 256; i++) {
			totalNumberOfPixel += his[i];
		}

		for (int t = 0; t < 256; t++) {
			float wb = 0, wf = 0, mb = 0, mf = 0, sumB = 0, sumF = 0, sumN2 = 0, sumN1 = 0, betweenVariance = 0;
			for (int i = 0; i < t; i++) {
				sumN1 += his[i];
				sumB = sumB + (i * his[i]);

			}
			wb = sumN1 / totalNumberOfPixel;
			if (wb == 0)
				continue;
			mb = sumB / sumN1;

			for (int i = t; i < 256; i++) {
				sumN2 += his[i];
				sumF = sumF + (i * his[i]);

			}

			wf = sumN2 / totalNumberOfPixel;
			if (wf == 0)
				break;
			mf = sumF / sumN2;

			betweenVariance = wb * wf * (mb - mf) * (mb - mf);
			if (betweenVariance > maxVariance) {
				maxVariance = betweenVariance;
				threshold = t;
			}

			
		}

		return threshold;
	}

	//this method creat histogram
	private int[] grayscaleTOHistogramConversion(BufferedImage img) {
		int[] his = new int[256];

		for (int i = 0; i < 256; i++) {
			his[i] = 0;
		}

		int col = img.getWidth();
		int row = img.getHeight();

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Color c = new Color(img.getRGB(j, i));
				int green = (int) c.getGreen();
				his[green]++;
			}

		}
		return his;
	}
}
