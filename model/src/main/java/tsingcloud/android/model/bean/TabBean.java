package tsingcloud.android.model.bean;

/**
 * Created by admin on 2016/3/16.
 */
public class TabBean extends BaseBean{
    private static final long serialVersionUID = 1L;


    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int drawableId;

    private int textId;

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }
}
