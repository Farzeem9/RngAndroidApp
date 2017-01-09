package com.androidbelieve.drawerwithswipetabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    private ListView lv;
    ArrayAdapter<String> adapter;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> productList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Listview Data
        String products[] = {
                "AIROLI",
                "AMAN LODGE",
                "AMBERNATH",
                "AMBIVLI",
                "ANDHERI",
                "APTA",
                "ASANGAON",
                "ATGAON",
                "BADLAPUR",
                "BANDRA",
                "BELAPUR CBD",
                "BHANDUP",
                "BHAYANDER",
                "BHIVPURI ROAD",
                "BHIWANDI ROAD",
                "BOISAR",
                "BORIVALI",
                "BYCULLA",
                "CST",
                "CHARNI ROAD",
                "CHEMBUR",
                "CHINCHPOKLI",
                "CHUNABHATTI",
                "CHURCHGATE",
                "COTTON GREEN",
                "CURREY ROAD",
                "DADAR",
                "DAHANU ROAD",
                "DAHISAR",
                "DATIVALI",
                "DIVA Jn",
                "DOCKYARD ROAD",
                "DOLAVLI",
                "DOMBIVLI",
                "ELPHINSTONE ROAD",
                "GTB NAGAR",
                "GHANSOLI",
                "GHATKOPAR",
                "GOREGAON",
                "GOVANDI",
                "GRANT ROAD",
                "HAMARAPUR",
                "JITE",
                "JOGESHWARI",
                "JUCHANDRA ROAD",
                "JUINAGAR",
                "KALAMBOLI",
                "KALVA",
                "KALYAN",
                "KAMAN ROAD",
                "KANDIVALI",
                "KANJUR MARG",
                "KARJAT",
                "KASARA",
                "KASU",
                "KELAVLI",
                "KELVA ROAD",
                "KHADAVLI",
                "KHANDESHWAR",
                "KHAR ROAD",
                "KHARBAO",
                "KHARDI",
                "KHARGHAR",
                "KHOPOLI",
                "KINGS CIRCLE",
                "KOPAR",
                "KOPARKHAIRNE",
                "KURLA",
                "LOWER PAREL",
                "LOWJEE",
                "MAHALAKSHMI",
                "MAHIM JN",
                "MALAD",
                "MANASAROVAR",
                "MANKHURD",
                "MARINE LINES",
                "MASJID",
                "MATHERAN",
                "MATUNGA",
                "MATUNGA ROAD",
                "MIRA ROAD",
                "MULUND",
                "MUMBAI CENTRAL",
                "MUMBRA",
                "NAGOTHANE",
                "NAHUR",
                "NAIGAON",
                "NALLA SOPARA",
                "NAVADE ROAD",
                "NERAL",
                "NERUL",
                "NIDI",
                "NILJE",
                "PALASDHARI",
                "PALGHAR",
                "PANVEL",
                "PAREL",
                "PEN",
                "RABALE",
                "RASAYANI",
                "REAY ROAD",
                "ROHA",
                "SANDHURST ROAD",
                "SANPADA",
                "SANTA CRUZ",
                "SAPHALE",
                "SEAWOOD DARAVE",
                "SEWRI",
                "SHAHAD",
                "SHELU",
                "SION",
                "SOMTANE",
                "TALOJA PANCHANAND",
                "THAKURLI",
                "THANE",
                "TILAKNAGAR",
                "TITWALA",
                "TURBHE",
                "ULHAS NAGAR",
                "UMROLI ROAD",
                "VADALA ROAD",
                "VAITARANA",
                "VANGANI",
                "VANGAON",
                "VASAI ROAD",
                "VASHI",
                "VASIND",
                "VIDYAVIHAR",
                "VIKHROLI",
                "VILE PARLE",
                "VIRAR",
                "VITHALWADI"
        };

        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, products);
        lv.setAdapter(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                SearchActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // selected item
        String selected = ((TextView) view.findViewById(R.id.product_name)).getText().toString();
            Intent intent=new Intent();
           // intent.putStringArrayListExtra("im")
            intent.putExtra("data",selected);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
	});
    }
}