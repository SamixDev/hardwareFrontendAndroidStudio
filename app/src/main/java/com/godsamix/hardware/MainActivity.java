package com.godsamix.hardware;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.godsamix.hardware.Controllers.HardListController;
import com.godsamix.hardware.Helpers.RESTapis;
import com.godsamix.hardware.Helpers.RetrofitService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public static String idToken;

    //shared prefs
    public  static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //shared prefs init
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        personEmail = sharedPreferences.getString("email", "");
        idToken = sharedPreferences.getString("token", "");

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
        }else{
        }
        updateUI(account);
    }
    private void signInUp(String login, String fullname){
        RESTapis RESTapis = RetrofitService.createService(RESTapis.class);
        Call call;
        call = RESTapis.signInUp(login, fullname);
        call.enqueue(new Callback() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()) {
                    String res = new Gson().toJson(response.body());
                    try {
                        JSONObject obj = new JSONObject(res);
                        idToken = obj.getString("message");
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("token", idToken);
                        myEdit.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        drawer.closeDrawers();
        int id = menuItem.getItemId();
        Bundle bundle = new Bundle();
        NavOptions navOptionshardware = new NavOptions.Builder().setPopUpTo(R.id.nav_cpu, true)
                .setEnterAnim(R.anim.slide_in_right).setExitAnim(R.anim.slide_out_left).setPopEnterAnim(R.anim.slide_in_left).setPopExitAnim(R.anim.slide_out_right)
                .build();
        switch (id) {
            case R.id.nav_cpu:
                bundle.putString("listType", "cpu");
                navController.navigate(R.id.nav_hardware,bundle,navOptionshardware);
                break;
            case R.id.nav_board:
                bundle.putString("listType", "board");
                navController.navigate(R.id.nav_hardware,bundle,navOptionshardware);
                break;
            case R.id.nav_vga:
                bundle.putString("listType", "vga");
                navController.navigate(R.id.nav_hardware,bundle,navOptionshardware);
                break;
            case R.id.nav_about:
                NavOptions navOptionsabout = new NavOptions.Builder().setPopUpTo(R.id.nav_about, true)
                        .setEnterAnim(R.anim.slide_in_right).setExitAnim(R.anim.slide_out_left).setPopEnterAnim(R.anim.slide_in_left).setPopExitAnim(R.anim.slide_out_right)
                        .build();
                navController.navigate(R.id.nav_about,bundle,navOptionsabout);
                break;
            case R.id.nav_version:
                NavOptions navOptionsversion = new NavOptions.Builder().setPopUpTo(R.id.nav_version, true)
                        .setEnterAnim(R.anim.slide_in_right).setExitAnim(R.anim.slide_out_left).setPopEnterAnim(R.anim.slide_in_left).setPopExitAnim(R.anim.slide_out_right)
                        .build();
                navController.navigate(R.id.nav_version,bundle,navOptionsversion);
                break;
            case R.id.nav_build:
                NavOptions navOptionsbuild = new NavOptions.Builder().setPopUpTo(R.id.nav_build, true)
                        .setEnterAnim(R.anim.slide_in_right).setExitAnim(R.anim.slide_out_left).setPopEnterAnim(R.anim.slide_in_left).setPopExitAnim(R.anim.slide_out_right)
                        .build();
                navController.navigate(R.id.nav_build,bundle,navOptionsbuild);
                break;
        }
        return true;
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            // Set the name in the nav head
             name.setText(account.getDisplayName());
             mail.setText(account.getEmail());
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
          //   idToken = account.getIdToken();
          //  personEmail = account.getEmail();
            signInUp(account.getEmail(), account.getDisplayName());
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("email", account.getEmail());
            myEdit.commit();
            personEmail = sharedPreferences.getString("email", "");
         //   Log.e("token is ", idToken);
          //  Log.e("email is ", personEmail);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {

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
                        personEmail = "";
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("email", "");
                        myEdit.putString("token", "");
                        myEdit.commit();
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
            signInUp(personEmail, personName);
            return true;
        }
        return false;
    }
}