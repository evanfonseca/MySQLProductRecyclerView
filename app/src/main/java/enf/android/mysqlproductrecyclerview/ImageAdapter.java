package enf.android.mysqlproductrecyclerview;

/**
 * Created by Dev02 on 02/06/2016.
 */
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int imageTotal;
    private ArrayList<String> Links=new ArrayList<>();
    public static String[] mThumbIds;


    public ImageAdapter(Context c, ArrayList<String> l) {
        mContext = c;
        this.Links=l;
        this.imageTotal=this.Links.size();

        mThumbIds = new String[imageTotal];
        int i=0;
        for (String link: this.Links ) {
            mThumbIds[i]=link;
            i++;
        }
    }

    public int getCount() {
        return imageTotal;
    }

    @Override
    public String getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200,200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
        } else {
            imageView = (ImageView) convertView;
        }
        String url = getItem(position);
        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.loader)
                .fit()
                .centerCrop().into(imageView);
        return imageView;
    }
}
