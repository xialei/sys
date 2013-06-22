package com.aug3.sys.compress;

import java.util.zip.Inflater;

import com.aug3.sys.util.ArrayUtil;

public class DeflateDecompressor extends Decompressor {
    final Inflater decompressor;

    DeflateDecompressor() {
        decompressor = new Inflater();
    }

    @Override
    public byte[] decompress(byte[] in, int offset, int length) throws Exception {
        byte[] decompressed = new byte[64];
        decompressor.reset();
        decompressor.setInput(in, offset, length);

        int totalCount = 0;
        while (true) {
            final int count = decompressor.inflate(decompressed, totalCount, decompressed.length - totalCount);
            totalCount += count;
            assert totalCount <= decompressed.length;
            if (decompressor.finished()) {
                break;
            } else {
                decompressed = ArrayUtil.grow(decompressed);
            }
        }
        decompressed = ArrayUtil.subarray(decompressed, 0, totalCount);
        return decompressed;
    }

    @Override
    public Decompressor clone() {
        return new DeflateDecompressor();
    }
}
