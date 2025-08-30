import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mathgame.QuizActivity;
import com.example.mathgame.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//this tests the 4 options the user has to select the difficulty of the test
@RunWith(AndroidJUnit4.class)
public class SelectionTest {

    @Rule
    public ActivityScenarioRule<QuizActivity> activityRule =
            new ActivityScenarioRule<>(QuizActivity.class);
    //Testing the Easy Button
    @Test
    public void testEasyButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnEasy))
                .perform(ViewActions.click());
    }
    //Testing the Medium Button
    @Test
    public void testMediumButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnMedium))
                .perform(ViewActions.click());
    }
    //Testing the Hard Button
    @Test
    public void testHardButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnHard))
                .perform(ViewActions.click());
    }
    //Testing the Very Hard Button
    @Test
    public void testVeryHardButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnVeryHard))
                .perform(ViewActions.click());
    }
}