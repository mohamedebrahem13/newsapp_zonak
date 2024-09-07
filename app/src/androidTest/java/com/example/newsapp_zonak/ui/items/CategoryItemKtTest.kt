package com.example.newsapp_zonak.ui.items

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.newsapp_zonak.domain.models.NewsCategory
import org.junit.Rule
import org.junit.Test

class CategoryItemKtTest{

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun categoryItem_isSelected_DisplaysCorrectly() {
        // Arrange
        val category = NewsCategory.Technology
        val isSelected = true
        val onClick = {}

        // Act
        composeTestRule.setContent {
            MaterialTheme {
                CategoryItem(
                    category = category,
                    isSelected = isSelected,
                    onClick = onClick
                )
            }
        }

        // Assert
        composeTestRule.onNodeWithText("technology")
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun categoryItem_isNotSelected_DisplaysCorrectly() {
        // Arrange
        val category = NewsCategory.Science
        val isSelected = false
        val onClick = {}

        // Act
        composeTestRule.setContent {
            MaterialTheme {
                CategoryItem(
                    category = category,
                    isSelected = isSelected,
                    onClick = onClick
                )
            }
        }

        // Assert
        composeTestRule.onNodeWithText("science")
            .assertIsDisplayed()
            .assertHasClickAction()
    }
}