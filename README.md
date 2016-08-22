# ndileber
学习google的MVP框架，来重构dileber，包含很多实用工具，activity context不用再乱传值了
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
