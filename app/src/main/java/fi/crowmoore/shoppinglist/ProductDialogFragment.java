package fi.crowmoore.shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Crowmoore on 10/10/2016.
 */

public class ProductDialogFragment extends DialogFragment {

    DialogListener listener;

    public interface DialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String name, int count, float price);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (DialogListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.dialog_view, null);
        builder.setView(dialogView)
                .setTitle("Add a new product")
                .setPositiveButton(R.string.dialog_button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText nameField = (EditText) dialogView.findViewById(R.id.product_name);
                        EditText countField = (EditText) dialogView.findViewById(R.id.product_count);
                        EditText priceField = (EditText) dialogView.findViewById(R.id.product_price);

                        String name = nameField.getText().toString();
                        int count = Integer.parseInt(countField.getText().toString());
                        float price = Float.parseFloat(priceField.getText().toString());

                        listener.onDialogPositiveClick(ProductDialogFragment.this, name, count, price);
                    }
                })
                .setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogNegativeClick(ProductDialogFragment.this);
                    }
                });

        return builder.create();
    }
}
