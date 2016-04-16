package pl.cloudy.conroller;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.cloudy.models.ActionObject;
import pl.cloudy.models.ActionResult;
import pl.cloudy.models.Image;
import pl.cloudy.service.ActionObjectService;
import pl.cloudy.service.ImageService;
import pl.cloudy.service.TestService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
public class LogTestDataController
{
    @Autowired
    ActionObjectService actionObjectService;

    @Autowired
    TestService testService;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/initializeTestRun", method = RequestMethod.POST)
    public int initializeTestRun(
            @RequestParam("testName") String testName, @RequestParam("startTimestamp") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") DateTime startTimestamp)
    {
        int testId = testService.getTestIdByTestName(testName);
        int testRunId = testService.saveTestRunStart(testId, startTimestamp);
        return testRunId;
    }


    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public ResponseEntity uploadImage(
            @RequestParam("imageFileName") String imageFileName, @RequestParam("imageStream") MultipartFile file) throws IOException
    {
        Image image = new Image();

        if (file.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
        {
            image.setFileName(imageFileName);
            image.setImage(new ByteArrayInputStream(file.getBytes()));

            imageService.saveImage(image);

            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/uploadAction", method = RequestMethod.POST)
    public ResponseEntity uploadAction(
            @RequestParam("testRunId") int testRunId,
            @RequestParam("actionType") String actionType,
            @RequestParam("className") String className,
            @RequestParam("methodName") String methodName,
            @RequestParam("arguments") String arguments,
            @RequestParam("startTimestamp") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") DateTime startTimestamp,
            @RequestParam("finishTimestamp") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") DateTime finishTimestamp,
            @RequestParam("returnValue") String returnValue,
            @RequestParam("screenshotBefore") String screenshotBefore
    )
    {
        ActionResult result = new ActionObject();
        result
                .setTestRunId(testRunId)
                .setActionType(actionType)
                .setClassName(className)
                .setMethodName(methodName)
                .setArguments(arguments)
                .setStartTime(startTimestamp)
                .setFinishTime(finishTimestamp)
                .setReturnValue(returnValue)
                .setScreenshotBefore(screenshotBefore);

        actionObjectService.saveActionResult(result);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}