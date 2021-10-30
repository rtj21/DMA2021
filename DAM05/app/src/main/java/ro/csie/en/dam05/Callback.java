package ro.csie.en.dam05;

public interface Callback<R> {
    void runResultOnUiThread(R result);
}
