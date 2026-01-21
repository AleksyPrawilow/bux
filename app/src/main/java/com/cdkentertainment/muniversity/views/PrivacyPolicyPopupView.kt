package com.cdkentertainment.muniversity.views

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import com.cdkentertainment.muniversity.R
import com.cdkentertainment.muniversity.UISingleton
import com.cdkentertainment.muniversity.UserDataSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PrivacyPolicyPopupView(
    onDismiss: () -> Unit = {}
) {
    val context: Context = LocalContext.current
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val shape: RoundedCornerShape = remember { RoundedCornerShape(UISingleton.uiElementsCornerRadius.dp) }
    Dialog(
        onDismissRequest = { }
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    shape = shape
                )
                .background(
                    color = UISingleton.color2,
                    shape = shape
                )
                .border(
                    width = 5.dp,
                    color = UISingleton.color1,
                    shape = shape
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                PopupHeaderView(stringResource(R.string.privacy_policy))
                Text(
                    text = stringResource(R.string.privacy_policy_confirm),
                    style = MaterialTheme.typography.titleMedium,
                    color = UISingleton.textColor1,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                TextAndIconCardView(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    title = stringResource(R.string.privacy_policy),
                    icon = ImageVector.vectorResource(R.drawable.rounded_privacy_tip_24),
                    backgroundColor = UISingleton.color1,
                    showArrow = true,
                    elevation = 0.dp,
                    iconSize = 40.dp,
                    iconPadding = 6.dp,
                    fontWeight = FontWeight.Bold,
                    textStyle = MaterialTheme.typography.titleMedium
                ) {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            "http://www.cdkentertainment.pl/muniversity/privacy".toUri()
                        )
                    )
                }
                TextAndIconCardView(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                    title = stringResource(R.string.privacy_policy_confirm_2),
                    icon = ImageVector.vectorResource(R.drawable.rounded_door_open_24),
                    backgroundColor = UISingleton.color1,
                    showArrow = true,
                    elevation = 0.dp,
                    iconSize = 40.dp,
                    iconPadding = 6.dp,
                    fontWeight = FontWeight.Bold,
                    textStyle = MaterialTheme.typography.titleMedium
                ) {
                    coroutineScope.launch {
                        UserDataSingleton.savePrivacyPolicyAcceptance(context, true)
                    }
                }
            }
        }
    }
}