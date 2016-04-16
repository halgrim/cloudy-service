package pl.cloudy.models;

import java.io.InputStream;

public class Image
{

    InputStream image;
    String fileName;

    public InputStream getImage()
    {
        return image;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(final String fileName)
    {
        this.fileName = fileName;
    }

    public void setImage(final InputStream image)
    {
        this.image = image;
    }
}
