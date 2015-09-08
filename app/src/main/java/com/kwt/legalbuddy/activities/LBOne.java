package com.kwt.legalbuddy.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.kwt.legalbuddy.controller.GetAccessTokenAsync;
import com.kwt.legalbuddy.controller.SubmitRequestAsync;
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
import org.json.JSONException;
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
        List<Questions> rowItems;
        //List<Names> dnames;
        Button button,b2,b3,b4,b5,b6,b7,b8,submit;
        Spinner s1,s2,s3,s4,s5,Dp,Rp,Is,timelinesp;
        EditText tv1,tv2,occet,snet,docet;
        TextView t3,t4,occtv,sntv,Ins,timlinetv,doctv;
         Dialog d,d2,d3,d4;
        NumberPicker np;
        ViewGroup parent;
        NDAuser ndauser;
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
                            if(position==0){
                                fragmentManager.beginTransaction()
                                        .replace(R.id.container, pf.newInstance(4)).addToBackStack(null)
                                        .commit();
                            }
                            else if(position==1){
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
                List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

                try {

                    json = jsonParser.makeHttpRequest("http://zciencecorporation.com/lbproject/api/nda/Ashutosh/get_token", "GET",postParameters);
                    Toast.makeText(LBOne.this,json.getString("access_token"),Toast.LENGTH_LONG).show();
                    access_token = json.getString("access_token");
                } catch (Exception e) {
                    Toast.makeText(LBOne.this,"Nothing"+e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }

                ndauser=new NDAuser();
                String sp3[]={"Company","Individual","Disclosure"};
                String yn[]={"Yes","No"};
                String termntn[]={"Discloser only","Mutual"};
                final EditText discloser_name,receiver_name;
                discloser_name= (EditText) findViewById(R.id.editText9);
                receiver_name= (EditText) findViewById(R.id.editText10);
                submit= (Button) getView().findViewById(R.id.button9);
                Rp= (Spinner) getView().findViewById(R.id.spinner7);
                Spinner info= (Spinner) getView().findViewById(R.id.spinner8);
                Spinner copy= (Spinner) getView().findViewById(R.id.spinner10);
                Spinner fbiz= (Spinner) getView().findViewById(R.id.spinner12);
                Spinner termntnsp= (Spinner) getView().findViewById(R.id.spinner13);
                Dp= (Spinner) getView().findViewById(R.id.spinner6);
                Is= (Spinner) getView().findViewById(R.id.spinner9);
                timelinesp= (Spinner) getView().findViewById(R.id.spinner11);
                occtv= (TextView) getView().findViewById(R.id.textView26);
                sntv= (TextView) getView().findViewById(R.id.textView27);
                Ins= (TextView) getView().findViewById(R.id.textView29);
                timlinetv= (TextView) getView().findViewById(R.id.textView31);
                doctv= (TextView) getView().findViewById(R.id.textView32);
                occet= (EditText) getView().findViewById(R.id.editText13);
                snet= (EditText) getView().findViewById(R.id.editText14);
                docet= (EditText) getView().findViewById(R.id.editText15);
                ArrayAdapter<CharSequence> a3 = new ArrayAdapter<CharSequence>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, sp3);
                ArrayAdapter<CharSequence> ayn = new ArrayAdapter<CharSequence>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, yn);
                ArrayAdapter<CharSequence> term = new ArrayAdapter<CharSequence>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, termntn);
                Dp.setAdapter(a3);
                Rp.setAdapter(a3);
                timelinesp.setAdapter(ayn);
                info.setAdapter(ayn);
                copy.setAdapter(ayn);
                Is.setAdapter(ayn);
                fbiz.setAdapter(ayn);
                termntnsp.setAdapter(term);
                Dp.setOnItemSelectedListener(this);
                Rp.setOnItemSelectedListener(this);
                timelinesp.setOnItemSelectedListener(this);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        PlaceholderFragment pf = new PlaceholderFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, pf.newInstance(8)).addToBackStack(null)
                                .commit();
                        ndauser.discloser_name=discloser_name.getText().toString();
                        ndauser.receiver_name=receiver_name.getText().toString();
                        JSONObject json = null;
                        JSONParser jsonParser = new JSONParser();
                        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

                        try {
                            postParameters.add(new BasicNameValuePair("_token", access_token));
                            postParameters.add(new BasicNameValuePair("first_party_type", ndauser.discloser_type));
                            postParameters.add(new BasicNameValuePair("first_party_name", ndauser.discloser_name));
                            postParameters.add(new BasicNameValuePair("second_party_type", ndauser.receiver_type));
                            postParameters.add(new BasicNameValuePair("second_party_name", ndauser.receiver_name));
                            postParameters.add(new BasicNameValuePair("first_party_occupation", ndauser.discloser_type));//string
                            postParameters.add(new BasicNameValuePair("second_party_occupation", ndauser.discloser_name));//string
                            postParameters.add(new BasicNameValuePair("effective_date", ndauser.receiver_type));//date mm/dd/yyyy
                            postParameters.add(new BasicNameValuePair("governing_law", ndauser.receiver_name));// string
                            postParameters.add(new BasicNameValuePair("software_shared", ndauser.discloser_type));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("info_shared_among_company", ndauser.discloser_name));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("make_copies", ndauser.receiver_type));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("timeline", ndauser.receiver_name));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("closing_date", ndauser.receiver_type));//date mm/dd/yyyy nullable
                            postParameters.add(new BasicNameValuePair("future_buiness", ndauser.receiver_name));// 1 for Yes 0 for No
                            postParameters.add(new BasicNameValuePair("agreement_terminated", ndauser.discloser_type));//string mutually ot discloser only
                            postParameters.add(new BasicNameValuePair("signing_date", ndauser.discloser_name));//date mm/dd/yyyy nullable
                            postParameters.add(new BasicNameValuePair("signing_first", ndauser.receiver_type));//string nullable
                            postParameters.add(new BasicNameValuePair("signing_second", ndauser.receiver_name));//string nullable

                            postParameters.add(new BasicNameValuePair("_email", "ashutosh.barthwal007@gmail.com"));


                            json = jsonParser.makeHttpRequest("http://zciencecorporation.com/lbproject/api/nda/submit", "POST",postParameters);


                        } catch (Exception e) {
                            Toast.makeText(LBOne.this,"Nothing"+e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }
                        Toast.makeText(getApplicationContext(),ndauser.discloser_type,Toast.LENGTH_LONG).show();
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
            Toast t=Toast.makeText(getActivity(),""+a2,Toast.LENGTH_LONG);
            t.show();
            }
            else if(adapterView==s2){
                a3=i;
                Toast t=Toast.makeText(getActivity(),""+a3,Toast.LENGTH_LONG);
                t.show();

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
                    ndauser.discloser_type ="Individual";
                    ndauser.company_signing_name="";
                    ndauser.occupation="";
                    occtv.setVisibility(View.GONE);
                    occet.setVisibility(View.GONE);
                    sntv.setVisibility(View.GONE);
                    snet.setVisibility(View.GONE);
                }
                else{
                    if(i==0)
                        ndauser.discloser_type ="Company";
                    if(i==2)
                        ndauser.discloser_type ="Disclosure";
                    occtv.setVisibility(View.VISIBLE);
                    occet.setVisibility(View.VISIBLE);
                    sntv.setVisibility(View.VISIBLE);
                    snet.setVisibility(View.VISIBLE);
                }
            }
            else if(adapterView==Rp){
                if(i==1){
                    Ins.setVisibility(View.GONE);
                    Is.setVisibility(View.GONE);
                }
                else{
                    Ins.setVisibility(View.VISIBLE);
                    Is.setVisibility(View.VISIBLE);
                }

            }
            else if(adapterView==timelinesp){
                if(i==1){
                    docet.setVisibility(View.GONE);
                    doctv.setVisibility(View.GONE);
                }
                else{
                    docet.setVisibility(View.VISIBLE);
                    doctv.setVisibility(View.VISIBLE);
                }

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }


}
