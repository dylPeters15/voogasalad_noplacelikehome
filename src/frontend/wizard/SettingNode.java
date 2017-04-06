/**
 * 
 */
package frontend.wizard;

import frontend.BaseUIManager;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * @author Stone Mathers
 *
 */
public abstract class SettingNode extends BaseUIManager<Region> {

	private HBox hb;
	private String myName;
	
	public SettingNode(String name){
		myName = name;
		hb = initHBox();
	}
	
	public String getName(){
		return myName;
	}
	
	protected abstract HBox initHBox();
	
	@Override
	public Region getObject(){
		return hb;
	}
	
}
