LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := securekey
LOCAL_SRC_FILES := SecureKey.c

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE    := activation
LOCAL_SRC_FILES := Activation.c

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE    := secureparam
LOCAL_SRC_FILES := SecureParam.cpp

include $(BUILD_SHARED_LIBRARY)


