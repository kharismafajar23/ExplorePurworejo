package com.demanganesia.explorepurworejo.HelperClasses;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.demanganesia.explorepurworejo.R;

public class NoInternetDialog extends Dialog {
    public NoInternetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_dialog);

        Button BtnCobaLagi = findViewById(R.id.BtnCobaLagi);

        BtnCobaLagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
