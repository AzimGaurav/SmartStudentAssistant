package com.azimgaurav.smartstudentassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.azimgaurav.smartstudentassistant.fragments.AttendanceFragment;
import com.azimgaurav.smartstudentassistant.fragments.CoursesFragment;
import com.azimgaurav.smartstudentassistant.fragments.NoticesFragment;
import com.azimgaurav.smartstudentassistant.fragments.ExpensesFragment;
import com.azimgaurav.smartstudentassistant.fragments.NotesFragment;
import com.azimgaurav.smartstudentassistant.fragments.ScheduleFragment;
import com.azimgaurav.smartstudentassistant.fragments.SubmissionsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    android.support.v4.app.FragmentTransaction fragmentTransaction;
    DrawerLayout drawer;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference userReference;
    FloatingActionButton fab;
    TextView tvUserEmail,tvUserName;
    android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up UI components
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        userReference=FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        actionBar.setTitle("Smart Student Assistant");

        View view=navigationView.getHeaderView(0);
        tvUserEmail=view.findViewById(R.id.user_email);
        tvUserName=view.findViewById(R.id.user_name);
        tvUserEmail.setText(firebaseUser.getEmail());

        userReference.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvUserName.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,new NotesFragment());
        fragmentTransaction.commit();

        fab = findViewById(R.id.add_notes_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Enter title");
                final EditText input = new EditText(getApplicationContext());
                input.setSingleLine(true);
                //input.setBackgroundColor(Color.parseColor("#FFFFFF"));
                input.setTextColor(Color.parseColor("#000000"));
                alertDialog.setView(input);
                alertDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(!input.getText().toString().trim().isEmpty())
                                {
                                    String title = input.getText().toString();
                                    Intent intent=new Intent(MainActivity.this,NotesDetailsActivity.class);
                                    intent.putExtra("title",title);
                                    intent.putExtra("isNew",true);
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(MainActivity.this,"Please enter title!",Toast.LENGTH_SHORT).show();

                            }
                        });
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            Toast.makeText(this,"Oops!",Toast.LENGTH_SHORT).show();
            //finishAffinity();
            startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            System.exit(0);
            Toast.makeText(this,"Finished!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_log_out)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Do you want to log out?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.finish();
                    firebaseAuth.signOut();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    System.exit(0);
                }
            }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_notes:
                    fab.setVisibility(View.VISIBLE);
                    fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new NotesFragment());
                    fragmentTransaction.commit();
                    actionBar.setTitle("Notes");
                    item.setCheckable(true);
                    drawer.closeDrawers();
                    break;
            case R.id.nav_courses:
                    fab.setVisibility(View.INVISIBLE);
                    fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new CoursesFragment());
                    fragmentTransaction.commit();
                    actionBar.setTitle("Courses");
                    item.setCheckable(true);
                    drawer.closeDrawers();
                    break;
            case R.id.nav_attendance:
                    fab.setVisibility(View.INVISIBLE);
                    fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new AttendanceFragment());
                    fragmentTransaction.commit();
                    actionBar.setTitle("Attendance");
                    item.setCheckable(true);
                    drawer.closeDrawers();
                    break;
            case R.id.nav_schedule:
                    fab.setVisibility(View.INVISIBLE);
                    fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new ScheduleFragment());
                    fragmentTransaction.commit();
                    actionBar.setTitle("Schedule");
                    item.setCheckable(true);
                    drawer.closeDrawers();
                    break;
            case R.id.nav_notices:
                    fab.setVisibility(View.INVISIBLE);
                    fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new NoticesFragment());
                    fragmentTransaction.commit();
                    actionBar.setTitle("Notices");
                    item.setCheckable(true);
                    drawer.closeDrawers();
                    break;
            case R.id.nav_submissions:
                    fab.setVisibility(View.INVISIBLE);
                    fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new SubmissionsFragment());
                    fragmentTransaction.commit();
                    actionBar.setTitle("Submissions");
                    item.setCheckable(true);
                    drawer.closeDrawers();
                    break;
            case R.id.nav_expenses:
                    fab.setVisibility(View.INVISIBLE);
                    fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new ExpensesFragment());
                    fragmentTransaction.commit();
                    actionBar.setTitle("Expenses");
                    item.setCheckable(true);
                    drawer.closeDrawers();
                    break;
            case R.id.settings:
                fab.setVisibility(View.INVISIBLE);
                fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new ExpensesFragment());
                fragmentTransaction.commit();
                actionBar.setTitle("Settings");
                item.setCheckable(true);
                drawer.closeDrawers();
                break;
            case R.id.feedback:
                fab.setVisibility(View.INVISIBLE);
                fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new ExpensesFragment());
                fragmentTransaction.commit();
                actionBar.setTitle("Feedback");
                item.setCheckable(true);
                drawer.closeDrawers();
                break;
            case R.id.contact_us:
                fab.setVisibility(View.INVISIBLE);
                fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new ExpensesFragment());
                fragmentTransaction.commit();
                actionBar.setTitle("Contact Us");
                item.setCheckable(true);
                drawer.closeDrawers();
                break;
            default:
                    break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}