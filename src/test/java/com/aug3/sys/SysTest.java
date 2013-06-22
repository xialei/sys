package com.aug3.sys;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.aug3.sys.compress.CompressionMode;
import com.aug3.sys.util.ZipUtil;

public class SysTest {

	@Ignore
	@Test
    public void compressTest() {
        byte[] data = getData();

        try {
            long t1 = System.currentTimeMillis();
            CompressionMode cm1 = CompressionMode.FAST_COMPRESSION;
            byte[] data11 = cm1.newCompressor().compress(data, 0, data.length);
            byte[] data12 = cm1.newDecompressor().decompress(data11, 0, data11.length);
            System.out.println("LZ4 fast:");
            System.out.println("size:" + (data.length - data11.length));
            System.out.println("used:" + (System.currentTimeMillis() - t1));
            //            Assert.assertArrayEquals(data, data12);
            data11 = null;
            data12 = null;
            System.out.println("=====================================================");

            long t3 = System.currentTimeMillis();
            CompressionMode cm3 = CompressionMode.HIGH_COMPRESSION;
            byte[] data31 = cm3.newCompressor().compress(data, 0, data.length);
            byte[] data32 = cm3.newDecompressor().decompress(data31, 0, data31.length);
            System.out.println("deflater:");
            System.out.println("size:" + (data.length - data31.length));
            System.out.println("used:" + (System.currentTimeMillis() - t3));
            //            Assert.assertArrayEquals(data, data32);
            data31 = null;
            data32 = null;
            System.out.println("=====================================================");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Ignore
    @Test
    public void zipUtilTest() {
        try {
            //            byte[] data = new byte[] { 'a', 'b', 'c', '-', '1', '2', '3', '#', '#', '#', '#' };
            byte[] data = getData();

            byte[] compressedData = ZipUtil.compress(data);

            byte[] data3 = ZipUtil.decompress(compressedData);
            Assert.assertArrayEquals(data, data3);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    private byte[] getData() {
        try {
            byte[] b = FileUtils.readFileToByteArray(new File("d:\\test\\aaa.pdf"));
            return b;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
