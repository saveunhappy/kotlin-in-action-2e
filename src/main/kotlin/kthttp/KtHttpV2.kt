package com.boycoder.kthttp

import com.boycoder.kthttp.annotations.Field
import com.boycoder.kthttp.annotations.GET
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Method
import java.lang.reflect.Proxy


interface ApiServiceV2 {
    @GET("/repo")
    fun repos(
        @Field("lang") lang: String,
        @Field("since") since: String
    ): RepoList
}


object KtHttpV2 {

    private val okHttpClient by lazy { OkHttpClient() }
    private val gson by lazy { Gson() }
//    var baseUrl = "https://trendings.herokuapp.com"
    var baseUrl = "http://localhost:8070"

    inline fun <reified T> create(): T {
        return Proxy.newProxyInstance(

            T::class.java.classLoader,
            arrayOf(T::class.java)
        ) { _, method, args ->

            return@newProxyInstance method.annotations
                .filterIsInstance<GET>()
                .takeIf { it.size == 1 }//method.annotations就是一个list，筛选出来还是一个list，然后it是一个list，那就取得下标
                ?.let { invoke("$baseUrl${it[0].value}", method, args) }
        } as T
    }

    fun invoke(url: String, method: Method, args: Array<Any>): Any? =
        method.parameterAnnotations
            .takeIf { method.parameterAnnotations.size == args.size }
            ?.mapIndexed { index, it -> Pair(it, args[index]) }//(@Field("lang"),Kotlin),获取的话就是first，second
            ?.fold(url, ::parseUrl)//这个就是拼接的，url是初始值，parseUrl就是mapIndexed得到的Pair,然后一直拼接，上次的结果再次拼接这次的，刚开始是http://localhost:8070/repo?lang=Kotlin，第二次就是http://localhost:8070/repo?lang=Kotlin&since=weekly
            ?.let { Request.Builder().url(it).build() }
            ?.let { okHttpClient.newCall(it).execute().body?.string() }
            ?.let { gson.fromJson(it, method.genericReturnType) }


    private fun parseUrl(acc: String, pair: Pair<Array<Annotation>, Any>) =
        pair.first.filterIsInstance<Field>()
            .first()
            .let { field ->
                if (acc.contains("?")) {
                    "$acc&${field.value}=${pair.second}"
                } else {
                    "$acc?${field.value}=${pair.second}"
                }
            }
}

fun main() {
    val data: RepoList = KtHttpV2.create<ApiServiceV2>().repos(
        lang = "Kotlin",
        since = "weekly"
    )

    println(data)
}
