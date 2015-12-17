package com.test.greendaodemo;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import greendao.Candidate;
import greendao.CandidateDao;
import greendao.DaoMaster;
import greendao.DaoSession;

public class MainActivity extends ListActivity {
    DaoMaster.DevOpenHelper helper;
    private EditText editText;
    SQLiteDatabase db;
    DaoMaster daoMaster;
    DaoSession daoSession;
    CandidateDao candidateDao;
    private Cursor cursor;
    private String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDatabase();
        String textColumn = CandidateDao.Properties.Name.columnName;
        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
        cursor = db.query(getCandidateDao().getTablename(), getCandidateDao().getAllColumns(), null, null, null, null, orderBy);
        String[] from = {textColumn, CandidateDao.Properties.Description.columnName};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from,
                to);
        setListAdapter(adapter);

        editText = (EditText) findViewById(R.id.editTextNote);
    }

    private void setupDatabase(){
        helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        candidateDao = daoSession.getCandidateDao();
    }

    private CandidateDao getCandidateDao(){
        return daoSession.getCandidateDao();
    }

    /**
     * Button 点击的监听事件
     *
     * @param view
     */
    public void onMyButtonClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAdd:
                addNote();
                break;
            case R.id.buttonSearch:
                search(editText.getText().toString());
                break;
            default:
                Log.d(TAG, "what has gone wrong ?");
                break;
        }
    }

    int age ;
    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        age++;
        // 插入操作，简单到只要你创建一个 Java 对象
        Candidate candidate = new Candidate(null, noteText, age,comment);
        getCandidateDao().insert(candidate);
        Log.d(TAG, "Inserted new note, ID: " + candidate.getId());
        cursor.requery();
    }

    private void search(String ss) {
        // Query 类代表了一个可以被重复执行的查询
        Query query = getCandidateDao().queryBuilder()
                .where(CandidateDao.Properties.Name.eq(ss))
                .orderAsc(CandidateDao.Properties.Id)
                .build();

// 查询结果以 List 返回
        List<Candidate> candidates = query.list();
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        if(null != candidates && candidates.size() > 0 ){
            Log.d(TAG, "search new note, ID: " + candidates.size()+" ---- "+candidates.toString());
        }else{
            Log.d(TAG, "search new note, ID: " + null);
        }

// QueryBuilder<> qb = getNoteDao().queryBuilder();
// qb.where(NoteDao.Properties.Text.eq(ss));
// qb.orderAsc(NoteDao.Properties.Id);// 排序依据
// qb.list();
    }

    /**
     * ListView 的监听事件，用于删除一个 Item
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // 删除操作，你可以通过「id」也可以一次性删除所有
        getCandidateDao().deleteByKey(id);
// getNoteDao().deleteAll();
        Log.d(TAG, "Deleted note, ID: " + id);
        cursor.requery();
    }
}

