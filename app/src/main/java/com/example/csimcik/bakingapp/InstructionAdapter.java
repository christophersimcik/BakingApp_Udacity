package com.example.csimcik.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import java.util.ArrayList;

/**
 * Created by csimcik on 6/13/2017.
 */
public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.ViewHolder>{
ArrayList<Instruction> instructList = new ArrayList<>();
    Context aContext;
    Uri uri;
    public int my;
    public int me;
    public InstructionAdapter(Context mContext, ArrayList<Instruction>instructListA){
    instructList = instructListA;
        aContext = mContext;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instruct_adapt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Instruction itemRec = instructList.get(position);
            holder.setInstruct(itemRec);

    }


    @Override
    public int getItemCount() {
        return instructList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView stepView;
        private TextView insView;
        public ViewHolder(final View itemView) {
            super (itemView);
            insView = (TextView)itemView.findViewById(R.id.long_desc);
            itemView.setOnClickListener(this);
            itemView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    my = Detail.insLayoutMang.findFirstVisibleItemPosition();
                    Log.i("logger ", String.valueOf(my)+" / "+String.valueOf(me));
                    if(!DetailFrag.vidURL.equals(instructList.get(my).getVidUrl())) {
                        uri = Uri.parse(instructList.get(my).getVidUrl());
                        Detail.vidNumView.setText(String.valueOf(my));
                        DetailFrag.vidURL = instructList.get(my).getVidUrl();
                        DetailFrag.mediaSource = buildMediaSource(uri);
                        DetailFrag.player.prepare(DetailFrag.mediaSource,true,false);
                    }


                    }
                private MediaSource buildMediaSource(Uri uri) {
                    return new ExtractorMediaSource(uri,
                            new DefaultHttpDataSourceFactory("ua"),
                            new DefaultExtractorsFactory(), null, null);
                }

            });

        }
       /* public void setInstructName(){
            String text = "Step" + " " + getAdapterPosition();
            stepView.setText(text);
            //stepView.setHeight((int)MainActivityFragment.adjHeight);
            stepView.setTextSize(20);
        }*/
        public void setInstruct(Instruction thisIns){
            insView.setText(thisIns.getLongDesc());
            insView.setTextSize(15);
            insView.setPadding(15,50,15,50);
        }
        public void setFill(){
            insView.setBackgroundResource(R.drawable.footer);
        }

        @Override
        public void onClick(View v) {
            Log.i("Recipedapt ", "Clicked" + " " + getAdapterPosition());

        }


    }
}
