package nz.ac.unitec.easybuy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eugene on 14/11/2017.
 */

public class ProductItemAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ProductListItem> mProducts;
    private List<ProductFilterListener> filterListeners = new ArrayList<ProductFilterListener> ();
    private List<ProductClickListener> clickListeners = new ArrayList<ProductClickListener> ();
    private ValueFilter mValueFilter;
    private ArrayList<ProductListItem> mStringFilterList;

    public ProductItemAdapter(Context context, ArrayList<ProductListItem> products) {
        mContext = context;
        mProducts = products;
        mStringFilterList = products;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnProductFilterListener (ProductFilterListener listener) {
        this.filterListeners.add(listener);
    }

    public void setOnProductClickListener (ProductClickListener listener) {
        this.clickListeners.add(listener);
    }

    public void CheckItems(boolean checked) {
        for(ProductListItem item : mProducts) {
            item.setBox(checked);
        }

        for(ProductFilterListener listener : filterListeners) {
            listener.onProductFilter(mProducts);
        }

        getFilter().filter("");
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.row_product_list, parent, false);
        }

        ProductListItem p = getProductListItem(position);

        Date date = p.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        ((TextView) view.findViewById(R.id.text_row_product)).setText(p.getName());
        ((TextView) view.findViewById(R.id.text_row_price)).setText("$" + Float.toString(p.getPrice()));
        ((TextView) view.findViewById(R.id.text_row_barcode)).setText(p.getBarcode());
        ((TextView) view.findViewById(R.id.text_row_date)).setText(sdf.format(date));
        ((ImageView) view.findViewById(R.id.image_row_product)).setImageResource(p.getImg());

        CheckBox cbProduct = (CheckBox) view.findViewById(R.id.check_row_product);
        cbProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getProductListItem((Integer) buttonView.getTag()).setBox(isChecked);
                for(ProductFilterListener listener : filterListeners) {
                    listener.onProductFilter(mProducts);
                }
            }
        });
        cbProduct.setTag(position);
        cbProduct.setChecked(p.isBox());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ProductClickListener listener : clickListeners) {
                    listener.onProductClick(position);
                }
            }
        });

        return view;
    }

    ProductListItem getProductListItem(int position) {
        return ((ProductListItem) getItem(position));
    }

    @Override
    public Filter getFilter() {
        if(mValueFilter == null) {
            mValueFilter = new ValueFilter();
        }
        return mValueFilter;
    }

    class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if((constraint != null) && (constraint.length() > 0)) {
                ArrayList<ProductListItem> filterList = new ArrayList<ProductListItem>();
                for(int i = 0; i < mStringFilterList.size(); i++) {
                    if((mStringFilterList.get(i).getName().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        ProductListItem products = new ProductListItem(
                                mStringFilterList.get(i).getId(),
                                mStringFilterList.get(i).getBarcode(),
                                mStringFilterList.get(i).getName(),
                                mStringFilterList.get(i).getPrice(),
                                mStringFilterList.get(i).getDate(),
                                mStringFilterList.get(i).isBox(),
                                R.mipmap.cart_icon);
                        filterList.add(products);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mProducts = (ArrayList<ProductListItem>) results.values;
            notifyDataSetChanged();
        }
    }
}
