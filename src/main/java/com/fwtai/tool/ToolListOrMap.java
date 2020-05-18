package com.fwtai.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * List或Map的工具类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年9月24日 16:25:16
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class ToolListOrMap{

    /**
     * 判断List集合是所有的元素是否相同,相同true,否则false
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2018/6/14 20:28
    */
    public final static boolean checkListIdentical(final ArrayList<String> list) {
        final Set set = new HashSet(list);
        return (set.size()==1) ? true : false;
    }

    /**
     * 处理复检的样本号的值
     * @param list 数据集合
     * @param key 指定样本号的key
     * @param field 指定样本号结果值的key
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2018年9月2日 16:17
    */
    public final static HashMap<String,ArrayList<String>> recheck(final List<HashMap<String,String>> list,final String key,final String field){
        final ArrayList<String> arrayList = new ArrayList<String>();
        for(final HashMap<String,String> hm : list){
            arrayList.add(hm.get(key));//获取样本号
        }
        final ArrayList<String> sampled = removeRepetition(arrayList);
        final HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
        for(int i = 0; i < sampled.size();i++){//遍历所有的样本号
            final String value = sampled.get(i);//样本号
            final ArrayList<String> values = new ArrayList<String>();
            for(int y = 0; y < list.size(); y++){
                final HashMap<String,String> hm = list.get(y);
                for(final String k : hm.keySet()){
                    if(hm.get(k).equalsIgnoreCase(value)){
                        values.add(hm.get(field));
                        break;
                    }
                }
            }
            map.put(value,values);
        }
        return map;
    }

    /**
     * list去重
     * @param
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2018年9月2日 17:08
    */
    public final static ArrayList<String> removeRepetition(final ArrayList<String> list){
        final HashSet<String> h = new HashSet<String>(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    //list转换HashMap
    public final static HashMap<String,String> listConvertMap(final List<HashMap<String,String>> list,final String fieldKey,final String fieldValue){
        final HashMap<String,String> map = new HashMap<>();
        for(int i = 0; i < list.size(); i++){
            final HashMap<String,String> temp = list.get(i);
            final String key = temp.get(fieldKey);
            final String value = temp.get(fieldValue);
            map.put(key,value);
        }
        return map;
    }
}