package nz.co.sample.googleScreen;

import nz.co.sample.Element;
import org.openqa.selenium.By;

public class GoogleLandingScreen {
    private Element searchString;
    private Element searchButton;
    private Element resultString;

    public GoogleLandingScreen() {
        searchString = new Element(By.className("YacQv"));
        searchButton = new Element(By.name("btnK"));
        resultString = new Element(By.className("PZPZlf ssJ7i B5dxMb"));
    }

    public Element getSearchString() { return searchString; }
    public Element getSearchButton() { return searchButton; }
    public Element getResultString() { return resultString; }
}
