package com.fwtai.service;

import com.fwtai.dao.DaoHandle;
import com.fwtai.tool.ToolClient;
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

    public String getListData(){
        return ToolClient.queryJson(daoHandle.queryForListString("wms.getListData"));
    }
}