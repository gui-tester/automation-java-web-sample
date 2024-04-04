package nz.co.sample;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Report {

    private Report() {}

    public static void generateReports(String project, String products, String coverage) throws IOException {
        //Which stack are we in
        String build = "BUILD_NUMBER";
        String outputDirectory = "target/";
        String cucumberReportsDirectory = outputDirectory + "cucumber";
        String stack = (System.getenv("env") == null || System.getenv("env").isEmpty())?System.getenv("ENV"):System.getenv("env");
        String environment = stack + ":" + InetAddress.getLocalHost().getHostName();

        File reportOutputDirectory = new File(outputDirectory);

        //Search for all the cucumber json result files.
        List<String> jsonFiles = new ArrayList<>();
        File cucumberInputDirectory = new File( cucumberReportsDirectory);
        File[] cucumberFiles = cucumberInputDirectory.listFiles(
                pathname -> pathname.getName().toLowerCase().endsWith("test-response.json"));

        for (File file: cucumberFiles )
        {
            jsonFiles.add( cucumberReportsDirectory + "/" + file.getName() );
        }

        String buildNumber = (System.getenv(build) == null || System.getenv(build).isEmpty())?"-":System.getenv(build);
        String projectName = project;

        boolean runWithJenkins = false;
        boolean parallelTesting = false;

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        // optional configuration
        //configuration.setParallelTesting(parallelTesting);
        configuration.addPresentationModes(PresentationMode.PARALLEL_TESTING);
        //configuration.setRunWithJenkins(runWithJenkins);
        configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
        configuration.setBuildNumber(buildNumber);

        // additional metadata presented on main page
        configuration.addClassifications("Environment", environment);
        configuration.addClassifications("Products", products);
        configuration.addClassifications("Coverage", coverage);
        configuration.addClassifications("Instructions", "Click on each feature below for more details");
        configuration.addClassifications("For PASSED results", "Actual Result is As Expected");

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }


}