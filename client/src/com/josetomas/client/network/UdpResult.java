package com.josetomas.client.network;

import java.net.InetAddress;

public class UdpResult {

    private InetAddress inetAddress;

    private NetworkException error;

    public UdpResult(InetAddress inetAddress, NetworkException error) {
        this.inetAddress = inetAddress;
        this.error = error;

    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public Throwable getError() {

        return error;

    }

    public boolean hasError() {

        return getError() != null;

    }

}
