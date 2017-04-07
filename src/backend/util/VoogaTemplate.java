package backend.util;

/**
 * @author Created by th174 on 4/5/2017.
 */
public abstract class VoogaTemplate<T extends VoogaTemplate> extends VoogaObject {

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

    public abstract T copy();

    //elevate access modifiers
    @Override
    public final T setName(String name) {
        super.setName(name);
        return (T) this;
    }

    @Override
    public final T setDescription(String description) {
        super.setDescription(description);
        return (T) this;
    }

    @Override
    public final T setImgPath(String imgPath) {
        super.setImgPath(imgPath);
        return (T) this;
    }
}
