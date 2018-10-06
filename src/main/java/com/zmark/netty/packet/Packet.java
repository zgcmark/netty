package com.zmark.netty.packet;

import lombok.Data;

@Data
public abstract class Packet {
    /**
     * 协议的版本
     */
    private Byte version=1;


    /**
     * 指令
     * @return
     */
    public abstract  Byte getCommand();


}
