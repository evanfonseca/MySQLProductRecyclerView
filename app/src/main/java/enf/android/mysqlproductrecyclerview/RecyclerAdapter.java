package enf.android.mysqlproductrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by enfonseca on 5/20/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.RecyclerViewHolder> {

    private static final int TYPE_HEAD =0;
    private static final int TYPE_LIST =1;

    ArrayList<Product> listProducts = new ArrayList<>();

    public RecyclerAdapter(ArrayList<Product> arrayList){
        this.listProducts=arrayList;


    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if(viewType==TYPE_HEAD)
        {

            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout,parent,false);
            RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view,viewType);
            return recyclerViewHolder;
        }
        else if (viewType==TYPE_LIST){

            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
            RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, viewType);
            return recyclerViewHolder;
        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        if(holder.viewType==TYPE_LIST)
        {
            Product product = listProducts.get(position-1);
            holder.Id.setText(Integer.toString(product.getId()));
            holder.Name.setText(product.getName());
            holder.Price.setText(String.valueOf(product.getPrice()));

        }

    }

    @Override
    public int getItemCount() {
        return listProducts.size()+1;
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{


        TextView Id,Name,Price;
        int viewType;

        public RecyclerViewHolder(View view, int viewType){

            super(view);
            if (viewType== TYPE_LIST)
            {

                Id= (TextView) view.findViewById(R.id.p_id);
                Name= (TextView) view.findViewById(R.id.p_name);
                Price= (TextView) view.findViewById(R.id.p_price);
                this.viewType=TYPE_LIST;
            }
            else if (viewType== TYPE_HEAD)
            {
                this.viewType=TYPE_HEAD;
            }


        }

    }


    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return TYPE_HEAD;
            return TYPE_LIST;

    }
}
