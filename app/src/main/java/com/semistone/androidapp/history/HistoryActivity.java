package com.semistone.androidapp.history;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.semistone.androidapp.R;
import com.semistone.androidapp.data.History;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.realm.Realm;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Realm.init(this);
        mRealm = Realm.getDefaultInstance();

        mRvHistory.setLayoutManager(new LinearLayoutManager(this));
        mRvHistory.setAdapter(new HistoryAdapter(this, mRealm.where(History.class).findAll()));
        mRvHistory.setHasFixedSize(true);
        mRvHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
    }

    @OnClick(R.id.fab)
    void onClickFab(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                History history = mRealm.createObject(History.class, History.getNextKey(mRealm));
                history.setDonateDate(System.currentTimeMillis());
                history.setPoint((int) (Math.random() * 100));
            }
        });
    }
}
