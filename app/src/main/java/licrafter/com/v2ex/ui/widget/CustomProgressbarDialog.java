package licrafter.com.v2ex.ui.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import licrafter.com.v2ex.R;

/**
 * Created by shell on 15-11-15.
 */
public class CustomProgressbarDialog extends DialogFragment {

    private String message = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        message = (String) getArguments().get("message");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_progressbar, null);
        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        tv_message.setText(message);
        builder.setView(view);
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.DialogInOutCenterAnimation);
        return dialog;
    }


}
