package com.azimgaurav.smartstudentassistant;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.azimgaurav.smartstudentassistant.model.Module;
import com.azimgaurav.smartstudentassistant.model.Session;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AttendanceDetails extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private static String COURSE_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        String courseKey= getIntent().getStringExtra("key");
        //Initialize UI components
        TextView tvCourseTitle=findViewById(R.id.tv_attendance_course_title);
        TextView tvSessionsTaken=findViewById(R.id.tv_sessions_taken);
        TextView tvPresent=findViewById(R.id.tv_present);
        TextView tvAbsent=findViewById(R.id.tv_absent);
        TextView tvPercent=findViewById(R.id.tv_attendance_percent);
        recyclerView=findViewById(R.id.rv_attendance);

        Log.e("Firebase","course key : "+courseKey+"  !!!!!!!!");

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Attendance").child(courseKey).child("sessions");
        databaseReference.keepSynced(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        tvCourseTitle.setText(getIntent().getStringExtra("title"));
        tvSessionsTaken.append(" "+getIntent().getIntExtra("sessions_count",0));
        tvPresent.append(" "+getIntent().getIntExtra("present_count",0));
        tvAbsent.append(" "+getIntent().getIntExtra("absent_count",0));
        tvPercent.append(" "+getIntent().getStringExtra("percent"));
    }
    @Override
    protected void onStart() {
        super.onStart();

        //Using FirebaseRecyclerAdapter to improve performance
        FirebaseRecyclerAdapter<Session,SessionViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Session, SessionViewHolder>(
                Session.class,R.layout.single_attendance_row,SessionViewHolder.class,databaseReference
        ) {
            @Override
            protected void populateViewHolder(SessionViewHolder viewHolder, Session model, int position) {
                DatabaseReference courseRef=getRef(position);
                COURSE_KEY=courseRef.getKey();
                viewHolder.setData(model.getSr(),model.getDate(),model.getTime(),model.getStatus());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    //DESIGN A VIEWHOLDER CLASS FOR RECYCLERVIEW
    public static class SessionViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        TextView tvSr,tvDate,tvTime,tvStatus;
        public SessionViewHolder(View view)
        {
            super(view);
            this.view=view;
        }
        void setData(int sr,String date, String time,String status)
        {
            tvSr=view.findViewById(R.id.tv_session_sr);
            tvDate=view.findViewById(R.id.tv_session_date);
            tvTime=view.findViewById(R.id.tv_session_time);
            tvStatus=view.findViewById(R.id.tv_session_status);
            tvSr.setText(String.valueOf(sr));
            tvDate.setText(date);
            tvTime.setText(time);
            tvStatus.setText(status);
        }
    }
}