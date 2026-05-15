package com.cmp.template.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.cmp.template.theme.SingpassRed
import io.github.alexzhirkevich.qrose.options.QrBallShape
import io.github.alexzhirkevich.qrose.options.QrFrameShape
import io.github.alexzhirkevich.qrose.options.QrLogoPadding
import io.github.alexzhirkevich.qrose.options.QrPixelShape
import io.github.alexzhirkevich.qrose.options.QrShapes
import io.github.alexzhirkevich.qrose.options.roundCorners
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import org.jetbrains.compose.resources.painterResource

// Ratios calibrated at the 260.dp "perfect" baseline:
//   border          4   / 260 ≈ 0.0154
//   cornerRadius   16   / 260 ≈ 0.0615
//   padding        20   / 260 ≈ 0.0769
//   innerCorner     8   / 260 ≈ 0.0308
//   spinnerSize    32   / 260 ≈ 0.1231
//   spinnerStroke   2.5 / 260 ≈ 0.0096
//   footerPadH     12   / 260 ≈ 0.0462
private const val RATIO_BORDER         = 0.0154f
private const val RATIO_CORNER         = 0.0615f
private const val RATIO_PADDING        = 0.0769f
private const val RATIO_INNER_CORNER   = 0.0308f
private const val RATIO_SPINNER        = 0.1231f
private const val RATIO_SPINNER_STROKE = 0.0096f
private const val RATIO_FOOTER_PAD_H   = 0.0462f

/**
 * A styled Singpass QR-code card.
 *
 * Size is controlled entirely by [modifier] — use `Modifier.size(...)`,
 * `Modifier.fillMaxWidth()`, etc. All internal spacing and proportions
 * scale automatically with the measured width.
 *
 * @param modifier   Controls the card's size and placement. Defaults to 260×260 dp.
 * @param qrContent  String to encode. Pass `null` to show a loading spinner.
 * @param isLoading  Override loading state. Defaults to `true` when [qrContent] is `null`.
 */
@Composable
fun SingpassQrCard(
    modifier: Modifier = Modifier,
    qrContent: String? = "Test QR",
    isLoading: Boolean = qrContent == null,
) {
    var footerHeightPx by remember { mutableIntStateOf(0) }

    // Outer Box: the caller's modifier (size/padding/etc.) lives here.
    // Inner BoxWithConstraints: reads maxWidth reliably from the already-sized Box.
    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    )
    {
        val cardSize = maxWidth   // the single source of truth for all sizing

        val borderWidth    = cardSize * RATIO_BORDER
        val cornerRadius   = cardSize * RATIO_CORNER
        val contentPadding = cardSize * RATIO_PADDING
        val innerCorner    = cardSize * RATIO_INNER_CORNER
        val spinnerSize    = cardSize * RATIO_SPINNER
        val spinnerStroke  = cardSize * RATIO_SPINNER_STROKE

        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(cornerRadius),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(width = borderWidth, color = SingpassRed),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                contentAlignment = Alignment.Center,
            ) {
                if (isLoading || qrContent == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color(0xFFF5F5F5),
                                shape = RoundedCornerShape(innerCorner),
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            color = SingpassRed,
                            strokeWidth = spinnerStroke,
                            modifier = Modifier.size(spinnerSize),
                        )
                    }
                } else {
                    val singpassIcon = painterResource(Res.drawable.singpass_icon)
                    val qrPainter = rememberQrCodePainter(data = qrContent) {
                        shapes {
                            QrShapes(
                                ball = QrBallShape.roundCorners(0.25f),
                                darkPixel = QrPixelShape.roundCorners(0.5f),
                                frame = QrFrameShape.roundCorners(0.25f),
                            )
                        }
                        logo {
                            painter = singpassIcon
                            padding = QrLogoPadding.Natural(.1f)
                            size = 0.2f
                        }
                    }
                    Image(
                        painter = qrPainter,
                        contentDescription = "Singpass QR code",
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }

        SingpassFooter(
            footerPaddingH = cardSize * RATIO_FOOTER_PAD_H,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .onSizeChanged { footerHeightPx = it.height }
                .offset { IntOffset(x = 0, y = footerHeightPx / 2) }
                .fillMaxWidth(0.5f),
        )
    }
}

/**
 * "singpass" logo footer, centred with a white background so it
 * reads cleanly when straddling the card's bottom border.
 */
@Composable
fun SingpassFooter(
    modifier: Modifier = Modifier,
    footerPaddingH: Dp = 12.dp,
) {
    Row(
        modifier = modifier.background(Color.White).padding(horizontal = footerPaddingH),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painterResource(Res.drawable.singpass_logo_fullcolours),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
