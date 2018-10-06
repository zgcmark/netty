package com.zmark.netty.packet;

import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {

    private Long userId;
    private String userName;
    private String passWord;


    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
