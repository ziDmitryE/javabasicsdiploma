package ru.netology.graphics.image;

import java.io.IOException;
import java.net.MalformedURLException;

public interface TextGraphicsConverter {

    String convert(String url) throws IOException, BadImageSizeException;
    void setMaxWidth(int width);
    void setMaxHeight(int height);
    void setMaxRatio(double maxRatio);
    void setTextColorSchema(TextColorSchema schema);
}
