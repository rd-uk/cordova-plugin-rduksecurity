/**
 * MIT License
 *
 * Copyright (c) 2016 Kim UNG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.rduk.security.util;

import java.util.Calendar;
import java.math.BigInteger;
import java.io.IOException;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.RSAKeyGenParameterSpec;

import javax.security.auth.x500.X500Principal;

//import android.security.keystore.KeyProperties;
//import android.security.keystore.KeyGenParameterSpec;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;

import android.content.Context;

public class Signing
{
    private static KeyStore getAndroidKeyStore() 
        throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException
    {
        KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
        ks.load(null);

        return ks;
    }

    public static byte[] sign(String message, String keyStoreAlias)
        throws KeyStoreException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, SignatureException, CertificateException, UnrecoverableEntryException, NoSuchProviderException
    {
        KeyStore ks = Signing.getAndroidKeyStore();

        PrivateKey pk = (PrivateKey) ks.getKey(keyStoreAlias, null);

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(message.getBytes("UTF-8"));

        Signature s = Signature.getInstance("SHA256withRSA");
        s.initSign(pk);
        s.update(md.digest());

        return s.sign();
    }

    public static boolean verify(String message, byte[] signature, String keyStoreAlias)
        throws KeyStoreException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, SignatureException, CertificateException, UnrecoverableEntryException, NoSuchProviderException
    {
        KeyStore ks = Signing.getAndroidKeyStore();

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(message.getBytes("UTF-8"));

        Signature s = Signature.getInstance("SHA256withRSA");
        s.initVerify(ks.getCertificate(keyStoreAlias));
        s.update(md.digest());

        return s.verify(signature);
    }

    public static byte[] generate(String alias, Context context)
        throws KeyStoreException, IOException, CertificateException, NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException
    {
        KeyStore ks = Signing.getAndroidKeyStore();

        if (ks.containsAlias(alias)) {
            return ks.getCertificate(alias).getPublicKey().getEncoded();
        }

        KeyPairGenerator kpg = KeyPairGenerator.getInstance(
            "RSA", 
            "AndroidKeyStore");

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        end.add(1, Calendar.YEAR);

        kpg.initialize(new KeyPairGeneratorSpec.Builder(context)
            .setAlias(alias)
            .setSubject(new X500Principal(String.format("CN=%s", alias)))
            .setSerialNumber(BigInteger.ONE)
            .setStartDate(start.getTime())
            .setEndDate(end.getTime())
            .setKeySize(2048)
            .build());

        KeyPair kp = kpg.generateKeyPair();

        return kp.getPublic().getEncoded();
    }
}
