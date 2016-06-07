package enf.android.mysqlproductrecyclerview;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Add_Product extends AppCompatActivity {


    public String st_url_upload = "/Products/uploadIMAGE.php";
    public static final String UPLOAD_KEY = "image";
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri mCapturedImageURI;
    private final int THUMBSIZE = 96;

    private String UPLOAD_URL="";

    private  Uri imageChoosenUri=null;
    Bitmap mBitmap=null;
    ImageView my_img_view;

    Image image;
    //para armazer o id retornado da imagem em base dados
    int imageId;
    //Quando tiver adicionar várias imagens para um produto
    //ArrayList<int> myImageIdList;

    EditText nome,quantidade,preco,desc;
    String name,description,quantity;
    String price="0.0";

    private String prefixoURL;
    int product_ID_Inserted;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__product);

        Bundle bundle = getIntent().getExtras();
        prefixoURL=bundle.getString("prefixoURL");

        UPLOAD_URL=prefixoURL+st_url_upload;
        nome= (EditText) findViewById(R.id.nome);
        quantidade= (EditText) findViewById(R.id.quantidade);
        preco= (EditText) findViewById(R.id.preco);
        desc= (EditText) findViewById(R.id.desc);

        my_img_view = (ImageView ) findViewById (R.id.imageView);


        //myImageList = new ArrayList<Image>() ;

        if (savedInstanceState!=null){
            imageChoosenUri = Uri.parse(
                    savedInstanceState.getString("imageChoosenUri"));

            mBitmap = savedInstanceState.getParcelable("mBitmap");

            name=savedInstanceState.getString("nome");
            price=savedInstanceState.getString("preco");

           // myImageList = (ArrayList<Image>)savedInstanceState.getSerializable("myImageList");
        }

        nome.setText(name);
        preco.setText(price.toString());

        if(mBitmap!=null){
            my_img_view.setImageBitmap(mBitmap);
        }


    }


    /**
     * Ir para Galeria
     */

    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);

    }


    /**
     * Tirar Foto
     */


    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);
            takePictureIntent
                    .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);


        }
    }



    /*
    *
    * CARREGAR IMAGEM
    * */

    public void btnInsertImageClick(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.setTitle("Alert Dialog View");
        Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.findViewById(R.id.btnChoosePath)
                .setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        activeGallery();
                    }
                });
        dialog.findViewById(R.id.btnTakePhoto)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activeTakePhoto();
                    }
                });

        // show dialog on screen
        dialog.show();
    }




    @Override protected void onActivityResult(int requestCode, int resultCode,
                                              Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (requestCode == RESULT_LOAD_IMAGE &&
                        resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver()
                            .query(selectedImage, filePathColumn, null, null,
                                    null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    image = new Image();
                    image.setTitle("Test");
                    image.setDescription(
                            "test choose a photo from gallery and add it to " +
                                    "list view");
                    image.setDatetime(System.currentTimeMillis());
                    image.setPath(picturePath);


                    //Quando tiver que adicionar muitas imagens, utiliza lista neste caso com um "for each"
                    //myImageList.add(image);

                    //Toast.makeText(this,""+selectedImage,Toast.LENGTH_LONG).show();

                    imageChoosenUri=selectedImage;

                }
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE &&
                        resultCode == RESULT_OK) {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor =
                            managedQuery(mCapturedImageURI, projection, null,
                                    null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(
                            MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String picturePath = cursor.getString(column_index_data);

                    Image myImage = new Image();

                    myImage.setTitle("Test");
                    myImage.setDescription(
                            "test take a photo and add it to list view");
                    myImage.setDatetime(System.currentTimeMillis());
                    myImage.setPath(picturePath);

                    //Quando tiver que adicionar muitas imagens, utiliza lista neste caso com um "for each"
                    //myImageList.add(myImage);

                    //Toast.makeText(this,""+mCapturedImageURI,Toast.LENGTH_LONG).show();

                    imageChoosenUri=mCapturedImageURI;

                }

                //Toast.makeText(this,"path_file: "+image.getPath(),Toast.LENGTH_LONG).show();
        }


        try {
            mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageChoosenUri);
            my_img_view.setImageBitmap(mBitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void insertProduct (View v){
        name = nome.getText().toString();
        description = desc.getText().toString();
        price = preco.getText().toString();
        quantity=quantidade.getText().toString();

        String method= "insert";

        CRUDBackgroundTask cbTask = new CRUDBackgroundTask(this,this.prefixoURL);
        cbTask.execute(method,name,quantity,description,price);

        String result = "";
        try {
            result=cbTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        product_ID_Inserted=Integer.parseInt(result);
        Toast.makeText(this,"Response Server:"+product_ID_Inserted,Toast.LENGTH_LONG).show();
        finish();


        //CHAMAR UPLOAD DE IMAGEN OU CICLO EM CASO DE LISTA DE IMAGENS (LEVANDO SEMPRE ID DO PRODUTO)
        uploadImage(product_ID_Inserted);





    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(int idP){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            private int id_product_inserted;

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            public UploadImage(int id_product_inserted) {
                this.id_product_inserted = id_product_inserted;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Add_Product.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);

                data.put("Product_ID", ""+id_product_inserted);
                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage(idP);
        ui.execute(mBitmap);
    }




    @Override protected void onSaveInstanceState(Bundle outState) {
        // Save the user's current game state
        if (mCapturedImageURI != null) {
            outState.putString("mCapturedImageURI",
                    mCapturedImageURI.toString());
        }

        if (imageChoosenUri != null) {
            outState.putString("imageChoosenUri",
                    imageChoosenUri.toString());
        }

        //mBitmap
        if (mBitmap != null) {
            outState.putParcelable("mBitmap", mBitmap);;
        }


        outState.putString("nome", name);
        outState.putString("preco", price);


        //outState.putSerializable("myImageList", myImageList);



        outState.putString("RESULT_LOAD_IMAGE",
                ""+RESULT_LOAD_IMAGE);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        if (savedInstanceState.containsKey("mCapturedImageURI")) {
            mCapturedImageURI = Uri.parse(
                    savedInstanceState.getString("mCapturedImageURI"));
        }

        //imageChoosenUri

        // Restore state members from saved instance
        if (savedInstanceState.containsKey("imageChoosenUri")) {
            imageChoosenUri = Uri.parse(
                    savedInstanceState.getString("imageChoosenUri"));
        }

        //myImageList = (ArrayList<Image>)savedInstanceState.getSerializable("myImageList");


    }

}
