package com.example.JIO.SolrSearch.Service;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.*;

public class RandomLongGenerator {


    private ArrayList<Long> arrayList;

    private int count = 7;

    private Long INIT_VALUE = Long.parseLong(RandomStringUtils.random(count , false , true));

    private Long MAX_NUM = Long.parseLong("50000");

    public RandomLongGenerator(ArrayList<Long> arrayList){

        this.arrayList = arrayList;

        addLongs(INIT_VALUE , MAX_NUM);

    }

    private void addLongs(Long INIT_VALUE , Long MAX_NUM){
        for(Long i = INIT_VALUE ; i < INIT_VALUE+MAX_NUM ; i++ ){
            arrayList.add(i);
        }

        Collections.shuffle(arrayList);

    }

    public Long getRandomLong(){

        Long number;

        if(!arrayList.isEmpty()) {
            number = arrayList.get(0);
            arrayList.remove(0);
            return number;
        }else{
            count++;
            this.addLongs(Long.parseLong(RandomStringUtils.random(count , false , true)) , Long.parseLong("10000"));
            number = arrayList.get(0);
            arrayList.remove(0);
            return number;
        }
    }




}
