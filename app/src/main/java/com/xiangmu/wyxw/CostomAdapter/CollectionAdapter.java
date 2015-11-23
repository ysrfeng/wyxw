package com.xiangmu.wyxw.CostomAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;
/**
 * Created by Administrator on 2015/11/12.
 */
public class CollectionAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> stringList;

    public CollectionAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> stringList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.stringList = stringList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
////        container.addView(fragmentList.get(position).getView());
//        return fragmentList.get(position).getView();
//    }
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
////        super.destroyItem(container, position, object);
//        container.removeView(fragmentList.get(position).getView());
//    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }
}
