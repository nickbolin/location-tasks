package com.example.neelarora.todoapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neelarora.todoapp.db.TaskContract;
import com.example.neelarora.todoapp.db.TaskDBHelper;

import java.util.ArrayList;

/**
 * View controller for the activity_main.xml VIEWTASK @CLASSDIAGRAM
 */

public class MainActivity extends AppCompatActivity implements geofence_fragment.AddGeofenceFragmentListener {

    private static final String TAG = "MainActivity";
    private TaskDBHelper mHelper; //INTERNAL DB
    private ListView mTaskListView; //LIST OF TASK @CLASSDIAGRAM
    private ArrayAdapter<String> mAdapter;

    /**
     * Oncreate init - android code
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        mHelper = new TaskDBHelper(this);

        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            Log.d(TAG, "Task: " + cursor.getString(idx));
        }
        cursor.close();
        db.close();
        updateUI();
        GeofenceController.getInstance().init(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //----------------------------------------------------------------------------------------
    // FEATURES 1-4
    @Override
    /**
     * Create a new task, ADD TASK TO LIST - FEATURE
     *
     * @CLASSDIAGRAM
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Log.d(TAG, "Add a new task");
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    /**
     * Delete task - FEATURE
     * @param view
     */
    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

    /**
     * Edit the task FEATURE
     * @param view
     */
    public void editTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        final String oldTask = String.valueOf(taskTextView.getText());
        Log.d(TAG, "Update task");
        //final String[] upTask = new String[1];
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Update task")
                .setMessage("What do you want to change?")
                .setView(taskEditText)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTask = String.valueOf(taskEditText.getText());
                        //upTask[0] = newTask;
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        ContentValues vals = new ContentValues();
                        vals.put(TaskContract.TaskEntry.COL_TASK_TITLE, newTask);
                        String selection = TaskContract.TaskEntry.COL_TASK_TITLE + "=?";
                        //String[] selectArgs = { newTask };
                        db.update(TaskContract.TaskEntry.TABLE,
                                vals,
                                selection,
                                new String[]{oldTask});
                        db.close();
                        updateUI();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    /**
     * Add_geofence to the view, user adds geofence - FEATURE
     * @param view
     */
    public void geoTag(View view)
    {
        geofence_fragment gf = new geofence_fragment();
        gf.setListener(this);
        gf.show(getSupportFragmentManager(), "Geofence");
    }

    //---------------------------------------------------------------------------------------

    /**
     * UPDATE UI and list item
     */
    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.todo_item,
                    R.id.task_title,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    /**
     * Listeners for geofence
     */
    private GeofenceController.GeofenceControllerListener geofenceControllerListener = new GeofenceController.GeofenceControllerListener() {
        @Override
        public void onGeofencesUpdated() {
            refresh();
        }

        @Override
        public void onError() {
            showErrorToast();
        }
    };
    private void refresh() {
        Button button = (Button) findViewById(R.id.task_geotag);
        button.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);

    }

    private void showErrorToast() {
        Toast.makeText(this, this.getString(R.string.Toast_Error), Toast.LENGTH_SHORT).show();
    }

    /**
     * On adding the geofence
     * @param dialog
     * @param geofence
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, NamedGeofence geofence) {
        GeofenceController.getInstance().addGeofence(geofence, geofenceControllerListener);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //do nothing
    }
}
