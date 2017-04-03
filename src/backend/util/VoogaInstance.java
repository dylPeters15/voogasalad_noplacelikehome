package backend.util;

/**
 * Timmy
 *
 * @author Created by th174 on 3/28/2017.
 */
public abstract class VoogaInstance<T extends VoogaObject> extends VoogaObject {
    private final T template;
    private boolean isVisible;

    protected VoogaInstance(String unitName, T template) {
        super(unitName, template.getDescription(), template.getImgPath());
        this.template = template;
        isVisible = true;
    }

    public T getTemplate() {
        return template;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
