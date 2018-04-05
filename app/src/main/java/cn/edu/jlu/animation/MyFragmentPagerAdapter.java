package cn.edu.jlu.animation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by paworks on 18-1-15.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitle = new String[]{"首页","新番","讨论","我的"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new AnimationStore();
        } else if (position == 2) {
            return new Discussion();
        } else if (position == 3) {
            return new Mine();
        }
        return new HomePage();
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
