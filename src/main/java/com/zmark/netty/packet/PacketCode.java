package com.zmark.netty.packet;

import com.zmark.netty.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class PacketCode {
    private static final int MAGIC_NUMBER = 0x12345678;

    public ByteBuf encode(Packet packet) {
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 2. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        //写入魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        //写入版本号
        byteBuf.writeByte(packet.getVersion());
        //写入我们序列化得算法  （json）
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        //写入我们这次的操作指令   1
        byteBuf.writeByte(packet.getCommand());
        //写入我们的数据的长度
        byteBuf.writeInt(bytes.length);
        //写入我们的数据的内容
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }



    public Packet decode(ByteBuf byteBuf) {
        // 校验魔数是不是对的 magic number
        byteBuf.skipBytes(4);
//        ByteBuf byteBuf1 = byteBuf.readByte(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return null;
    }

}
