package org.example.p2p.p2pfiletransfx.Interfaces;

import java.io.BufferedReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Define {
    public interface Promise<T> {
        void onSuccess(T s);
        void onFailure(Exception e);
//        default void then(Consumer<T> onSuccess) {
//            this.onSuccess(result -> {
//                onSuccess.accept(result);
//            });
//        }
//
//        default void catchError(Consumer<Exception> onFailure) {
//            this.onFailure(e -> {
//                onFailure.accept(e);
//            });
//        }
    }
}
