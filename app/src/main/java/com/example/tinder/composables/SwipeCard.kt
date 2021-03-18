package com.example.tinder.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tinder.domain.model.Person
import com.example.tinder.ui.TinderTheme
import com.example.tinder.util.SwipeResult

/**
 * Swipeable card. Uses Modifier property of swiper
 * It provides a swipe result with an enum ACCEPT or REJECT
 */
@ExperimentalMaterialApi
@Composable
fun SwipeCard(
    modifier: Modifier = Modifier,
    onSwiped: (result: SwipeResult) -> Unit,
    content: @Composable (BoxScope.() -> Unit)
) {
    val swiped = remember { mutableStateOf(false) }
    BoxWithConstraints(modifier = modifier) {
        val swipeState = rememberSwipeState(
            maxWidth = constraints.maxWidth.toFloat(),
            maxHeight = constraints.maxHeight.toFloat()
        )
        if (swiped.value.not()) {
            Box(
                modifier = Modifier
                    .swiper(
                        state = swipeState,
                        onDragAccepted = {
                            swiped.value = true
                            onSwiped(SwipeResult.ACCEPT)
                        },
                        onDragRejected = {
                            swiped.value = true
                            onSwiped(SwipeResult.REJECT)
                        }
                    ),
                content = content
            )
        }
    }
}

@Preview
@ExperimentalMaterialApi
@Composable
fun PreviewSwipeCard() {
    TinderTheme {
        SwipeCard(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center), onSwiped = { /*TODO*/ }) {
//            PersonCard(Person(
//                "",
//                "Elliot Alderson", "12-12-2012", "Embassy Village, Gurgaon, 67876546", "+91 12334"
//            ))
        }
    }
}