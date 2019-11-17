package com.cxw.avnight.weight;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import android.view.KeyEvent;
import android.view.View;

import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.TextView;

import com.cxw.avnight.R;


public class CommentDialog extends Dialog implements View.OnClickListener {


    TextView tv_commit;//提交

    EditText et_comment;//评论内容

    private Context context;
    private OnCommitListener listener;

    public CommentDialog(Context context) {
        this(context, R.style.inputDialog);
        this.context = context;
    }

    public CommentDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_dialog_layout);
        et_comment = findViewById(R.id.et_comment);
        tv_commit = findViewById(R.id.tv_commit);
        initListener();
    }

    private void initListener() {
        //设置显示对话框时的返回键的监听
        this.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0)
                CommentDialog.this.cancel();
            return false;
        });
        //设置EditText内容改变的监听
        et_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    tv_commit.setClickable(true);
                } else {

                    tv_commit.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tv_commit.setOnClickListener(this);//提交

    }

    public void setOnCommitListener(OnCommitListener listener) {
        this.listener = listener;
    }

    public interface OnCommitListener {

        void onCommit(EditText et, View v);//提交数据
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                if (null != listener) {
                    listener.onCommit(et_comment, v);
                }
                break;

        }
    }



}