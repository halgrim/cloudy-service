package pl.cloudy.database;

public class Queries
{

    //CREATE TABLE tests(      testId MEDIUMINT NOT NULL AUTO_INCREMENT,     testName VARCHAR(512) NOT NULL,      PRIMARY KEY ( testId )        );
    //CREATE TABLE testRun(    testRunId MEDIUMINT NOT NULL AUTO_INCREMENT,  testId MEDIUMINT NOT NULL,   startTimestamp TIMESTAMP NOT NULL,   PRIMARY KEY ( testRunId )         );
    //CREATE TABLE images(     imageId MEDIUMINT NOT NULL AUTO_INCREMENT,     imageFileName VARCHAR(512) NOT NULL, image MEDIUMBLOB,      PRIMARY KEY ( imageId )         );
    //CREATE TABLE actions(    id MEDIUMINT NOT NULL AUTO_INCREMENT ,  testRunId MEDIUMINT NOT NULL, actionType VARCHAR(64) NOT NULL, className VARCHAR(128) NOT NULL, methodName VARCHAR(128) NOT NULL, arguments VARCHAR(256) NOT NULL, startTimestamp TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),  finishTimestamp TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),     returnValue VARCHAR(256) NOT NULL, screenshotBefore VARCHAR(512) NOT NULL  , PRIMARY KEY ( id ) );

    //SELECT * FROM tests; SELECT * FROM testRun; SELECT * FROM images; SELECT * FROM actions;
    //SELECT * FROM tests; SELECT * FROM testRun; SELECT * FROM actions;

    //TRUNCATE TABLE tests; TRUNCATE TABLE testRun; TRUNCATE TABLE images; TRUNCATE TABLE actions;

    //INSERT INTO tests (testName) SELECT * FROM (SELECT ? as blaTemp) AS tmp WHERE NOT EXISTS ( SELECT testName FROM tests WHERE testName = blaTemp ) LIMIT 1;


    public static final String retrieveTestIdByTestName = "SELECT testId " +
            "FROM tests " +
            "WHERE testName = ? ;";


    public static final String retrieveAllTestReportData = "SELECT * " +
            "FROM actions ;" ;

    public static final String retrieveTestRunReportData = "SELECT * " +
            "FROM actions " +
            "WHERE testRunId = ? ;";


    //SELECT * FROM actions WHERE DATE(startTimestamp) = '2016-04-08' ORDER BY testRunId, startTimestamp;

    public static final String retrieveAllTestReportForAGivenDate = "SELECT * " +
            "FROM actions " +
            "WHERE DATE(startTimestamp) = ? ;";

    //SELECT * FROM testRun INNER JOIN actions ON testRun.testRunId = actions.testRunId WHERE DATE(startTimestamp) = '2016-04-08' AND testRun.testId = 1;
    //SELECT * FROM testRun INNER JOIN actions ON testRun.testRunId = actions.testRunId WHERE DATE(actions.startTimestamp) = '2016-04-11' AND testRun.testId = 1;
    public static final String retrieveSingleTestReportsForAGivenDate = "SELECT * " +
        "FROM testRun " +
        "INNER JOIN actions ON testRun.testRunId = actions.testRunId " +
        "WHERE DATE(actions.startTimestamp) = ? " +
        "AND testRun.testId = ? ;";

    //SELECT * FROM testRun INNER JOIN actions ON testRun.testRunId = actions.testRunId WHERE testRun.testId = 1;

    public static final String retrieveAllReportForAGivenTestId = "SELECT * " +
            "FROM testRun " +
            "INNER JOIN actions ON testRun.testRunId = actions.testRunId " +
            "WHERE testRun.testId = ? ;";


    public static final String retrieveImageByName = "SELECT image " +
            "FROM images " +
            "WHERE imageFileName = ? ;";

    public static final String createTestIdIfDoNotExist = "INSERT INTO tests (testName) " +
            "SELECT * FROM (SELECT ? as blaTemp) AS tmp " +
            "WHERE NOT EXISTS ( " +
            "SELECT testName FROM tests WHERE testName = blaTemp " +
            ") LIMIT 1 ";

    public static String saveTestRunStartData = "INSERT INTO testRun (testId, startTimestamp) " +
            " values (?, ?);";

    public static String retrieveAllDatesWhichHaveTestRunResults;
}