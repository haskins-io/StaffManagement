package io.haskins.staffmanagement.dao

class RateDao private constructor() {

    companion object {

        @Volatile
        private var instance: RateDao? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: RateDao().also { instance = it}
        }
    }
}