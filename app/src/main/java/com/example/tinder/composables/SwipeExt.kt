package com.example.tinder.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Remebers the swipe state for a card on which action has been taken
 */
@Composable
@ExperimentalMaterialApi
fun rememberSwipeState(maxWidth: Float, maxHeight: Float): Swipe =
    remember { Swipe(maxWidth, maxHeight) }

/**
 * Setting the offset with animation
 *
 */
open class Swipe(val maxWidth: Float, val maxHeight: Float) {
    val offsetX = Animatable(0f)
    val offsetY = Animatable(0f)

    fun reset(scope: CoroutineScope) = scope.launch {
        launch { offsetX.animateTo(0f, tween(400)) }
        launch { offsetY.animateTo(0f, tween(400)) }
    }

    fun accepted(scope: CoroutineScope) = scope.launch {
        offsetX.animateTo(maxWidth * 2, tween(400))
    }

    fun rejected(scope: CoroutineScope) = scope.launch {
        offsetX.animateTo(-(maxWidth * 2), tween(400))
    }

    fun drag(scope: CoroutineScope, x: Float, y: Float) = scope.launch {
        launch { offsetX.animateTo(x) }
        launch { offsetY.animateTo(y) }
    }
}

/**
 * Actual custom Modifier which handles the transition states from drog start to drag end
 * As mentioned in android dev blog here: https://developer.android.com/jetpack/compose/gestures#dragging
 * detectDragGestures has been used to continuously listen to touch pointer and perform action on drag events
 */
fun Modifier.swiper(
    state: Swipe,
    onDragReset: () -> Unit = {},
    onDragAccepted: () -> Unit,
    onDragRejected: () -> Unit
): Modifier = composed {
    val scope = rememberCoroutineScope()
    Modifier.pointerInput(Unit) {
            detectDragGestures(
                onDragEnd = {
                    when {
                        abs(state.offsetX.targetValue) < state.maxWidth / 4 -> {
                            state
                                .reset(scope)
                                .invokeOnCompletion { onDragReset() }
                        }
                        state.offsetX.targetValue > 0 -> {
                            state
                                .accepted(scope)
                                .invokeOnCompletion { onDragAccepted() }
                        }
                        state.offsetX.targetValue < 0 -> {
                            state
                                .rejected(scope)
                                .invokeOnCompletion { onDragRejected() }
                        }
                    }
                },
                onDrag = { change, dragAmount ->
                    val original = Offset(state.offsetX.targetValue, state.offsetY.targetValue)
                    val summed = original + dragAmount
                    val newValue = Offset(
                        x = summed.x.coerceIn(-state.maxWidth, state.maxWidth),
                        y = summed.y.coerceIn(-state.maxHeight, state.maxHeight)
                    )
                    change.consumePositionChange()
                    state.drag(scope, newValue.x, newValue.y)
                }
            )
        /**
         * Doing translation on the graphics layer
         * which mimics the rotation and translation of tinder swipeable card. This can be improved
         * if I start swiping a card it first rotates along edges left or right according to drag and
         * slowly decrease alpha to look it more like dismissing
         */
        }.graphicsLayer(
            translationX = state.offsetX.value,
            translationY = state.offsetY.value,
            rotationZ = (state.offsetX.value / 60).coerceIn(-40f, 40f),
            alpha = ((state.maxWidth - abs(state.offsetX.value)) / state.maxWidth).coerceIn(0f, 1f)
        )
}
