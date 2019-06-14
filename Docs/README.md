## ǰ��׼��

    ���� [Unity IMLib](https://github.com/rongcloud/rongcloud-unity-imlib) ���� IMLib SDK 2.9.17 �汾Ϊ����ʵ�ֵĿ�Դ��Ŀ��֧�� Android��iOS���������ڼ���ʹ�ù�����������������ύ�� GitHub �� Issues �У����Ƽ���֧����Ա���� 1 ���������ڻظ����⣬лл�������Ƶ������֧�֡�

### ע�Ὺ�����˺�

��ǰ��[���ƹٷ���վ](https://developer.rongcloud.cn/signup)ע�Ὺ�����ʺš�ע��ʱ������Ҫ�ṩ��ʵ��������ֻ��ţ��Է�����������������Ҫ֪ͨ���ڽ���ʱ���ܹ���ϵ������
�����û���ṩ��ȷ���õ�������ֻ��ţ�������ʱ���ܹر�����Ӧ�á�

### ����Ӧ��

ע���˿������ʺ�֮���ڽ��п��� App ֮ǰ������Ҫǰ��[���ƿ����߿���̨](https://developer.rongcloud.cn/signup)����Ӧ�á�
������Ӧ��֮��������Ӧ���У����Զ��������׻���������������������������

![](https://user-images.githubusercontent.com/1709072/55678058-2bcd1280-5926-11e9-867f-b6964b1bdbaf.png)

��������ר�����ڿ������ԣ���������ר����������Ӧ��������ʽ���á����׻����໥���룬ÿ������������Ӧ�� App Key �� App Secret������������Ϣ����ͨ��

### ��ȡ Token

Token ��Ϊ�û����ƣ�App Key ������ App ��Ψһ��ʶ��Token ������ App �ϵ�ÿһ���û��������Ȩ������
������ͨ���ύ userId ����Ϣ�����һ�����û���Ӧ�� Token����ʹ����� Token ��Ϊ���û���Ψһ���ƾ֤�������û�����ͨ�š�

Token ����Ҫ�����������Ȩ�Ͱ�ȫ����˲���ͨ���ͻ���ֱ�ӷ������Ʒ�������ȡ Token��������ͨ�� Server API �����Ʒ�������ȡ Token ���ظ����� App������֮������ʱʹ�á�
��ϸ������ο� [Server ����ָ��](https://www.rongcloud.cn/docs/server.html#��ȡ_Token_����) �е��û�����ͻ�ȡ Token ����С�ڡ�

> Ϊ�˷������ڼ��ɺͲ��Թ�����ʹ�ã����ǻ��ṩ�� API ���Թ��ߣ��������ܲ������������ʱ������ֱ��ͨ������ userId �� name ����� Token��
> ��������ƿ�����ƽ̨����������Ե�Ӧ�ã������˵���ѡ�� ��[API ����](https://developer.rongcloud.cn/apitool/qmZqBYUO1SBO+jDieKo=)�����ɡ�

## SDK ����

���� [Unity IMLib](https://github.com/rongcloud/rongcloud-unity-imlib) �� rongcloud_unity.unitypackage ���� Unity ���̡�
�� RongCloud.prefab ������������������
![image](https://raw.githubusercontent.com/rongcloud/rongcloud-unity-imlib/master/Docs/images/dropprefab.png)

## �����÷�

### ��ʼ�� SDK

����ʹ������ SDK ���й���֮ǰ���������ȵ��ô˷�����ʼ�� SDK���� App ���������������У���ֻ��Ҫ�� SDK ��ʼ��һ�Ρ�

```c
using cn.rongcloud.sdk;

RongCloud.init(appKey);
```

### ���ӷ�����

�� App �����������ڣ���ֻ��Ҫ����һ�δ˷��������Ʒ������������ӡ�֮����������������쳣���� App ��ǰ��̨���л��ȣ�SDK ���Ḻ���Զ�������
SDK ��� App ��ǰ��̨�͸�������״�������������Ӻ��������Ƶ��Ż�������������һ�� `connect` ���ɣ����ཻ�� SDK ����
�������Ѿ��ֶ������ӶϿ�������������Ҫ�Լ����ֶ�������

```c
using cn.rongcloud.sdk;
void Start()
{
    //���ü���
    RCEvent.connectEvent+=connectEvent;
    RongCloud.connect(token);
}
void connectEvent(JsonData msg)
 {
     if(msg["status"].ToString()=="success"){
         Debug.Log("���ӳɹ�");
     }else if(msg["status"].ToString()=="error"){
         Debug.Log("����ʧ�ܣ�"+(ErrorCode)int.Parse(msg["errorCode"].ToString()));
     }else{
         Debug.Log("Token ����ȷ���ѹ���");
     }
}

void OnDestroy()
{
    //�Ƴ�����
    RCEvent.connectEvent-=connectEvent;
}
```

### ��������״̬

```c
void Start()
{
    //���ü���
    RCEvent.connectionStatusEvent+=connectionStatusEvent;
    RongCloud.connect(token);
}
void connectionStatusEvent(JsonData msg)
 {
     if(msg["status"].ToString()=="success"){
         Debug.Log("���ӳɹ�");
     }else if(msg["status"].ToString()=="error"){
         Debug.Log("����ʧ�ܣ�"+(ErrorCode)int.Parse(msg["errorCode"].ToString()));
     }else{
         Debug.Log("Token ����ȷ���ѹ���");
     }
}
void OnDestroy()
{
    //�Ƴ�����
    RCEvent.connectionStatusEvent-=connectionStatusEvent;
}
```

### �Ͽ�����

```c
RongCloud.logout();
```

## ��Ϣ�ӿ�

### ������Ϣ

����֧�����ġ�Ⱥ�鼰�������з���������Ϣ��ͼƬ��Ϣ��������Ϣ���ļ���Ϣ�����ı���Ϣ������λ����Ϣ��

#### �����ı���Ϣ

```c
void Start()
{
    //���ü���
    RCEvent.sendMessageEvent+=sendMessageEvent;
    RongCloud.connect(token);
}
void sendMessageEvent(JsonData msg)
 {
     if(msg["status"].ToString()=="success"&&msg["eventId"]=="eventID"){
         Debug.Log("���ͳɹ�");
     }else {
         Debug.Log("����ʧ�ܣ�"+(ErrorCode)int.Parse(msg["errorCode"].ToString()));
     }
}
void OnDestroy()
{
    //�Ƴ�����
    RCEvent.sendMessageEvent-=sendMessageEvent;
}
void sendMessage(){
    TextMessage tm = new TextMessage();
    var conversationType = ConversationType.CHATROOM;
    var targetId = "roomId"; // ���ݻỰ���͵Ĳ�ͬ���������û� ID�������� ID����Ⱥ ID ��
    tm.content = m_message.text;
    //eventID ÿ����ϢIdΨһ�������жϸ�����Ϣ�Ƿ��ͳɹ�
    RongCloud.sendMessage(conversationType, targetId, tm, "eventID");
}
```