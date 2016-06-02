package enf.android.mysqlproductrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by enfonseca on 5/20/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.RecyclerViewHolder> {

    private static final int TYPE_HEAD =0;
    private static final int TYPE_LIST =1;
    private Context ctx;
    private OnItemClick mListener;

    ArrayList<Product> listProducts = new ArrayList<>();

    ArrayList<ArrayList<String>> LinksArrays=new ArrayList<>();

    public RecyclerAdapter(ArrayList<Product> arrayList, Context context,OnItemClick listener, ArrayList<ArrayList<String>> listArrayLinks){
        this.listProducts=arrayList;
        this.ctx=context;
        this.mListener=listener;
        this.LinksArrays=listArrayLinks;

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if(viewType==TYPE_HEAD)
        {

            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout,parent,false);
            RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view,viewType,parent.getContext(),mListener);
            return recyclerViewHolder;
        }
        else if (viewType==TYPE_LIST){

            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
            RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, viewType,parent.getContext(),mListener);
            return recyclerViewHolder;
        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        if(holder.viewType==TYPE_LIST)
        {

            ArrayList<String> listLink = new ArrayList<>();
            listLink = this.LinksArrays.get(position-1);

            Product product = listProducts.get(position - 1);
            //holder.Id.setText(Integer.toString(product.getId()));
            holder.Name.setText(product.getName());
            holder.Price.setText(String.valueOf(product.getPrice()));

            if (listLink.size()> 0){

                String ultimaImagem = listLink.get(listLink.size() - 1);
                String url = "http://172.16.40.247/Products/images/"+ultimaImagem+".jpg";;
                Picasso.with(ctx)
                        .load(url)
                        .placeholder(R.drawable.loader)
                        .fit()
                        .centerCrop().into(holder.imageViewList);
            }
            else {
                holder.imageViewList.setImageResource(R.drawable.withoutimage);
            }

        }

    }

    @Override
    public int getItemCount() {
        return listProducts.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return TYPE_HEAD;
        return TYPE_LIST;

    }

    public interface OnItemClick {
        void onClickItem (View caller, int position);
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView Id,Name,Price;
        int viewType;
        Context c;
        private OnItemClick mListener;
        ImageView imageViewList;

        public RecyclerViewHolder(View view, int viewType,Context ctxx,OnItemClick listener){

            super(view);
            c=ctxx;
            this.mListener=listener;
            view.setOnClickListener(this);
            if (viewType== TYPE_LIST)
            {

                //Id= (TextView) view.findViewById(R.id.p_id);
                imageViewList= (ImageView) view.findViewById(R.id.imageViewInList);
                Name= (TextView) view.findViewById(R.id.p_name);
                Price= (TextView) view.findViewById(R.id.p_price);
                this.viewType=TYPE_LIST;
            }
            else if (viewType== TYPE_HEAD)
            {
                this.viewType=TYPE_HEAD;
            }

        }

        @Override
        public void onClick(View v) {
        mListener.onClickItem(v, getAdapterPosition());

        }
    }



}
