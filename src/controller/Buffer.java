package controller;

import java.util.Queue;
/**
 * Interface for Buffer which saves the update for frontend and backend
 * 
 * @author Noah Pritt
 *
 * @param <T>
 */
interface Buffer<T> {
	// This method lets the user add an element to the end of the buffer
	void addToBuffer(T t);
	// This method lets the user read an element from the beginning of the
	// buffer
	T getBufferHead() throws ParsingErrorException;
	// This method allows the user to check if the Buffer is empty
	boolean isBufferEmpty();
	// This method allows the user to clear all the elements in buffer.
	void clear();
}
