package com.aug3.sys.compress;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;

public class LZ4FastCompressor extends Compressor {
    private LZ4Compressor compressor = LZ4Factory.safeInstance().fastCompressor();

    LZ4FastCompressor() {

    }

    @Override
    public byte[] compress(byte[] bytes, int off, int len) throws Exception {
        try {
            int maxCompressedLength = compressor.maxCompressedLength(bytes.length);
            byte[] compressed = new byte[maxCompressedLength];
            int compressedLength = compressor.compress(bytes, 0, bytes.length, compressed, 0, maxCompressedLength);
            byte[] out = new byte[compressedLength];
            System.arraycopy(compressed, 0, out, 0, compressedLength);
            return out;
        } catch (Exception e) {
            throw e;
        }
    }
}
