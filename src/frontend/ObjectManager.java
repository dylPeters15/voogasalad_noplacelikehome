/**
 * 
 */
package frontend;

/**
 * ObjectManager is an interface used to describe classes that "manage" an
 * object. The class that implements the ObjectManager interface is responsible
 * for the setup and maintenance of the object that it manages. It can allow
 * other classes to reference the object via the getObject method. Below are a
 * few examples of how one can extend and/or implement this interface in a
 * useful way:
 * 
 * A class that is designed to work on the front end of a project may manage a
 * Parent or Scene object. For example, a class may be called PongManager
 * implements ObjectManager<Region>. The class may contain many UI elements as
 * well as logical elements that control the UI elements, but other classes only
 * need to know that PongManager creates a Region object that can be put in a
 * Scene and displayed to user to allow the user to play the classic Pong game.
 * 
 * The ObjectManager<T extends Object> interface could be extended into
 * something like CompositeManager<Collection<Object>>. This would allow client
 * code to control many objects via the same method calls, thus eliminating the
 * need for client code to loop through all the objects. The class that
 * implements the CompositeManager interface can handle looping through the
 * Collection.
 * 
 * @author Dylan Peters
 *
 */
interface ObjectManager<T extends Object> {

	/**
	 * Returns the object that is managed by the class implementing the
	 * ObjectManager interface. The object returned must be of the type
	 * specified in the generics in the class declaration. This helps ensure
	 * accurate typing.
	 * 
	 * @return the object that is managed by the class implementing the
	 *         ObjectManager interface.
	 */
	T getObject();

}
