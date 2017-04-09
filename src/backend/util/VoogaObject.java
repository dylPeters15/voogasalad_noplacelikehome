package backend.util;

/**
 * @author Created by th174 on 3/31/2017.
 */
public interface VoogaObject {
	<U extends VoogaObject> U copy();

	String getName();

	String getDescription();

	String getImgPath();
}
