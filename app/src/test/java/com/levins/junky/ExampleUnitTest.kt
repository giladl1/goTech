package com.levins.junky

import com.levins.junky.databinding.FragmentMapViewBinding
import com.levins.junky.repository.PileRepository
import com.levins.junky.ui.main.ItemActivityFragment
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(5, 2 + 2)
    }
    @Test
    fun sample_size(){
        MainActivity().calculateSampleSize(100,100)
    }
}