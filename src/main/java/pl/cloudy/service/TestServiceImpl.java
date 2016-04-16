package pl.cloudy.service;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import pl.cloudy.database.DatabaseConnection;
import pl.cloudy.database.TestQueryHelper;

import java.sql.Connection;

@Service("TestService")
public class TestServiceImpl implements TestService
{
    private Connection connect = new DatabaseConnection().getConnect();

    @Override
    public int getTestIdByTestName(final String testName)
    {
        TestQueryHelper helper = new TestQueryHelper(connect);
        helper.createTestIdIfDoNotExist(testName);

        return helper.getTestIdByTestName(testName);
    }

    @Override
    public int saveTestRunStart(final int testId, final DateTime startTimestamp)
    {
        TestQueryHelper helper = new TestQueryHelper(connect);
        return helper.saveTestRunStart(testId, startTimestamp);
    }
}
