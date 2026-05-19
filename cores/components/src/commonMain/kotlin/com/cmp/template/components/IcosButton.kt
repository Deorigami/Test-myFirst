package com.cmp.template.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cmp.template.theme.Color298CDE
import com.cmp.template.theme.ColorECF3FF
import com.cmp.template.theme.PrimaryLight
import com.cmp.template.theme.SecondaryDark
import com.cmp.template.theme.rememberIcosTextStyles

@Composable
fun IcosButton(
	onClick: () -> Unit,
	label: String,
	modifier: Modifier = Modifier,
	isLoading: Boolean = false,
	isEnabled: Boolean = true
) {
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

@Composable
fun IcosOutlinedButton(
	onClick: () -> Unit,
	label: String,
	modifier: Modifier = Modifier,
	isLoading: Boolean = false,
	isEnabled: Boolean = true
) {
	val ts = rememberIcosTextStyles()
	OutlinedButton(
		onClick = onClick,
		modifier = modifier.height(90.dp).widthIn(370.dp),
		shape = RoundedCornerShape(8.dp),
		enabled = isEnabled,
		colors = ButtonDefaults.outlinedButtonColors(
			containerColor = ColorECF3FF,
			contentColor = PrimaryLight
		),
		border = BorderStroke(
			width = 1.dp,
			color =
				if (isEnabled) {
					PrimaryLight
				} else {
					SecondaryDark
				},
		)
	) {
		Text(text = label, style = ts.Medium32.copy(PrimaryLight))
	}
}