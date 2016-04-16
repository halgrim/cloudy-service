package pl.cloudy.database;

import org.joda.time.DateTime;
import pl.cloudy.models.ActionObject;
import pl.cloudy.models.ActionResult;
import pl.cloudy.models.Image;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class QueryHelper
{
    private Connection connect;

    public QueryHelper(final Connection connect)
    {

        this.connect = connect;
    }

    public List<ActionResult> getAllTestResults()
    {
        List<ActionResult> actionObjects = new ArrayList<>();

        try
        {
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(Queries.retrieveAllTestReportData);
            actionObjects = getActionResults(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return actionObjects;
    }

    public List<ActionResult> getTestRunResults(int testRunId)
    {
        List<ActionResult> actionObjects = new ArrayList<>();

        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement(Queries.retrieveTestRunReportData);
            preparedStmt.setInt(1, testRunId);
            ResultSet rs = preparedStmt.executeQuery();
            actionObjects = getActionResults(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return actionObjects;
    }

    public List<ActionResult> getAllResultsForAGivenDate(final DateTime date)
    {
        List<ActionResult> actionObjects = new ArrayList<>();

        try
        {
            Date bl =new Date( date.getMillis() );
            PreparedStatement preparedStmt = connect.prepareStatement(Queries.retrieveAllTestReportForAGivenDate);
            preparedStmt.setDate(1,bl );
            ResultSet rs = preparedStmt.executeQuery();
            actionObjects = getActionResults(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return actionObjects;

    }

    public List<ActionResult> getSingleTestResultsForAGivenDay(final DateTime date, final int testID)
    {
        List<ActionResult> actionObjects = new ArrayList<>();

        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement(Queries.retrieveSingleTestReportsForAGivenDate);
            preparedStmt.setDate(1, new Date( date.getMillis() ));
            preparedStmt.setInt(2, testID);
            ResultSet rs = preparedStmt.executeQuery();
            actionObjects = getActionResults(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return actionObjects;

    }
    public List<ActionResult> getTestResultsForAGivenTestID(final int testID)
    {
        List<ActionResult> actionObjects = new ArrayList<>();

        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement(Queries.retrieveAllReportForAGivenTestId);
            preparedStmt.setInt(1, testID);
            ResultSet rs = preparedStmt.executeQuery();
            actionObjects = getActionResults(rs);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return actionObjects;
    }

    private List<ActionResult> getActionResults(ResultSet rs) throws SQLException
    {
        List<ActionResult> actionObjects = new ArrayList<>();

        while (rs.next())
        {
            ActionResult actionObject = new ActionObject();
            actionObject
                    .setTestRunId(rs.getInt("testRunId")) //
                    .setClassName(rs.getString("className")) //
                    .setMethodName(rs.getString("methodName")) //
                    .setArguments(rs.getString("arguments")) //
                    .setStartTime(new DateTime(rs.getDate("startTimestamp"))) //
                    .setFinishTime(new DateTime(rs.getDate("finishTimestamp"))) //
                    .setReturnValue(rs.getString("returnValue")) //
                    .setScreenshotBefore(rs.getString("screenshotBefore")); //

            actionObjects.add(actionObject);

        }

        return actionObjects;
    }


    public Image getImageByName(final String imageFileName)
    {
        Image image = new Image();

        try
        {
            //PreparedStatement preparedStmt = connect.prepareStatement(Queries.retrieveImageByName);
            //preparedStmt.setString(1, imageFileName);

            PreparedStatement preparedStmt = connect.prepareStatement("select image from images where testRunId = 50;");


            ResultSet resultSet = preparedStmt.executeQuery();

            if (resultSet.next())
            {
                Blob imageBlob = resultSet.getBlob("image");
                InputStream binaryStream = imageBlob.getBinaryStream(1, imageBlob.length());

                image.setImage(binaryStream);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return image;
    }

    public void saveImage(final Image image)
    {
        String query = "INSERT INTO images (imageFileName, image)" + " values (?, ?)";

        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.setString(1, image.getFileName());
            preparedStmt.setBlob(2, image.getImage());
            int rowsUpdated = preparedStmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("No rows updated during saving image");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();

        }

    }

    public void saveActionResult(final ActionResult action)
    {
        String query = " insert into actions (testRunId, actionType, className, methodName, arguments, startTimestamp, finishTimestamp, returnValue, screenshotBefore )"


                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.setInt(1, action.getTestRunId());
            preparedStmt.setString(2, action.getActionType());
            preparedStmt.setString(3, action.getClassName());
            preparedStmt.setString(4, action.getMethodName());
            preparedStmt.setString(5, action.getArguments());
            preparedStmt.setTimestamp(6, new Timestamp(action.getStartTime().getMillis()));
            preparedStmt.setTimestamp(7, new Timestamp(action.getFinishTime().getMillis()));
            preparedStmt.setString(8, action.getReturnValue());
            preparedStmt.setString(9, action.getScreenshotBefore());

            int rowsUpdated = preparedStmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("No rows updated during saving ActionResult");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<Date> getDatesWhichHaveTestRunResults()
    {
        List<Date> datesWhichHaveTestRunResults = new ArrayList<>();

        try
        {
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(Queries.retrieveAllDatesWhichHaveTestRunResults);
            while (rs.next())
            {
                datesWhichHaveTestRunResults.add(rs.getDate("startTimestamp"));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return datesWhichHaveTestRunResults;
    }
}
