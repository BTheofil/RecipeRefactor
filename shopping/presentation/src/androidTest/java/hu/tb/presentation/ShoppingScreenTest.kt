package hu.tb.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import hu.tb.presentation.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class ShoppingScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testShoppingScreen_itemsVisible() {
        var mockState by mutableStateOf(ShoppingState())

        composeRule.setContent {
            AppTheme {
                ShoppingScreen(
                    state = mockState,
                    onAction = {
                        when(it){
                            ShoppingAction.OnClearButtonClick -> {}
                            is ShoppingAction.OnCreateDialogSaveButtonClick -> {
                                mockState = mockState.copy(
                                    uncheckedItems = listOf(it.newItem)
                                )
                            }
                            is ShoppingAction.OnDeleteSingleButtonClick -> {}
                            is ShoppingAction.OnEditItemChange -> {}
                            is ShoppingAction.OnItemCheckChange -> {}
                        }
                    }
                )
            }
        }

        //check title
        composeRule.onNodeWithText("Shopping list").assertIsDisplayed()

        composeRule.onNode(
            hasContentDescription("FAB add icon").and(hasClickAction())
        ).performClick()
        composeRule.onNodeWithText("Create new item").assertIsDisplayed()
        composeRule.onNodeWithText("New item name").performTextInput("first")
        composeRule.onNodeWithText("Add").performClick()
        composeRule.onNodeWithText("first").assertIsDisplayed()
    }

}