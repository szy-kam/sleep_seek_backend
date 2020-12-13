package com.sleepseek.image;

import java.io.File;

public interface ImageStorage {
    String uploadFile(File file, String fileName);
}
