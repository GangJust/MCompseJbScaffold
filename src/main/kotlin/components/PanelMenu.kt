package components

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import utils.ColorUtils

/**
 * 面板菜单接口
 */
interface PanelMenuScope {
    var isExtend: Boolean

    var selectedIndex: MutableState<Int>

    var onSelected: (index: Int) -> Unit

    var indexes: MutableList<Int>

    var unSelectedColor: Color

    var selectedColor: Color
}

/**
 * 面板菜单实例
 */
internal object PanelMenuScopeInstance : PanelMenuScope {
    //默认展开
    override var isExtend: Boolean = true

    //默认选中, 第1项
    override var selectedIndex: MutableState<Int> = mutableStateOf(0)

    //被选择的菜单项下标
    override var onSelected: (index: Int) -> Unit = {}

    //菜单项下标集合, 默认没有
    override var indexes: MutableList<Int> = mutableListOf()

    //未选中颜色
    override var unSelectedColor: Color = Color.Transparent

    //选中颜色
    override var selectedColor: Color = Color.Transparent
}

@Composable
fun PanelMenu(
    isPanelExtend: Boolean,
    unSelectedColor: Color = Color.Transparent,
    selectedColor: Color = ColorUtils.parseColor("#C5CAE9"),
    onSelected: (index: Int) -> Unit,
    modifier: Modifier = Modifier.padding(vertical = 12.dp),
    content: @Composable PanelMenuScope.() -> Unit
) {
    VerScrollableContainer {
        Column(modifier) {
            PanelMenuScopeInstance.also {
                it.isExtend = isPanelExtend
                //it.selectedIndex.value = 0 //这里需要注意
                it.onSelected = onSelected
                it.indexes = mutableListOf()
                it.unSelectedColor = unSelectedColor
                it.selectedColor = selectedColor
            }.content()
        }
    }
}

/**
 *面板可展开ListItem, 这是一个外接方法(扩展方法)
 */
@Composable
fun PanelMenuScope.PanelIconMenuItem(
    icon: @Composable () -> Unit,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tmpIndex = if (indexes.isEmpty()) 0 else indexes.last() //下标赋值
    indexes.add(tmpIndex + 1) //存储下标集合
    BoxWithConstraints(Modifier
        .background(color = if (tmpIndex == selectedIndex.value) selectedColor else unSelectedColor)
        .clickable(
            indication = PanelMenuItemIndication,
            interactionSource = remember { MutableInteractionSource() },
        ) {
            //因为每次点击都会[selectedIndex]改变都会造成重构, 因此需要清空
            indexes.clear()
            selectedIndex.value = tmpIndex
            onSelected(selectedIndex.value)
        }) {
        Row(
            modifier = modifier.padding(horizontal = 24.dp, vertical = 16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()
            if (isExtend) {
                BoxWithConstraints(modifier = Modifier.weight(1f).padding(horizontal = 24.dp)) {
                    title()
                }
            }
        }
    }
}

@Composable
fun PanelMenuIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier.size(28.dp),
    tint: Color = Color.Black.copy(alpha = 0.6f)
) = Icon(
    imageVector = imageVector,
    contentDescription = contentDescription,
    modifier = modifier,
    tint = tint,
)

@Composable
fun PanelMenuIcon(
    bitmap: ImageBitmap,
    contentDescription: String?,
    modifier: Modifier = Modifier.size(28.dp),
    tint: Color = Color.Black.copy(alpha = 0.6f)
) = Icon(
    bitmap = bitmap,
    contentDescription = contentDescription,
    modifier = modifier,
    tint = tint,
)

@Composable
fun PanelMenuIcon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier.size(28.dp),
    tint: Color = Color.Black.copy(alpha = 0.6f)
) = Icon(
    painter = painter,
    contentDescription = contentDescription,
    modifier = modifier,
    tint = tint,
)

@Composable
fun PanelMenuTitle(
    title: String,
    style: TextStyle = MaterialTheme.typography.body1
) = Text(
    text = title,
    style = style,
    maxLines = 1,
)

//焦点指示器(鼠标悬浮、按下暗色波纹)
private object PanelMenuItemIndication : Indication {

    private class PanelMenuIndication(
        private val isPressed: State<Boolean>,
        private val isHovered: State<Boolean>,
        private val isFocused: State<Boolean>,
    ) : IndicationInstance {
        override fun ContentDrawScope.drawIndication() {
            drawContent()
            if (isPressed.value) {
                drawRect(color = Color.Gray.copy(alpha = 0.3f), size = size)
            } else if (isHovered.value || isFocused.value) {
                drawRect(color = Color.Gray.copy(alpha = 0.1f), size = size)
            }
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val isPressed = interactionSource.collectIsPressedAsState()
        val isHovered = interactionSource.collectIsHoveredAsState()
        val isFocused = interactionSource.collectIsFocusedAsState()
        return remember(interactionSource) {
            PanelMenuIndication(isPressed, isHovered, isFocused)
        }
    }
}