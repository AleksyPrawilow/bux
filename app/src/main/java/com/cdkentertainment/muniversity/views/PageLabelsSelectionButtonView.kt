package com.cdkentertainment.muniversity.views

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cdkentertainment.muniversity.R
import com.cdkentertainment.muniversity.UISingleton
import com.cdkentertainment.muniversity.UISingleton.color2
import com.cdkentertainment.muniversity.UISingleton.color3
import com.cdkentertainment.muniversity.UISingleton.textColor1
import com.cdkentertainment.muniversity.UISingleton.textColor4
import com.cdkentertainment.muniversity.UserDataSingleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PageLabelSelectionButtonView(modifier: Modifier = Modifier) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val shape: RoundedCornerShape = remember { RoundedCornerShape(UISingleton.uiElementsCornerRadius.dp) }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = color2,
            disabledContainerColor = color2,
            contentColor = textColor1,
            disabledContentColor = textColor1
        ),
        shape = shape,
        elevation = CardDefaults.cardElevation(3.dp),
        onClick = {
            UserDataSingleton.showPageLabels = !UserDataSingleton.showPageLabels
            coroutineScope.launch {
                try {
                    UserDataSingleton.saveUserSettings(context)
                } catch (e: Exception) {
                    e.printStackTrace()
                    UserDataSingleton.showPageLabels = !UserDataSingleton.showPageLabels
                }
            }
        },
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = stringResource(R.string.show_page_labels),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = textColor1,
                modifier = Modifier
                    .weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "More",
                tint = UISingleton.textColor1,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(UISingleton.color3, CircleShape)
                    .border(5.dp, color3, CircleShape)
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = UserDataSingleton.showPageLabels,
                    enter = scaleIn(spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium)),
                    exit = scaleOut(spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium))
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = "Yes",
                        tint = textColor4,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                            .align(Alignment.Center)
                    )
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = !UserDataSingleton.showPageLabels,
                    enter = scaleIn(spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium)),
                    exit = scaleOut(spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium))
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "No",
                        tint = textColor4,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}