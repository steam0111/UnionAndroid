package com.itrocket.union.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BaseCheckbox(isChecked: Boolean, onCheckClickListener: () -> Unit) {
    Checkbox(
        checked = isChecked, onCheckedChange = {
            onCheckClickListener()
        }, modifier = Modifier.size(20.dp, 20.dp),
        colors = CheckboxDefaults.colors(
            uncheckedColor = psb4,
            checkedColor = AppTheme.colors.mainColor
        )
    )
}

@Composable
fun BaseRadioButton(isSelected: Boolean, onClick: () -> Unit) {
    RadioButton(
        selected = isSelected, onClick = onClick, modifier = Modifier.size(20.dp, 20.dp),
        colors = RadioButtonDefaults.colors(
            unselectedColor = psb4,
            selectedColor = AppTheme.colors.mainColor
        )
    )
}

@Composable
@Preview
fun BaseCheckboxPreview() {
    Row {
        BaseCheckbox(isChecked = true) {

        }
        Spacer(modifier = Modifier.width(16.dp))
        BaseCheckbox(isChecked = false) {

        }
    }
}

@Composable
@Preview
fun BaseRadioButtonPreview() {
    BaseRadioButton(isSelected = true) {

    }
}