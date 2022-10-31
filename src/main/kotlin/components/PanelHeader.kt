package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowState

/**
 * 面板头部
 */
@Composable
fun PanelHeader(
    state: WindowState,
    icon: Painter,
    title: String,
    isPanelExtend: Boolean,
    divider: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        PanelHeaderBox(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp).fillMaxWidth(),
            icon = {
                BoxWithConstraints(modifier = Modifier.padding(8.dp)) {
                    Icon(
                        icon,
                        contentDescription = title,
                        modifier = Modifier.size(32.dp),
                        tint = Color.Gray,
                    )
                }
            },
            title = {
                if (isPanelExtend) {
                    BoxWithConstraints {
                        Text(title, style = TextStyle(fontSize = 28.sp))
                    }
                }
            },
        )

        if (divider) {
            BoxWithConstraints(modifier = Modifier.padding(horizontal = 16.dp)) {
                Divider()
            }
        }
    }
}

/**
 * 通用面板头部盒子
 */
@Composable
fun PanelHeaderBox(
    icon: @Composable () -> Unit,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        icon()
        title()
    }
}