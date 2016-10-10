package fi.crowmoore.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Crowmoore on 10/10/2016.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "product_database";
    public static final String DATABASE_TABLE = "products";
    private final String NAME = "name";
    private final String COUNT = "count";
    private final String PRICE = "price";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + COUNT + " INTEGER, " + PRICE + " REAL);");

        ContentValues values = new ContentValues();
        values.put(NAME, "Bread");
        values.put(COUNT, 1);
        values.put(PRICE, 2.5);
        db.insert(DATABASE_TABLE, null, values);

        values.put(NAME, "Milk");
        values.put(COUNT, 2);
        values.put(PRICE, 1.5);
        db.insert(DATABASE_TABLE, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
}
