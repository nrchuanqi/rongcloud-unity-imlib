#ifndef __RongCloudUnity
#define __RongCloudUnity
#import <Foundation/Foundation.h>
#import <RongIMLib/RongIMLib.h>
#import "UnityInterface.h"
#import "RongCloudUnityUtils.h"
@interface RongCloudUnity:NSObject<RCIMClientReceiveMessageDelegate,RCConnectionStatusChangeDelegate>
+(void)Instance;
@end
#endif

