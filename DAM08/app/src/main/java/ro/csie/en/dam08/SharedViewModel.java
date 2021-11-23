package ro.csie.en.dam08;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<ClipData.Item> selected = new MutableLiveData<>();

    public void select(ClipData.Item item)
    {
        selected.setValue(item);
    }
    public LiveData<ClipData.Item> getSelected()
    {
        return selected;
    }
}
