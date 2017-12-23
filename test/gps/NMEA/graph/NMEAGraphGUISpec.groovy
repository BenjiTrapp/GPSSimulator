package gps.NMEA.graph

import annotations.End2EndTest
import org.assertj.swing.core.GenericTypeMatcher
import org.assertj.swing.core.MouseButton
import org.assertj.swing.fixture.FrameFixture
import spock.lang.Requires
import spock.lang.Specification

import static org.assertj.swing.finder.JFileChooserFinder.*
import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.*
import javax.swing.JFileChooser

class NMEAGraphGUISpec extends Specification{
    private static final def TITLE = "NMEA-GGA Sentences Graph Imaging Tool"
    private static final int TIMEOUT = 5000
    private static final def REFRESH_BUTTON_NAME = "Refresh Image"
    private static final def OPEN_BUTTON_NAME = "Open"
    private static final def LOG_BASE_DIR = ".\\log"
    private static final def LABEL_STATUSBAR = "STATUSBAR"
    private static final def STATUS_TEXT = "Status: "
    private static final def OPEN_BUTTON = "OPEN"
    private static final def CHOOSER = "CHOOSER"
    private static final def REPAINT_BUTTON = "REPAINT"

    private def window

    def setup() {
        given:
        window = new FrameFixture(new NMEAGraphGUI().getFrame())
    }

    def cleanup() {
        window.cleanUp()
    }

    @End2EndTest
    @Requires({ os.windows })
    def shouldHaveCorrectTitle() {
        when:
        window.show()

        then:
        window.requireTitle(TITLE)
        window.requireVisible()
        window.label(LABEL_STATUSBAR).requireVisible().requireText(STATUS_TEXT)
    }

    @End2EndTest
    @Requires({ os.windows })
    def "shouldRunFileChooserAfterClickOnOpen"() {
        when:
        window.show()
        window.button(OPEN_BUTTON).requireText(OPEN_BUTTON_NAME).click(MouseButton.LEFT_BUTTON)

        then:
        that findFileChooser(CHOOSER).withTimeout(TIMEOUT), is(not(null))
    }

    @End2EndTest
    @Requires({ os.windows })
    def "Should run fileChooser and use the log base directory as default directory"() {
        when:
        window.show()
        def matcher = new GenericTypeMatcher<JFileChooser>(JFileChooser.class) {
            protected boolean isMatching(JFileChooser fileChooser) {
                return fileChooser.getCurrentDirectory().getAbsolutePath() == LOG_BASE_DIR
            }
        }

        window.button(OPEN_BUTTON)
                .requireText(OPEN_BUTTON_NAME)
                .click(MouseButton.LEFT_BUTTON)

        then:
        that findFileChooser(matcher).withTimeout(TIMEOUT), is(not(null))
    }

    @End2EndTest
    @Requires({ os.windows })
    def "Should repaint the GUI after it was maximized"() {
        when:
        window.show()
        window.button(REPAINT_BUTTON).requireText(REFRESH_BUTTON_NAME).click(MouseButton.LEFT_BUTTON)

        then:
        window.maximize().requireVisible()
    }
}
