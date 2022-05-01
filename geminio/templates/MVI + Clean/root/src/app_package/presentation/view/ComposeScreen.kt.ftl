package ${packageName}.${featurePackageName}.presentation.view

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ${packageName}.R
import ${packageName}.ui.AppTheme
import ru.interid.weatherford.core.AppInsets
import ${packageName}.${featurePackageName}.presentation.store.${featureName}Store

@Composable
fun ${featureName}Screen(
    state: ${featureName}Store.State,
    appInsets: AppInsets,
    onBackClickListener: () -> Unit
) {
    AppTheme {

    }
}

@Preview(name = "светлая тема экран - 6.3 (3040x1440)", showSystemUi = true, device = Devices.PIXEL_4_XL, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "темная тема экран - 4,95 (1920 × 1080)", showSystemUi = true, device = Devices.NEXUS_5, uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "планшет", showSystemUi = true, device = Devices.PIXEL_C)
@Composable
fun ${featureName}ScreenPreview() {
    ${featureName}Screen(${featureName}Store.State(), AppInsets(), {})
}