package com.somacode.guides.service.tool

import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import org.springframework.mock.web.MockMultipartFile
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.annotation.PostConstruct

@Component
class MockTool {

    lateinit var imageApi: ImageApi
    lateinit var pdfApi: PdfApi


    @PostConstruct
    fun init() {
        val gsonConverter = GsonConverterFactory.create(GsonBuilder().setLenient().create())
        imageApi = Retrofit.Builder().baseUrl("https://picsum.photos").addConverterFactory(gsonConverter).build().create(ImageApi::class.java)
        pdfApi = Retrofit.Builder().baseUrl("https://lavamancer.com").addConverterFactory(gsonConverter).build().create(PdfApi::class.java)
    }

    interface ImageApi {
        @GET("/1920/1080?random")
        fun getImage(): Call<ResponseBody>
    }

    interface PdfApi {
        @GET("/document.pdf")
        fun getPdf(): Call<ResponseBody>
    }

    fun multipartFileImage(): MultipartFile {
        val body = imageApi.getImage().execute().body()!!
        return MockMultipartFile("image", "image.jpg", "image/jpg", body.bytes())
    }

    fun multipartFilePdf(): MultipartFile {
        val body = pdfApi.getPdf().execute().body()!!
        return MockMultipartFile("document", "document.pdf", "application/pdf", body.bytes())
    }

}