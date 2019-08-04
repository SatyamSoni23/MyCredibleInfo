package com.example.mycredibleinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class details extends AppCompatActivity {
    DatabaseReference rootRef, demoRef, demoRef1, demoRef2, demoRef3;
    private StorageReference storageRef,dataRef;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    CircleImageView image_view;
    TextView name, collage;
    public static String strName, strCollage, strLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        viewPager = (ViewPager) findViewById(R.id.viewPage);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        dl = (DrawerLayout) findViewById(R.id.dl);
        name = findViewById(R.id.name);
        collage = findViewById(R.id.collage);
        image_view = findViewById(R.id.image_view);



        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("profile").child(login.newEmail).child("personalDetail").child("name");
        demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                strName = dataSnapshot.getValue().toString();
                name.setText(strName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                openErrorActivity();
            }
        });
        demoRef1 = rootRef.child("profile").child(login.newEmail).child("educationalDetail").child("organisation");
        demoRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strCollage = dataSnapshot.getValue().toString();
                collage.setText(strCollage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                openErrorActivity();
            }
        });
        demoRef2 = rootRef.child("profile").child(login.newEmail).child("educationalDetail").child("location");
        demoRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strLocation = dataSnapshot.getValue().toString();
                collage.append(" | " + strLocation);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                openErrorActivity();
            }
        });


        storageRef = FirebaseStorage.getInstance().getReference().child("profileImage").child(login.newEmail);
        dataRef = storageRef.child(login.newEmail + ".jpg");
        dataRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(details.this).load(uri).into(image_view);
            }
        });

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab3Fragment(), "Personal");
        adapter.addFragment(new Tab1Fragment(), "Education");
        adapter.addFragment(new Tab2Fragment(), "Professional");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //*******************************Nav bar****************************************

        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.updatePassword){
                    Toast.makeText(details.this, "Update Password", Toast.LENGTH_SHORT).show();
                    openUpdatePasswordActivity();
                }
                else if(id == R.id.deleteAccount){
                    Toast.makeText(details.this, "Delete Account", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(details.this)
                            .setTitle("Delete Account")
                            .setMessage("Are you sure you want to delete this account?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    demoRef3 = rootRef.child("profile");
                                    demoRef3.child(login.newEmail).removeValue();
                                    openDeleteActivity();
                                }
                            })

                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void openErrorActivity(){
        Intent intent = new Intent(this, errorActivity.class);
        startActivity(intent);
    }

    public void openDeleteActivity(){
        Intent intent = new Intent(this, deleteAccount.class);
        startActivity(intent);
    }
    public void openUpdatePasswordActivity(){
        Intent intent = new Intent(this, updatePassword.class);
        startActivity(intent);
    }
}
