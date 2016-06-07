package enf.android.mysqlproductrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button BtnlistProduct;
    String prefixoURL = "http://172.16.40.247";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnlistProduct = (Button) findViewById(R.id.listProduct);

        BtnlistProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(MainActivity.this,DisplayList.class);
                intent.putExtra("prefixoURL",prefixoURL);
                startActivity(intent);
            }
        });

    }

    public void btnAddProductOnClick (View v){

        Intent intent= new Intent(MainActivity.this,Add_Product.class);
        intent.putExtra("prefixoURL",prefixoURL);
        startActivity(intent);
    }

}
