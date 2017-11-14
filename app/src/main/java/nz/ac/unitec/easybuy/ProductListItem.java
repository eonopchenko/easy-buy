package nz.ac.unitec.easybuy;

/**
 * Created by eugene on 14/11/2017.
 */

public class ProductListItem {

    private String mProduct;
    private boolean mBox;
    private int mImg;

    public ProductListItem(String product, int img) {
        this.setProduct(product);
        this.setImg(img);
        this.setBox(true);
    }

    public String getProduct() {
        return mProduct;
    }
    public final void setProduct(String product) {
        mProduct = product;
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
