package ar.com.carrion.simuladordemercado.backend.Features.Singleton;


import ar.com.carrion.simuladordemercado.backend.Domains.Price;

public class PriceSingleton {

    private static Price instance;

    private PriceSingleton() {}

    public static synchronized Price getInstance() {
        if (instance == null) {
            instance = new Price();
        }
        return instance;
    }
}