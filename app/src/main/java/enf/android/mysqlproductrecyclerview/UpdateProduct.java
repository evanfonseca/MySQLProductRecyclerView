package enf.android.mysqlproductrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class UpdateProduct extends AppCompatActivity {

    private String prefixoURL = "";
    Product product;
    //String UPDATE_URL;
    //String st_url_update="Products/updateProduct.php";

    EditText nome,quantidade,preco,desc;
    String name,description,quantity,st_id_Prod;
    String price="0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        Intent i = getIntent();
        product = (Product) i.getSerializableExtra("Product");
        st_id_Prod=""+ product.getId();
        prefixoURL=i.getStringExtra("prefixoURL");

        //UPDATE_URL=prefixoURL+st_url_update;

        nome= (EditText) findViewById(R.id.nome);
        nome.setText(product.getName());
        quantidade= (EditText) findViewById(R.id.quantidade);
        quantidade.setText(""+product.getQuantity());
        preco= (EditText) findViewById(R.id.preco);
        preco.setText(""+product.getPrice());
        desc= (EditText) findViewById(R.id.desc);
        desc.setText(product.getDescription());
    }



    public void updateProduct (View v){
        name = nome.getText().toString();
        description = desc.getText().toString();
        price = preco.getText().toString();
        quantity=quantidade.getText().toString();

        String method= "update";

        //Toast.makeText(this,"Update id:",Toast.LENGTH_LONG).show();

        CRUDBackgroundTask cbTask = new CRUDBackgroundTask(this,this.prefixoURL);
        cbTask.execute(method, name, quantity, description, price, st_id_Prod);

        finish();


        //CHAMAR UPLOAD DE IMAGEN OU CICLO EM CASO DE LISTA DE IMAGENS (LEVANDO SEMPRE ID DO PRODUTO)






    }

}
