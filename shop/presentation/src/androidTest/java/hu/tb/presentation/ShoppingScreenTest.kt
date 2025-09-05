package hu.tb.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import hu.tb.core.domain.product.Measure
import hu.tb.core.domain.shop.ShopItem
import hu.tb.presentation.theme.AppTheme
import hu.tb.shopping.presentation.ShopAction
import hu.tb.shopping.presentation.ShopScreen
import hu.tb.shopping.presentation.ShopState
import org.junit.Rule
import org.junit.Test

class ShoppingScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testShoppingScreen_itemsVisible() {
        var mockState by mutableStateOf(ShopState())

        val testItem = ShopItem(1, "apple", 1.0, Measure.PIECE)

        composeRule.setContent {
            AppTheme {
                ShopScreen(
                    state = mockState,
                    onAction = {
                        when (it) {
                            ShopAction.DeleteAllItems -> {}
                            is ShopAction.DeleteItem -> {}
                            is ShopAction.ShopItemChange -> {
                                mockState = mockState.copy(
                                    uncheckedItems = listOf(testItem)
                                )
                            }
                            ShopAction.AddAllItemsToStorage -> {}
                        }
                    }
                )
            }
        }

        composeRule.onNodeWithText("Shopping list").assertIsDisplayed()

        composeRule.onNodeWithText("Create new item").assertIsDisplayed().performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("Ingredient name").performTextInput(testItem.name)
        composeRule.onNodeWithText("Amount").performTextInput(testItem.quantity.toString())
        composeRule.onNode(
            hasTestTag("DropdownMenuIconTag").and(hasClickAction())
        ).performClick()
        composeRule.onNodeWithText("PIECE").performClick()
        Espresso.closeSoftKeyboard()
        composeRule.onNodeWithText("Add ingredient").assertIsDisplayed().performClick()
        composeRule.onNodeWithTag("closeIcon").performClick()

        composeRule.onNodeWithText(testItem.name).assertIsDisplayed()
    }

}