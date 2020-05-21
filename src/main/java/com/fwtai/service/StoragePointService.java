package com.fwtai.service;

import com.fwtai.bean.PageFormData;
import com.fwtai.dao.DaoHandle;
import com.fwtai.tool.ToolClient;
import com.fwtai.tool.ToolString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-05-17 11:16
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
 */
@Service
public class StoragePointService{

    @Autowired
    private DaoHandle daoHandle;

    public String addStoragePoint(final PageFormData formData){
        final String p_item_storage_code = "item_storage_code";
        final String p_point = "point";
        final String validateField = ToolClient.validateField(formData,p_item_storage_code,p_point);
        if(validateField !=null)return validateField;
        final String point = formData.getString(p_point);
        final int type = ToolString.jsonType(point);
        if(type != 1)return ToolClient.createJsonFail("货位坐标的格式有误");
        final String kid = ToolString.getIdsChar32();
        formData.put("kid",kid);
        final int rows = daoHandle.execute("wms.addStoragePoint",formData);
        return ToolClient.executeRows(rows);
    }

    public String getListData(){
        return ToolClient.queryJson(daoHandle.queryForListString("wms.getListData"));
    }
}