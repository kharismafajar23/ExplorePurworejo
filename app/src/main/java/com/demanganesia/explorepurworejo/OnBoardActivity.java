package com.demanganesia.explorepurworejo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.demanganesia.explorepurworejo.HelperClasses.SliderAdapter;
import com.demanganesia.explorepurworejo.MasukDanDaftar.LoginActivity;

public class OnBoardActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;

    SliderAdapter sliderAdapter;
    TextView[] dots;
    TextView lanjut, mulai;
    int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        //menghilangkan status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager = (ViewPager) findViewById(R.id.sliderViewPager);
        dotsLayout = (LinearLayout) findViewById(R.id.dots);
        lanjut = (TextView) findViewById(R.id.lanjut);
        mulai = (TextView) findViewById(R.id.mulai);

        //memanggil adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    private void addDots(int position) {

        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }

        if(dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.hijau));
        }

    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos = position;

            if(position == 0){
                lanjut.setVisibility(View.VISIBLE);
                mulai.setVisibility(View.INVISIBLE);
            }
            else if(position == 1){
                lanjut.setVisibility(View.VISIBLE);
                mulai.setVisibility(View.INVISIBLE);
            }
            else {
                lanjut.setVisibility(View.INVISIBLE);
                mulai.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void Lanjut(View view) {
        viewPager.setCurrentItem(currentPos + 1);
    }

    public void Mulai(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}