package gps.NMEA.graph;

import annotations.End2EndTest;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.MouseButton;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.finder.JFileChooserFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NMEAGraphGUITest {
    private static final String TITLE = "NMEA-GGA Sentences Graph Imaging Tool";
    private static final int TIMEOUT = 5000;
    private static final String REFRESH_BUTTON_NAME = "Refresh Image";
    private static final String OPEN_BUTTON_NAME = "Open";
    private static final String LOG_BASE_DIR = ".\\log";

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
    @End2EndTest
    public void shouldHaveCorrectTitle() {
        // when
        window.show();

        // then
        window.requireTitle(TITLE);
        window.requireVisible();
        window.label("STATUSBAR").requireVisible()
                                        .requireText("Status: ");
    }

    @Test
    @End2EndTest
    public void shouldRunFileChooserAfterClickOnOpen() {
        // when
        window.show();

        // then
        window.button("OPEN")
              .requireText(OPEN_BUTTON_NAME)
              .click(MouseButton.LEFT_BUTTON);

        JFileChooserFinder tmp = JFileChooserFinder.findFileChooser("CHOOSER")
                                                   .withTimeout(TIMEOUT);
        assertNotNull(tmp);
    }

    @Test
    @End2EndTest
    public void shouldRunFileChooserAfterAtCorrectDirectory() {
        // when
        window.show();
        GenericTypeMatcher<JFileChooser> matcher = new GenericTypeMatcher<JFileChooser>(JFileChooser.class) {
            protected boolean isMatching(JFileChooser fileChooser) {
                return fileChooser.getCurrentDirectory()
                                  .getAbsolutePath()
                                  .equals(LOG_BASE_DIR);
            }
        };

        // then
        window.button("OPEN")
              .requireText(OPEN_BUTTON_NAME)
              .click(MouseButton.LEFT_BUTTON);

        JFileChooserFinder.findFileChooser(matcher)
                          .withTimeout(TIMEOUT);
    }

    @Test
    @End2EndTest
    public void shouldRepaintAndMaximize() {
        // when
        window.show();

        // then
        window.button("REPAINT")
              .requireText(REFRESH_BUTTON_NAME)
              .click(MouseButton.LEFT_BUTTON);

        window.maximize().requireVisible();
    }
}