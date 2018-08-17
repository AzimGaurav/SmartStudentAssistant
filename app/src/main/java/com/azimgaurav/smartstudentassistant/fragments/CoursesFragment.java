package com.azimgaurav.smartstudentassistant.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azimgaurav.smartstudentassistant.CourseDetailsActivity;
import com.azimgaurav.smartstudentassistant.NotesDetailsActivity;
import com.azimgaurav.smartstudentassistant.R;
import com.azimgaurav.smartstudentassistant.model.Course;
import com.azimgaurav.smartstudentassistant.model.Note;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CoursesFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;

    public CoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Courses");
        databaseReference.keepSynced(true);
        View view=inflater.inflate(R.layout.fragment_courses, container, false);
        recyclerView=view.findViewById(R.id.rcCoursesView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //Using FirebaseRecyclerAdapter to improve performance
        FirebaseRecyclerAdapter<Course,CourseViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter
                <Course, CourseViewHolder>(
                Course.class,R.layout.single_course,CourseViewHolder.class,databaseReference
        ) {
            @Override
            protected void populateViewHolder(CourseViewHolder viewHolder, Course model, int position) {
                DatabaseReference courseRef=getRef(position);
                viewHolder.setKey(courseRef.getKey());
                viewHolder.setTitle(model.getTitle());
                viewHolder.setPre(model.getPre());
                viewHolder.setTextbooks(model.getTextbooks());
                viewHolder.onClick(getActivity());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    //DESIGN A VIEWHOLDER CLASS FOR RECYCLERVIEW
    public static class CourseViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        TextView tvTitle;
        String key,title,pre,textbooks;
        LinearLayout layout;
        public CourseViewHolder(View view)
        {
            super(view);
            this.view=view;
        }
        void setKey(String key)
        {
            this.key=key;
        }
        void setTitle(String t)
        {
            title=t;
            tvTitle=view.findViewById(R.id.tv_course_title);
            tvTitle.setText(title);
        }
        void setPre(String p)
        {
            pre=p;
        }
        void setTextbooks(String tb)
        {
            textbooks=tb;
        }
        void onClick(final Context context)
        {
            layout=view.findViewById(R.id.course_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,CourseDetailsActivity.class);
                    intent.putExtra("course_key",key);
                    intent.putExtra("course_title",title);
                    intent.putExtra("course_pre",pre);
                    intent.putExtra("course_textbooks",textbooks);
                    context.startActivity(intent);
                }
            });
        }
    }
}