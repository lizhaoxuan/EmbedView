#EmbedView
嵌入式View框架

================

##前言

**一朝被蛇咬十年怕井绳。**刚开始实习接触到的第一个项目，因为是企业内部项目，所以在用户体验方面设计的并不严谨，就比如：所有的列表页面在数据为空时没有做任何处理，就是空着摆在那里。那么实际上我们应该根据现实为空的原因给用户以现实。


**结构图**

	RootView(FrameLayout){
		ToolBar
		ContentView(LinearLayout){
			E-TopWidge
			UserView
			E-BottomWidget
		}
		coverWidgets(ArrayMap<Integer, View>)[
			0:Loading(gone)
			1:TipView(match,match,gone)
			2:customView
			...
		]
	}


