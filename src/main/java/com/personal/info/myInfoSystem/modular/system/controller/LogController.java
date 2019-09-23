package com.personal.info.myInfoSystem.modular.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.personal.info.myInfoSystem.core.common.annotation.BussinessLog;
import com.personal.info.myInfoSystem.core.common.annotation.Permission;
import com.personal.info.myInfoSystem.core.common.constant.Const;
import com.personal.info.myInfoSystem.core.common.constant.page.PageFactory;
import com.personal.info.myInfoSystem.core.common.constant.state.BizLogType;
import com.personal.info.myInfoSystem.modular.system.entity.OperationLog;
import com.personal.info.myInfoSystem.modular.system.service.LogService;
import com.personal.info.myInfoSystem.modular.system.warpper.LogWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("log")
public class LogController extends BaseController {

    private static final String PREFIX = "/modular/system/log/";

    @Autowired
    private LogService logService;

    /**
     * 跳转到日志页面
     * @return
     */
    @RequestMapping("")
    public String index(){
        return PREFIX+"log.html";
    }

    @Permission(value = Const.ADMIN_NAME)
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime,@RequestParam (required = false) String endTime,
                       @RequestParam(required = false) String logName,@RequestParam(required = false) Integer logType){

        Page page = PageFactory.defaultPage();
        List<Map<String,Object>> result= logService.getOperationLogs(page,beginTime,endTime,logName,BizLogType.valueOf(logType));

        page.setRecords(new LogWrapper(result).wrap());
        return PageFactory.createPageInfo(page);
    }

    @RequestMapping("/detail/{id}")
    @Permission(value = Const.ADMIN_NAME)
    @ResponseBody
    public Object getLogDetail(@PathVariable Long id){
        OperationLog operationLog = logService.getById(id);
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(operationLog);
        return super.warpObject(new LogWrapper(stringObjectMap));
    }

    @BussinessLog(value = "清空业务日志")
    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/delLog")
    @ResponseBody
    public Object delLog(){
        SqlRunner.db().delete("delete from sys_operation_log");
        return SUCCESS_TIP;
    }
}
