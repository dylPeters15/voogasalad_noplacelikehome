import java.util.LinkedList;
import java.util.Queue;
/**
 * This is a Buffer designed to hold information/updates from frontend/backend.
 * We designed this way so that it's possible to save the update in the
 * communication controller without truly informing the updates
 * 
 * @author ncp14
 *
 * @param <T>
 */
public class MyBuffer<T> implements Buffer<T> {
	private Queue<T> myBuffer;
	private CommunicationController myListener;
	public MyBuffer() {
		this.myBuffer = new LinkedList<T>();
	}
	@Override
	public void addToBuffer(T t) {
		this.myBuffer.add((T) t);
	}
	@Override
	public T getBufferHead() throws ParsingErrorException {
		return this.myBuffer.poll();
	}
	@Override
	public boolean isBufferEmpty() {
		return this.myBuffer.isEmpty();
	}
	@Override
	public void clear() {
		this.myBuffer.clear();
	}
}