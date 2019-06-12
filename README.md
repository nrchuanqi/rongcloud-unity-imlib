# rongcloud-unity-imlib

���� Unity IMLib ���� IMLib SDK 2.9.17 �汾Ϊ����ʵ�ֵĿ�Դ��Ŀ��֧�� Android��iOS���������ڼ���ʹ�ù�����������������ύ�� GitHub �� Issues �У����Ƽ���֧����Ա���� 1 ���������ڻظ����⣬лл�������Ƶ������֧�֡�

## �ĵ�

## �÷�

### ��ʼ�����������Ʒ���

```c#
RongCloud.init(appKey);
RongCloud.connect(token);
```

### ������Ϣ

```c#
RCEvent.onReceiveMessageEvent += receiveMessage;
```

### ������Ϣ

```c#
TextMessage tm = new TextMessage();
tm.content = "��Ϣ���Ͳ��ԣ�";
RongCloud.sendMessage(ConversationType.CHATROOM, "testRoom", tm,(eventID++).ToString());
```

����ʾ����ο� [SampleScene]��

## ����ʾ��
���� rongcloud_unity.unitypackage ��
�� RongCloud/example/Scenes/SampleScene
�� RongCloudSdkExample.cs ������ appKey token ����Ϣ��
## ��Ŀ�ṹ
```
������ RongUnity (Unity ʾ��Ӧ��)
������ UnityPackage (Unity SDK ��)
������ lib
     ������ android (Android ԭ��ģ��)
     ������ IOS   (ios ԭ��ģ��)

