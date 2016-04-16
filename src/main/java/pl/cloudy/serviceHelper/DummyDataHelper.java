package pl.cloudy.serviceHelper;

import org.joda.time.DateTime;
import pl.cloudy.models.ActionObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DummyDataHelper
{
    private static final AtomicInteger counter = new AtomicInteger();

    public static List<ActionObject> populateDummyActions(){
        List<ActionObject> actions = new ArrayList<>();

        ActionObject actionObject = new ActionObject();
        actionObject
                .setTestRunId(DummyDataHelper.counter.incrementAndGet()) //
                .setClassName("some class") //
                .setMethodName("some method") //
                .setArguments("some arguments") //
                .setStartTime(new DateTime()) //
                .setFinishTime(new DateTime()) //
                .setReturnValue("some returnValue") //
                .setScreenshotBefore("some screenshotBefore"); //

        actions.add(actionObject);

        return actions;
    }
}
