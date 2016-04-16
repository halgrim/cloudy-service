package pl.cloudy.service;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import pl.cloudy.database.DatabaseConnection;
import pl.cloudy.models.ActionObject;
import pl.cloudy.models.ActionResult;
import pl.cloudy.serviceHelper.DummyDataHelper;
import pl.cloudy.database.QueryHelper;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

@Service("ActionObjectService")
public class ActionObjectServiceImpl implements ActionObjectService
{

	private static List<ActionObject> results ;
	private Connection connect = new DatabaseConnection().getConnect();

	static{
		results = DummyDataHelper.populateDummyActions();
	}

	@Override
	public List<ActionResult> findAllResults()
	{
		QueryHelper helper = new QueryHelper(connect);
		return helper.getAllTestResults();
	}

	@Override
	public List<ActionResult> findTestRunResults(final int testRunId)
	{
		QueryHelper helper = new QueryHelper(connect);
		return helper.getTestRunResults(testRunId);
	}

	@Override
	public List<ActionResult> findAllResultsForAGivenDay(final DateTime date)
	{
		QueryHelper helper = new QueryHelper(connect);
		return helper.getAllResultsForAGivenDate(date);
	}

	@Override
	public List<ActionResult> findTestResultsForAGivenDay(final DateTime date, final int testID)
	{
		QueryHelper helper = new QueryHelper(connect);
		return helper.getSingleTestResultsForAGivenDay(date, testID);
	}


	@Override
	public List<ActionResult> findAllResultsByTestID(final int testID)
	{
		QueryHelper helper = new QueryHelper(connect);
		return helper.getTestResultsForAGivenTestID(testID);
	}

	@Override
	public void saveActionResult(final ActionResult result)
	{
		QueryHelper helper = new QueryHelper(connect);
		helper.saveActionResult(result);
	}

	@Override
	public List<Date> findAllDatesWithTestRunResults()
	{
		QueryHelper helper = new QueryHelper(connect);
		return helper.getDatesWhichHaveTestRunResults();
	}

}
