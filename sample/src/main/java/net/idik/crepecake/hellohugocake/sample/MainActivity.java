package net.idik.crepecake.hellohugocake.sample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import net.idik.crepecake.hugo.annotations.DebugLog;

public class MainActivity extends AppCompatActivity {

    @Override
    @DebugLog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(net.idik.crepecake.hellohugocake.sample.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(net.idik.crepecake.hellohugocake.sample.R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(net.idik.crepecake.hellohugocake.sample.R.id.fab);
        fab.setOnClickListener(new MyOnClickListener());

        toolbar.setOnClickListener(new View.OnClickListener() {

            @Override
            @DebugLog
            public void onClick(View v) {
                System.out.println("v in toolbar");
                sayHello(true);
            }

        });
    }

    @Override
    @DebugLog
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(net.idik.crepecake.hellohugocake.sample.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == net.idik.crepecake.hellohugocake.sample.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @DebugLog
    private long sayHello(boolean h) {
        System.out.println(h);
        return System.currentTimeMillis();
    }

    public static class MyOnClickListener implements View.OnClickListener {
        @Override
        @DebugLog
        public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            getHome(System.currentTimeMillis(), "ToT", true);
        }

        @DebugLog
        public void getHome(Long time, String name, boolean h) {
//            return true;
        }
    }
}
