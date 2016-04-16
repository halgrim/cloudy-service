package pl.cloudy.service;


import pl.cloudy.models.ActionResult;

import org.joda.time.DateTime;

import java.sql.Date;
import java.util.List;


public interface ActionObjectService
{
    List<ActionResult> findAllResults();

    List<ActionResult> findTestRunResults(int testRunId);

    List<ActionResult> findAllResultsForAGivenDay(DateTime date);

    List<ActionResult> findAllResultsByTestID(int testID);

    List<ActionResult> findTestResultsForAGivenDay(DateTime date, int testID);

    void saveActionResult(ActionResult result);

    List<Date> findAllDatesWithTestRunResults();
}