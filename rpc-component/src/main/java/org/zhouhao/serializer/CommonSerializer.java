package org.zhouhao.serializer;


import java.io.IOException;

/**
 * 序列化与反序列化接口
 */
public interface CommonSerializer {
    byte[] serialize(Object obj) throws IOException;

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
                return new KyroSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
