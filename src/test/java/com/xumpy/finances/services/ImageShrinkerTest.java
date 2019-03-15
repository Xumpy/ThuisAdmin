package com.xumpy.finances.services;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class ImageShrinkerTest {

    @Test
    public void testShrinkImage() throws IOException {
        byte[] image = IOUtils.toByteArray(ImageShrinkerTest.class.getResourceAsStream("/images/image.png"));
        System.out.println(image.length);
        byte[] shrinkedImage = ImageShrinker.shrinkImage(image, "image/png", 50);
    }
}
