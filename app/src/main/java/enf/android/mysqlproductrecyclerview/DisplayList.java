package enf.android.mysqlproductrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DisplayList extends AppCompatActivity  implements RecyclerAdapter.OnItemClick {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);



        BackgroundTask backgroundTask = new BackgroundTask(DisplayList.this);

        //continuar apartir do minuto 48
        backgroundTask.execute();


    }

    @Override
    public void onClickItem(View caller, int position) {

        Toast.makeText(this,"AKiiiiiiiiiI",Toast.LENGTH_LONG).show();

    }
}
