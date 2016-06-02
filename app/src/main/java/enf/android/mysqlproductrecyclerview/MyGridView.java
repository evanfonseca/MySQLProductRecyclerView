package enf.android.mysqlproductrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class MyGridView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grid_view);

        Intent i = getIntent();

        ArrayList<String> imageLinks = new ArrayList<>();

        imageLinks=i.getStringArrayListExtra("ListImageLink");

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, imageLinks));
    }
}
