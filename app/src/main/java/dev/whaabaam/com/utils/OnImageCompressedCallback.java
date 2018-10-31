package dev.whaabaam.com.utils;

import java.io.File;

public interface OnImageCompressedCallback {
    void onImageCompressed(File compressedImage, int uploadType);
}
