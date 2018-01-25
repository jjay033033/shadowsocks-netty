package org.netty.encryption.impl;

import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.netty.encryption.CryptBase;

/**
 * rc4 cipher implementation
 */
public class Rc4Crypt extends CryptBase {

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
        if (_name.equals(CIPHER_RC4_MD5)) {
        	return new RC4Engine();
        }
        else {
            throw new InvalidAlgorithmParameterException(_name);
        }
    }

    @Override
    public int getIVLength() {
        return 16;
    }

    @Override
    protected SecretKey getKey() {
        return null;
    }

//    @Override
//    protected void _encrypt(byte[] data, ByteArrayOutputStream stream) {
//        int noBytesProcessed;
//        byte[] buffer = new byte[data.length];
//
//        noBytesProcessed = cipher.processBytes(data, 0, data.length, buffer, 0);
//        stream.write(buffer, 0, noBytesProcessed);
//    }
//
//    @Override
//    protected void _decrypt(byte[] data, ByteArrayOutputStream stream) {
//        int noBytesProcessed;
//        byte[] buffer = new byte[data.length];
//
//        noBytesProcessed = cipher.processBytes(data, 0, data.length, buffer, 0);
//        stream.write(buffer, 0, noBytesProcessed);
//    }
}
