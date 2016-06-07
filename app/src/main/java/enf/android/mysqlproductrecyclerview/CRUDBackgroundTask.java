package enf.android.mysqlproductrecyclerview;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Dev02 on 29/05/2016.
 */
public class CRUDBackgroundTask extends AsyncTask<String,Void,String>{

    Context ctx;
    String prefixoURL;

    public CRUDBackgroundTask(Context context,String prefURL) {

        this.ctx=context;
        this.prefixoURL=prefURL;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String method=params[0];

        if (method.equals("insert"))
        {

            String ins_url = prefixoURL+"/Products/insert_product.php";

            String name=params[1];
            String quantity=params[2];
            String description=params[3];
            String price=params[4];

            try {

                URL url = new URL(ins_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data");

                OutputStream OS ;
                OS =httpURLConnection.getOutputStream();


                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("name","UTF-8") + "="+URLEncoder.encode(name,"UTF-8")+"&"+
                              URLEncoder.encode("quantity","UTF-8") + "="+URLEncoder.encode(quantity,"UTF-8")+"&"+
                              URLEncoder.encode("description","UTF-8") + "="+URLEncoder.encode(description,"UTF-8")+"&"+
                              URLEncoder.encode("price","UTF-8") + "="+URLEncoder.encode(price,"UTF-8");
                bufferedWriter.write(data);

                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();

                final StringBuilder output = new StringBuilder("Request URL " + url);
                BufferedReader br = new BufferedReader(new InputStreamReader(IS));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                //System.out.println("output===============" + br);

                while((line = br.readLine()) != null ) {
                    responseOutput.append(line);
                }
                br.close();

                String result =responseOutput.toString();
                //System.out.println("responseOutput===============================================================" + responseOutput);


                IS.close();



                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (method.equals("remove"))
        {
            String del_url = prefixoURL+"/Products/delete_product.php";

            String id_Product=params[1];

            try {

                URL url = new URL(del_url);


                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);

                OutputStream OS ;
                OS =httpURLConnection.getOutputStream();


                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("id_Product","UTF-8") + "="+URLEncoder.encode(id_Product,"UTF-8");
                bufferedWriter.write(data);

                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();

                final StringBuilder output = new StringBuilder("Request URL " + url);
                BufferedReader br = new BufferedReader(new InputStreamReader(IS));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                //System.out.println("output===============" + br);

                while((line = br.readLine()) != null ) {
                    responseOutput.append(line);
                }
                br.close();

                String result =responseOutput.toString();

                IS.close();



                return result;
                //return  id_Product;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }  catch (IOException e) {
                e.printStackTrace();
            }

        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
      protected void onPostExecute(String result) {
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();

    }
}
