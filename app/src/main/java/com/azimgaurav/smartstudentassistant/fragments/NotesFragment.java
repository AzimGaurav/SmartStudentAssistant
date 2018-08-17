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

import com.azimgaurav.smartstudentassistant.NotesDetailsActivity;
import com.azimgaurav.smartstudentassistant.model.Note;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.azimgaurav.smartstudentassistant.R;

public class NotesFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Notes").child(firebaseUser.getUid());
        databaseReference.keepSynced(true);
        View view=inflater.inflate(R.layout.fragment_notes, container, false);
        recyclerView=view.findViewById(R.id.rcNotesView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

        //Using FirebaseRecyclerAdapter to improve performance
        FirebaseRecyclerAdapter<Note,NoteViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Note, NoteViewHolder>(
                Note.class,R.layout.single_note,NoteViewHolder.class,databaseReference
        ) {
            @Override
            protected void populateViewHolder(NoteViewHolder viewHolder, Note model, int position) {
                DatabaseReference noteRef=getRef(position);
                viewHolder.setData(noteRef.getKey(),model.getTitle(),model.getDesc(),model.getColor(),model.getFont());
                viewHolder.onClick(getActivity());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    //DESIGN A VIEWHOLDER CLASS FOR RECYCLERVIEW
    public static class NoteViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        TextView tvTitle;
        String key,title,desc,color,font;
        LinearLayout layout;
        public NoteViewHolder(View view)
        {
            super(view);
            this.view=view;
        }
        void setData(String key,String title,String desc,String color,String font)
        {
            this.key=key;
            this.title=title;
            this.desc=desc;
            this.color=color;
            this.font=font;
            tvTitle=view.findViewById(R.id.tvNoteTitle);
            tvTitle.setText(title);
        }
        void onClick(final Context context)
        {
            layout=view.findViewById(R.id.notes_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,NotesDetailsActivity.class);
                    intent.putExtra("key",key);
                    intent.putExtra("title",title);
                    intent.putExtra("desc",desc);
                    intent.putExtra("color",color);
                    intent.putExtra("font",font);
                    context.startActivity(intent);
                }
            });
        }
    }
}