package nz.ac.unitec.easybuy;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eugene on 1/11/2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, ProductItemAvailableListener {

    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private View mView;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == Activity.RESULT_OK) {
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView)view.findViewById(R.id.view_map);
        if(mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MapsInitializer.initialize(getContext());
        }

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mGoogleMap.setPadding(0, 0, 0, 140);
    }

    @Override
    public void onProductItemAvailable(List<ProductItem> products) {
        List<ProductListItem> filter = new ArrayList<ProductListItem>();

        for(ProductItem product : products) {
            filter.add(new ProductListItem(product.getName(), R.mipmap.ic_launcher));
        }

        ProductItemAdapter adapter = new ProductItemAdapter(getActivity(), (ArrayList<ProductListItem>) filter);
        ListView lvProducts = (ListView) mView.findViewById(R.id.list_products);
        lvProducts.setAdapter(adapter);

        mView.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
    }
}
