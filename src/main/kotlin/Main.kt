// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.view.HomeUI
import components.*
import mvvm.ViewCompose


fun main() = application {
    val state = rememberWindowState(size = DpSize(800.dp, 600.dp), position = WindowPosition.Aligned(Alignment.Center))

    /*ClassicWindowScaffold(
        onCloseRequest = ::exitApplication,
    ) {
        ViewCompose(HomeUI())
    }*/

    var isPanelExtend by remember { mutableStateOf(false) }
    PanelWindowScaffold(
        onCloseRequest = ::exitApplication,
        state = state,
        resizable = true,
        isPanelExtend = isPanelExtend,
        panelContent = {
            PanelMenu(isPanelExtend = isPanelExtend, onSelected = {
                println(it)
            }) {
                PanelIconMenuItem(
                    icon = { PanelMenuIcon(Icons.Default.Home, contentDescription = "Home") },
                    title = { PanelMenuTitle(title = "首页") },
                )
                PanelIconMenuItem(
                    icon = { PanelMenuIcon(Icons.Default.Settings, contentDescription = "Setting") },
                    title = { PanelMenuTitle(title = "设置") },
                )
            }
        }
    ) {
        Button(onClick = { isPanelExtend = !isPanelExtend }) {
            ViewCompose(HomeUI())
        }
    }
}
