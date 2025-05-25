package com.example.nestedlazycolumnjetpack
import com.example.nestedlazycolumnjetpack.viewmodel.MainViewModel
import com.example.nestedlazycolumnjetpack.model.ImageItem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nestedlazycolumnjetpack.ui.theme.ChildDataClass
import com.example.nestedlazycolumnjetpack.ui.theme.NestedLazyColumnJetpackTheme
import com.example.nestedlazycolumnjetpack.ui.theme.ParentDataClass
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Mulai fetch data dari API
        mainViewModel.fetchImages()

        setContent {
            NestedLazyColumnJetpackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Ambil state images dari ViewModel sebagai Compose State
                    val imagesState = mainViewModel.images.collectAsState()

                    // Map dari List<ImageItem> ke List<ParentDataClass>
                    val parentItemList = mapImagesToParentData(imagesState.value)

                    MyApp(modifier = Modifier, parentItemList = parentItemList)
                }
            }
        }
    }

    // Fungsi konversi data dari API (ImageItem) ke data UI yang dibutuhkan (ParentDataClass)
    private fun mapImagesToParentData(imageItems: List<ImageItem>): List<ParentDataClass> {
        if (imageItems.isEmpty()) return emptyList()

        // Contoh grouping sederhana, kamu bisa sesuaikan sesuai kebutuhan
        // Misal buat satu kategori saja yang judulnya "API Images"
        val childList = imageItems.map {
            // Konversi ImageItem ke ChildDataClass yang pakai drawable,
            // tapi kamu harus modifikasi ChildDataClass supaya bisa pakai imageUrl (ganti int drawable jadi String url)
            ChildDataClass(imageUrl = it.imageUrl) // kalau pakai url, jangan pake painterResource, tapi Image Coil/Picasso dll
        }
        return listOf(ParentDataClass(title = "API Images", childList = childList))
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier, parentItemList: List<ParentDataClass>) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(parentItemList) { item ->
            ColumnItemUI(
                parentItem = item
            )
        }
    }
}

@Composable
fun ColumnItemUI(
    parentItem: ParentDataClass
) {
    Card(
        modifier = Modifier.padding(12.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        Text(
            text = parentItem.title,
            modifier = Modifier.padding(12.dp),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFC107)
        )
        LazyRow(
            modifier = Modifier.padding(12.dp)
        ) {
            items(parentItem.childList) { child ->
                RowItemUI(child = child)
            }
        }
    }
}

@Composable
fun RowItemUI(child: ChildDataClass) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .width(160.dp)
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xff201e1e))
    ) {
        AsyncImage(
            model = child.imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}