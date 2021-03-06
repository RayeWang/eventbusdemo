package wang.raye.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import wang.raye.eventbus.bean.OnEvent;
import wang.raye.eventbus.bean.OnEventAsync;
import wang.raye.eventbus.bean.OnEventBackgroundThread;
import wang.raye.eventbus.bean.OnEventMainThread;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;
import wang.raye.preioc.annotation.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindById(R.id.onEvent)
    TextView onEvent;
    @BindById(R.id.onEventMainThread)
    TextView onEventMainThread;
    @BindById(R.id.onEventBackgroundThread)
    TextView onEventBackgroundThread;
    @BindById(R.id.onEventAsync)
    TextView onEventAsync;
    @BindById(R.id.main)
    TextView main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        PreIOC.binder(this);
        main.append("  id:"+Thread.currentThread().getId());
        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.open})
    public void click(View view){
        Intent intent = new Intent(this,ActivityTwo.class);
        startActivity(intent);
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void posting(OnEvent msg){
        onEvent.append("  id:"+Thread.currentThread().getId());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void main(OnEventMainThread msg){
        onEventMainThread.append("  id:"+Thread.currentThread().getId());
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void back(OnEventBackgroundThread msg){
        final long id = Thread.currentThread().getId();
        onEventBackgroundThread.post(new Runnable() {
            @Override
            public void run() {
                onEventBackgroundThread .append("  id:"+id);
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void async(OnEventAsync msg){
        final long id = Thread.currentThread().getId();
        onEventAsync.post(new Runnable() {
            @Override
            public void run() {
                onEventAsync .append("  id:"+id);
            }
        });
    }

    @Override
    protected void onDestroy() {
        final long id = Thread.currentThread().getId();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
