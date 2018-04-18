package com.developer.nennenwodo.medmanager;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


import com.developer.nennenwodo.medmanager.account.AccountActivity;
import com.developer.nennenwodo.medmanager.monthlycategory.SingleCategoryActivity;
import com.developer.nennenwodo.medmanager.contact.ContactActivity;
import com.developer.nennenwodo.medmanager.faqs.FAQActivity;
import com.developer.nennenwodo.medmanager.medication.EditMedicationActivity;
import com.developer.nennenwodo.medmanager.medication.SingleMedicationActivity;
import com.developer.nennenwodo.medmanager.medication.NewMedicationActivity;
import com.developer.nennenwodo.medmanager.profile.ProfileActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;



@RunWith(AndroidJUnit4.class)
public class DashboardActivityBasicTest {

    @Rule
    public ActivityTestRule<DashboardActivity> mActivityTestRule = new ActivityTestRule<>(DashboardActivity.class);

    @Test
    public void clickAddButton_LaunchesAddMedicationIntent(){
        //1. find the view
        //2. perform the action on the view
        Intents.init();
        onView((withId(R.id.action_add_medication))).perform(click());


        //3. check if the view does what we expected
        intended(hasComponent(NewMedicationActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void clickHomeButton_LaunchesHomeFragment(){
        //1. find the view
        //2. perform the action on the view
        onView((withId(R.id.navigation_home))).perform(click());


        //3. check if the view does what we expected
        onView(withId(R.id.home_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void clickCategoryButton_LaunchesCategoryFragment(){
        //1. find the view
        //2. perform the action on the view
        onView((withId(R.id.navigation_categorize))).perform(click());


        //3. check if the view does what we expected
        onView(withId(R.id.monthly_category_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void clickSettingsButton_LaunchesSettingsFragment(){
        //1. find the view
        //2. perform the action on the view
        onView((withId(R.id.navigation_user))).perform(click());


        //3. check if the view does what we expected
        onView(withId(R.id.settings_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void clickProfileLayout_LaunchesProfileActivity(){

        //1. find the view
        //2. perform the action on the view
        onView((withId(R.id.navigation_user))).perform(click());

        Intents.init();

        onView((withId(R.id.relative_layout_profile))).perform(click());


        //3. check if the view does what we expected
        intended(hasComponent(ProfileActivity.class.getName()));

        Intents.release();

    }

    @Test
    public void clickAccountLayout_LaunchesAccountActivity(){

        //1. find the view
        //2. perform the action on the view
        onView((withId(R.id.navigation_user))).perform(click());

        Intents.init();

        onView((withId(R.id.relative_layout_account))).perform(click());


        //3. check if the view does what we expected
        intended(hasComponent(AccountActivity.class.getName()));

        Intents.release();

    }

    @Test
    public void clickFAQLayout_LaunchesFAQActivity(){

        //1. find the view
        //2. perform the action on the view
        onView((withId(R.id.navigation_user))).perform(click());

        Intents.init();

        onView((withId(R.id.relative_layout_faqs))).perform(click());


        //3. check if the view does what we expected
        intended(hasComponent(FAQActivity.class.getName()));

        Intents.release();

    }

    @Test
    public void clickContactLayout_LaunchesContactActivity(){

        //1. find the view
        //2. perform the action on the view
        onView((withId(R.id.navigation_user))).perform(click());

        Intents.init();

        onView((withId(R.id.relative_layout_contact_me))).perform(click());


        //3. check if the view does what we expected
        intended(hasComponent(ContactActivity.class.getName()));

        Intents.release();

    }

    @Test
    public void clickMedicationRecyclerViewItem_LaunchesSingleActivity(){

        //1. find the view
        //2. perform the action on the view
        onView((withId(R.id.navigation_home))).perform(click());

        try{
            //On click of recycler view item, open detailed page
            Intents.init();


            onView((withId(R.id.nestedscrollview))).perform(click());


            //3. check if the view does what we expected
            intended(hasComponent(SingleMedicationActivity.class.getName()));


            onView((withId(R.id.action_edit_medication))).perform(click());


            //3. check if the view does what we expected
            intended(hasComponent(EditMedicationActivity.class.getName()));

            Intents.release();

        }catch (NoMatchingViewException e){
            onView(withText(R.string.no_meds_yet)).check(matches(isDisplayed()));
            //No Item in Recycler view so show text
        }

    }

}
