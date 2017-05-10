# ndileber

ndileber官方网站  [(戳这里dileber.dreamsoso.com)][18]

学习google的MVP框架，来重构dileber，包含很多实用工具。  

dileber 包含了网络框架Retrofit ，并对其进行了封装，支持cookie，并对cookie失效进行了处理。  



实用工具1.BottomBar用来创建菜单栏使用如下  


mBar= (BottomBar) findViewById(R.id.bar);
        mBar.addItem(new BottomBarTab(this,R.string.home2,BottomBarTab.TYPE_FONT)).
                addItem(new BottomBarTab(this,R.string.bubbles,BottomBarTab.TYPE_FONT)).
                addItem(new BottomBarTab(this,R.string.newspaper,BottomBarTab.TYPE_FONT)).
                addItem(new BottomBarTab(this,R.string.cogs,BottomBarTab.TYPE_FONT));

mBar.setOnClickItemMenu(new BottomBar.OnClickItemMenu() {
            @Override
            public void onClickItem(int position) {
           }
});

教程如下：  
[android开发（如何开发一个可以维护的好项目）：一 、定义基类][1]


[开发一个好项目：二、actvity简便跳转，创建菜单按钮，正确使用fragment][2]


[开发一个好项目：三、创建数据源，首先创建本地数据源][3]


[android开发（如何开发一个可以维护的好项目）：四 、项目结构][4]


[开发一个好项目：五、创建数据源，创建网络数据Service和网络数据源][5]


[开发一个好项目：六、创建数据源，创建数据仓库][6]


[开发一个好项目：七、创建Contract类，连接presenter和view][7]


[开发一个好项目：八、创建view][8]

[开发一个好项目：九、android奔溃日记记录系统][9]

[Android 框架Dileber ：一、ActivityManager的使用][10]

[Android 框架Dileber ：二、HPref的使用][11]

[Android 框架Dileber ：四、SelectDialog的使用][12]

[Android 框架Dileber ：三、HJson，HNetwork，HSafe的使用][14]

[android 框架Dileber ：五，提供一套免费使用的图标库，超级棒的工具][13]






  [1]: http://blog.csdn.net/s297165331/article/details/53006941
  [2]: http://blog.csdn.net/s297165331/article/details/53006947
  [3]: http://blog.csdn.net/s297165331/article/details/53006956
  [4]: http://blog.csdn.net/s297165331/article/details/53006959
  [5]: http://blog.csdn.net/s297165331/article/details/53006968
  [6]: http://blog.csdn.net/s297165331/article/details/53008061
  [7]: http://blog.csdn.net/s297165331/article/details/53008083
  [8]: http://blog.csdn.net/s297165331/article/details/53008096
  [9]: http://blog.csdn.net/s297165331/article/details/57077261
  [10]: http://blog.csdn.net/s297165331/article/details/53045048
  [11]: http://blog.csdn.net/s297165331/article/details/53045055
  [12]: http://blog.csdn.net/s297165331/article/details/57077342
  [13]: http://blog.csdn.net/s297165331/article/details/69684454
  [14]: http://blog.csdn.net/s297165331/article/details/69689390
  [18]: http://dileber.dreamsoso.com
