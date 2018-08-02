package ntk.ambrose.foodinspector.sampleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import ntk.ambrose.foodinspector.R;

public class RecyclerSampleViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgSample;
    public RecyclerSampleViewHolder(View itemView) {
        super(itemView);
        imgSample = itemView.findViewById(R.id.imgSample);
    }
}
