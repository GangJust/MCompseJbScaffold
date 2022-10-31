package components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState

/**
 * Windows 标题栏
 */
@Composable
fun WindowTitleBar(
    onCloseRequest: () -> Unit,
    state: WindowState,
    icon: Painter,
    title: String,
    onMinimizing: () -> Unit = {},
    onMaximizing: () -> Unit = {},
    divider: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        WindowTitleBarBox(
            icon = {
                BoxWithConstraints(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Icon(
                        icon,
                        contentDescription = title,
                        modifier = Modifier.size(14.dp)
                    )
                }
            },
            title = { Text(title) },
            actions = { WindowActionIconButtonGroup(onCloseRequest, state, onMinimizing, onMaximizing) }
        )

        if (divider) Divider()
    }
}

/**
 * Windows 标题栏通用盒子
 */
@Composable
fun WindowTitleBarBox(
    icon: @Composable () -> Unit,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        icon()
        title()
        Spacer(modifier = Modifier.weight(1f))
        actions()
    }
}

/**
 * 通用Window操作按钮(最小化, 最大化, 关闭)
 */
@Composable
fun WindowActionIconButtonGroup(
    onCloseRequest: () -> Unit,
    state: WindowState,
    onMinimizing: () -> Unit = {},
    onMaximizing: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var toggleMaxIcon by remember { mutableStateOf(false) }
    Row(modifier = modifier) {
        BoxWithConstraints(modifier = Modifier.clickable {
            state.isMinimized = true
            onMinimizing()
        }.padding(vertical = 12.dp, horizontal = 24.dp)) {
            Icon(
                painterResource("icons/titlebar/minimizing.svg"),
                contentDescription = "minimizing",
                modifier = Modifier.size(14.dp)
            )
        }
        BoxWithConstraints(modifier = Modifier.clickable {
            toggleMaxIcon = !toggleMaxIcon
            onMaximizing()
        }.padding(vertical = 12.dp, horizontal = 24.dp)) {
            if (toggleMaxIcon) {
                state.placement = WindowPlacement.Maximized
                Icon(
                    painterResource("icons/titlebar/recovery.svg"),
                    contentDescription = "recovery",
                    modifier = Modifier.size(14.dp)
                )
            } else {
                state.placement = WindowPlacement.Floating
                Icon(
                    painterResource("icons/titlebar/maximizing.svg"),
                    contentDescription = "maximizing",
                    modifier = Modifier.size(14.dp)
                )
            }
        }
        BoxWithConstraints(modifier = Modifier.clickable {
            onCloseRequest()
        }.padding(vertical = 12.dp, horizontal = 24.dp)) {
            Icon(
                painterResource("icons/titlebar/closed.svg"),
                contentDescription = "closed",
                modifier = Modifier.size(14.dp)
            )
        }
    }
}