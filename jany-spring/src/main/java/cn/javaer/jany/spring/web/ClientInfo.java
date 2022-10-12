package cn.javaer.jany.spring.web;

import lombok.Data;

@Data
public class ClientInfo {
    private String ip;

    private String browser;

    private String os;
}