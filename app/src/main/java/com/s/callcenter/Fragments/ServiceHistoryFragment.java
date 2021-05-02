package com.s.callcenter.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.s.callcenter.POJOS.FaultyPojo;
import com.s.callcenter.POJOS.ServiceHistoryPojo;
import com.s.callcenter.R;


public class ServiceHistoryFragment extends Fragment {

    private DatabaseReference mRef;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_service_history, container, false);
        mRef = FirebaseDatabase.getInstance().getReference().child("Service_history");
        mRecyclerView = (RecyclerView) root.findViewById(R.id.service_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<ServiceHistoryPojo, ServiceHistoryFragment.MyViewHolder> adapter = new FirebaseRecyclerAdapter<ServiceHistoryPojo, ServiceHistoryFragment.MyViewHolder>(
                ServiceHistoryPojo .class,
                R.layout.layout_service_history,
                ServiceHistoryFragment.MyViewHolder.class,
                mRef
        ) {


            @Override
            protected void populateViewHolder(ServiceHistoryFragment.MyViewHolder myViewHolder, ServiceHistoryPojo serviceHistoryPojo, int i) {
                myViewHolder.setupViews(serviceHistoryPojo.getTechnician_name(), serviceHistoryPojo.getDateServiced());
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
        void setupViews(String technicianName, String Date){
            TextView tName = (TextView) mView.findViewById(R.id.tName);
            TextView serviceDate = (TextView) mView.findViewById(R.id.date);

            tName.setText(technicianName);
            serviceDate.setText(Date);

        }
    }
}