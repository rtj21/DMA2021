package ro.csie.en.dam08;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GreenFragment extends Fragment {

    Button button;
    OnGreenFragmentListener greenFrgCallback;
    public interface OnGreenFragmentListener
    {
        void messageFromGreenFragment(String value);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnGreenFragmentListener)
            greenFrgCallback = (OnGreenFragmentListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.green_fragment, container, false);
        button = view.findViewById(R.id.button);

        //sending data to MainActivity through interface implementation
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Hello, Blue! I'm Green.";
                greenFrgCallback.messageFromGreenFragment(message);
            }
        });
        //sending data to MainActivity through FragmentManager listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("dataKey", "Hello, Parent! I'm Green.");
                getParentFragmentManager().setFragmentResult("requestKey", bundle);
            }
        });
        return view;
    }
}
