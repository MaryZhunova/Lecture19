package com.example.recipe.utils

import io.reactivex.Scheduler

interface ISchedulersProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
}