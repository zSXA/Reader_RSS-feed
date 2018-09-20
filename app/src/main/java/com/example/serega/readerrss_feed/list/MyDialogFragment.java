package com.example.serega.readerrss_feed.list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.serega.readerrss_feed.R;

public class MyDialogFragment extends DialogFragment {

    private EditText etDialog;
    private Button btnDialog;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String s = getArguments().getString("rssAddress");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_custom, null);
        builder.setView(view);

        etDialog = (EditText) view.findViewById(R.id.etDialog);
        btnDialog = (Button) view.findViewById(R.id.btnDialog);

        etDialog.setText(s);

        View.OnClickListener btnSave = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rss = etDialog.getText().toString();
                ((MainActivity) getActivity()).onChange(rss);
            }
        };

        btnDialog.setOnClickListener(btnSave);
        return builder.create();
    }
}
