package cn.javaer.jany.util;

import cn.hutool.core.io.file.PathUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author cn-src
 */
public class PathUtils extends PathUtil {

    public static Path userDirJoin(String path) {
        return Paths.get(System.getProperty("user.dir"), path);
    }
}