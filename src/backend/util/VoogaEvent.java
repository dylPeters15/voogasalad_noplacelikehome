package backend.util;

import java.util.EventObject;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class VoogaEvent extends EventObject {
	public VoogaEvent(VoogaEntity source) {
		super(source);
	}

	@Override
	public VoogaEntity getSource() {
		return (VoogaEntity) super.getSource();
	}
}
