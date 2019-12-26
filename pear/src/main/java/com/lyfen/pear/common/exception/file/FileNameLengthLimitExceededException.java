package com.lyfen.pear.common.exception.file;

/**
 * 文件名称超长限制异常类
 *
 * @author lyfen
 */
public class FileNameLengthLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("上传的文件名最长{0}个字符", new Object[]{defaultFileNameLength});
    }
}
