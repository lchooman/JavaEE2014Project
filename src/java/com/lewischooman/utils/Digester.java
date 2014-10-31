/***********************************************************************************************************************************
    Customer movie show booking and employee reporting Java EE system, using Spring 3+, Hibernate 4+ and JSF 2.1+
    Copyright (C) 2014 Lewis Tat Fong CHOO MAN

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
************************************************************************************************************************************/

package com.lewischooman.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.Charset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Digester implements IDigester {
    private final String algorithm;
    private final Charset charset;

    @Autowired
    public Digester(@Qualifier(value="digestAlgorithm") String algorithm, @Qualifier(value="charsetName") String charsetName) {
        this.algorithm = algorithm;
        this.charset = Charset.forName(charsetName);
    }

    public static String bytesToHex(byte [] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%1$02x", b));
        }
        return sb.toString();
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public byte[] digest (String toDigest) {
        MessageDigest mDigest;
        byte [] inBytes, outBytes = null;

        if (toDigest == null) {
            return null;
        }

        try {
            mDigest = MessageDigest.getInstance(this.algorithm);

            mDigest.reset();
            inBytes = toDigest.getBytes(this.charset);
            mDigest.update(inBytes);
            outBytes = mDigest.digest();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return outBytes;
    }

    @Override
    public String digestToHex (String toDigest) {
        return bytesToHex(digest(toDigest));
    }
}
