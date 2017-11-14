package nz.ac.unitec.easybuy;

/**
 * Created by eugene on 14/11/2017.
 */

public class ProductListItem {

    private String mId;
    private String mName;
    private float mPrice;
    private boolean mBox;
    private int mImg;

    public ProductListItem(String id, String name, float price, int img) {
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setBox(true);
        this.setImg(img);
    }

    public String getId() {
        return mId;
    }

    public final void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        this.mPrice = price;
    }

    public boolean isBox() {
        return mBox;
    }

    public void setBox(boolean box) {
        this.mBox = box;
    }

    public int getImg() {
        return mImg;
    }

    public void setImg(int img) {
        this.mImg = img;
    }
}
