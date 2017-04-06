package backend.util;

/**
 * @author Created by th174 on 4/5/2017.
 */
public abstract class VoogaTemplate extends VoogaObject {

    public VoogaTemplate() {
        this("");
    }

    public VoogaTemplate(String name) {
        this(name, "");
    }

    public VoogaTemplate(String name, String description) {
        this(name, description, "");
    }

    public VoogaTemplate(String name, String description, String imgPath) {
        super(name, description, imgPath);
    }

    //elevate access modifiers
    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    public void setImgPath(String imgPath) {
        super.setImgPath(imgPath);
    }
}
