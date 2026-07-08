package com.example.budgetbuddy

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.budgetbuddy.view.RegistrationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpInstrumentTesting {

    @get:Rule
    val composeRule = createAndroidComposeRule<RegistrationActivity>()

    @Test
    fun signUpScreen_acceptsInput() {

        composeRule.onNodeWithTag("fullName")
            .performTextInput("John Doe")

        composeRule.onNodeWithTag("address")
            .performTextInput("Kathmandu")

        composeRule.onNodeWithTag("contact")
            .performTextInput("9800000000")

        composeRule.onNodeWithTag("email")
            .performTextInput("john@gmail.com")

        composeRule.onNodeWithTag("createPassword")
            .performTextInput("password123")

        composeRule.onNodeWithTag("confirmPassword")
            .performTextInput("password123")
    }

    @Test
    fun signUpButton_existsAndClickable() {

        composeRule.onNodeWithTag("signupButton")
            .assertIsDisplayed()
            .performClick()
    }

    @Test
    fun loginButton_existsAndClickable() {

        composeRule.onNodeWithTag("login")
            .assertIsDisplayed()
            .performClick()
    }
}