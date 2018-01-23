package org.netty.encryption.impl;

import java.io.ByteArrayOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.netty.encryption.StreamCryptBase;

/**
 * rc4 cipher implementation
 */
public class Rc4Crypt extends StreamCryptBase {

    public final static String CIPHER_RC4_MD5 = "rc4-md5";

    public static Map<String, String> getCiphers() {
        Map<String, String> ciphers = new HashMap<>();
        ciphers.put(CIPHER_RC4_MD5, Rc4Crypt.class.getName());

        return ciphers;
    }

    public Rc4Crypt(String name, String password) {
        super(name, password);
    }

    @Override
    public int getKeyLength() {
        return 16;
    }

    @Override
    protected StreamCipher getCipher(boolean isEncrypted) throws InvalidAlgorithmParameterException {
//        RC4Engine engine = new RC4Engine();
        StreamCipher cipher;

        if (_name.equals(CIPHER_RC4_MD5)) {
//            cipher = new CFBBlockCipher(engine, getIVLength() * 8);
        	cipher = new RC4Engine();
        }
        else {
            throw new InvalidAlgorithmParameterException(_name);
        }

        return cipher;
    }

    @Override
    public int getIVLength() {
        return 16;
    }

    @Override
    protected SecretKey getKey() {
        return new SecretKeySpec(_ssKey.getEncoded(), "AES");
    }

    @Override
    protected void _encrypt(byte[] data, ByteArrayOutputStream stream) {
        int noBytesProcessed;
        byte[] buffer = new byte[data.length];

        noBytesProcessed = encCipher.processBytes(data, 0, data.length, buffer, 0);
        stream.write(buffer, 0, noBytesProcessed);
    }

    @Override
    protected void _decrypt(byte[] data, ByteArrayOutputStream stream) {
        int noBytesProcessed;
        byte[] buffer = new byte[data.length];

        noBytesProcessed = decCipher.processBytes(data, 0, data.length, buffer, 0);
        stream.write(buffer, 0, noBytesProcessed);
    }
}
