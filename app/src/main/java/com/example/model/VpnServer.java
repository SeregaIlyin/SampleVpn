package com.example.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class VpnServer implements Serializable {

    private int flag;

    private String country;

    private String ip;

    private String port;

    private int type;

}
