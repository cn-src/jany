package cn.javaer.jany.spring.util;

import cn.hutool.core.io.FileUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author cn-src
 */
public interface RequestUtils {
    /**
     * 判断文件是否是 Excel 文件。
     *
     * @param file MultipartFile
     *
     * @return true 表示是 Excel 文件。
     */
    static boolean isExcel(final MultipartFile file) {

        if (null == file || file.isEmpty()) {
            return false;
        }
        final String suffix = FileUtil.getSuffix(file.getOriginalFilename());
        if (null == suffix) {
            return false;
        }
        return "xls".equalsIgnoreCase(suffix) ||
            "xlsx".equalsIgnoreCase(suffix) ||
            "xlsm".equalsIgnoreCase(suffix);
    }
}