package com.aug3.sys.compress;

/**
 * A data decompressor.
 * 
 * @author Roger.xia
 *
 */
public abstract class Decompressor implements Cloneable {

    /** Sole constructor, typically called from sub-classes. */
    protected Decompressor() {
    }

    /**
     * Decompress bytes that were stored between offsets <code>offset</code> and
     * <code>offset+length</code> in the original stream from the compressed
     * stream <code>in</code> to <code>bytes</code>. After returning, the length
     * of <code>bytes</code> (<code>bytes.length</code>) must be equal to
     * <code>length</code>. Implementations of this method are free to resize
     * <code>bytes</code> depending on their needs.
     * 
     * @param in
     *            the input that stores the compressed stream
     * @param originalLength
     *            the length of the original data (before compression)
     * @param offset
     *            bytes before this offset do not need to be decompressed
     * @param length
     *            bytes after <code>offset+length</code> do not need to be
     *            decompressed
     * @param bytes
     *            a {@link BytesRef} where to store the decompressed data
     */
    public abstract byte[] decompress(byte[] in, int offset, int length) throws Exception;

    @Override
    public abstract Decompressor clone();
}
