package filter;/* this filter expects the bonding discs to be completely white: pixel value of 255 on a scale of 0..255
 * all other pixels in the image are expected to have a pixel value < 255
 * use this filter adapting eventually the package name 
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import Catalano.Imaging.FastBitmap;
import interfaces.IPullPipe;
import interfaces.IPushPipe;

import javax.swing.*;


public class CalcCentroidsFilter extends AbstractFilter<FastBitmap, Point[]> {

	private HashMap<Point, Boolean> _general = new HashMap<Point, Boolean>();
	private LinkedList<LinkedList<Point>> _figures = new LinkedList<LinkedList<Point>>();
	private FastBitmap _image;

	public CalcCentroidsFilter(IPullPipe<FastBitmap> input) throws InvalidParameterException {
		super(input);
	}
	
	public CalcCentroidsFilter(IPushPipe<Point[]> output) throws InvalidParameterException {
		super(output);
	}

	@Override
	public Point[] processFilter(FastBitmap value) {
		//BufferedImage bi = entity.getAsBufferedImage();
		_image = value;
		BufferedImage bi = value.toBufferedImage();

		for (int x = 0; x < bi.getWidth(); x++){
			for (int y = 0; y < bi.getHeight(); y++){
				int p = bi.getRaster().getSample(x, y, 0);
				if (p==255 && _general.containsKey(new Point(x,y)) == false){
					getNextFigure(bi, x, y);		//if there is a not visited white pixel, save all pixels belonging to the same figure
				}
			}
		}

		return calculateCentroids();	//calculate the centroids of all figures
	}

	private void getNextFigure(BufferedImage img, int x, int y){
		LinkedList<Point> figure = new LinkedList<Point>();
		_general.put(new Point(x,y), true);
		figure.add(new Point(x,y));
		
		addConnectedComponents(img, figure, x, y);	
		
		_figures.add(figure);
	}
	
	private void addConnectedComponents(BufferedImage img, LinkedList<Point> figure, int x, int y){
		if (x-1>=0 && _general.containsKey((new Point(x-1, y))) == false && img.getRaster().getSample(x-1, y, 0) == 255){
			_general.put(new Point(x-1,y), true);
			figure.add(new Point(x-1, y));
			addConnectedComponents(img, figure, x-1, y);		
		}
		if (x+1<img.getWidth() && _general.containsKey((new Point(x+1, y))) == false && img.getRaster().getSample(x+1, y, 0) == 255){
			_general.put(new Point(x+1,y), true);
			figure.add(new Point(x+1, y));
			addConnectedComponents(img, figure, x+1, y);		
		}
		if (y-1>=0 && _general.containsKey((new Point(x, y-1))) == false && img.getRaster().getSample(x, y-1, 0) == 255){
			_general.put(new Point(x,y-1), true);
			figure.add(new Point(x, y-1));
			addConnectedComponents(img, figure, x, y-1);		
		}
		if (y+1 < img.getHeight() && _general.containsKey((new Point(x, y+1))) == false && img.getRaster().getSample(x, y+1, 0) == 255){
			_general.put(new Point(x,y+1), true);
			figure.add(new Point(x, y+1));
			addConnectedComponents(img, figure, x, y+1);		
		}
	}
	
	private Point[] calculateCentroids(){
		Point[] centroids = new Point[_figures.size()];
		int i = 0;
		for (LinkedList<Point> figure : _figures){
			LinkedList<Integer> xValues= new LinkedList<Integer>();
			LinkedList<Integer> yValues= new LinkedList<Integer>();
			
			for (Point c : figure){
				xValues.add(c.x);
				yValues.add(c.y);
			}
			
			Collections.sort(xValues);
			Collections.sort(yValues);
			
			int xMedian = xValues.get(xValues.size() / 2);
			int yMedian = yValues.get(yValues.size() / 2);

			centroids[i] = new Point(xMedian, yMedian);
//			System.out.println(yMedian + " " + xMedian);
			_image.setRGB(yMedian, xMedian, 0,0,0);
			i++;
		}
		//JOptionPane.showMessageDialog(null, _image.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
		return centroids;
	}

	@Override
	public void run() {

	}
}
