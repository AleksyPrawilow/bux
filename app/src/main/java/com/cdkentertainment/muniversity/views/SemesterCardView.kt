package com.cdkentertainment.muniversity.views

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cdkentertainment.muniversity.R
import com.cdkentertainment.muniversity.UISingleton
import com.cdkentertainment.muniversity.getLocalized
import com.cdkentertainment.muniversity.models.SharedDataClasses

@Composable
fun SemesterCardView(text: SharedDataClasses.IdAndName, modifier: Modifier = Modifier, icon: ImageVector = ImageVector.vectorResource(R.drawable.rounded_calendar_month_24)) {
    val context: Context = LocalContext.current
    Card(
        colors = CardColors(
            contentColor = UISingleton.textColor1,
            containerColor = UISingleton.color1,
            disabledContainerColor = UISingleton.color1,
            disabledContentColor = UISingleton.textColor1
        ),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(UISingleton.uiElementsCornerRadius.dp),
        modifier = Modifier.fillMaxWidth().padding(top = 4.dp).then(modifier)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = UISingleton.textColor1,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = text.name.getLocalized(context),
                color = UISingleton.textColor1,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}