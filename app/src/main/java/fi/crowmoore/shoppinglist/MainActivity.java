package fi.crowmoore.shoppinglist;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import static fi.crowmoore.shoppinglist.DatabaseOpenHelper.DATABASE_TABLE;

public class MainActivity extends AppCompatActivity implements ProductDialogFragment.DialogListener {

    private ListView productList;
    private SQLiteDatabase db;
    private final int COUNT = 2;
    private final int PRICE = 3;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = (ListView) findViewById(R.id.product_list);
        registerForContextMenu(productList);

        db = (new DatabaseOpenHelper(this)).getWritableDatabase();
        getProducts(db);
    }

    public void onAddButtonClick(View view) {
        ProductDialogFragment dialog = new ProductDialogFragment();
        dialog.show(getFragmentManager(), null);
    }

    public void getProducts(SQLiteDatabase db) {
        String[] results = new String[]{"_id", "name", "count", "price"};
        cursor = db.query(DATABASE_TABLE, results, null, null, null, null, "name DESC", null);

        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_item, cursor,
                new String[]{"name", "count", "price"},
                new int[]{R.id.name, R.id.count, R.id.price}, 0);

        productList.setAdapter(adapter);

        float totalCost = 0f;
        if(cursor.moveToFirst()) {
            do {
                int count = cursor.getInt(COUNT);
                float price = cursor.getFloat(PRICE);
                totalCost += price * count;
            } while(cursor.moveToNext());
        }
        Toast.makeText(getApplicationContext(), "Total price: " + totalCost, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name, int count, float price) {
        ContentValues values=new ContentValues(3);
        values.put("name", name);
        values.put("count", count);
        values.put("price", price);
        db.insert(DATABASE_TABLE, null, values);

        getProducts(db);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo info) {
        menu.add(Menu.NONE, 0, Menu.NONE, "Remove product");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String[] id = {String.valueOf(info.id)};
        db.delete(DATABASE_TABLE, "_id=?", id);
        getProducts(db);
        return true;
    }
}
