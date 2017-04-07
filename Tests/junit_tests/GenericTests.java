package junit_tests;

import frontend.ObservableQueue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GenericTests {

    @Test
    public void test() {
        ObservableQueue<String> queue1 = new ObservableQueue<>();
        ObservableQueue<String> queue2 = new ObservableQueue<>();
        String string = "Test";
        queue1.passTo(queue2);
        queue1.add(string);
        assertEquals(string, queue2.poll());
    }

}
