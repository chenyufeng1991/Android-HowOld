# Android-HowOld
&emsp;&emsp;&nbsp;该项目是基于Face++开发的一款人脸识别App，通过照片可以检测性别、年龄和照片中人物数量。是模仿微软(Microsoft)的HowOld应用开发的类似App。目前已经上线豌豆荚、魅族应用市场、搜狗手机助手和联想市场等多个安卓应用市场。同时我决定开源该项目，为更多的安卓应用开发者或者想进行人脸识别类应用的开发人员提供服务和便利。当然App中还有不少bug和可扩展的功能模块，也希望各位开发者为该项目贡献自己的code力量。
##1.项目简介
&emsp;&emsp;&nbsp;在前段时间，微软推出的一款产品非常火热，叫做“HowOld”,可以通过你提交的图片检测照片中人物的数量、性别和年龄。但是由于微软推出的只是网页版的产品，并没有在移动端提供App。我的该项目实现的是在Android上高仿微软的“HowOld”，并实现相同功能的Android App。你只要在手机图库中选择照片，并上传检测，几秒钟后就能在原来的照片上绘制出人物数量、性别和年龄的数据。系统主界面如下：
![Alt text](https://github.com/chenyufeng1991/Android-HowOld/raw/master/Screenshots/1.jpg)<br/><br/>
##2.开发指南
&emsp;&emsp;&nbsp;为了项目的开发效率和其他方面的考虑，我并没有使用微软提供的关于人脸识别的接口，而是采用了国内的[Face++](http://www.faceplusplus.com.cn/)来进行开发。Face++有良好的接口，为很多App提供服务，可以方便的进行人脸识别项目的开发。
###（1）创建应用
&emsp;&emsp;&nbsp;作为开发的需要，建议先去[Face++](http://www.faceplusplus.com.cn/)官网申请一个账号，然后创建一个应用，因为在开发中需要使用API Key和API Secret。创建应用如图：
![Alt text](https://github.com/chenyufeng1991/Android-HowOld/raw/master/Screenshots/4.png)<br/><br/>
###（2）获取API Key和API Secret
&emsp;&emsp;&nbsp;通过步骤（1）后，就能获取API Key和API Secret了。如图所示：
![Alt text](https://github.com/chenyufeng1991/Android-HowOld/raw/master/Screenshots/5.png)<br/><br/>
分别复制到如下代码中：
![Alt text](https://github.com/chenyufeng1991/Android-HowOld/raw/master/Screenshots/6.png)<br/><br/>
###（3）下载SDK
&emsp;&emsp;&nbsp;其实在该项目中，你不需要进行这一步操作，项目中已经集成了Face++的Java(Android)SDK了。如果你想要在其他平台下进行开发，Face++也提供了Python、iOS、PHP等SDK。可以到以下地方进行下载：
![Alt text](https://github.com/chenyufeng1991/Android-HowOld/raw/master/Screenshots/7.png)<br/><br/>
###（4）编译运行项目
&emsp;&emsp;&nbsp;通过以上步骤，你就可以进行编译运行项目了。期待你的code。
##3.开发建议
&emsp;&emsp;&nbsp;程序可能还存在bug，你也可以通过Face++提供的其他接口为App增加功能模块，你有的任何创意和想法都可以来实现。希望这个开源项目可以在大家的努力下越来越好,期待你的code。
##4.运行效果
###（1）选择图片
![Alt text](https://github.com/chenyufeng1991/Android-HowOld/raw/master/Screenshots/2.jpg)<br/><br/>
###（2）图片检测
![Alt text](https://github.com/chenyufeng1991/Android-HowOld/raw/master/Screenshots/3.jpg)<br/><br/>
##5.技术博客
我的个人技术博客：[http://blog.csdn.net/chenyufeng1991](http://blog.csdn.net/chenyufeng1991) 。欢迎大家访问！