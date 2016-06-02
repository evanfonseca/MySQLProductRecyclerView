package enf.android.mysqlproductrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Show_Product_Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__product__details);

        Intent i = getIntent();
        Product product = (Product) i.getSerializableExtra("Product");

        ArrayList<String> imageLinks = new ArrayList<>();

        imageLinks=i.getStringArrayListExtra("ListImageLink");

        TextView tv = (TextView) findViewById(R.id.textView);

        tv.setText(product.toString());

        TextView tv2= (TextView) findViewById(R.id.textView2);
        tv2.setText("ImageLinks: "+imageLinks.toString());
    }
}
