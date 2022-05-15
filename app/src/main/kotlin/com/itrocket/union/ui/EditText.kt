package com.itrocket.union.ui


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.itrocket.ui.EditText

@Composable
fun AuthEditText(
    text: String,
    hint: String,
    onTextChanged: (String) -> Unit,
    focusRequester: FocusRequester,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    var underlineColor by remember {
        mutableStateOf(brightGray)
    }
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
}