package com.josetomas.client.network;

public class NetworkException extends Throwable {
    Throwable error;

    public NetworkException(String details, Throwable error) {
        this.error = new Throwable(details, error);
    }
    //TODO HAY QUE MODIFICAR LA EXCEPCION PARA QUE LE DIGA ALGO AL USUSARIO
}
