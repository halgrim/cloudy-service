package pl.cloudy.service;


import org.joda.time.DateTime;

public interface TestService
{
    int getTestIdByTestName(String testName);

    int saveTestRunStart(int testId, DateTime startTimestamp);
}

