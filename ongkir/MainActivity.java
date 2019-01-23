import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DataHelper myDb;
    EditText etName, etAlamat, etWeight;
    Button btnProcess, btnAll;
    Spinner dariProvinsi, dariKota, keProvinsi, keKota;
    String dProvinsi, dKota, kProvinsi, kKota,text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DataHelper(this);
        etName = (EditText)findViewById(R.id.etName);

        dariProvinsi = (Spinner) findViewById(R.id.dariProvinsi);
//        dProvinsi = dariProvinsi.getSelectedItem().toString();

        dariKota = (Spinner) findViewById(R.id.dariKota);
//        dKota = dariKota.getSelectedItem().toString();

        etAlamat = (EditText) findViewById(R.id.etAlamat);

        keProvinsi = (Spinner) findViewById(R.id.keProvinsi);
//        kProvinsi = keProvinsi.getSelectedItem().toString();

        keKota = (Spinner) findViewById(R.id.keKota);
//        kKota = keKota.getSelectedItem().toString();

        etWeight = (EditText) findViewById(R.id.etWeight);
        btnProcess = (Button)findViewById(R.id.btnProcess);
        btnAll = (Button)findViewById(R.id.btnAll);
        addData();
        viewAll();
//        updateData();
//        deleteData();

        Spinner keKota = findViewById(R.id.keKota);
        ArrayAdapter<CharSequence> keKotaAdapter = ArrayAdapter.createFromResource(this, R.array.keKota, android.R.layout.simple_spinner_item);
        keKotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        keKota.setAdapter(keKotaAdapter);
        keKota.setOnItemSelectedListener(this);

        Spinner keProvinsi = findViewById(R.id.keProvinsi);
        ArrayAdapter<CharSequence> keProvinsiAdapter = ArrayAdapter.createFromResource(this, R.array.keProvinsi, android.R.layout.simple_spinner_item);
        keProvinsiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        keProvinsi.setAdapter(keProvinsiAdapter);
        keProvinsi.setOnItemSelectedListener(this);

        Spinner dariKota = findViewById(R.id.dariKota);
        ArrayAdapter<CharSequence> dariKotaAdapter = ArrayAdapter.createFromResource(this, R.array.dariKota, android.R.layout.simple_spinner_item);
        dariKotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dariKota.setAdapter(dariKotaAdapter);
        dariKota.setOnItemSelectedListener(this);

        Spinner dariProvinsi = findViewById(R.id.dariProvinsi);
        ArrayAdapter<CharSequence> dariProvinsiAdapter = ArrayAdapter.createFromResource(this, R.array.dariProvinsi, android.R.layout.simple_spinner_item);
        dariProvinsiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dariProvinsi.setAdapter(dariProvinsiAdapter);
        dariProvinsi.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String text = parent.getItemAtPosition(i).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addData() {
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(etName.getText().toString(), dariProvinsi.getSelectedItem().toString(), dariKota.getSelectedItem().toString(),
                        etAlamat.getText().toString(), keProvinsi.getSelectedItem().toString(), keKota.getSelectedItem().toString(), etWeight.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    etName.setText("");
                    etAlamat.setText("");
                    etWeight.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewAll() {
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0){
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Id: "+res.getString(0)+"\n");
                    buffer.append("Name: "+res.getString(1)+"\n");
                    buffer.append("Dari Provinsi: "+res.getString(2)+"\n");
                    buffer.append("Dari Kota: "+res.getString(3)+"\n");
                    buffer.append("Alamat: "+res.getString(4)+"\n");
                    buffer.append("Ke Provinsi: "+res.getString(5)+"\n");
                    buffer.append("Ke Kota: "+res.getString(6)+"\n");
                    buffer.append("Berat : "+res.getString(7)+"\n\n");
                }
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
