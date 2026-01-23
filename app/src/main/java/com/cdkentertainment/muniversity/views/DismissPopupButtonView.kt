package com.cdkentertainment.muniversity.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.cdkentertainment.muniversity.UISingleton

@Composable
fun DismissPopupButtonView(
    onDismissRequest: () -> Unit = {},
    modifier: Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(48.dp)
            .shadow(7.dp, shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = UISingleton.uiElementsCornerRadius.dp, bottomEnd = 0.dp))
            .clickable(onClick = onDismissRequest)
            .background(UISingleton.color1)
            .then(modifier)
    ) {
        Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = "Close",
            tint = UISingleton.textColor1,
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp)
        )
    }
}