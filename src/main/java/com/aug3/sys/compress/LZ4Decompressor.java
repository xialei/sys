package com.aug3.sys.compress;

import net.jpountz.lz4.LZ4Exception;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4UnknownSizeDecompressor;

public class LZ4Decompressor extends Decompressor {
    private LZ4UnknownSizeDecompressor unknownSizeDecompressor = LZ4Factory.safeInstance().unknownSizeDecompressor();

    LZ4Decompressor() {

    }

    @Override
    public byte[] decompress(byte[] in, int offset, int length) throws Exception {
        int size = in.length << 3;
        while (true) {
            try {
                byte[] restored = new byte[size];
                int decompressedLength = unknownSizeDecompressor.decompress(in, 0, in.length, restored, 0);
                byte[] result = new byte[decompressedLength];
                System.arraycopy(restored, 0, result, 0, decompressedLength);
                return result;
            } catch (LZ4Exception e) {
                if (size < Integer.MAX_VALUE >> 1) {
                    size <<= 1;
                } else if (size >= Integer.MAX_VALUE >> 1 && size < Integer.MAX_VALUE) {
                    size = Integer.MAX_VALUE;
                } else {
                    throw e;
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    @Override
    public Decompressor clone() {
        return this;
    }
}
