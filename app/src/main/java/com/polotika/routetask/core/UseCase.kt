package com.polotika.routetask.core

abstract class UseCase<Type, Params> {
    abstract suspend operator fun invoke(params: Params? = null):Type
}