package com.mjc.stage2.impl;

import org.junit.jupiter.api.Test;

public class ProxyConnectionTest {
    private static final String URL = "url";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    RealConnection realConnection = new RealConnection(URL, LOGIN, PASSWORD);
    private final ProxyConnection proxyConnection = new ProxyConnection(realConnection);

    @Test
    public void isClosedShouldBeFalse() {
        assert !proxyConnection.isClosed() : "Expected isClosed() to be false but was true" ;
    }
    @Test
    public void reallyCloseShouldCloseConnection() {
        proxyConnection.reallyClose();
        assert proxyConnection.isClosed() : "Expected isClosed() to be true after .reallyClose() but was false";
    }

    @Test
    public void closeShouldReturnConnectionToPool(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        int freeConnectionsBeforeClose = connectionPool.getFreeConnectionsCount();
        proxyConnection.close();
        int freeConnectionsAfterClose = connectionPool.getFreeConnectionsCount();
        assert freeConnectionsAfterClose > freeConnectionsBeforeClose : "Number of FreeConnection in the ConnectionPool didnt grow after close()";
    }
}
