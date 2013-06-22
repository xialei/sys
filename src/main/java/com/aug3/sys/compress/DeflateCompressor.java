package com.aug3.sys.compress;

import java.util.zip.Deflater;

import com.aug3.sys.util.ArrayUtil;

public class DeflateCompressor extends Compressor {
    final Deflater compressor;

    DeflateCompressor(int level) {
        compressor = new Deflater(level);
    }

    DeflateCompressor() {
        compressor = new Deflater();
    }

    @Override
    public byte[] compress(byte[] bytes, int off, int len) throws Exception {
        byte[] compressed = new byte[64];
        compressor.reset();
        compressor.setInput(bytes, off, len);
        compressor.finish();

        if (compressor.needsInput()) {
            // no output
            assert len == 0 : len;
        }

        int totalCount = 0;
        while (true) {
            final int count = compressor.deflate(compressed, totalCount, compressed.length - totalCount);
            totalCount += count;
            assert totalCount <= compressed.length;
            if (compressor.finished()) {
                break;
            } else {
                compressed = ArrayUtil.grow(compressed);
            }
        }

        compressed = ArrayUtil.subarray(compressed, 0, totalCount);
        return compressed;
    }
}
