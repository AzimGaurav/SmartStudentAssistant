package com.azimgaurav.smartstudentassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azimgaurav.smartstudentassistant.fragments.CoursesFragment;
import com.azimgaurav.smartstudentassistant.model.Course;
import com.azimgaurav.smartstudentassistant.model.Module;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CourseDetailsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private static String MODULE_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String courseKey= getIntent().getStringExtra("course_key");
        //Initialize UI components
        TextView tvCourseTitle=findViewById(R.id.tv_course_title);
        TextView tvCoursePre=findViewById(R.id.tv_course_pre);
        TextView tvCourseTextbooks=findViewById(R.id.tv_textbook);
        recyclerView=findViewById(R.id.contents_recycleview);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Courses").child(courseKey).child("contents");
        databaseReference.keepSynced(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        tvCourseTitle.append(" "+getIntent().getStringExtra("course_title"));
        tvCoursePre.append(" "+getIntent().getStringExtra("course_pre"));
        tvCourseTextbooks.append("\n"+getIntent().getStringExtra("course_textbooks"));
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Using FirebaseRecyclerAdapter to improve performance
        FirebaseRecyclerAdapter<Module,ModuleViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Module, ModuleViewHolder>(
                Module.class,R.layout.single_module,ModuleViewHolder.class,databaseReference
        ) {
            @Override
            protected void populateViewHolder(ModuleViewHolder viewHolder, Module model, int position) {
                DatabaseReference moduleRef=getRef(position);
                MODULE_KEY=moduleRef.getKey();
                viewHolder.setData(model.getName(),model.getPoints());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    //DESIGN A VIEWHOLDER CLASS FOR RECYCLERVIEW
    public static class ModuleViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        TextView tvName,tvPoints;
        public ModuleViewHolder(View view)
        {
            super(view);
            this.view=view;
        }
        void setData(String name,String points)
        {
            Log.e("Font","Module name : "+name+"!!");
            Log.e("Font","Module points : "+points+"!!!");
            tvName=view.findViewById(R.id.tv_module_name);
            tvPoints=view.findViewById(R.id.tv_module_points);
            tvName.setText(MODULE_KEY);
            tvName.append(" : "+name);
            tvPoints.append(" "+points);
        }
    }
}