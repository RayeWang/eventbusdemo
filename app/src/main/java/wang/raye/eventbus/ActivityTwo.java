package wang.raye.eventbus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import wang.raye.eventbus.bean.OnEvent;
import wang.raye.eventbus.bean.OnEventAsync;
import wang.raye.eventbus.bean.OnEventBackgroundThread;
import wang.raye.eventbus.bean.OnEventMainThread;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.OnClick;

public class ActivityTwo extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_two);
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
    }

    @OnClick({R.id.onEvent,R.id.onEventAsync,R.id.onEventBackgroundThread,R.id.onEventMainThread})
    public void click(View view){
        switch (view.getId()){
            case R.id.onEvent:
                EventBus.getDefault().post(new OnEvent());
                break;
            case R.id.onEventAsync:
                EventBus.getDefault().post(new OnEventAsync());
                break;
            case R.id.onEventBackgroundThread:
                EventBus.getDefault().post(new OnEventBackgroundThread());
                break;
            case R.id.onEventMainThread:
                EventBus.getDefault().postSticky(new OnEventMainThread());
                break;

        }
    }

}
