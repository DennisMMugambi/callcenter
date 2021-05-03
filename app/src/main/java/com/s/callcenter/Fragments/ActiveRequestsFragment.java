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
import com.s.callcenter.POJOS.ActiveRequestPojo;
import com.s.callcenter.R;


public class ActiveRequestsFragment extends Fragment {
    private DatabaseReference mRef;
    private RecyclerView mRecyclerView;
    private static String tNumber, pNumber;
    private static TextView no_requests_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_active_requests, container, false);
        mRef = FirebaseDatabase.getInstance().getReference().child("active_requests");
        no_requests_tv = root.findViewById(R.id.no_requests);
        no_requests_tv.setVisibility(View.VISIBLE);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.active_requests_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<ActiveRequestPojo, MyViewHolder> adapter = new FirebaseRecyclerAdapter<ActiveRequestPojo, MyViewHolder>(
                ActiveRequestPojo .class,
                R.layout.active_requests_layout,
                MyViewHolder.class,
                mRef
        ) {


            @Override
            protected void populateViewHolder(MyViewHolder myViewHolder, ActiveRequestPojo activeRequestPojo, int i) {
                myViewHolder.setupViews(activeRequestPojo.getPassengerFirstName(),activeRequestPojo.getPassengerLastName(),
                        activeRequestPojo.getTechnicianFirstName(), activeRequestPojo.getTechnicianLastName(),
                        activeRequestPojo.getPassengerPhoneNumber(), activeRequestPojo.getTechnicianNumber());
            }
        };
        mRecyclerView.setAdapter(adapter);
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            Button callPassenger = (Button) mView.findViewById(R.id.call_pass);
            Button callTechnician = (Button) mView.findViewById(R.id.call_tech);

            callPassenger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + pNumber));
                    try {
                        itemView.getContext().startActivity(intent);
                    } catch(ActivityNotFoundException e){
                        e.printStackTrace();
                    }
                }
            });

            callTechnician.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + tNumber));
                    try {
                        itemView.getContext().startActivity(intent);
                    } catch(ActivityNotFoundException e){
                        e.printStackTrace();
                    }
                }
            });

        }
        void setupViews(String passFirstName,String passLastName, String techFirstName, String techLastName, String passengerNumber, String technicianNumber){
            TextView passengerName = (TextView) mView.findViewById(R.id.pass_name);
            TextView techName = (TextView) mView.findViewById(R.id.tech_name);
            TextView passNumber = (TextView) mView.findViewById(R.id.pass_number);
            TextView techNumber = (TextView) mView.findViewById(R.id.tech_number);


            String pname = passFirstName + " " + passLastName;
            String tname = techFirstName + " " + techLastName;

            pNumber = passengerNumber;
            tNumber = technicianNumber;

            no_requests_tv.setVisibility(View.INVISIBLE);
            passengerName.setText(pname);
            techName.setText(tname);
            passNumber.setText(passengerNumber);
            techNumber.setText(technicianNumber);

        }
    }
}