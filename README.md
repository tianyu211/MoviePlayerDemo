# 安卓本地视频播放器

标签（空格分隔）： 安卓

---

其实这个东西和安卓的音乐播放器差不多

[安卓音乐播放器](https://www.zybuluo.com/tianyu-211/note/612033)

基本思路都是一样的，无非是把视图换成了videoView，在扫描的时候把访问的uri从`MediaStore.Audio.Media.EXTERNAL_CONTENT_URI`换成了`MediaStore.Video.Media.EXTERNAL_CONTENT_URI`,其余不变。

效果图
![视频播放器](https://i.niupic.com/images/2016/12/29/R4WeAa.png)




