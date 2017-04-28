//
// Created by Administrator on 2017/4/28.
//

#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_lyf_bookreader_utils_NativeHelper_getUrl(JNIEnv* env) {
    std::string url = "http://119.29.216.181:8080/book/";


    return env->NewStringUTF(url.c_str());
}