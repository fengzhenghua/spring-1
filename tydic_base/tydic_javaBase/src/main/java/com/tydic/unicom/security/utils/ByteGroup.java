package com.tydic.unicom.security.utils;
import java.util.ArrayList;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月5日 下午4:46:06
 * @ClassName ByteGroup
 * @Description 字节组
 * @version V1.0
 */
public class ByteGroup {
    ArrayList<Byte> byteContainer = new ArrayList<Byte>();

    public byte[] toBytes() {
        byte[] bytes = new byte[byteContainer.size()];
        for (int i = 0; i < byteContainer.size(); i++) {
            bytes[i] = byteContainer.get(i);
        }
        return bytes;
    }

    public ByteGroup addBytes(byte[] bytes) {
        for (byte b : bytes) {
            byteContainer.add(b);
        }
        return this;
    }

    public int size() {
        return byteContainer.size();
    }
}
