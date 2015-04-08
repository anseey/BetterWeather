package com.anseey.betterweather;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.anseey.betterweather.manager.CityManager;
import com.anseey.betterweather.model.CityMod;
import com.anseey.betterweather.utils.PreferencesUtils;

import java.util.List;


public class CityActivity extends Activity {


    private TextView mTVTitle;
    private EditText mETTitle;
    private ListView mLVCity;
    private CityAdapter mCityAdapter = new CityAdapter();
    private CityManager mCityManager = CityManager.getInstance(this);
    private List<CityMod> mCities = mCityManager.getAllCity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        mTVTitle = (TextView) findViewById(R.id.tv_title);
        mLVCity = (ListView) findViewById(R.id.lv_city);
        mETTitle = (EditText) findViewById(R.id.et_city);

        mTVTitle.setText("当前城市: " + PreferencesUtils.getString(getApplicationContext(),"city"));

        mLVCity.setAdapter(mCityAdapter);

        mLVCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTVTitle.setText("当前城市: " + mCities.get(position).getCity());
                PreferencesUtils.putString(getApplicationContext(),"number",mCities.get(position).getNumber());
                finish();
            }
        });

        mETTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mCities = mCityManager.getAllCity();
                if (mETTitle.getText().toString().equals("")){
                    mCityAdapter.notifyDataSetChanged();
                    return;
                }
                for (int i = 0; i < mCities.size(); i++) {
                    String tmp = mCities.get(i).getProvince()+" "+ mCities.get(i).getCity();
                    if (!tmp.contains(mETTitle.getText().toString())){
                        mCities.remove(i);
                        i--;
                    }
                }
                mCityAdapter.notifyDataSetChanged();
            }
        });

    }

    public void onClickBack(View view){
        finish();
    }


    private class CityAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mCities.size();
        }

        @Override
        public Object getItem(int position) {
            return mCities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, android.view.View convertView, ViewGroup parent) {
            ViewHolder item;
            if (convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                item = new ViewHolder();
                convertView = inflater.inflate(R.layout.list_view_item,null);
                item.mTVCity = (TextView) convertView.findViewById(R.id.tv_city);
                convertView.setTag(item);
            }else {
                item = (ViewHolder) convertView.getTag();
            }
            item.mTVCity.setText(mCities.get(position).getProvince()+" "+mCities.get(position).getCity());
            return convertView;
        }
    }

    private static class ViewHolder{
        private TextView mTVCity;
    }


}
