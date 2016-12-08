
package com.shihuo.shihuo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.shihuo.shihuo.R;

public class ProgressDialog extends Dialog {
    private Context context;

    private ImageView img;

    private TextView txt;

    public ProgressDialog(Context context) {
        super(context, R.style.progress_dialog);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_progress, null);
        img = (ImageView)view.findViewById(R.id.progress_dialog_img);
        txt = (TextView)view.findViewById(R.id.progress_dialog_txt);
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.loading_dialog_progressbar);
        img.setAnimation(anim);
        txt.setText(R.string.progressbar_dialog_txt);
        setContentView(view);
    }

    public void setMsg(String msg) {
        txt.setText(msg);
    }

    public void setMsg(int msgId) {
        txt.setText(msgId);
    }

}
