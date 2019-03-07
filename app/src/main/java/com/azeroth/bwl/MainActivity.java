package com.azeroth.bwl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.azeroth.model.SpBucket;
import com.azeroth.view.ViewPagerNoScroll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BwActivity {

    HashMap<Integer,Integer> dictRadioAndVpIndex=new HashMap<>();
    ViewPagerNoScroll viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((RadioGroup)this.findViewById(R.id.mainRadioGroup)).setOnCheckedChangeListener(this::radioGroupOnCheckedChange);
        this.viewPager=(ViewPagerNoScroll)this.findViewById(R.id.mainViewPage);
    }

    @Override
    public void initData() throws Exception {
        dictRadioAndVpIndex.put(R.id.mainRadioBtnHome,0);
        dictRadioAndVpIndex.put(R.id.mainRadioBtnTrip,1);
        dictRadioAndVpIndex.put(R.id.mainRadioBtnMsg,2);
        dictRadioAndVpIndex.put(R.id.mainRadioBtnZLQ,3);
        dictRadioAndVpIndex.put(R.id.mainRadioBtnSetting,4);
        ArrayList<Page> lstPage=new ArrayList<Page>();
        lstPage.add(new PageHome(this));
        lstPage.add(new PageTrip(this));
        lstPage.add(new PageMsg(this));
        lstPage.add(new PageZLQ(this));
        lstPage.add(new PageSetting(this));
        BwPagerAdapter<Page> adapter=new BwPagerAdapter(lstPage);
        adapter.instantiateItemHandler=(lst,container,position)->lst.get(position).view;
        this.viewPager.setAdapter(adapter);
    }

    public void radioGroupOnCheckedChange(RadioGroup group, int checkedId)
    {
        this.viewPager.setCurrentItem(this.dictRadioAndVpIndex.get(checkedId));
    }

    public  void btnQuitOnclick(View view){
        SharedPreferences sp= this.getSharedPreferences(SpBucket.Index.Login,MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.remove(SpBucket.Item.UserInfo);
        editor.commit();
        Intent it=new Intent();
        it.setClass(this,LoginActivity.class);
        this.startActivity(it);
        this.finish();
    }
}
