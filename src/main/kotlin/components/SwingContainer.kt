package components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import java.awt.Component
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import javax.swing.JComponent
import javax.swing.JTextArea
import javax.swing.TransferHandler
import javax.swing.plaf.FileChooserUI

/**
 * @Author: Gang
 * @Date: 2022/10/7 19:42
 * @Description: 嵌套原生 Swing 组件
 */

@Composable
fun <T : Component> SwingContainer(
    background: Color = Color.White,
    modifier: Modifier = Modifier.fillMaxSize(),
    content: () -> T,
) {
    SwingPanel(
        background = background,
        modifier = modifier,
        factory = {
            ComposePanel().apply {
                setContent {
                    Box {
                        SwingPanel(
                            modifier = Modifier.fillMaxSize(),
                            factory = { content() }
                        )
                    }
                }
            }
        },
    )
}


@Composable  //测试文件拖拽, 值得注意的是, 当应用程序是以管理员身份运行时, 拖拽方法将不可使用
fun testSwingContainer() {
    SwingContainer {

        //第一种实现;
        //see at: https://xiets.blog.csdn.net/article/details/78389272
        //see at: https://github.com/JetBrains/compose-jb/tree/master/tutorials/Window_API_new#swing-interoperability
        /*JTextArea().apply {
            text = "将Apk文件拖拽到这里安装..."
            dropTarget = DropTarget().apply {
                addDropTargetListener(object : DropTargetAdapter() {
                    override fun drop(dtde: DropTargetDropEvent) {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY)
                        val transferData = dtde.transferable.getTransferData(DataFlavor.javaFileListFlavor)
                        text = "dropTarget: $transferData"
                    }
                })
            }
        }*/


        //第二中实现;
        //ses at: https://blog.csdn.net/java_faep/article/details/53523401
        JTextArea().apply {
            text = "将Apk文件拖拽到这里安装..."
            transferHandler = object : TransferHandler() {
                override fun importData(comp: JComponent, t: Transferable): Boolean {
                    val transferData = t.getTransferData(DataFlavor.javaFileListFlavor)
                    text = "transferHandler: $transferData"
                    return true
                }

                override fun canImport(
                    comp: JComponent,
                    transferFlavors: Array<out DataFlavor>
                ): Boolean {
                    return true
                }
            }
        }
    }
}