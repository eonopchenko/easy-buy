package nz.ac.unitec.easybuy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by eugene on 14/11/2017.
 */

public class ProductItemAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ProductListItem> mProducts;

    public ProductItemAdapter(Context context, ArrayList<ProductListItem> products) {
        mContext = context;
        mProducts = products;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.row_product_list, parent, false);
        }

        ProductListItem p = getProductListItem(position);

        ((TextView) view.findViewById(R.id.text_row_product)).setText(p.getProduct());
        ((ImageView) view.findViewById(R.id.image_row_product)).setImageResource(p.getImg());

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.check_row_product);
        cbBuy.setTag(position);
        cbBuy.setChecked(p.isBox());
        return view;
    }

    ProductListItem getProductListItem(int position) {
        return ((ProductListItem) getItem(position));
    }
}
