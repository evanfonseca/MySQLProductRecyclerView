package enf.android.mysqlproductrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Show_Product_Details extends AppCompatActivity {

    private  int id_produto;
    private String prefixoURL = "";
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__product__details);

        Intent i = getIntent();
        product = (Product) i.getSerializableExtra("Product");
        id_produto=product.getId();

        Bundle bundle = getIntent().getExtras();
        prefixoURL=bundle.getString("prefixoURL");



        ArrayList<String> imageLinks = new ArrayList<>();

        imageLinks=i.getStringArrayListExtra("ListImageLink");


        TextView nome = (TextView) findViewById(R.id.nome);
        nome.setText(product.getName());

        TextView preco = (TextView) findViewById(R.id.preco);
        preco.setText("Preço: "+product.getPrice());

        TextView desc = (TextView) findViewById(R.id.desc);
        desc.setText("Descrição: "+product.getDescription());

        TextView quantidade = (TextView) findViewById(R.id.quantidade);
        quantidade.setText("Quantidade disponível: "+product.getQuantity());

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this,imageLinks));

        final ArrayList<String> finalImageLinks = imageLinks;
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
                i.putExtra("id", position);
                i.putStringArrayListExtra("Links", finalImageLinks);
                startActivity(i);
            }
        });

        /*
        TextView tv2= (TextView) findViewById(R.id.textView2);
        tv2.setText("ImageLinks: "+imageLinks.toString());
        */




    }

    public void btnRemoveProductOnClick(View v){
        String method= "remove";

        //Toast.makeText(this,"Aki",Toast.LENGTH_LONG).show();


        CRUDBackgroundTask cbTask = new CRUDBackgroundTask(this,this.prefixoURL);
        cbTask.execute(method,""+id_produto);

        Intent intent= new Intent(Show_Product_Details.this,DisplayList.class);
        intent.putExtra("prefixoURL", prefixoURL);
        startActivity(intent);

    }

    public void btnUpdateProductOnClick(View v){
        String method= "update";

        //Toast.makeText(this,"Aki",Toast.LENGTH_LONG).show();




        Intent intent= new Intent(Show_Product_Details.this,UpdateProduct.class);
        intent.putExtra("Product", (Serializable) product);
        intent.putExtra("prefixoURL", prefixoURL);
        startActivity(intent);

    }


}
