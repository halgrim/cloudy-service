package pl.cloudy.models;

import org.joda.time.DateTime;

public class ActionObject implements ActionResult
{
    private Integer testRunId;
    private String className;
    private String methodName;
    private String arguments;
    private DateTime startTime;
    private DateTime finishTime;
    private String returnValue;
    private String screenshotBefore;
    private String actionType;

    public Integer getTestRunId()
    {
        return testRunId;
    }

    @Override
    public String getActionType()
    {
        return actionType;
    }

    public String getClassName()
    {
        return className;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public String getArguments()
    {
        return arguments;
    }

    public DateTime getStartTime()
    {
        return startTime;
    }

    public DateTime getFinishTime()
    {
        return finishTime;
    }

    public String getReturnValue()
    {
        return returnValue;
    }

    public String getScreenshotBefore()
    {
        return screenshotBefore;
    }

    public ActionObject setTestRunId(final Integer testRunId)
    {
        this.testRunId = testRunId;
        return this;
    }

    public ActionObject setClassName(final String className)
    {
        this.className = className; return this;
    }

    public ActionObject setMethodName(final String methodName)
    {
        this.methodName = methodName; return this;
    }

    public ActionObject setArguments(final String arguments)
    {
        this.arguments = arguments;
        return this;
    }

    public ActionObject setStartTime(final DateTime startTime)
    {
        this.startTime = startTime;
        return this;
    }

    public ActionObject setFinishTime(final DateTime finishTime)
    {
        this.finishTime = finishTime;
        return this;
    }

    public ActionObject setReturnValue(final String returnValue)
    {
        this.returnValue = returnValue;
        return this;
    }


    public ActionObject setScreenshotBefore(final String screenshotBefore)
    {
        this.screenshotBefore = screenshotBefore;
        return this;
    }

    public ActionObject setActionType(final String actionType)
    {
        this.actionType = actionType;
        return this;
    }
}