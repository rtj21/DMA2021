package ro.csie.en.dam08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements GreenFragment.OnGreenFragmentListener {

    private static final String BLUE_FRAG = "BLUE_FRAG";
    private static final String GREEN_FRAG = "GREEN_FRAG";
    private static final String TAG = MainActivity.class.getName();
    private BlueFragment blueFragment;
    private GreenFragment greenFragment;
    SharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        blueFragment = (BlueFragment) fragmentManager.findFragmentByTag(BLUE_FRAG);
        if(blueFragment == null)
        {
            blueFragment = new BlueFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.blue_fragment_container, blueFragment, BLUE_FRAG);
            fragmentTransaction.commit();
        }

        greenFragment = (GreenFragment) fragmentManager.findFragmentByTag(GREEN_FRAG);
        if(greenFragment == null) {
            greenFragment = new GreenFragment();
            fragmentManager.beginTransaction().add(R.id.green_fragment_container, greenFragment, GREEN_FRAG).commit();
        }

        //receiving data from fragments
        fragmentManager.setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String message = result.getString("dataKey");
                Log.d(TAG, "Value received from fragments: " + message);
            }
        });

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        viewModel.getSelected().observe(this, new Observer<ClipData.Item>() {
            @Override
            public void onChanged(ClipData.Item item) {
                Log.d(TAG, "Selected value: " + item.toString());
            }
        });

    }

    @Override
    public void messageFromGreenFragment(String value) {
        blueFragment.receiveMessage(value);
    }
}