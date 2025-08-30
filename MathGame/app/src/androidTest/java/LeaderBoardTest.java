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

//This test checks if the leaderboard button works and opens the correct page then scrolls to the bottom and clicks on the go back button
//which then checks if they are brought back to the main activity
@RunWith(AndroidJUnit4.class)
public class LeaderBoardTest {
    //Starts on the Main Activity
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testLeaderboardButton() {
        //Clicks on the leaderboard button
        Espresso.onView(ViewMatchers.withId(R.id.btnLeaderboard)).perform(ViewActions.click());
        //Checks if the LeaderboardActivity is launched
        Espresso.onView(withId(android.R.id.content)).check(matches(isDisplayed()));
        //Clicks on the Go Back button
        Espresso.onView(withId(R.id.btnGoBack)).perform(ViewActions.scrollTo(), ViewActions.click());
        //Checks if the MainActivity is displayed again
        Espresso.onView(withId(android.R.id.content)).check(matches(isDisplayed()));
    }
}
