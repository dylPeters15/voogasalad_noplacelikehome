package backend.util;

import java.util.EventObject;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class VoogaEvent extends EventObject {
    public VoogaEvent(VoogaObject source) {
        super(source);
    }

    @Override
    public VoogaObject getSource() {
        return (VoogaObject) super.getSource();
    }
}
