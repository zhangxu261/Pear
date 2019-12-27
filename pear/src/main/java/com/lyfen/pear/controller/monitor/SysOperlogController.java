package com.lyfen.pear.controller.monitor;

import com.lyfen.pear.common.utils.poi.ExcelUtil;
import com.lyfen.pear.framework.aspectj.lang.annotation.Log;
import com.lyfen.pear.framework.aspectj.lang.enums.BusinessType;
import com.lyfen.pear.framework.web.controller.BaseController;
import com.lyfen.pear.framework.web.domain.AjaxResult;
import com.lyfen.pear.framework.web.page.TableDataInfo;
import com.lyfen.pear.project.monitor.domain.SysOperLog;
import com.lyfen.pear.service.monitor.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志记录
 *
 * @author lyfen
 */
@RestController
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController {
    @Autowired
    private SysOperLogService sysOperLogService;

    @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysOperLog operLog) {
        startPage();
        List<SysOperLog> list = sysOperLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @GetMapping("/export")
    public AjaxResult export(SysOperLog operLog) {
        List<SysOperLog> list = sysOperLogService.selectOperLogList(operLog);
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        return util.exportExcel(list, "操作日志");
    }

    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/{operIds}")
    public AjaxResult remove(@PathVariable Long[] operIds) {
        return toAjax(sysOperLogService.deleteOperLogByIds(operIds));
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/clean")
    public AjaxResult clean() {
        sysOperLogService.cleanOperLog();
        return AjaxResult.success();
    }
}
