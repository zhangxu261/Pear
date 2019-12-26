package com.lyfen.pear.common.exception.file;

/**
 * 文件名大小限制异常类
 *
 * @author lyfen
 */
public class FileSizeLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("上传的文件大小超出限制的文件大小!<br/>允许的文件最大大小是：{0}MB!", new Object[]{defaultMaxSize});
    }
}
