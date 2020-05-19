package com.fwtai.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fwtai.bean.PageFormData;
import com.fwtai.dao.DaoHandle;
import com.fwtai.tool.ToolClient;
import com.fwtai.tool.ToolString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-05-17 11:16
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
 */
@Service
public class TaskService{

    @Autowired
    private DaoHandle daoHandle;

    @Transactional
    public Integer insert(final JSONObject jsonObject){
        final String p_invoices_code = "invoices_code";
        final String p_type = "type";
        final String p_total = "total";
        final String p_status = "status";
        final String data = jsonObject.getString("data");
        final ArrayList<HashMap<String,String>> list = ToolString.parseJsonArrayOriginal(data);
        final String kid = ToolString.getIdsChar32();
        final HashMap<String,String> params = new HashMap<>(5);
        final String invoices_code = jsonObject.getString(p_invoices_code);
        params.put("kid",kid);
        params.put("invoices_code",invoices_code);
        params.put("type",jsonObject.getInteger(p_type)+"");
        params.put("total",jsonObject.getInteger(p_total)+"");
        params.put("status",jsonObject.getInteger(p_status)+"");
        for(int i = 0; i < list.size(); i++){
            list.get(i).put("kid",ToolString.getIdsChar32());
            list.get(i).put("task_id",kid);
            list.get(i).put("invoices_code",invoices_code);
        }
        final int rows1 = daoHandle.execute("wms.addTask",params);
        final int rows2 = daoHandle.execute("wms.addTaskDetail",list);
        if(rows1 <= 0 || rows2 <= 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }

    @Transactional
    public String insert0(final JSONObject jsonObject){
        final String p_invoices_code = "invoices_code";
        final String p_type = "type";
        final String p_total = "total";
        final String p_status = "status";
        final String p_data = "data";
        final String validateField = ToolClient.validateField(jsonObject,p_invoices_code,p_type,p_total,p_status,p_data);
        if(validateField !=null)return validateField;
        final String validateInteger = ToolClient.validateInteger(jsonObject,p_type,p_total,p_status);
        if(validateInteger !=null)return validateInteger;
        final String data = jsonObject.getString("data");
        final ArrayList<HashMap<String,String>> list = ToolString.parseJsonArrayOriginal(data);
        final String kid = ToolString.getIdsChar32();
        final HashMap<String,String> params = new HashMap<>(5);
        final String invoices_code = jsonObject.getString(p_invoices_code);
        params.put("kid",kid);
        params.put("invoices_code",invoices_code);
        params.put("type",jsonObject.getInteger(p_type)+"");
        params.put("total",jsonObject.getInteger(p_total)+"");
        params.put("status",jsonObject.getInteger(p_status)+"");
        for(int i = 0; i < list.size(); i++){
            list.get(i).put("kid",ToolString.getIdsChar32());
            list.get(i).put("task_id",kid);
            list.get(i).put("invoices_code",invoices_code);
        }
        final int rows1 = daoHandle.execute("wms.addTask",params);
        final int rows2 = daoHandle.execute("wms.addTaskDetail",list);
        if(rows1 <= 0 || rows2 <= 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return ToolClient.executeRows(rows2);
    }

    public String status(final JSONArray jsonArray){
        if(jsonArray == null || jsonArray.size() <= 0)return ToolClient.jsonValidateField();
        final List<Map> list = jsonArray.toJavaList(Map.class);
        boolean b = false;
        for(int i = 0; i < list.size(); i++){
            final Map map = list.get(i);
            if( !map.containsKey("invoices_code") || !map.containsKey("item_code") || !map.containsKey("status")){
                b = true;
                break;
            }
        }
        if(b){
            return ToolClient.jsonValidateField();
        }
        //更新状态
        int index = 0;
        for(int i = 0; i < list.size(); i++){
            final int rows = daoHandle.execute("wms.editStatus",list.get(i));
            if(rows > 0) index ++;
        }
        if(index > 0){
            if( index == list.size()){
                return ToolClient.createJsonSuccess("操作成功");
            }else{
                return ToolClient.createJsonSuccess("操作成功"+ index + "条,失败数"+ (list.size() - index));
            }
        }else{
            return ToolClient.createJsonFail("操作失败");
        }
    }

    public Integer confirm(final PageFormData formData){
        final String p_invoices_code = "invoices_code";
        final String p_item_code = "item_code";
        final String validateField = ToolClient.validateField(formData,p_invoices_code,p_item_code);
        if(validateField !=null)return -1;
        final int rows = daoHandle.execute("wms.confirm",formData);
        return rows > 0 ? 1 : 0;
    }

    public List<HashMap<String,String>> queryStorageCode(final ArrayList<String> storages){
        return daoHandle.queryForListString("wms.getStoragePoint",storages);
    }
}