package enf.android.mysqlproductrecyclerview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by enfonseca on 5/20/16.
 */
public class BackgroundTask extends AsyncTask<Void,Product,Void> implements RecyclerAdapter.OnItemClick {

    Context ctx;
    Activity activity;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Product> arrayList = new ArrayList<>();
    ProgressDialog progressDialog;
    ArrayList<ArrayList<Integer>> ArrayImageIDsArray=new ArrayList<>();


    public BackgroundTask(Context ctx) {
        this.ctx = ctx;
        activity = (Activity) ctx;
    }

    String json_string = "http://172.16.40.247/Products/get_product_details.php";

    @Override
    protected void onPreExecute() {
        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter(arrayList,ctx,this);
        recyclerView.setAdapter(adapter);
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setTitle("List is Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();


    }

    @Override
    protected void onProgressUpdate(Product... values) {
        arrayList.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL(json_string);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line+"\n");

            }

            httpURLConnection.disconnect();

            String json_string = stringBuilder.toString().trim();

            JSONObject jsonObject = new JSONObject(json_string);

            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

            int count =0;

            while(count<jsonArray.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                count++;



                Product product = new Product(JO.getInt("productId"),JO.getString("name"),JO.getInt("quantity"),JO.getString("description"),JO.getDouble("price"));

                JSONArray jsonArrayimIDs = JO.getJSONArray("images");

                ArrayList<Integer> arrayListActual=new ArrayList<>();
                int countJ=0;
                while (countJ<jsonArrayimIDs.length()){
                    JSONObject joIMID = jsonArrayimIDs.getJSONObject(countJ);
                    arrayListActual.add(joIMID.getInt("idImagem"));
                    countJ++;
                }
                ArrayImageIDsArray.add(arrayListActual);
                publishProgress(product);
                Thread.sleep(100);
            }

            Log.d("JSON_STRING", json_string);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    @Override
    public void onClickItem(View caller, int position) {
        Toast.makeText(ctx,"position: "+position,Toast.LENGTH_LONG).show();
        Product product=arrayList.get(position-1);
         Intent intent = new Intent(ctx,Show_Product_Details.class);
        intent.putExtra("Product", (Serializable) product);
        intent.putExtra("ListImageID",ArrayImageIDsArray.get(position-1));
        ctx.startActivity(intent);

    }
}
