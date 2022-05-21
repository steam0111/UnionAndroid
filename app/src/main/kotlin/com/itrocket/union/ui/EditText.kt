package com.itrocket.union.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
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
    isShowVisibilityButton: Boolean = false,
    isTextVisible: Boolean = true,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onVisibilityClickListener: () -> Unit = {}
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
            keyboardOptions = keyboardOptions,
            visualTransformation = if (isTextVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            singleLine = singleLine,
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
                    id = if (isTextVisible) {
                        R.drawable.ic_visibility_off
                    } else {
                        R.drawable.ic_visibility
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickableUnbounded {
                        onVisibilityClickListener()
                    }
            )
        }
    }
}