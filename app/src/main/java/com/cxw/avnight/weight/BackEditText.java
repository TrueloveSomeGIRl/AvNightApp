package com.cxw.avnight.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.cxw.avnight.util.SoftInputUtil;


public class BackEditText extends AppCompatEditText {
    public BackEditText(Context context) {
        super(context);
    }

    public BackEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface BackListener {
        void back(TextView textView);
    }


    private BackListener listener;

    public void setBackListener(BackListener listener) {
        this.listener = listener;
    }

    @Override

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (listener != null) {
                listener.back(this);
                SoftInputUtil.INSTANCE.hideSoftInput(getContext(),this);
            }
        }
        return false;
    }
}