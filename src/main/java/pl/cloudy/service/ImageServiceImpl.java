package pl.cloudy.service;


import org.springframework.stereotype.Service;
import pl.cloudy.database.DatabaseConnection;
import pl.cloudy.models.Image;
import pl.cloudy.database.QueryHelper;

import java.sql.Connection;

@Service("ImageService")
public class ImageServiceImpl implements ImageService
{

    private static Image results ;
    private Connection connect = new DatabaseConnection().getConnect();

    @Override
    public Image findImageByImageName(final String imageFileName)
    {
        QueryHelper helper = new QueryHelper(connect);
        return helper.getImageByName(imageFileName);
    }

    @Override
    public void saveImage(final Image image)
    {
        QueryHelper helper = new QueryHelper(connect);
        helper.saveImage(image);
    }
}
