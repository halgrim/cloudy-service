package pl.cloudy.conroller;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.cloudy.models.ActionResult;
import pl.cloudy.models.Image;
import pl.cloudy.service.ActionObjectService;
import pl.cloudy.service.ImageService;

import java.sql.Date;
import java.util.List;


@RestController
public class ResultsController
{
	@Autowired
	ActionObjectService actionObjectService;

	@Autowired
	ImageService imageService;


	//-------------------Retrieve All Test Results Ever---------------------------------------------------------------

	@RequestMapping(value = "/results", method = RequestMethod.GET)
	public ResponseEntity<List<ActionResult>> listAllTestResults()
	{
		List<ActionResult> allTestsResults = actionObjectService.findAllResults();
		if (allTestsResults.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(allTestsResults, HttpStatus.OK);
	}

	//-------------------Retrieve All Dates containing test run results-----------------------------------------------

	@RequestMapping(value = "/results/dates", method = RequestMethod.GET)
	public ResponseEntity<List<Date>> getDates()
	{
		List<Date> allDatesWithTestRunResultsResults = actionObjectService.findAllDatesWithTestRunResults();
		if (allDatesWithTestRunResultsResults.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(allDatesWithTestRunResultsResults, HttpStatus.OK);
	}

	//-------------------Retrieve All Tests Results For a Given Day---------------------------------------------------

	@RequestMapping(value = "/results/{date}", method = RequestMethod.GET)
	public ResponseEntity<List<ActionResult>> getTestForAGivenDay(
			@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") DateTime date)
	{
		List<ActionResult> allTestsResults = actionObjectService.findAllResultsForAGivenDay(date);
		if (allTestsResults.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(allTestsResults, HttpStatus.OK);
	}



	//-------------------Retrieve All Results For a Given test and Date-----------------------------------------------

	@RequestMapping(value = "/results/{date}/{testID}", method = RequestMethod.GET)
	public ResponseEntity<List<ActionResult>> getTestFromAGivenDayAndDate(
			@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") DateTime date, @PathVariable("testID") int testID)
	{
		List<ActionResult> allTestsResults = actionObjectService.findTestResultsForAGivenDay(date, testID);
		if (allTestsResults.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(allTestsResults, HttpStatus.OK);
	}

	//-------------------Retrieve All Results For a Given test run--------------------------------------------------------

	@RequestMapping(value = "/results/test/{testRunId}", method = RequestMethod.GET)
	public ResponseEntity<List<ActionResult>> getTestFromAGivenDay(@PathVariable("testRunId") int testRunId)
	{
		List<ActionResult> testRunResults = actionObjectService.findTestRunResults(testRunId);
		if (testRunResults.isEmpty())
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Access-Control-Allow-Origin", "*");


		return new ResponseEntity<>(testRunResults, responseHeaders, HttpStatus.OK);
	}

	//-------------------Retrieve Images For a Given test run---------------------------------------------------------

	@RequestMapping(value = "/images/{imageFileName}", method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<InputStreamResource>  getImagesByName(@PathVariable("imageFileName") String imageFileName)
	{
		Image image = imageService.findImageByImageName(imageFileName);

		return ResponseEntity.ok()
                .contentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE))
                .body(new InputStreamResource(image.getImage()));
	}

}
