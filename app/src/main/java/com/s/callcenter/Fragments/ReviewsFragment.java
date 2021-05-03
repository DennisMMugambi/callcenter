package com.s.callcenter.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.s.callcenter.POJOS.FaultyPojo;
import com.s.callcenter.R;

import org.w3c.dom.Text;

public class ReviewsFragment extends Fragment {

    private DatabaseReference mRef;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reviews, container, false);
        mRef = FirebaseDatabase.getInstance().getReference().child("faults");
        mRecyclerView = (RecyclerView) root.findViewById(R.id.reviews_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<FaultyPojo, ReviewsFragment.MyViewHolder> adapter = new FirebaseRecyclerAdapter<FaultyPojo, ReviewsFragment.MyViewHolder>(
                FaultyPojo .class,
                R.layout.faults_layout,
                ReviewsFragment.MyViewHolder.class,
                mRef
        ) {


            @Override
            protected void populateViewHolder(ReviewsFragment.MyViewHolder myViewHolder, FaultyPojo faultyPojo, int i) {
                myViewHolder.setupViews(faultyPojo.getCompanyName(), faultyPojo.getFault(), faultyPojo.getDate());
            }
        };
        mRecyclerView.setAdapter(adapter);
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

        }
        void setupViews(String Cname, String faultz, String date){
            TextView companyName = (TextView) mView.findViewById(R.id.buildingName);
            TextView fault = (TextView) mView.findViewById(R.id.fault);
            TextView time_stamp = (TextView) mView.findViewById(R.id.timestamp_tv);


           companyName.setText(Cname);
           fault.setText(faultz);
           time_stamp.setText(date);

        }
    }
}