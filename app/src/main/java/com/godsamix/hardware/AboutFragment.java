package com.godsamix.hardware;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.godsamix.hardware.BuildConfig;
import com.godsamix.hardware.R;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutFragment extends Fragment {
    

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       // View root = inflater.inflate(R.layout.fragment_about, container, false);

        View aboutPage = new AboutPage(getContext())
                .isRTL(false)
                .enableDarkMode(false)
                .setDescription(getString(R.string.app_desc))
 //               .setCustomFont(String) // or Typeface
                .setImage(R.drawable.pc)
 //               .addItem(versionElement)
                .addGroup("Connect with us")
                .addEmail("samer.hd.ah@gmail.com","Contact me")
                .addWebsite("https://www.linkedin.com/in/samer-dev","Connect with me on LinkedIn")
 //               .addFacebook("")
 //               .addTwitter("")
 //               .addYoutube("")
                .addPlayStore("https://play.google.com/store/apps/developer?id=Samix","Rate me on the play store")
                .addGitHub("SamixDev","Fork me on GitHub")
 //               .addInstagram("medyo80")
                .addItem(getCopyRightsElement())
                .create();


        return aboutPage;
    }
    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.github);
        copyRightsElement.setAutoApplyIconTint(true);
      //  copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }
}