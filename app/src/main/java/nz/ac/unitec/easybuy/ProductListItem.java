package nz.ac.unitec.easybuy;

import java.util.Date;

/**
 * Created by eugene on 14/11/2017.
 */

public class ProductListItem {

    private String mId;
    private String mBarcode;
    private String mName;
    private float mPrice;
    private Date mDate;
    private boolean mBox;
    private int mImg;

    public ProductListItem(String id, String barcode, String name, float price, Date date, boolean box, int img) {
        this.setId(id);
        this.setBarcode(barcode);
        this.setName(name);
        this.setPrice(price);
        this.setDate(date);
        this.setBox(box);
        this.setImg(img);
    }

    public String getId() {
        return mId;
    }

    public final void setId(String id) {
        mId = id;
    }

    public String getBarcode() {
        return mBarcode;
    }

    public void setBarcode(String barcode) {
        this.mBarcode = barcode;
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
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
