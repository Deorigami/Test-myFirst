package com.cmp.template.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cmp.template.theme.rememberIcosTextStyles

@Composable
fun IcosButton(
	onClick: () -> Unit,
	label: String,
	modifier : Modifier = Modifier,
	isLoading: Boolean = false,
	isEnabled: Boolean = true
){
	val ts = rememberIcosTextStyles()
	Button(
		onClick = onClick,
		modifier = modifier.height(90.dp).widthIn(370.dp),
		shape = RoundedCornerShape(8.dp),
		enabled = isEnabled
	) {
		Text(text = label, style = ts.Medium32.copy(Color.White))
	}
}