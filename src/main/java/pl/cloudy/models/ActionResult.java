package pl.cloudy.models;

import org.joda.time.DateTime;

public interface ActionResult
{

    Integer getTestRunId();

    String getActionType();

    String getClassName();

    String getMethodName();

    String getArguments();

    DateTime getStartTime();

    DateTime getFinishTime();

    String getReturnValue();

    String getScreenshotBefore();

    ActionObject setTestRunId(Integer testRunId);

    ActionObject setActionType(String actionType);

    ActionObject setClassName(String className);

    ActionObject setMethodName(String methodName);

    ActionObject setArguments(String arguments);

    ActionObject setStartTime(DateTime startTime);

    ActionObject setFinishTime(DateTime finishTime);

    ActionObject setReturnValue(String returnValue);

    ActionObject setScreenshotBefore(String screenshotBefore);
}
