package com.huotu.loanmarket.common.utils;

import com.huotu.loanmarket.common.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * @author helloztt
 */
public class GZipUtils {
    private static final Log log = LogFactory.getLog(GZipUtils.class);

    public static String uncompress(String src){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in;
        GZIPInputStream ginzip;
        byte[] compressed;
        String decompressed = null;
        try {
            // 对返回数据BASE64解码
            compressed = new sun.misc.BASE64Decoder().decodeBuffer(src);
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);

            // 解码后对数据gzip解压缩
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }

            // 最后对数据进行utf-8转码
            decompressed = out.toString(Constant.ENCODING_UTF8);
        } catch (IOException e) {
            log.error("gunzip error ", e);
        }
        return decompressed;
    }
}
