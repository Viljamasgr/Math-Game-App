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

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

//This test is for testing the Medium questions and inputting a username at the end
@RunWith(AndroidJUnit4.class)
public class MediumQuizTest {
    //Starts on the Quiz Activity
    @Rule
    public ActivityScenarioRule<QuizActivity> activityRule =
            new ActivityScenarioRule<>(QuizActivity.class);

    @Test
    public void testQuizEasy() {
        //Selecting the "Medium" option
        Espresso.onView(ViewMatchers.withId(R.id.btnMedium))
                .perform(ViewActions.click());

        //Waits for the quiz to complete
        for (int i = 0; i < 10; i++) {
            //Answers the current question "10" for each question
            Espresso.onView(ViewMatchers.withId(R.id.Input))
                    .perform(ViewActions.typeText("10"))
                    .perform(ViewActions.closeSoftKeyboard());
            Espresso.onView(ViewMatchers.withId(R.id.btnSubmit))
                    .perform(ViewActions.click());

            //Checks that the "Correct" or "Incorrect" message is displayed
            Espresso.onView(ViewMatchers.withId(R.id.Result))
                    .check(matches(isDisplayed()));

            //Clicks the "Next" button to move on to the next question
            Espresso.onView(ViewMatchers.withId(R.id.btnSubmit))
                    .perform(ViewActions.click());
        }

        //Checks that the username input field is displayed
        Espresso.onView(ViewMatchers.withId(R.id.username))
                .check(matches(isDisplayed()));

        //Entering a username into the input field
        Espresso.onView(ViewMatchers.withId(R.id.username))
                .perform(ViewActions.typeText("TestUser"))
                .perform(ViewActions.closeSoftKeyboard());

        //Clicking the submit button
        Espresso.onView(ViewMatchers.withId(R.id.btnSubmit))
                .perform(ViewActions.click());
    }
}