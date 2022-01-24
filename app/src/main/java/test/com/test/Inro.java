package test.com.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import me.relex.circleindicator.CircleIndicator;

//Used For Show Fragments in Intro Activity...

public class Inro extends AppCompatActivity {
    ViewPager viewPager;
    Context ctx = this; // cache This

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inro);


        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        int service = sharedPreferences.getInt("service", 0);
        Boolean show_intro = sharedPreferences.getBoolean("show_intro", false);
        if (service == 1) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (service == 0) {

            if (show_intro == true) {
                startActivity(new Intent(this, Splash.class));
                finish();
            } else {
                final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rootlay);
                //use  me.relex.circleindicator Lib For show Dot for Each Fragment
                final me.relex.circleindicator.CircleIndicator indicatorView = (me.relex.circleindicator.CircleIndicator) findViewById(R.id.indicator);
                viewPager = (ViewPager) findViewById(R.id.viewpager);
                final CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                FragmentAdpter m = new FragmentAdpter(getSupportFragmentManager(), getApplicationContext());
                viewPager.setAdapter(m);
                indicator.setViewPager(viewPager);

                viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    Window window = getWindow();

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        //for each position used Diffrent Color And Set this Color in linearLayout and indicatorView
                        switch (position) {
                            case 0:
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    window.setStatusBarColor(getResources().getColor(R.color.frag1));
                                }

                                linearLayout.setBackgroundColor(getResources().getColor(R.color.frag1));
                                indicatorView.setBackgroundColor(getResources().getColor(R.color.frag1));
                                break;
                            case 1:
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    window.setStatusBarColor(getResources().getColor(R.color.frag2));
                                }

                                linearLayout.setBackgroundColor(getResources().getColor((R.color.frag2)));
                                indicatorView.setBackgroundColor(getResources().getColor((R.color.frag2)));
                                break;
                            case 2:
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    window.setStatusBarColor(getResources().getColor(R.color.frag3));
                                }

                                linearLayout.setBackgroundColor(getResources().getColor((R.color.frag3)));
                                indicatorView.setBackgroundColor(getResources().getColor((R.color.frag3)));
                                break;
                            case 3:
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                    window.setStatusBarColor(getResources().getColor(R.color.frag4));
                                }

                                linearLayout.setBackgroundColor(getResources().getColor((R.color.frag4)));
                                indicatorView.setBackgroundColor(getResources().getColor((R.color.frag4)));
                                break;
                        }


                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
            }
        }
    }
    //these Method used For Fragmnet Navigation

    public void Prev(View view) {
        if (index <= 0) {
            index = 1;
        }
        --index;
        viewPager.setCurrentItem(index);
    }

    public void next(View view) {
        if (index >= 3) {
            index = 2;
        }
        ++index;
        viewPager.setCurrentItem(index);
    }
}
