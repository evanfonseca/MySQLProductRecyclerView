package enf.android.mysqlproductrecyclerview;

/**
 * Created by Dev02 on 02/06/2016.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;


public class FullImageActivity extends Activity {

    ProgressDialog pDialog;
    ImageView img;
    Bitmap bitmap;
    ArrayList<String> links;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullimageview);

        Intent i = getIntent();
        int position = i.getExtras().getInt("id");
        links=i.getStringArrayListExtra("Links");
        ImageAdapter imageAdapter = new ImageAdapter(this,links);

        img = (ImageView) findViewById(R.id.image);
        String url = imageAdapter.getItem(position);

        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.loader)
                .fit()
                .centerCrop().into(img);

    }


}
