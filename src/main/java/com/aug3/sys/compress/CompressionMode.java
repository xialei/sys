package com.aug3.sys.compress;

import java.util.zip.Deflater;

/**
 * A compression mode. Tells how much effort should be spent on compression and
 * decompression of stored fields.
 * 
 * @author Roger.xia
 * 
 */
public abstract class CompressionMode {

    /**
     * A compression mode that trades compression ratio for speed. Although the
     * compression ratio might remain high, compression and decompression are
     * very fast. Use this mode with indices that have a high update rate but
     * should be able to load documents from disk quickly.
     */
    public static final CompressionMode FAST_COMPRESSION = new CompressionMode() {

        @Override
        public Compressor newCompressor() {
            return new LZ4FastCompressor();
        }

        @Override
        public Decompressor newDecompressor() {
            return new LZ4Decompressor();
        }

        @Override
        public String toString() {
            return "FAST";
        }
    };

    /**
     * A compression mode that trades speed for compression ratio. Although
     * compression and decompression might be slow, this compression mode should
     * provide a good compression ratio. This mode might be interesting if/when
     * your index size is much bigger than your OS cache.
     */
    public static final CompressionMode HIGH_COMPRESSION = new CompressionMode() {

        @Override
        public Compressor newCompressor() {
            return new DeflateCompressor(Deflater.BEST_COMPRESSION);
        }

        @Override
        public Decompressor newDecompressor() {
            return new DeflateDecompressor();
        }

        @Override
        public String toString() {
            return "HIGH_COMPRESSION";
        }

    };

    /** Sole constructor. */
    protected CompressionMode() {
    }

    /**
     * Create a new {@link Compressor} instance.
     */
    public abstract Compressor newCompressor();

    /**
     * Create a new {@link Decompressor} instance.
     */
    public abstract Decompressor newDecompressor();
}
