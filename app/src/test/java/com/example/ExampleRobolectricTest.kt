package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.PreloadedData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Super50 Tracker", appName)
  }

  @Test
  fun `verify super50 preloaded tests count`() {
    // 8 Part Tests + 11 Full Tests = 19 Tests
    assertEquals(19, PreloadedData.defaultTests.size)
    val partTest1 = PreloadedData.defaultTests.first { it.testNumber == 1 }
    assertEquals("PART TEST-1", partTest1.testName)
    assertEquals("04-Oct-2026", partTest1.testDate)
  }

  @Test
  fun `verify preloaded chapters have all PCM subjects`() {
    val chapters = PreloadedData.defaultChapters
    assertTrue(chapters.isNotEmpty())
    assertTrue(chapters.any { it.subject == "PHYSICS" })
    assertTrue(chapters.any { it.subject == "CHEMISTRY" })
    assertTrue(chapters.any { it.subject == "MATHEMATICS" })
  }
}
