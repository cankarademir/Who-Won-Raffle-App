package com.cankarademir.kimkazandiapplication.services

import android.util.Log
import com.cankarademir.kimkazandiapplication.models.Data
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class jsoup {
    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }
        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }
        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    })
    private fun createTrustAllSSLContext(): SSLContext? {
        return try {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, SecureRandom())
            sslContext
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            null
        } catch (e: KeyManagementException) {
            e.printStackTrace()
            null
        }
    }

    // https://www.kimkazandi.com/cekilisler sayfasında ilk 8 çekiliş
    fun cekilisler(): List<Data> {
        val list = mutableListOf<Data>()
        try {
            val sslContext = createTrustAllSSLContext()
            if (sslContext != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
            }
            val url = "https://www.kimkazandi.com/cekilisler"
            val doc: Document = Jsoup.connect(url).timeout(15000).ignoreContentType(true).get()

            //sublist ile ilk 8 veriyi alma koşulu sağlandı
            val cekilisElements = doc.getElementsByClass("bg-transparent pb20").select(".col-sm-3.col-lg-3.item").subList(0, 8)
            var i = 0
            for (element in cekilisElements) {
                val imgUrl = element.getElementsByClass("img").select("img").attr("abs:src")
                val detailUrl = element.select("a").attr("abs:href")
                val title = element.select("h4").text()
                val time = element.select(".date.d-flex").get(0).text()
                val hediye = element.select(".date.d-flex").get(1).text()
                val kosul = element.select(".date.d-flex").get(2).text()

                //Detay sayfasına gidilir ve veriler alınır
                val doc2: Document = Jsoup.connect(detailUrl).ignoreContentType(true).get()
                val detail = doc2.getElementsByClass("secondGallery").select(".scrollbar-dynamic").get(0).text()
                val basTarih = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(1).text()
                val sonTarih = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(2).text()
                val cekTarih = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(3).text()
                val ilnTarih = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(4).text()
                val minharcama = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(5).text()
                val hediyeDeger = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(6).text()
                val hediyeSayi = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(7).text()

                val datalist = Data(i, false, detailUrl, imgUrl, title, time, hediye, kosul, "cekilisler/ilk8", detail, basTarih, sonTarih, cekTarih, ilnTarih, minharcama, hediyeDeger, hediyeSayi)
                Log.d("DATA", datalist.toString())
                list.add(datalist)
                i++
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return list
    }

    // Seçili sayfasalarda tüm çekilişleri getirir
    fun cekilisturler(): List<Data> {
        val list = mutableListOf<Data>()
        try {
            val sslContext = createTrustAllSSLContext()
            if (sslContext != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
            }
            // Çekilecek farklı türlerin çekilmesi için turler liste haline getirildi
            val Turs: MutableList<String> = mutableListOf(
                "yeni-baslayanlar",
                "bedava-katilim",
                "araba-kazan",
                "telefon-tablet-kazan",
                "tatil-kazan"
            )
            var i = 8
            for (Tur in Turs) {
                val url = "https://www.kimkazandi.com/cekilisler/$Tur"
                val doc: Document = Jsoup.connect(url).timeout(15000).ignoreContentType(true).get()
                val cekilisElements =
                    doc.getElementsByClass("bg-transparent pb20").select(".col-sm-3.col-lg-3.item")
                for (element in cekilisElements) {
                    val imgUrl = element.getElementsByClass("img").select("img").attr("abs:src")
                    val detailUrl = element.select("a").attr("abs:href")
                    val title = element.select("h4").text()
                    val time = element.select(".date.d-flex").get(0).text()
                    val hediye = element.select(".date.d-flex").get(1).text()
                    val kosul = element.select(".date.d-flex").get(2).text()

                    //Detay sayfasına gidilir ve veriler alınır
                    val doc2: Document = Jsoup.connect(detailUrl).ignoreContentType(true).get()
                    val detail = doc2.getElementsByClass("secondGallery").select(".scrollbar-dynamic").get(0).text()
                    val basTarih = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(1).text()
                    val sonTarih = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(2).text()
                    val cekTarih = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(3).text()
                    val ilnTarih = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(4).text()
                    val minharcama = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(5).text()
                    val hediyeDeger = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(6).text()
                    val hediyeSayi = doc2.getElementsByClass("info mainSocial").select(".kalanSure").get(7).text()

                    val datalist = Data(i, false, detailUrl, imgUrl, title, time, hediye, kosul, Tur, detail, basTarih, sonTarih, cekTarih, ilnTarih, minharcama, hediyeDeger, hediyeSayi)
                    Log.d("DATA", datalist.toString())
                    list.add(datalist)
                    i++
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return list
    }
}