package homework.shoppinglist.newdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import homework.shoppinglist.R;
import homework.shoppinglist.model.ShoppingList;

/**
 * Created by Akos on 2017. 11. 24..
 */

public class NewShoppingListDialogFragment  extends AppCompatDialogFragment {

    private EditText nameEditText;
    private DatePicker datePicker;

    public static final String TAG = "NewShoppingListDialogFragment";

    public interface INewShoppingListDialogListener {
        void onShoppingListCreated(ShoppingList newList);
    }

    private INewShoppingListDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof INewShoppingListDialogListener) {
            listener = (INewShoppingListDialogListener) activity;
        } else {
            throw new RuntimeException("Activity must implement the INewShoppingItemDialogListener interface!");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.new_item)
                .setView(getContentView())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isValid()) {
                            listener.onShoppingListCreated(getShoppingList());
                        }
                    }

                    private boolean isValid() {
                        return nameEditText.getText().length() > 0 && nameEditText.getText().length() < 20;
                    }

                    private ShoppingList getShoppingList() {
                        ShoppingList shoppingList = new ShoppingList();

                        StringBuilder ss = new StringBuilder(nameEditText.getText().toString());
                        ss.setCharAt(0,ss.toString().toUpperCase().charAt(0));
                        String[] sse = ss.toString().split("\n");
                        shoppingList.listName = sse[0].toString();

                        try {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
                            shoppingList.date = calendar.getTime();
                        } catch (NumberFormatException e) {
                            shoppingList.date = null;
                        }
                        shoppingList.save();
                        return shoppingList;
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_shopping_list, null);
        nameEditText = (EditText) contentView.findViewById(R.id.ShoppingListName);
        datePicker = (DatePicker) contentView.findViewById(R.id.ListActivatedDate);
        return contentView;
    }
}
