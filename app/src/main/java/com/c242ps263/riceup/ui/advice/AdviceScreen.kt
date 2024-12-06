package com.c242ps263.riceup.ui.advice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.c242ps263.core.theme.RiceUpTheme
import com.c242ps263.riceup.data.model.Advice
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun AdviceScreen(
    advice: Advice
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(vertical = 8.dp)
        ) {
            Text("Saran dari kami",
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Spacer(Modifier.height(20.dp))
            MarkdownText(
                markdown = advice.text,
                style = TextStyle(
                    color = Color.Black
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdvicePreview() {
    RiceUpTheme {
        AdviceScreen(
            advice = Advice(
                "Pak/Bu, tanaman padinya terkena penyakit *leaf blast*.  Ini perlu ditangani segera!\n\n**Langkah-langkah:**\n\n1. **Identifikasi:** Pastikan benar-benar *leaf blast* (bercak coklat lonjong, kering, dan meluas).  Jika ragu, konsultasi petugas pertanian setempat.\n\n2. **Pengendalian:** Semprotkan fungisida **Tricyclazole** (misal, *Triazol*)  atau **Isoprothiolane** (misal, *Isopro*) sesuai dosis anjuran pada kemasan.  Semprot merata pada bagian tanaman yang terkena dan sekitarnya. Ulangi penyemprotan 7-10 hari sekali selama 2-3 kali, atau sesuai petunjuk kemasan.\n\n3. **Pupuk:** Berikan pupuk **NPK** (misal, *Phonska* atau *Petroganik*)  dengan dosis sesuai anjuran dan kebutuhan tanaman.  Pupuk yang seimbang membantu meningkatkan daya tahan tanaman terhadap penyakit.  Berikan secara merata di sekitar tanaman.\n\n4. **Sanitasi & Drainase:** Pastikan sawah terbebas dari gulma.  Buat drainase yang baik agar air tidak menggenang, karena kelembapan tinggi mendukung perkembangan jamur penyebab *leaf blast*.  Setelah panen, bersihkan sisa-sisa tanaman dan bakar untuk memutus siklus penyakit.\n\n**Catatan:** Selalu ikuti petunjuk penggunaan pada kemasan obat dan pupuk. Gunakan alat pelindung diri (APD) saat menyemprot pestisida.\n\n\n**Penting:**  Informasi ini bersifat saran umum.  Untuk hasil terbaik, konsultasikan dengan petugas penyuluh pertanian setempat untuk diagnosis dan rekomendasi yang lebih tepat sesuai kondisi sawah Anda.\n"
            )
        )
    }
}