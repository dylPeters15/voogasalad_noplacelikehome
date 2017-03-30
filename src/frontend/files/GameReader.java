package frontend.files;

import java.io.File;

import frontend.Displayable;

/**
 * @author Stone Mathers
 *
 */
public interface GameReader {
	
	Displayable getGame(File gameFile);

}
