package com.example.ubt.threadtestdemo;

import java.text.DecimalFormat;

/**
 * Created by ubt on 2017/12/8 0008.
 */

public class Utils {
    public static String getStringFormNumber(long size) {
        DecimalFormat format = new DecimalFormat("#.0");
        double tb = 1024d * 1024d * 1024d * 1024d;
        double gb = 1024d * 1024d * 1024d;
        double mb = 1024d * 1024d;
        double kb = 1024d;

        if (size > tb) {
            return format.format(size / tb) + "TB";
        }

        if (size > gb) {
            return format.format(size / gb) + "GB";
        }

        if (size > mb) {
            return format.format(size / mb) + "MB";
        }

        if (size > kb) {
            return format.format(size / kb) + "KB";
        }

        return size + "B";
    }
}
