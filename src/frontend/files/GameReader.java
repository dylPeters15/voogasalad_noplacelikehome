/**
 * The GameReader reads in files containing game data and returns the game as a Displayable object.
 * Therefore, the game can be shown within the GUI.
 */
package frontend.files;

import java.io.File;

import frontend.Displayable;

/**
 * @author Stone Mathers
 *
 */
public interface GameReader {
	
	/**
	 * Takes in a file and, if valid, returns a Displayable object that shows the game contained within the file.
	 * 
	 * @param File containing the game to be read in.
	 * @return A Displayable object that shows the game contained within the file.
	 */
	Displayable getGame(File gameFile) throws RuntimeException;

}
