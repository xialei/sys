package com.aug3.sys.util;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Decompressor;
import net.jpountz.lz4.LZ4Exception;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4UnknownSizeDecompressor;

public class ZipUtil {
    private static LZ4Factory factory = LZ4Factory.safeInstance();
    private static LZ4Compressor compressor = factory.fastCompressor();
    private static LZ4Decompressor decompressor = factory.decompressor();
    private static LZ4UnknownSizeDecompressor unknownSizeDecompressor = factory.unknownSizeDecompressor();

    public static byte[] compress(byte[] data) {
        try {
            int maxCompressedLength = compressor.maxCompressedLength(data.length);
            byte[] compressed = new byte[maxCompressedLength];
            int compressedLength = compressor.compress(data, 0, data.length, compressed, 0, maxCompressedLength);
            byte[] result = new byte[compressedLength];
            System.arraycopy(compressed, 0, result, 0, compressedLength);
            return result;
        } catch (Exception e) {
            return data;
        }
    }

    public static byte[] decompress(byte[] compressData, int decompressedLength) {
        try {
            byte[] restored = new byte[decompressedLength];
            decompressor.decompress(compressData, 0, restored, 0, decompressedLength);
            return restored;
        } catch (Exception e) {
            return compressData;
        }
    }

    public static byte[] decompress(byte[] compressData) {
        int size = compressData.length << 3;
        while (true) {
            try {
                byte[] restored = new byte[size];
                int decompressedLength = unknownSizeDecompressor.decompress(compressData, 0, compressData.length,
                        restored, 0);
                byte[] result = new byte[decompressedLength];
                System.arraycopy(restored, 0, result, 0, decompressedLength);
                return result;
            } catch (LZ4Exception e) {
                if (size < Integer.MAX_VALUE >> 1) {
                    size <<= 1;
                } else if (size >= Integer.MAX_VALUE >> 1 && size < Integer.MAX_VALUE) {
                    size = Integer.MAX_VALUE;
                } else {
                    return compressData;
                }
            } catch (Exception e) {
                return compressData;
            }
        }
    }
}
