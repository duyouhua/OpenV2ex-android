package licrafter.com.v2ex.ui.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import licrafter.com.v2ex.R;

/**
 * Created by shell on 15-11-15.
 */
public class ProgressbarDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_progressbar, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.DialogInOutCenterAnimation);
        return dialog;
    }


}
