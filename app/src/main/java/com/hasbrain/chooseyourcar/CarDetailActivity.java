package com.hasbrain.chooseyourcar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.hasbrain.chooseyourcar.fragment.CarDetailFragment;
import com.hasbrain.chooseyourcar.model.Car;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/19/15.
 */
public class CarDetailActivity extends AppCompatActivity {
    private static List<Car> carList;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    public static void initialize(List<Car> carList) {
        CarDetailActivity.carList = carList;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert carList == null : "Initialize First!!";

        setContentView(R.layout.car_detail);
        ButterKnife.bind(this);

        int carPostion = getIntent().getIntExtra("position", 0);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        if (carList.size() > 2)
            viewPager.setCurrentItem(carPostion + 1);
        else
            viewPager.setCurrentItem(carPostion);
        viewPager.setPageMargin(-dipToPixels(100 + 30));
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(viewPager.getAdapter().getPageTitle(position));
                int pageCount = viewPager.getAdapter().getCount();

                if (pageCount > 3) {
                    if (position == 0)
                        viewPager.setCurrentItem(pageCount - 2, false);
                    else if (position == pageCount - 1)
                        viewPager.setCurrentItem(1, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private int dipToPixels(float dipValue) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class Adapter extends FragmentStatePagerAdapter {

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int virtualPostion) {
            int realPosition = getRealPosition(virtualPostion);
            return CarDetailFragment.newInstance(carList.get(realPosition));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Infinite scroll: " + (getRealPosition(position) + 1) + "/" + carList.size();
        }

        public int getRealPosition(int virtualPosition) {
            int realPosition;
            if (getCount() > 3) {
                if (virtualPosition == 0)
                    realPosition = carList.size() - 1;
                else if (virtualPosition == getCount() - 1)
                    realPosition = 0;
                else
                    realPosition = virtualPosition - 1;
            } else
                realPosition = virtualPosition;
            return realPosition;
        }

        @Override
        public int getCount() {
            return carList.size() > 1 ? carList.size() + 2 : carList.size();
        }

    }
}
