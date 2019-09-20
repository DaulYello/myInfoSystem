package com.personal.info.myInfoSystem.modular.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.info.myInfoSystem.modular.system.entity.OperationLog;
import com.personal.info.myInfoSystem.modular.system.mapper.OperationLogMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LogService extends ServiceImpl<OperationLogMapper,OperationLog> {


    /**
     * 获取操作日志
     * @param page
     * @param beginTime
     * @param endTime
     * @param logName
     * @param s
     * @return
     */
    public List<Map<String,Object>> getOperationLogs(Page page, String beginTime, String endTime, String logName, String s) {
        return this.baseMapper.getOperationLogs(page,beginTime,endTime,logName,s);
    }
}
