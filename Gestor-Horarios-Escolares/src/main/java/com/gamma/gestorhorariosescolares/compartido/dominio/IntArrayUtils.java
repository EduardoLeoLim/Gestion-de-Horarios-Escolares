package com.gamma.gestorhorariosescolares.compartido.dominio;

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntArrayUtils {
    public static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    public static int[] add(final int[] arr, final int key) {
        return IntStream.concat(Arrays.stream(arr), IntStream.of(key)).toArray();
    }

    public static int[] remove(final int[] arr, final int key) {
        return Arrays.stream(arr).filter(i -> i != key).toArray();
    }
}