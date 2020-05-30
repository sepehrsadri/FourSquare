package com.sadri.foursquare.di.screen

import javax.inject.Scope

/**
 * Created by Sepehr Sadri on 5/31/2020.
 * sepehrsadri@gmail.com
 * Tehran, Iran.
 * Copyright © 2020 by Sepehr Sadri. All rights reserved.
 */
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class PerActivity

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class PerFragment