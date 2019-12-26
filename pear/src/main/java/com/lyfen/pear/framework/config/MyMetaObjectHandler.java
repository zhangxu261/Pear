package com.lyfen.pear.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lyfen.pear.common.exception.CustomException;
import com.lyfen.pear.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 字段自动填充
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        String username = "admin";
        try {
            username = SecurityUtils.getUsername();
        } catch (CustomException e) {
            // do nothind
        }
        this.setFieldValByName("createdBy", username, metaObject);
        this.setFieldValByName("createdTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updatedBy", username, metaObject);
        this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        String username = "admin";
        try {
            username = SecurityUtils.getUsername();
        } catch (CustomException e) {
            // do nothind
        }
        this.setFieldValByName("updatedBy", username, metaObject);
        this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
    }
}
