package democoncatrx.com.democoncatrx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demoConcat();
    }

    private void demoConcat() {
        List<Integer> data = new ArrayList<>();
        Flowable.concat(Flowable.just(getDataLocal(), getDataServer()))
                .subscribe(
                        o -> {
                            for (Integer i : o) {
                                if (!data.contains(i)) data.add(i);
                            }
                        },

                        throwable -> {
                            Log.i("MainActivity", "onError --------------------: ");
                        },

                        () -> {
                            for (Integer o : data) {
                                Log.i("MainActivity", "onComplete --------------------: " + o);
                            }
                        }
                );
    }

    private Flowable<List<Integer>> getDataLocal() {
        List<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(2);
        data.add(3);
        return Flowable.just(data);
    }

    private Flowable<List<Integer>> getDataServer() {
        List<Integer> data = new ArrayList<>();
        data.add(4);
        data.add(5);
        data.add(6);
        return Flowable.just(data).doOnNext(this::insertDataToLocal);
    }

    private void insertDataToLocal(List<Integer> data) {
        // TODO: insert data to database
    }
}
