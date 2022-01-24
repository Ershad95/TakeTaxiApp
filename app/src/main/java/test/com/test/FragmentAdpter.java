package test.com.test;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

//Use this Adapter For Managing App Introduce Fragment
public class FragmentAdpter extends FragmentPagerAdapter {

    Context context;
    String[] name = {"Fragment1", "Fragment2", "Fragment3", "Fragment4"};

    public FragmentAdpter(android.support.v4.app.FragmentManager supportFragmentManager, Context applicationContext) {
        super(supportFragmentManager);
        context = applicationContext;

    }


    //Choose Position of each Fragment in Intro
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            case 3:
                return new Fragment4();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return name[position];
    }
}
