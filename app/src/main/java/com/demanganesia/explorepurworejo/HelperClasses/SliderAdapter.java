package com.demanganesia.explorepurworejo.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.demanganesia.explorepurworejo.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int images[] = {
            R.drawable.ic_onboard1,
            R.drawable.ic_onboard2,
            R.drawable.ic_onboard3
    };

    int titlenya [] = {
            R.string.onboard_title_1,
            R.string.onboard_title_2,
            R.string.onboard_title_3
    };

    int deskripsinya [] = {
            R.string.onboard_deskripsi_1,
            R.string.onboard_deskripsi_2,
            R.string.onboard_deskripsi_3
    };

    @Override
    public int getCount() {
        return titlenya.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container, false);

        ImageView imageView = view.findViewById(R.id.image_onboard);
        TextView title = view.findViewById(R.id.title_onboard);
        TextView deskripsi = view.findViewById(R.id.deskripsi_onboard);

        imageView.setImageResource(images[position]);
        title.setText(titlenya[position]);
        deskripsi.setText(deskripsinya[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
