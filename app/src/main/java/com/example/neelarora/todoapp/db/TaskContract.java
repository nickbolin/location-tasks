package com.example.neelarora.todoapp.db;

import android.provider.BaseColumns;

/**
 * holds the DB code for task creation, TASKMODEL @CLASSDIAGRAM
 * Created by neelarora on 11/16/16.
 */

public class TaskContract {
    public static final String DB_NAME = "com.example.neelarora.todoapp.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_GEOTAG = "geotag";
        public static final String COL_TASK_LAT = "lat";
        public static final String COL_TASL_LONG = "long";
    }
}
