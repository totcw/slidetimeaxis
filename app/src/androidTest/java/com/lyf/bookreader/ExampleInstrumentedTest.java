package com.lyf.bookreader;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.lyf.bookreader.application.MyApplication;
import com.lyf.bookreader.db.ChapterDao;
import com.lyf.bookreader.javabean.Chapter;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.lyf.slidetime", appContext.getPackageName());
    }

    @Test
    public void testDb() throws Exception{
        ChapterDao dao = MyApplication.getInstance().getDaoSession().getChapterDao();
        dao.insert(new Chapter("gl","测试"));
    }
}
