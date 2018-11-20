package homework.shoppinglist.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import homework.shoppinglist.R;

/**
 * Created by Akos on 2017. 11. 24..
 */

public class ItemsPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public ItemsPagerAdapter(FragmentManager fm, Context cm) {
        super(fm);
        context = cm;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment ret = null;
        switch (position) {
            case 0:
                ret = new ActItemsFragment();
                break;
            case 1:
                ret = new InactItemsFragment();
                break;
        }
        return ret;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position) {
            case 0:
                title = context.getString(R.string.activeitem);
                break;
            case 1:
                title = context.getString(R.string.deactiveitem);
                break;
            default:
                title = "";
        }
        return title;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
