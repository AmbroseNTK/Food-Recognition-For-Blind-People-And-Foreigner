package ntk.ambrose.foodinspector.sampleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import ntk.ambrose.foodinspector.R;

public class ListViewImageAdapter extends ArrayAdapter<ListImageElement> implements View.OnClickListener {
    ArrayList<ListImageElement> dataset;
    Context context;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //super.getView(position, convertView, parent);
        ListImageElement element = dataset.get(position);
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_item,parent,false);
            holder.imageView = convertView.findViewById(R.id.imgSample);
            holder.imageView.setImageBitmap(element.getBitmap());
            holder.imageView.setImageBitmap(element.getBitmap());
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }



        return convertView;

    }

    private static class ViewHolder{
        ImageView imageView;
    }

    public ListViewImageAdapter(ArrayList<ListImageElement> dataset,Context context){
        super(context, R.layout.image_item,dataset);
        this.dataset=dataset;
        this.context=context;
    }

    @Override
    public void onClick(View view) {

    }
}
