package com.aug3.sys.compress;

/**
 * A data compressor.
 * 
 * @author Roger.xia
 * 
 */
public abstract class Compressor {

    /**
     * Sole constructor, typically called from sub-classes.
     **/
    protected Compressor() {
    }

    /**
     * Compress bytes into <code>out</code>. It it the responsibility of the
     * compressor to add all necessary information so that a
     * {@link Decompressor} will know when to stop decompressing bytes from the
     * stream.
     */
    public abstract byte[] compress(byte[] bytes, int off, int len) throws Exception;
}
