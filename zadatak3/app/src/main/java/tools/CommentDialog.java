package tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.user.aplikacija.R;

public class CommentDialog extends AppCompatDialogFragment {
    private EditText etCommentTitle;
    private EditText etCommentDescription;
    private CommentDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_comment, null);

        builder.setView(view)
                .setTitle("Create comment")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = etCommentTitle.getText().toString();
                        String description = etCommentDescription.getText().toString();
                        listener.applyTexts(title, description);
                    }
                });
        etCommentTitle = view.findViewById(R.id.etCommentTitle);
        etCommentDescription = view.findViewById(R.id.etCommentDescription);

        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (CommentDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement CommentDialogLiostener");
        }
    }


    public interface CommentDialogListener {
        void applyTexts(String title, String description);
    }
}
