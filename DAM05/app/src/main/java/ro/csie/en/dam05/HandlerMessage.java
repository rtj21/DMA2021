package ro.csie.en.dam05;

public class HandlerMessage<R> implements Runnable {
    Callback<R> mainThreadOperation;
    R result;
    public <T> HandlerMessage(Callback<R> callableResponse, R result) {
        this.mainThreadOperation = callableResponse;
        this.result = result;
    }

    @Override
    public void run() {
        mainThreadOperation.runResultOnUiThread(result);
    }
}
