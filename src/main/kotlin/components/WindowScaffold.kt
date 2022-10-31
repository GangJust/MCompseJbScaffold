package components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState

/**
 * 经典窗口样式脚手架
 */
@Composable
fun ClassicWindowScaffold(
    onCloseRequest: () -> Unit,
    state: WindowState = rememberWindowState(),
    icon: Painter = painterResource("icons/titlebar/icon.svg"),
    title: String = "Untitled",
    windowCorner: Dp = 8.dp,
    visible: Boolean = true,
    resizable: Boolean = true,
    enabled: Boolean = true,
    focusable: Boolean = true,
    alwaysOnTop: Boolean = false,
    onPreviewKeyEvent: (KeyEvent) -> Boolean = { false },
    onKeyEvent: (KeyEvent) -> Boolean = { false },
    titleBarModifier: Modifier = Modifier.background(MaterialTheme.colors.onSurface.copy(0.04f)),
    titleBar: @Composable () -> Unit = { WindowTitleBar(onCloseRequest, state, icon, title) },
    content: @Composable () -> Unit,
) = Window(
    onCloseRequest = onCloseRequest,
    state = state,
    undecorated = true, //必须true
    transparent = true, //必须true
    visible = visible,
    resizable = resizable,
    enabled = enabled,
    focusable = focusable,
    alwaysOnTop = alwaysOnTop,
    onPreviewKeyEvent = onPreviewKeyEvent,
    onKeyEvent = onKeyEvent
) {
    WindowClassic(
        windowCorner = windowCorner,
        titleBarModifier = titleBarModifier,
        titleBar = titleBar,
        content = content,
    )
}

/**
 * 经典Windows样式
 */
@Composable
private fun WindowScope.WindowClassic(
    windowCorner: Dp,
    titleBarModifier: Modifier,
    titleBar: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Card(modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(windowCorner)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BoxWithConstraints(modifier = titleBarModifier) { WindowDraggableArea { Box(modifier = Modifier.fillMaxWidth()) { titleBar() } } }
            content()
        }
    }
}

/**
 * 面板Windows样式脚手架
 */
@Composable
fun PanelWindowScaffold(
    onCloseRequest: () -> Unit,
    state: WindowState = rememberWindowState(),
    icon: Painter = painterResource("icons/titlebar/icon.svg"),
    title: String = "Untitled",
    windowCorner: Dp = 8.dp,
    visible: Boolean = true,
    resizable: Boolean = true,
    enabled: Boolean = true,
    focusable: Boolean = true,
    alwaysOnTop: Boolean = false,
    onPreviewKeyEvent: (KeyEvent) -> Boolean = { false },
    onKeyEvent: (KeyEvent) -> Boolean = { false },
    panelModifier: Modifier = Modifier.background(MaterialTheme.colors.onSurface.copy(0.04f)),
    titleBarModifier: Modifier = Modifier.background(MaterialTheme.colors.onSurface.copy(0.04f)),
    isPanelExtend: Boolean = false,
    panelWidth: Dp = 280.dp,
    panelHeader: @Composable () -> Unit = {
        PanelHeader(
            state = state,
            icon = icon,
            title = title,
            isPanelExtend = isPanelExtend
        )
    },
    panelContent: @Composable () -> Unit = { },
    titleBar: @Composable () -> Unit = {
        WindowTitleBarBox(
            icon = {},
            title = {},
            actions = { WindowActionIconButtonGroup(onCloseRequest, state) }
        )
    },
    content: @Composable () -> Unit,
) = Window(
    onCloseRequest = onCloseRequest,
    state = state,
    undecorated = true, //必须true
    transparent = true, //必须true
    visible = visible,
    resizable = resizable,
    enabled = enabled,
    focusable = focusable,
    alwaysOnTop = alwaysOnTop,
    onPreviewKeyEvent = onPreviewKeyEvent,
    onKeyEvent = onKeyEvent
) {
    WindowPanel(
        panelModifier = panelModifier,
        titleBarModifier = titleBarModifier,
        windowCorner = windowCorner,
        isPanelExtend = isPanelExtend,
        panelWidth = panelWidth,
        panelHeader = panelHeader,
        panelContent = panelContent,
        titleBar = titleBar,
        content = content,
    )
}

/**
 * 面板窗口样式
 */
@Composable
private fun WindowScope.WindowPanel(
    panelModifier: Modifier,
    titleBarModifier: Modifier,
    windowCorner: Dp,
    isPanelExtend: Boolean,
    panelWidth: Dp = 280.dp,
    panelHeader: @Composable () -> Unit,
    panelContent: @Composable () -> Unit,
    titleBar: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Card(modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(windowCorner)) {
        Row(modifier = Modifier.fillMaxSize()) {
            //Left Panel
            val leftPanelWidth = if (isPanelExtend) panelWidth else 80.dp //未扩展宽度固定为80dp
            BoxWithConstraints(modifier = panelModifier.fillMaxHeight().animateContentSize().width(leftPanelWidth)) {
                Column(modifier = Modifier.fillMaxHeight()) {
                    WindowDraggableArea { Box(modifier = Modifier.fillMaxWidth()) { panelHeader() } }
                    panelContent()
                }
            }
            //Right Content
            Column(modifier = Modifier.fillMaxHeight().weight(1f)) {
                BoxWithConstraints(modifier = titleBarModifier) {
                    WindowDraggableArea { Box(modifier = Modifier.fillMaxWidth()) { titleBar() } }
                }
                content()
            }
        }
    }
}