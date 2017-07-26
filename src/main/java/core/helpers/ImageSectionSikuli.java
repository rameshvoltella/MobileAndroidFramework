package core.helpers;

import org.apache.log4j.Logger;
import org.sikuli.script.Finder;
import org.sikuli.script.Match;

import java.awt.image.BufferedImage;

public class ImageSectionSikuli {

	private final static Logger log = Logger.getLogger(ImageSectionSikuli.class);
	
	/**
	 * Finds subimage in a mother image.
	 * 
	 * @param motherImage
	 * @param subImageFileName
	 * @param coordinatesFound holder for the found coordinates
	 * @return a percentage of the similarities of the searched image and the
	 *         section in mother image
	 */
	public static double findSubImage(BufferedImage motherImage,
			String subImageFileName, Coordinates coordinatesFound) {

		double result = 0;
		// Add the image to work with to the Finder
		Finder f2 = new Finder(motherImage);

		// See if the subImage is found. A percentage to match will be available
		if (findMyImage(f2, subImageFileName)) {
			// Get the next/first match we found.
			// Match Class holds all the information of the match
			Match m = f2.next();
			// As an example let's print out the coordinates and how good the
			// match is
			coordinatesFound.setX(m.getX());
			coordinatesFound.setY(m.getY());
			result = m.getScore();

			System.out
					.println(String
							.format("Section found at coordonates %d/%d, with a score of %f match",
									coordinatesFound.getX(),
									coordinatesFound.getY(), result));
		} else {
			log.info("Section NOT found");
		}

		return result;
	}

	/**
	 * @param f
	 *            - Finder
	 * 
	 * @param imgToCompare
	 *            - String
	 * 
	 *
	 * @return
	 */
	private static boolean findMyImage(Finder f, String imgToCompare) {
		f.find(imgToCompare);
		// Returns if at least one match was found
		return f.hasNext();
	}

}
