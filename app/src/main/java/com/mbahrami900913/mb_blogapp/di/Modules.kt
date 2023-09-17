package com.mbahrami900913.mb_blogapp.di

import com.mbahrami900913.mb_blogapp.data.net.createApiService
import org.koin.dsl.module

val myModules= module {
    single { createApiService() }
}