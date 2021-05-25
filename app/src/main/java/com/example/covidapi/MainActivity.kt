package com.example.covidapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.casos.*
import kotlinx.android.synthetic.main.casos_hoje.*
import kotlinx.android.synthetic.main.imagem.*
import kotlinx.android.synthetic.main.mortes.*
import kotlinx.android.synthetic.main.populacao.*
import kotlinx.android.synthetic.main.recuperados.*
import kotlinx.android.synthetic.main.recuperados_hoje.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    val API_URL = "https://disease.sh/v3/covid-19/countries/brazil?strict=true"

    var totalCasos: Long = 0
    var totalMortes: Long = 0
    var populacao: Long = 0
    var totalCasosHoje: Long = 0
    var totalRecuperados: Long = 0
    var recuperadosHoje: Long = 0
    var morteHoje: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buscaDados()

    }
    fun buscaDados(){
        doAsync {
            //acessar API e buscar seu resultado
            val resposta = URL(API_URL).readText()
            totalCasos = JSONObject(resposta).getLong("cases")
            totalMortes = JSONObject(resposta).getLong("deaths")
            populacao = JSONObject(resposta).getLong("population")
            totalCasosHoje = JSONObject(resposta).getLong("todayCases")
            totalRecuperados = JSONObject(resposta).getLong("recovered")
            recuperadosHoje = JSONObject(resposta).getLong("todayRecovered")
            morteHoje = JSONObject(resposta).getLong("todayDeaths")
            val f = NumberFormat.getNumberInstance(Locale("pt", "br"))
            val date: Date = Date()
            val formato = "dd/MM/yyyy HH:mm:ss"
            val format = SimpleDateFormat(formato)
            val dataFormatada = format.format(date)

            val totalCasosFormatado = f.format(totalCasos)
            val totalMortesFormatada = f.format(totalMortes)
            val populacaoFormatada = f.format(populacao)
            val totalCasosHojeFormatada = f.format(totalCasosHoje)
            val totalRecuperadosFormatada = f.format(totalRecuperados)
            val recuperadosHojeFormatada = f.format(recuperadosHoje)
            val morteHojeFormatada = f.format(morteHoje)

            uiThread {
                // alert ("$cotacaoBitcoin").show()
                //alert("$totalCasosFormatado").show()
                txtCasos.setText("$totalCasosFormatado   $dataFormatada")
                txtTotalMortes.setText("$totalMortesFormatada       Mortes Hoje: $morteHojeFormatada")
                txtPopulacao.setText("$populacaoFormatada")
                txtCasosHoje.setText("$totalCasosHojeFormatada")
                txtRecuperados.setText("$totalRecuperadosFormatada")
                txtRecuperadosHoje.setText("$recuperadosHojeFormatada")

            }
        }
    }
}