package ntk.ambrose.foodinspector;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import ntk.ambrose.foodinspector.recognizer.Result;
import ntk.ambrose.foodinspector.sampleview.ListImageElement;
import ntk.ambrose.foodinspector.sampleview.ListViewImageAdapter;

public class RecognitionActivity extends AppCompatActivity {

    ListView lvSample;
    ListViewImageAdapter listViewImageAdapter;

    ArrayList<ListImageElement> samples;
    int currentPos=0;
    TextView textResult;

    Button btAgain;
    Button btOK;
    TextView tvOriginal;
    TextView tvDescription;

    FirebaseDatabase database;

    ExpandableLayout expandableLayout;
    ExpandableLayout infoLayout;

    NumberProgressBar progressConfidence;

    PieChartView tasteChart;

    public static ArrayList<Result> results;
    public static String language="en";

    Timer timer;

    int sourness,saltiness, spice, sweetness, bitterness,nonTaste;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.recognition_activity);

        database = FirebaseDatabase.getInstance();
        samples=new ArrayList<>();
        textResult = findViewById(R.id.textResult);

        expandableLayout = findViewById(R.id.expandableLayout);
        infoLayout=findViewById(R.id.infoLayout);

        tvDescription=findViewById(R.id.tvDescription);
        tvOriginal=findViewById(R.id.tvOriginal);

        btAgain = findViewById(R.id.btAgain);
        btAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPos++;
                infoLayout.collapse();
                viewItem(currentPos);
            }
        });

        btOK = findViewById(R.id.btOK);
        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout.collapse();
                loadInfo(results.get(currentPos).getResult());
                infoLayout.expand();
            }
        });

        progressConfidence = findViewById(R.id.progressConfidence);
        tasteChart=findViewById(R.id.tasteChart);

        if(results!=null && results.size()!=0) {
            lvSample= findViewById(R.id.lvSample);
            listViewImageAdapter = new ListViewImageAdapter(samples,getBaseContext());
            lvSample.setAdapter(listViewImageAdapter);
            viewItem(currentPos);
        }
        else{
            finish();
        }

    }

    private void configChart(){

        List<SliceValue> sliceValues = new ArrayList<>();
        sliceValues.add(new SliceValue(sweetness, ContextCompat.getColor(this,R.color.sweetness)).setLabel("Sweetness"));
        sliceValues.add(new SliceValue(bitterness,ContextCompat.getColor(this,R.color.bitterness)).setLabel("bitterness"));
        sliceValues.add(new SliceValue(sourness,ContextCompat.getColor(this,R.color.sourness)).setLabel("Sourness"));
        sliceValues.add(new SliceValue(saltiness,ContextCompat.getColor(this,R.color.saltiness)).setLabel("Saltiness"));
        sliceValues.add(new SliceValue(spice,ContextCompat.getColor(this,R.color.spicy)).setLabel("Spice"));
        sliceValues.add(new SliceValue(nonTaste,ContextCompat.getColor(this,R.color.nonTaste)).setLabel("Non-taste"));

        PieChartData data = new PieChartData(sliceValues);
        data.setCenterText1("Taste");
        data.setCenterCircleScale(0.5f);
        data.setCenterCircleColor(ContextCompat.getColor(this,R.color.gray50));
        data.setHasLabels(true);
        data.setHasLabelsOnlyForSelected(true);
        data.setHasCenterCircle(true);
        data.setCenterText1FontSize(10);
        data.setCenterText1Color(ContextCompat.getColor(this,R.color.amber900));

        tasteChart.setPieChartData(data);

    }

    private void loadInfo(String keyword){

        DatabaseReference ref = database.getReference(keyword+"/languages/");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals(language)){
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        if(child.getKey().equals("original")){
                            tvOriginal.setText(child.getValue().toString());
                        }
                        else if(child.getKey().equals("description")){
                            tvDescription.setText(child.getValue().toString());
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref = database.getReference(keyword+"/taste/");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.getKey().equals("sweetness")) {
                    sweetness = Integer.valueOf(dataSnapshot.getValue().toString());
                } else if (dataSnapshot.getKey().equals("sourness")) {
                    sourness = Integer.valueOf(dataSnapshot.getValue().toString());
                } else if (dataSnapshot.getKey().equals("bitterness")) {
                    bitterness = Integer.valueOf(dataSnapshot.getValue().toString());
                } else if (dataSnapshot.getKey().equals("spice")) {
                    spice = Integer.valueOf(dataSnapshot.getValue().toString());
                } else if (dataSnapshot.getKey().equals("saltiness")) {
                    saltiness = Integer.valueOf(dataSnapshot.getValue().toString());
                } else if (dataSnapshot.getKey().equals("nonTaste")) {
                    nonTaste = Integer.valueOf(dataSnapshot.getValue().toString());
                }

                configChart();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadSample(String keyword){
        samples.clear();

        DatabaseReference ref = database.getReference(keyword+"/images/");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("FoodInspector",dataSnapshot.getValue().toString());

                try {
                    Bitmap result = new DownloadImage().execute(dataSnapshot.getValue().toString()).get();
                    if(result!=null){
                        samples.add(new ListImageElement(result));
                        listViewImageAdapter.notifyDataSetChanged();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                Log.i("FoodInspector","Finished");

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void viewItem(int id){
        if(id < results.size()){
            final int percent = (int)(results.get(id).getConfidence()*100);
            textResult.setText("Result: "+results.get(id).getResult());
            progressConfidence.setProgress(0);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(progressConfidence.getProgress()<percent) {
                                progressConfidence.incrementProgressBy(1);
                            }
                            else{
                                timer.cancel();
                                expandableLayout.expand();
                            }
                        }
                    });
                }
            },1000,10);
            Log.i("FoodInspector","Confidence = "+progressConfidence.getProgress());
            loadSample(results.get(id).getResult());
        }
    }

    private class DownloadImage extends AsyncTask<String,Void,Bitmap> {
        public DownloadImage() {
            super();
        }

        @Override
        protected Bitmap doInBackground(String... arrayLists) {

            Bitmap bitmap = null;
            try {
                if(!arrayLists[0].equals("")) {

                    bitmap = Picasso.get().load(arrayLists[0]).get();
                }
            } catch (IOException ex) {
                Log.i("FoodInspector", "Cannot load image");
            }
            return bitmap;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
