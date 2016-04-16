package pl.cloudy.database;

import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class TestQueryHelper
{
    private Connection connect;

    public TestQueryHelper(final Connection connect)
    {

        this.connect = connect;
    }

    public int getTestIdByTestName(final String testName)
    {
        int testId = 0;
        try
        {

            PreparedStatement stmt = connect.prepareStatement(Queries.retrieveTestIdByTestName);
            stmt.setString(1, testName);

            ResultSet resultSet = stmt.executeQuery();

            int recounts = 0;
            if (resultSet.last()) {
                recounts = resultSet.getRow();
                resultSet.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }

            if (recounts != 1)
            {
                throw new SQLException("Found more than one test id");
            }

            if (resultSet.next())
            {
                testId = resultSet.getInt("testId");

            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return testId;

    }

    public void createTestIdIfDoNotExist(final String testName)
    {
        try
        {

            PreparedStatement stmt = connect.prepareStatement(Queries.createTestIdIfDoNotExist, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, testName);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated != 1 && rowsUpdated != 0)
            {
                throw new SQLException("This query should return 1 or 0");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();

        }

    }

    public int saveTestRunStart(final int testId, final DateTime startTimestamp)
    {
        int testRunId = 0;
        try
        {

            PreparedStatement preparedStmt = connect.prepareStatement(Queries.saveTestRunStartData, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setInt(1, testId);
            preparedStmt.setTimestamp(2, new Timestamp(startTimestamp.getMillis()));
            int rowsUpdated = preparedStmt.executeUpdate();

            if (rowsUpdated != 1)
            {
                throw new SQLException("Saving image query should return 1 row affected");
            }

            try (ResultSet generatedKeys = preparedStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    testRunId = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }


        } catch (SQLException e)
        {
            e.printStackTrace();

        }

        return testRunId;
    }
}
