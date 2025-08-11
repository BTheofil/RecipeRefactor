package hu.tb.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import hu.tb.presentation.theme.AppTheme
import hu.tb.shopping.presentation.ShoppingAction
import hu.tb.shopping.presentation.ShoppingScreen
import hu.tb.shopping.presentation.ShoppingState
import kotlinx.coroutines.delay
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
                        when (it) {
                            ShoppingAction.OnClearButtonClick -> {}
                            is ShoppingAction.OnDeleteSingleButtonClick -> {}
                            is ShoppingAction.SaveItem -> {}
                            is ShoppingAction.ShopItemChange -> TODO()
                        }
                    }
                )
            }
        }

        //check title
        composeRule.onNodeWithText("Shopping list").assertIsDisplayed()

        composeRule.onNode(hasContentDescription("menu icon")).performClick()
        composeRule.onNodeWithText("Add item").assertIsDisplayed()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("Ingredient name").performTextInput("apple")
        composeRule.onNodeWithText("Amount").performTextInput("1")
        composeRule.onNode(
            hasTestTag("DropdownMenuIconTag").and(hasClickAction())
        ).performClick()
        composeRule.onNodeWithText("PIECE").performClick()
        Espresso.closeSoftKeyboard()
        composeRule.onNodeWithText("Add ingredient").assertIsDisplayed()
    }

}