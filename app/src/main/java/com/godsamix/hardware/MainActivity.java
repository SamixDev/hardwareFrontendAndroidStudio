package com.godsamix.hardware;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    private static final int RC_SIGN_IN = 9001;
    private AppBarConfiguration mAppBarConfiguration;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private Button signOutButton;
    private NavigationView navigationView;
    private TextView name;
    private TextView mail;
    private ImageView img;
    private DrawerLayout drawer;
    private NavController navController;
    private  Toolbar toolbar;
    //for google retrieve sign in info
    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;
    Uri personPhoto;
    String idToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.nav_view);

        name = navigationView.getHeaderView(0).findViewById(R.id.namenav);
        mail = navigationView.getHeaderView(0).findViewById(R.id.emailnav);
        img = navigationView.getHeaderView(0).findViewById(R.id.imagenav);
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
         mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        //updateUI(account);


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_menu,
                R.id.nav_build, R.id.nav_board, R.id.nav_cpu,R.id.nav_version,R.id.nav_about,R.id.nav_vga)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
//        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//            @Override
//            public void onDestinationChanged(@NonNull NavController controller,
//                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
//             //   Toast.makeText(getApplicationContext(),"destination "+destination.getId() , Toast.LENGTH_LONG).show();
//              if  (destination.getId() == R.id.nav_cpu){
//                  Toast.makeText(getApplicationContext(),"arrived " + controller.getNavigatorProvider(), Toast.LENGTH_LONG).show();
//
//              }
//            }
//        });

        // Set the dimensions of the sign-in button.
        signInButton = navigationView.getHeaderView(0).findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                //Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
                        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                        startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        //sign out button
        signOutButton = navigationView.getHeaderView(0).findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signOut();
            }
        });


        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInClient.silentSignIn().addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {
            @Override
            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                handleSignInResult(task);

            }
        });

        //update layout from sign in
        if (retrieveSignInInfo()){
         //   Toast.makeText(getApplicationContext(),"Hello "+ personName,Toast.LENGTH_SHORT).show();
        }else{

        }
        updateUI(account);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setChecked(true);

        drawer.closeDrawers();

        int id = menuItem.getItemId();
        Bundle bundle = new Bundle();
        NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.nav_cpu, true).build();
        switch (id) {

            case R.id.nav_cpu:
               // navController.navigate(R.id.firstFragment);
              //  navController.popBackStack(R.id.nav_host_fragment, true);

                bundle.putString("listType", "cpu");
                navController.navigate(R.id.nav_cpu,bundle,navOptions);
                break;
            case R.id.nav_board:
                // navController.navigate(R.id.firstFragment);
               // navController.popBackStack(R.id.nav_host_fragment, true);
                bundle.putString("listType", "board");
                navController.navigate(R.id.nav_cpu,bundle,navOptions);
                break;
            case R.id.nav_vga:
                // navController.navigate(R.id.firstFragment);
                bundle.putString("listType", "vga");
                navController.navigate(R.id.nav_cpu,bundle,navOptions);
                break;

            case R.id.nav_about:
                navController.navigate(R.id.nav_about);
                break;

            case R.id.nav_version:
                navController.navigate(R.id.nav_version);
                break;

        }
        return true;

    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            // Set the name in the nav head
             name.setText(account.getDisplayName());
             mail.setText(account.getEmail());
          //  img.setImageURI(account.getPhotoUrl());
             if (account.getPhotoUrl() == null){

              img.setImageResource(R.drawable.profiledefault);
             }else{
                Picasso.get().load(account.getPhotoUrl()).into(img);
             }

        signInButton.setVisibility(View.GONE);
        signOutButton.setVisibility(View.VISIBLE);
        } else {
         name.setText("");
         mail.setText("");
         img.setImageResource(R.drawable.profiledefault);
         signInButton.setVisibility(View.VISIBLE);
         signOutButton.setVisibility(View.GONE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
             idToken = account.getIdToken();


         //   Toast.makeText(getApplicationContext(),"Hello "+ idToken,Toast.LENGTH_SHORT).show();
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("google sign in: ", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    // [END handleSignInResult]

    // [START signOut]
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        idToken = "";
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private boolean retrieveSignInInfo(){

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
             personName = acct.getDisplayName();
             personGivenName = acct.getGivenName();
             personFamilyName = acct.getFamilyName();
             personEmail = acct.getEmail();
             personId = acct.getId();
             personPhoto = acct.getPhotoUrl();
          //  idToken = acct.getIdToken();
            return true;
        }
        return false;
    }
}