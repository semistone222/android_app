package com.semistone.donately.intro;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.semistone.donately.R;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(getString(R.string.onboaderpage1_title), getString(R.string.onboaderpage1_description), R.drawable.ic_loving, ContextCompat.getColor(this, R.color.colorAccent)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.onboaderpage2_title), getString(R.string.onboaderpage2_description), R.drawable.ic_video_play, ContextCompat.getColor(this, R.color.colorAccent)));
        addSlide(AppIntroFragment.newInstance(getString(R.string.onboaderpage3_title), getString(R.string.onboaderpage3_description), R.drawable.ic_free, ContextCompat.getColor(this, R.color.colorAccent)));
        addSlide(SampleSlide.newInstance(R.layout.intro_login));

        showSkipButton(false);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}