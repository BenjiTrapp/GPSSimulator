package gps.NMEA.graph;

import org.assertj.swing.core.MouseButton;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.finder.JFileChooserFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JFileChooserFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;

class NMEAGraphGUITest {
    private static final String TITLE = "NMEA-GGA Sentences Graph Imaging Tool";
    private static final int TIMEOUT = 5000;
    private String REFRESH_BUTTON_NAME = "Refresh Image";
    private static final String OPEN_BUTTON_NAME = "Open";

    private FrameFixture window;

    @BeforeEach
    protected void onSetUp() {
        // given
        NMEAGraphGUI frame = GuiActionRunner.execute(NMEAGraphGUI::new);
        window = new FrameFixture(frame.getFrame());
    }

    @AfterEach
    public void tearDownTest() {
        window.cleanUp();
    }


    @Test
    @Ignore("Not finished yet")
    @Tag("E2ETest")
    public void shouldHaveCorrectTitle() {
        // when
        window.show();

        // then
        window.requireTitle(TITLE);
        window.label("STATUSBAR").requireVisible();
        window.label("STATUSBAR").requireText("Status: ");
    }

    @Test
    @Ignore("Not finished yet")
    @Tag("E2ETest")
    public void shouldRunFileChooserAfterClickOnOpen() {
        // when
        window.show();

        // then
        window.button("OPEN").requireText(OPEN_BUTTON_NAME).click(MouseButton.LEFT_BUTTON);
        JFileChooserFinder.findFileChooser("CHOOSER").withTimeout(TIMEOUT);
    }
}