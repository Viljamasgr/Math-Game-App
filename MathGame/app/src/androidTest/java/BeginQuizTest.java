import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import com.example.mathgame.MainActivity;
import com.example.mathgame.R;

//This test checks if the begin quiz button works
@RunWith(AndroidJUnit4.class)
public class BeginQuizTest {
    //Test begins on the Main Activity
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testQuizButton() {
        //Clicks on the quiz button
        Espresso.onView(ViewMatchers.withId(R.id.btnBegin)).perform(ViewActions.click());
        //Checks if the QuizActivity is launched
        Espresso.onView(withId(android.R.id.content)).check(matches(isDisplayed()));
    }
}