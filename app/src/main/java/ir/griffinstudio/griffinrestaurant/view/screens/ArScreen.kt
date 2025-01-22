package ir.griffinstudio.griffinrestaurant.view.screens

import android.view.MotionEvent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Frame
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.isValid
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.node.Node
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener
import io.github.sceneview.rememberView

@Composable
fun ViewAr() {
    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val view = rememberView(engine)
    val childNode = rememberNodes()
    val modelInstance = remember {
        mutableStateListOf<ModelInstance>()
    }
    val materialLoader = rememberMaterialLoader(engine)
    val cameraNode = rememberARCameraNode(engine)
    val frame = remember {
        mutableStateOf<Frame?>(null)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ARScene(
            modifier = Modifier.fillMaxSize(),
            childNodes = childNode,
            engine = engine,
            view = view,
            modelLoader = modelLoader,
            cameraNode = cameraNode,
            materialLoader = materialLoader,
            onSessionUpdated = { _, sessionFrame ->
                frame.value = sessionFrame
            },
            sessionConfiguration = { session, config ->
                when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                    true -> Config.DepthMode.AUTOMATIC
                    else -> Config.DepthMode.DISABLED
                }
                config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
            },
            onGestureListener = rememberOnGestureListener(
                onSingleTapConfirmed = { e: MotionEvent, node: Node? ->
                    if (node == null) {
                        val hitTestResult = frame.value?.hitTest(e.x, e.y)
                        hitTestResult?.firstOrNull {
                            it.isValid(
                                depthPoint = false,
                                point = false
                            )
                        }?.createAnchorOrNull()?.let {
                            val nodeModel = createAnchorNode(
                                engine = engine,
                                modelLoader = modelLoader,
                                materialLoader = materialLoader,
                                modelInstance = modelInstance,
                                anchor = it,
                            )
                            childNode += nodeModel
                        }
                    }
                }
            )
        )
    }
}

fun createAnchorNode(
    engine: Engine,
    modelLoader: ModelLoader,
    materialLoader: MaterialLoader,
    modelInstance: MutableList<ModelInstance>,
    anchor: Anchor,
): AnchorNode {
    val anchorNode = AnchorNode(engine = engine, anchor)
    val modelNode = ModelNode(
        modelInstance = modelInstance.apply {
            if (isEmpty()) {
                this += modelLoader.createInstancedModel("models/pizza.glb", 1)
            }
        }.removeAt(modelInstance.lastIndex),
        scaleToUnits = 0.2f
    ).apply {
        isEditable = true
    }
    val editingBox = CubeNode(
        engine = engine,
        size = modelNode.extents,
        center = modelNode.center,
        materialInstance = materialLoader.createColorInstance(Color.White)
    ).apply {
        isVisible = false
    }
    modelNode.addChildNode(editingBox)
    anchorNode.addChildNode(modelNode)
    listOf(modelNode, anchorNode).forEach {
        it.onEditingChanged = {
            editingBox.isVisible = it.isNotEmpty()
        }
    }
    return anchorNode
}