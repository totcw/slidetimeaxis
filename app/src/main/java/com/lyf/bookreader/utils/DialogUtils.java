package com.lyf.bookreader.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.lyf.bookreader.R;
import com.lyf.bookreader.view.refreshload.CommonProgressBar;


/**
 * @author ganlin
 * @email ganlin@zhangkongapp.com
 * @time 2017/5/5 9:39
 * @company 长沙掌控信息科技有限公司
 * @desc
 */

public class DialogUtils {
    public static Dialog createProgressDialog(Context context, String tips) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.BookReaderDialog);
        View contentView = View.inflate(context, R.layout.dialog_progress, null);
        CommonProgressBar progressBar = (CommonProgressBar) contentView.findViewById(R.id.id_cpb_dialog_progress_progress);
        TextView tipsView = (TextView) contentView.findViewById(R.id.id_tv_dialog_progress_tips);
        tipsView.setText(tips);
        progressBar.startProgress();
        builder.setView(contentView);
        return builder.create();
    }
}
