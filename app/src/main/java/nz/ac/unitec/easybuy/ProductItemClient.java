package nz.ac.unitec.easybuy;

import android.content.Context;
import android.os.AsyncTask;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by eugene on 14/11/2017.
 */
class ProductItemClient {

    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;

    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<ProductItem> mProductItemTable;

    private List<ProductItemAvailableListener> productListeners = new ArrayList<ProductItemAvailableListener> ();

    void setOnProductItemListener(ProductItemAvailableListener listener)
    {
        // Store the listener object
        this.productListeners.add(listener);
    }

    ProductItemClient(Context context) {

        // Create the Mobile Service Client instance, using the provided
        // Mobile Service URL and key
        try {
            mClient = new MobileServiceClient(
                    "https://easy-buy.azurewebsites.net",
                    context).withFilter(new ProductItemClient.ProgressFilter());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Extend timeout from default of 10s to 20s
        mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
            @Override
            public OkHttpClient createOkHttpClient() {
                OkHttpClient client = new OkHttpClient();
                client.setReadTimeout(20, TimeUnit.SECONDS);
                client.setWriteTimeout(20, TimeUnit.SECONDS);
                return client;
            }
        });

        // Get the Mobile Service Table instance to use
        mProductItemTable = mClient.getTable(ProductItem.class);

        // Offline Sync
        //mProductItemTable = mClient.getSyncTable("ProductItem", ProductItem.class);

        // Init local storage
        try {
            initLocalStore().get();
        } catch (InterruptedException | ExecutionException | MobileServiceLocalStoreException e) {
            e.printStackTrace();
        }

        // Load the items from the Mobile Service
        refreshItemsFromTable();
    }

    /**
     * Refresh the list with the items in the Table
     */
    public void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the adapter
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){

            List<ProductItem> results = new ArrayList<>();

            @Override
            protected Void doInBackground(Void... params) {

                try {
                    results = refreshItemsFromMobileServiceTable();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                for (ProductItemAvailableListener listener : productListeners) {
                    listener.onProductItemAvailable(results);
                }
            }
        };

        runAsyncTask(task);
    }

    /**
     * Refresh the list with the items in the Mobile Service Table
     */
    private List<ProductItem> refreshItemsFromMobileServiceTable() throws ExecutionException, InterruptedException {
        try {
            return mProductItemTable.execute().get();
        } catch (MobileServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Initialize local storage
     * @return
     * @throws MobileServiceLocalStoreException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    MobileServiceSyncContext syncContext = mClient.getSyncContext();

                    if (syncContext.isInitialized())
                        return null;

                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);

                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                    tableDefinition.put("id", ColumnDataType.String);
                    tableDefinition.put("product", ColumnDataType.String);

                    localStore.defineTable("ProductItem", tableDefinition);

                    SimpleSyncHandler handler = new SimpleSyncHandler();

                    syncContext.initialize(localStore, handler).get();

                } catch (final Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        return runAsyncTask(task);
    }

    /**
     * Run an ASync task on the corresponding executor
     * @param task
     * @return
     */
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class ProgressFilter implements ServiceFilter {

        @Override
        public ListenableFuture<ServiceFilterResponse> handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback) {

            final SettableFuture<ServiceFilterResponse> resultFuture = SettableFuture.create();

            ListenableFuture<ServiceFilterResponse> future = nextServiceFilterCallback.onNext(request);

            Futures.addCallback(future, new FutureCallback<ServiceFilterResponse>() {
                @Override
                public void onFailure(Throwable e) {
                    resultFuture.setException(e);
                }

                @Override
                public void onSuccess(ServiceFilterResponse response) {
                    resultFuture.set(response);
                }
            });

            return resultFuture;
        }
    }

    public ProductItem addItemToMobileServiceTable(ProductItem item) throws ExecutionException, InterruptedException {
        return mProductItemTable.insert(item).get();
    }
}
