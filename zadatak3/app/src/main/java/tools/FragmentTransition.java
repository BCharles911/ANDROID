package tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.user.aplikacija.R;

public class FragmentTransition {

    public static void to(Fragment newFragment, FragmentActivity activity){
        to(newFragment , activity);

    }
    public static void to(Fragment newFragment, FragmentActivity activity, boolean addToBackStack){
        FragmentTransaction transaction = activity.getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.mainContent, newFragment);
        if(addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void remove(Fragment fragment, FragmentActivity activity){
        activity.getSupportFragmentManager().popBackStack();
    }
}
