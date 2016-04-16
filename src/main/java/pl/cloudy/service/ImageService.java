package pl.cloudy.service;

import pl.cloudy.models.Image;

public interface ImageService
{
    Image findImageByImageName(String imageFileName);

    void saveImage(Image image);
}
