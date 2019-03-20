package raster;

import java.util.Optional;

public interface Raster<T> {

    void set(int x, int y, T value);

    Optional<T> get(int x, int y);

    int getWidth();

    int getHeight();

}
