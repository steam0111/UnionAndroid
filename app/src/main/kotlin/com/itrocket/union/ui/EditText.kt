package com.itrocket.union.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.itrocket.ui.EditText
import com.itrocket.union.R
import com.itrocket.utils.clickableUnbounded

@Composable
fun AuthEditText(
    text: String,
    hint: String,
    onTextChanged: (String) -> Unit,
    focusRequester: FocusRequester,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isShowVisibilityButton: Boolean = false,
    onVisibilityClickListener: (VisualTransformation) -> Unit = {}
) {
    var underlineColor by remember {
        mutableStateOf(brightGray)
    }
    Box(contentAlignment = Alignment.CenterEnd) {
        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = text,
            hint = hint,
            textStyle = AppTheme.typography.body1,
            hintStyle = AppTheme.typography.body1,
            hintColor = psb3,
            visualTransformation = visualTransformation,
            onTextChanged = onTextChanged,
            focusRequester = focusRequester,
            onFocusChanged = {
                underlineColor = if (it.hasFocus) {
                    psb6
                } else {
                    psb1
                }
            },
            underlineColor = underlineColor
        )
        if (isShowVisibilityButton) {
            Image(
                painter = painterResource(
                    id = if (visualTransformation is PasswordVisualTransformation) {
                        R.drawable.ic_visibility
                    } else {
                        R.drawable.ic_visibility_off
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickableUnbounded {
                        onVisibilityClickListener(visualTransformation)
                    }
            )
        }
    }
}

@Composable
fun DefaultEditText(
    text: String,
    hint: String,
    focusRequester: FocusRequester,
    onSearchTextChanged: (String) -> Unit,
) {
    var underlineColor by remember {
        mutableStateOf(brightGray)
    }
    EditText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 4.dp),
        text = text,
        hint = hint,
        textStyle = AppTheme.typography.body1,
        hintStyle = AppTheme.typography.body2,
        hintColor = psb3,
        onTextChanged = onSearchTextChanged,
        focusRequester = focusRequester,
        onFocusChanged = {
            underlineColor = if (it.hasFocus) {
                psb6
            } else {
                brightGray
            }
        },
        underlineColor = underlineColor
    )
}