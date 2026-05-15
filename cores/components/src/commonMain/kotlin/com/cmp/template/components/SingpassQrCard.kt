package com.cmp.template.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

/**
 * A styled Singpass QR-code card.
 *
 * Layout mirrors the official Singpass branding:
 *  - White card with red rounded border
 *  - QR code area with the Singpass "i" icon centred on top
 *  - "singpass" footer with flanking red divider lines
 *
 * @param modifier    Standard Compose modifier.
 * @param qrContent   The string to encode as a QR code (URL, token, etc.).
 *                    Pass `null` to show a loading spinner while the content
 *                    is being fetched.
 * @param isLoading   Explicit loading flag. Defaults to `true` when [qrContent] is `null`.
 * @param cardSize    Width & height of the card. Defaults to 260.dp.
 * @param iconSize    Size of the centred Singpass icon overlay. Defaults to 48.dp.
 */
@Composable
fun SingpassQrCard(
    modifier: Modifier = Modifier,
    qrContent: String? = "Mengetest QR Code",
    isLoading: Boolean = qrContent == null,
    cardSize: Dp = 260.dp,
    iconSize: Dp = 48.dp,
) {
    var footerHeightPx by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier.width(cardSize),
        contentAlignment = Alignment.TopCenter,
    ) {
        Card(
            modifier = Modifier.size(cardSize),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(width = 4.dp, color = SingpassRed),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center,
            ) {
                if (isLoading || qrContent == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color(0xFFF5F5F5),
                                shape = RoundedCornerShape(8.dp),
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            color = SingpassRed,
                            strokeWidth = 2.5.dp,
                            modifier = Modifier.size(32.dp),
                        )
                    }
                } else {
                    val singpassIcon = painterResource(Res.drawable.singpass_icon)
                    // QR code rendered by qrose
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

        // ── Singpass branded footer ─────────────────────────────────────
        // Lives OUTSIDE the Card so the Card's shape-clip never applies.
        // onSizeChanged captures the footer's rendered height; offset shifts
        // it down by exactly half so it straddles the card's bottom border.
        SingpassFooter(
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
fun SingpassFooter(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.background(Color.White).padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painterResource(Res.drawable.singpass_logo_fullcolours),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
