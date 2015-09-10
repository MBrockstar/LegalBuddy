package com.kwt.legalbuddy.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kwt.legalbuddy.R;
import com.kwt.legalbuddy.model.NDAuser;
import com.kwt.legalbuddy.webservice.JSONParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class LBOne extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    ActionMenuItemView search;
    public String access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lbone);
//        super.onCreate(savedInstanceState);
        //setContentView(R.layout.abc_search_view);

        // Intent intent=getIntent();
        // if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
        //     String query = intent.getStringExtra(SearchManager.QUERY);
        //     doMySearch(query);
        // }
        StrictMode.enableDefaults();
        handleIntent(getIntent());








        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //Cursor c = db.getWordMatches(query, null);
            //c.moveToFirst();
            //res=c.getString(1);
            /*Toast t=Toast.makeText(this, query, Toast.LENGTH_LONG);
            t.show();*/
        }

        else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // Handle a suggestions click (because the suggestions all use ACTION_VIEW)
            String query = intent.getStringExtra(SearchManager.QUERY);
            /*Toast t=Toast.makeText(this, "aaaaa", Toast.LENGTH_LONG);
            t.show();*/
        }
    }

    private void doMySearch(String query) {
        /*Toast t=Toast.makeText(this, query, Toast.LENGTH_LONG);
        t.show();*/
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        PlaceholderFragment pf=new PlaceholderFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, pf.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.app_name);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        actionBar.setIcon(R.mipmap.ic_launcher);

        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.lbone, menu);
            SearchManager searchManager =
                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            search =
                    (ActionMenuItemView) menu.findItem(R.id.search1).getActionView();
            // search.setSearchableInfo(
            // searchManager.getSearchableInfo(getComponentName()));
            restoreActionBar();
            return true;
        }



        return super.onCreateOptionsMenu(menu);
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
        if (id == R.id.search1) {
            onSearchRequested();
            search= (ActionMenuItemView) findViewById(R.id.search1);
            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    /*Toast t = Toast.makeText(getApplicationContext(),s , Toast.LENGTH_LONG);
                    t.show();*/
                }

                @Override
                public void afterTextChanged(Editable s) {
                    /*Toast t = Toast.makeText(getApplicationContext(),"YYhh" , Toast.LENGTH_LONG);
                    t.show();*/
                }
            });
            /*search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // TODO Auto-generated method stub
                    Toast t = Toast.makeText(getApplicationContext(), "yayy", Toast.LENGTH_LONG);
                    t.show();

                    // Toast.makeText(this, String.valueOf(hasFocus),Toast.LENGTH_SHORT).show();
                }
            });*/
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    public class PlaceholderFragment extends Fragment implements AdapterView.OnItemSelectedListener{
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        ListView listView,listView2,lv3;
        String cats[],ques[],arrTemp[],arrText[];
        String is_software_shared="1",is_information_shared="1",is_copies_permitted="1",is_timeline="1",is_future_biz="1",termination="discloser only";
        String email="ashutosh.barthwal007@gmail.com";
        List<Questions> rowItems;
        //List<Names> dnames;
        Button button,b2,b3,b4,b5,b6,b7,b8,submit;
        Spinner s1,s2,s3,s4,s5,Dp,Rp, information_shared_sp, timeline_sp;
        Spinner softawre_shared_sp, making_copies_sp, future_biz_sp, termination_type_sp;
        EditText tv1,tv2, date_of_closing,date_of_signing;
        TextView t3,t4, information_shared, timline_tv, date_of_closing_tv;
         Dialog d,d2,d3,d4;
        EditText reciever_signing_name,reciever_occupation,discloser_signing_name,discloser_occupation;
        TextView reciever_s_name,reciever_ocupation,discloser_s_name,discloser_ocupation;
        NumberPicker np;
        ViewGroup parent;
        NDAuser ndauser;
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        int a1,a2,a3;
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int ab;
            ab=getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = inflater.inflate(R.layout.fragment_lbone, container, false);
            if(ab==2)
                rootView = inflater.inflate(R.layout.fragment_2, container, false);
            else if(ab==3)
                rootView = inflater.inflate(R.layout.fragment_3, container, false);
            else if(ab==4)
                rootView = inflater.inflate(R.layout.atype, container, false);
            else if(ab==5)
                rootView = inflater.inflate(R.layout.fragment_menu, container, false);
            else if(ab==6)
                rootView = inflater.inflate(R.layout.fmenu, container, false);
            else if(ab==7){
                rootView = inflater.inflate(R.layout.nda, container, false);}
            else if(ab==8)
                rootView = inflater.inflate(R.layout.thankyou, container, false);
            return rootView;
        }

        public void getdata()
        {
            String result="";
            InputStream isr=null;
            try
            {
              /*  HttpClient hc = new DefaultHttpClient();
                HttpGet hp = new HttpGet("http://zciencecorporation.com/LB/api/v1/agreements");
                HttpResponse hr = hc.execute(hp);
                HttpEntity entity = hr.getEntity();*/
                 isr = getResources().openRawResource(R.raw.abc);
                //isr = entity.getContent();
            }
            catch(Exception e)
            {
                Log.e("log_tag", "Error" + e.toString());
                //resultView.setText("Cc db");
            }
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                isr.close();
                result = sb.toString();
            }
            catch (Exception e)
            {
                Log.e("log_tag", "Error 2" + e.toString());
            }

            try{
                String s = "";
                int a,b,c;
                JSONObject jsono = new JSONObject(result);
                JSONArray jarray = jsono.getJSONArray("agreements");
                cats=new String[jarray.length()];

                for(int i=0;i<jarray.length();i++)
                {
                    JSONObject json = jarray.getJSONObject(i);
                    cats[i]=json.getString("title");


                    //Toast.makeText(getApplicationContext(),"fetch "+b+""+c+":"+tfdata[b][c], Toast.LENGTH_SHORT).show();
                }

                //resultView.setText(s);
            }
            catch (Exception e)
            {
                Log.e("log_tag", "Error 2" + e.toString());
            }

        }

        public void getdata1()
        {
            String result="";
            InputStream isr=null;
            try
            {
                HttpClient hc = new DefaultHttpClient();
                HttpGet hp = new HttpGet("http://zciencecorporation.com/LB/api/v1/questions");
                HttpResponse hr = hc.execute(hp);
                HttpEntity entity = hr.getEntity();
                isr = entity.getContent();
            }
            catch(Exception e)
            {
                Log.e("log_tag", "Error" + e.toString());
                //resultView.setText("Cc db");
            }
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                isr.close();
                result = sb.toString();
            }
            catch (Exception e)
            {
                Log.e("log_tag", "Error 2" + e.toString());
            }

            try{
                String s = "";
                int a,b,c;
                JSONObject jsono = new JSONObject(result);
                JSONArray jarray = jsono.getJSONArray("questions");
                ques=new String[jarray.length()];
                rowItems = new ArrayList<Questions>();
               // for (int i = 0; i < titles.length; i++) {
                   // RowItem item = new RowItem(images[i], titles[i], descriptions[i]);
                   // rowItems.add(item);
                //}

                for(int i=0;i<jarray.length();i++)
                {
                    JSONObject json = jarray.getJSONObject(i);
                    Questions item=new Questions(json.getString("question"));

                    rowItems.add(item);


                    //Toast.makeText(getApplicationContext(),"fetch "+b+""+c+":"+tfdata[b][c], Toast.LENGTH_SHORT).show();
                }

                //resultView.setText(s);
            }
            catch (Exception e)
            {
                Log.e("log_tag", "Error 2" + e.toString());


            }

        }

        @Override
        public void onStart() {
            super.onStart();
            int ab;
            //setContentView(R.layout.abc_search_view);
            StrictMode.enableDefaults();
            ab=getArguments().getInt(ARG_SECTION_NUMBER);
            if(ab==1) {
                    getdata();
                if(cats!=null) {
                    listView = (ListView) getView().findViewById(R.id.listView1);
                    //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                    // R.array.Loc_array, android.R.layout.simple_list_item_1);
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(),
                            android.R.layout.simple_list_item_1, cats);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            a1=position;
                          /* Toast t = Toast.makeText(getActivity(), "" +a1, Toast.LENGTH_LONG);
                            t.show();*/
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            PlaceholderFragment pf = new PlaceholderFragment();
                            /*if(position==0){
                                fragmentManager.beginTransaction()
                                        .replace(R.id.container, pf.newInstance(4)).addToBackStack(null)
                                        .commit();
                            }
                            else */if(position==0){
                                fragmentManager.beginTransaction()
                                        .replace(R.id.container, pf.newInstance(7)).addToBackStack(null)
                                        .commit();
                            }


                            // t.addToBackStack(null);
                            //t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            //t.commit();

                        }
                    });
                }
            }

            if(ab==4) {
               String sp1[]={"Non-Binding","Binding"};
               String sp2[]={"Standard","Custom"};

               s1= (Spinner) getView().findViewById(R.id.spinner);
               s2= (Spinner) getView().findViewById(R.id.spinner2);
               ArrayAdapter<CharSequence> a1 = new ArrayAdapter<CharSequence>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, sp1);
               ArrayAdapter<CharSequence> a2 = new ArrayAdapter<CharSequence>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, sp2);
                s1.setAdapter(a1);
                s2.setAdapter(a2);
                s1.setOnItemSelectedListener(this);
                s2.setOnItemSelectedListener(this);
                b2= (Button) getView().findViewById(R.id.button2);
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        PlaceholderFragment pf = new PlaceholderFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, pf.newInstance(6)).addToBackStack(null)
                                .commit();
                    }
                });
            }

            if(ab==5) {
                getdata1();
                button= (Button) getView().findViewById(R.id.button);

                listView2 = (ListView) getView().findViewById(R.id.listView2);
                //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                // R.array.Loc_array, android.R.layout.simple_list_item_1);
                //ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(),
                        //android.R.layout.simple_list_item_1, ques);
                if(rowItems!=null) {
                    CustomListViewAdapter adapter = new CustomListViewAdapter(getActivity(),
                            R.layout.list_v, rowItems);

                    listView2.setAdapter(adapter);

                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast t=Toast.makeText(getActivity(),CustomListViewAdapter.s,Toast.LENGTH_LONG);
                       // t.show();
                    }
                });
            }
            if(ab==6){
                b3= (Button) getView().findViewById(R.id.button3);
                b6= (Button) getView().findViewById(R.id.button6);
                submit= (Button) getView().findViewById(R.id.button9);
                t3= (TextView) getView().findViewById(R.id.textView8);
                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show();

                    }
                });
                b6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        show2();

                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        PlaceholderFragment pf = new PlaceholderFragment();
                        fragmentManager.beginTransaction()
                                    .replace(R.id.container, pf.newInstance(8)).addToBackStack(null)
                                    .commit();
                    }
                });
                String sp3[]={"Company","Individual"};
                String sp4[]={"CCPS","DC","DD SECURE","DD UNSECURE","EQUITY"};
                t4= (TextView) getView().findViewById(R.id.textView17);
                t4.setVisibility(View.INVISIBLE);
                s3= (Spinner) getView().findViewById(R.id.spinner3);
                ArrayAdapter<CharSequence> a3 = new ArrayAdapter<CharSequence>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, sp3);
                s3.setAdapter(a3);
                s3.setOnItemSelectedListener(this);

                s4= (Spinner) getView().findViewById(R.id.spinner4);
                ArrayAdapter<CharSequence> a4 = new ArrayAdapter<CharSequence>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, sp4);
                s4.setAdapter(a4);
                s5= (Spinner) getView().findViewById(R.id.spinner5);
                s5.setVisibility(View.INVISIBLE);
            }

            if(ab==7){

                /*GetAccessTokenAsync aAsync = new GetAccessTokenAsync(LBOne.this,
                        getAccessTokenHandler,"Legal Buddy");
                aAsync.execute("Get Access Token");*/
                JSONObject json = null;
                JSONParser jsonParser = new JSONParser();
                final List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

                try {

                    json = jsonParser.makeHttpRequest("http://zciencecorporation.com/lbproject/api/nda/Ashutosh/get_token", "GET",postParameters);
                    Toast.makeText(LBOne.this,json.getString("access_token"),Toast.LENGTH_LONG).show();
                    access_token = json.getString("access_token");
                } catch (Exception e) {
                    Toast.makeText(LBOne.this,"Nothing"+e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }

                ndauser=new NDAuser();
                String sp3[]={"Company","Individual","Discloser"};
                String yn[]={"Yes","No"};
                String termntn[]={"Discloser only","Mutual"};
                final EditText discloser_name,receiver_name;
                discloser_name= (EditText) findViewById(R.id.editText9);
                receiver_name= (EditText) findViewById(R.id.editText10);
                submit= (Button) getView().findViewById(R.id.button9);
                Rp= (Spinner) getView().findViewById(R.id.spinner7);
                softawre_shared_sp= (Spinner) getView().findViewById(R.id.software_shared);
                making_copies_sp = (Spinner) getView().findViewById(R.id.making_copies);
                future_biz_sp = (Spinner) getView().findViewById(R.id.future_biz);
                termination_type_sp = (Spinner) getView().findViewById(R.id.termination_type);
                Dp= (Spinner) getView().findViewById(R.id.spinner6);
                information_shared_sp = (Spinner) getView().findViewById(R.id.information_shared);
                timeline_sp = (Spinner) getView().findViewById(R.id.timeline);

                information_shared = (TextView) getView().findViewById(R.id.txt_information_shared);
                timline_tv = (TextView) getView().findViewById(R.id.txt_timeline);
                date_of_closing_tv = (TextView) getView().findViewById(R.id.txt_closing_date);
                date_of_closing = (EditText) getView().findViewById(R.id.closing_date);
                date_of_signing= (EditText) getView().findViewById(R.id.value_date_of_signing);

                discloser_ocupation= (TextView) getView().findViewById(R.id.txt_discloser_occupation);
                discloser_s_name= (TextView) getView().findViewById(R.id.txt_discloser_signing_name);
                discloser_occupation= (EditText) getView().findViewById(R.id.value_discloser_occupation);
                discloser_signing_name= (EditText) getView().findViewById(R.id.value_discloser_signing_name);

                reciever_s_name= (TextView) getView().findViewById(R.id.txt_reciever_signing_name);
                reciever_ocupation= (TextView) getView().findViewById(R.id.txt_reciever_occupation);
                reciever_signing_name= (EditText) getView().findViewById(R.id.value_reciever_signing_name);
                reciever_occupation= (EditText) getView().findViewById(R.id.value_receiver_occupation);

                ArrayAdapter<CharSequence> a3 = new ArrayAdapter<CharSequence>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, sp3);
                ArrayAdapter<CharSequence> ayn = new ArrayAdapter<CharSequence>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, yn);
                ArrayAdapter<CharSequence> term = new ArrayAdapter<CharSequence>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, termntn);
                Dp.setAdapter(a3);
                Rp.setAdapter(a3);
                timeline_sp.setAdapter(ayn);
                softawre_shared_sp.setAdapter(ayn);
                making_copies_sp.setAdapter(ayn);
                information_shared_sp.setAdapter(ayn);
                future_biz_sp.setAdapter(ayn);
                termination_type_sp.setAdapter(term);

                Dp.setOnItemSelectedListener(this);
                Rp.setOnItemSelectedListener(this);
                softawre_shared_sp.setOnItemSelectedListener(this);
                information_shared_sp.setOnItemSelectedListener(this);
                making_copies_sp.setOnItemSelectedListener(this);
                timeline_sp.setOnItemSelectedListener(this);
                future_biz_sp.setOnItemSelectedListener(this);
                termination_type_sp.setOnItemSelectedListener(this);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        email_popup();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        PlaceholderFragment pf = new PlaceholderFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, pf.newInstance(8)).addToBackStack(null)
                                .commit();

                        JSONObject json = null;
                        JSONParser jsonParser = new JSONParser();
                        email=
                        ndauser.first_party_name=discloser_name.getText().toString();
                        ndauser.effective_date=((EditText) findViewById(R.id.editText11)).getText().toString();
                        ndauser.governing_law=((EditText) findViewById(R.id.editText12)).getText().toString();
                        ndauser.second_party_name=receiver_name.getText().toString();
                        if(ndauser.first_party_type.equalsIgnoreCase("Company")||ndauser.first_party_type.equalsIgnoreCase("Disclosure")) {
                            ndauser.signing_first = discloser_signing_name.getText().toString();
                            ndauser.first_party_occupation = discloser_occupation.getText().toString();
                        }
                        if(ndauser.second_party_type.equalsIgnoreCase("Company")||ndauser.second_party_type.equalsIgnoreCase("Disclosure")) {
                            ndauser.signing_second = reciever_signing_name.getText().toString();
                            ndauser.second_party_occupation = reciever_occupation.getText().toString();
                        }
                        if(is_timeline.equalsIgnoreCase("1")) {
                            ndauser.closing_date= date_of_closing.getText().toString();
                        }
                        ndauser.signing_date=date_of_signing.getText().toString();

                        ndauser.software_shared=is_software_shared;
                        ndauser.info_shared_among_company=is_information_shared;
                        ndauser.make_copies=is_copies_permitted;
                        ndauser.timeline=is_timeline;
                        ndauser.future_buiness=is_future_biz;
                        ndauser.agreement_terminated=termination;
                        email=((EditText) findViewById(R.id.email_to)).getText().toString();

                        try {
                            postParameters.add(new BasicNameValuePair("_token", "ashutoshLBaPP"));
                            postParameters.add(new BasicNameValuePair("first_party_type", ndauser.first_party_type));
                            postParameters.add(new BasicNameValuePair("first_party_name", ndauser.first_party_name));
                            postParameters.add(new BasicNameValuePair("signing_first", ndauser.signing_first));//string nullable
                            postParameters.add(new BasicNameValuePair("first_party_occupation", ndauser.first_party_occupation));//string
                            postParameters.add(new BasicNameValuePair("second_party_type", ndauser.second_party_type));
                            postParameters.add(new BasicNameValuePair("second_party_name", ndauser.second_party_name));
                            postParameters.add(new BasicNameValuePair("signing_second", ndauser.signing_second));//string nullable
                            postParameters.add(new BasicNameValuePair("second_party_occupation", ndauser.second_party_occupation));//string
                            postParameters.add(new BasicNameValuePair("effective_date", ndauser.effective_date));//date mm/dd/yyyy
                            postParameters.add(new BasicNameValuePair("governing_law", ndauser.governing_law));// string
                            postParameters.add(new BasicNameValuePair("software_shared", ndauser.software_shared));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("info_shared_among_company", ndauser.info_shared_among_company));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("make_copies", ndauser.make_copies));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("timeline", ndauser.timeline));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("closing_date", ndauser.closing_date));//date mm/dd/yyyy nullable
                            postParameters.add(new BasicNameValuePair("future_buiness", ndauser.future_buiness));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("agreement_terminated", ndauser.agreement_terminated));//string mutually ot discloser only
                            postParameters.add(new BasicNameValuePair("signing_date", ndauser.signing_date));//date mm/dd/yyyy nullable

                            postParameters.add(new BasicNameValuePair("_email", email));
                            /*postParameters.add(new BasicNameValuePair("_token", "ashutoshLBaPP"));
                            postParameters.add(new BasicNameValuePair("first_party_type", "Individual"));
                            postParameters.add(new BasicNameValuePair("first_party_name", "Mr. Ashutosh Barthwal"));
                            postParameters.add(new BasicNameValuePair("signing_first", "Same as First Party Name"));//string nullable
                            postParameters.add(new BasicNameValuePair("first_party_occupation", "No Occupation"));//string
                            postParameters.add(new BasicNameValuePair("second_party_type", "Individual"));
                            postParameters.add(new BasicNameValuePair("second_party_name", "Mr. John Doe"));
                            postParameters.add(new BasicNameValuePair("signing_second", "Same as Second Party Name"));//string nullable
                            postParameters.add(new BasicNameValuePair("second_party_occupation", "No Occupation"));//string
                            postParameters.add(new BasicNameValuePair("effective_date", "08/22/2015"));//date mm/dd/yyyy
                            postParameters.add(new BasicNameValuePair("governing_law", "India"));// string
                            postParameters.add(new BasicNameValuePair("software_shared", "1"));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("info_shared_among_company", "0"));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("make_copies","!"));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("timeline", "1"));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("closing_date", "03/10/2015"));//date mm/dd/yyyy nullable
                            postParameters.add(new BasicNameValuePair("future_buiness", "1"));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("agreement_terminated", "mutually"));//string mutually ot discloser only
                            postParameters.add(new BasicNameValuePair("signing_date", "09/15/2015"));//date mm/dd/yyyy nullable
                            postParameters.add(new BasicNameValuePair("_email", "ashutosh.barthwal007@gmail.com"));*/


                            json = jsonParser.makeHttpRequest("http://zciencecorporation.com/lbproject/api/nda/submit/tatti", "GET",postParameters);

                        } catch (Exception e) {
                            Toast.makeText(LBOne.this,"Nothing ** "+postParameters.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }
                        Toast.makeText(getApplicationContext(),ndauser.first_party_type,Toast.LENGTH_LONG).show();
                       // SubmitRequestAsync mAsync = new SubmitRequestAsync(this,ndaSubmitHandler,"legal buddy api request");

                    }
                });
            }



        }

        @SuppressLint("HandlerLeak")
        Handler getAccessTokenHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                if (msg.obj != null) {


                    Toast.makeText(LBOne.this, ((JSONObject) msg.obj).toString(), Toast.LENGTH_LONG).show();


                }
                else
                {
                    Toast.makeText(LBOne.this,"Nothing",Toast.LENGTH_LONG).show();
                }
            };
        };

        public void show()
        {

            final Dialog d = new Dialog(LBOne.this);
            d.setTitle("Co-Founders");
            d.setContentView(R.layout.dialog);
            b4 = (Button) d.findViewById(R.id.button4);
            b5 = (Button) d.findViewById(R.id.button5);
            final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);
            //np = (NumberPicker) findViewById(R.id.numberPicker);
            String[] nums = new String[49];
            for(int i=0; i<nums.length; i++)
                nums[i] = Integer.toString(i+2);

            np.setMinValue(2);
            np.setMaxValue(50);
            np.setWrapSelectorWheel(false);
            np.setDisplayedValues(nums);
            np.setValue(1);
            //np.setOnValueChangedListener(this);
            b4.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    b3.setText(String.valueOf(np.getValue())); //set the value to textview
                    String sTextFromET = b3.getText().toString();
                    int nd = new Integer(sTextFromET).intValue();
                    arrTemp=new String[nd];
                    d.dismiss();
                }
            });
            b5.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    d.dismiss(); // dismiss the dialog
                }
            });
            d.show();


        }
        public void show2()
        {   final Dialog d2 = new Dialog(LBOne.this);
            d2.setTitle("Name of Co-Founders");
            d2.setContentView(R.layout.dialog2);
            String sTextFromET = b3.getText().toString();
            int nd = new Integer(sTextFromET).intValue();
            arrText=new String[nd];
            if(arrTemp==null){arrTemp=new String[nd];}
            for(int i=0;i<nd;i++){
                arrText[i]=new String("Director"+(i+1));
                if(arrTemp[i]==null){arrTemp[i]=new String("");}
            }
            lv3 = (ListView) d2.findViewById(R.id.listView3);
            MyListAdapter myListAdapter = new MyListAdapter();
            lv3.setAdapter(myListAdapter);
            b7 = (Button) d2.findViewById(R.id.button7);
            b8 = (Button) d2.findViewById(R.id.button8);

            b7.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    t4.setVisibility(View.VISIBLE);
                    s5.setVisibility(View.VISIBLE);
                    ArrayAdapter<CharSequence> a5 = new ArrayAdapter<CharSequence>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item, arrTemp);
                    s5.setAdapter(a5);
                    d2.dismiss();
                }
            });
            b8.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    d2.dismiss(); // dismiss the dialog
                }
            });
            d2.show();


        }

        public void email_popup()
        {

            final Dialog email_dialog = new Dialog(LBOne.this);
            email_dialog.setTitle("Email To");
            email_dialog.setContentView(R.layout.email_dialog);
            Button email_set = (Button) email_dialog.findViewById(R.id.email_set);
            Button email_cancel = (Button) email_dialog.findViewById(R.id.email_cancel);
            final EditText email_to= (EditText) email_dialog.findViewById(R.id.email_to);

            email_set.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    email=email_to.getText().toString();
                    email_dialog.dismiss();
                }
            });
            email_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    email_dialog.dismiss(); // dismiss the dialog
                }
            });
//            email_dialog.show();


        }

        private class MyListAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                if(arrText != null && arrText.length != 0){
                    return arrText.length;
                }
                return 0;
            }

            @Override
            public Object getItem(int position) {
                // TODO Auto-generated method stub
                return arrText[position];
            }

            @Override
            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                //ViewHolder holder = null;
                final ViewHolder holder;
                if (convertView == null) {

                    holder = new ViewHolder();
                    LayoutInflater inflater = LBOne.this.getLayoutInflater();
                    convertView = inflater.inflate(R.layout.list_v, null);
                    holder.textView1 = (TextView) convertView.findViewById(R.id.textView3);
                    holder.editText1 = (EditText) convertView.findViewById(R.id.editText);

                    convertView.setTag(holder);

                } else {

                    holder = (ViewHolder) convertView.getTag();
                }

                holder.ref = position;

                holder.textView1.setText(arrText[position]);
                if(arrTemp[position]!=null){ holder.editText1.setText(arrTemp[position]);}
                holder.editText1.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub


                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                  int arg3) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                        arrTemp[holder.ref] =arg0.toString();
                       // Toast t=Toast.makeText(getActivity(),arrTemp[holder.ref],Toast.LENGTH_LONG);
                        //t.show();
                    }
                });

                return convertView;
            }

            private class ViewHolder {
                TextView textView1;
                EditText editText1;
                int ref;
            }


        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((LBOne) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(adapterView==s1){
                a2=i;
            /*Toast t=Toast.makeText(getActivity(),""+a2,Toast.LENGTH_LONG);
            t.show();*/
            }
            else if(adapterView==s2){
                a3=i;
                /*Toast t=Toast.makeText(getActivity(),""+a3,Toast.LENGTH_LONG);
                t.show();*/

            }
            else if(adapterView==s3){
                if(i==0){
                    t3.setText("Director's name");
                }
                else if(i==1){
                    t3.setText("Father's name");
                }
            }
            else if(adapterView==Dp){
                if(i==1){
                    ndauser.first_party_type ="Individual";
                    ndauser.signing_first="Same as First Party";
                    ndauser.first_party_occupation="No Occupation";
                    discloser_s_name.setVisibility(View.GONE);
                    discloser_ocupation.setVisibility(View.GONE);
                    discloser_signing_name.setVisibility(View.GONE);
                    discloser_occupation.setVisibility(View.GONE);
                }
                else{
                    if(i==0)
                        ndauser.first_party_type ="Company";
                    if(i==2)
                        ndauser.first_party_type ="Discloser";
                    discloser_s_name.setVisibility(View.VISIBLE);
                    discloser_ocupation.setVisibility(View.VISIBLE);
                    discloser_signing_name.setVisibility(View.VISIBLE);
                    discloser_occupation.setVisibility(View.VISIBLE);
                }
            }
            else if(adapterView==Rp){
                if(i==1){
                    ndauser.second_party_type ="Individual";
                    ndauser.signing_second="Same as First Party";
                    ndauser.second_party_occupation="No Occupation";
                    information_shared.setVisibility(View.GONE);
                    information_shared_sp.setVisibility(View.GONE);
                    reciever_s_name.setVisibility(View.GONE);
                    reciever_ocupation.setVisibility(View.GONE);
                    reciever_signing_name.setVisibility(View.GONE);
                    reciever_occupation.setVisibility(View.GONE);
                }
                else{
                    if(i==0)
                        ndauser.second_party_type ="Company";
                    if(i==2)
                        ndauser.second_party_type ="Discloser";
                    reciever_s_name.setVisibility(View.VISIBLE);
                    reciever_ocupation.setVisibility(View.VISIBLE);
                    reciever_signing_name.setVisibility(View.VISIBLE);
                    reciever_occupation.setVisibility(View.VISIBLE);
                    information_shared.setVisibility(View.VISIBLE);
                    information_shared_sp.setVisibility(View.VISIBLE);
                }

            }
            else if(adapterView== timeline_sp){
                if(i==1){
                    date_of_closing.setVisibility(View.GONE);
                    date_of_closing_tv.setVisibility(View.GONE);
                    is_timeline="0";
                }
                else if(i==0){
                    date_of_closing.setVisibility(View.VISIBLE);
                    date_of_closing_tv.setVisibility(View.VISIBLE);
                    is_timeline="1";
                }

            }
            else  if (adapterView==softawre_shared_sp){
                if(i==0)
                    is_software_shared="1";
                else if(i==1)
                    is_software_shared="0";

            }
            else  if (adapterView==information_shared_sp){
                if(i==0)
                    is_information_shared="1";
                else if(i==1)
                    is_information_shared="0";
            }
            else  if (adapterView==making_copies_sp){
                if(i==0)
                    is_copies_permitted="1";
                else if(i==1)
                    is_copies_permitted="0";
            }
            else  if (adapterView==future_biz_sp){
                if(i==0)
                    is_future_biz="1";
                else if(i==1)
                    is_future_biz="0";
            }
            else  if (adapterView==termination_type_sp){
                if(i==0)
                    termination="discloser only";
                else if(i==1)
                    termination="mutually";
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }


}
