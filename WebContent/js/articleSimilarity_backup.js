Ext.require( [ 'Ext.grid.*', 'Ext.data.*', 'Ext.form.*',
		'Ext.layout.container.Column', 'Ext.tab.Panel' ]);

Ext.Loader.setConfig( {
	enabled : true
});
var loadMask;
var hideConfirmationMsg;
var showConfirmationMsg;
var contentPanel;
/* Hide the Confirmation Message */
hideConfirmationMsg = function() {
	var confMsgDiv = Ext.get('confirmationMessage');
	confMsgDiv.dom.innerHTML = "";
	confMsgDiv.dom.style.display = 'none';
}
/* Show Confirmation Message */
showConfirmationMsg = function(msg) {
	var confMsgDiv = Ext.get('confirmationMessage');
	confMsgDiv.dom.innerHTML = msg;
	confMsgDiv.dom.style.display = 'inline-block';
}
function generateJSONRequestForSimilarity()
{
	var reviewGen={};
	var articleNameLeft=Ext.getCmp('articleNameLeft').getValue();
	if(articleNameLeft)
	{
		reviewGen.articleNameLeft=articleNameLeft;
	}
	var articleNameRight=Ext.getCmp('articleNameRight').getValue();
	
	if(articleNameRight!=null)
	{
		reviewGen.articleNameRight=articleNameRight;
	}
	var typeCombo=Ext.getCmp('typeCombo').getValue();

	if(typeCombo!=null)
	{
		reviewGen.typeCombo=typeCombo;
	}
	return reviewGen;
}


function doJSONRequestForSimilarity(reviewGen, urlLink)
{
loadMask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading"});
loadMask.show();
Ext.Ajax.request({	
method: 'POST',
processData: false,
contentType:'application/json',
jsonData: Ext.encode(reviewGen),
url:urlLink, 
success: function(response) {
var data;
if (response){
			 
			var JsonData = Ext.decode(response.responseText);
				if(JsonData.ebErrors != null){
					var errorObj=JsonData.ebErrors;
					for(i=0;i<errorObj.length;i++)
					{
							var value=errorObj[i].errMessage;
							showConfirmationMsg(value);
					}
					loadMask.hide();
				}
				else
				{
					var value=JsonData.message;
					var data =
					showConfirmationMsg(value);
					loadMask.hide();
					contentPanel.hide();
					
					var dataToBeProcessed =JsonData.model;
					
					processDataAndFillGrid(dataToBeProcessed);
					
				}
			}
},
failure : function(data) {
loadMask.hide();
}
});
}

var mainColumns=[
					{
     				header : 'Union Sum',
     				dataIndex : 'unionSum',
     				sortable:true,
     				width:50
					},
					{
     				header : 'Intersection Sum',
     				dataIndex : 'intersectionSum',
     				sortable:true,
     				width:50
					},
					{
     				header : 'Similarity Status',
     				dataIndex : 'similarityStatus',
     				sortable:true,
     				width:50
					},{
     				header : 'Similarity Measure Message',
     				dataIndex : 'similarityMeasureMsg',
     				sortable:true,
     				width:50
					}
					
];


var mainStoreTemp=Ext.create('Ext.data.ArrayStore',{
				storeId:'mainStore',
				fields: [
						{name:'unionSum', type:'double'},
						{name:'intersectionSum',type:'double'},
						{name:'similarityStatus',type:'boolean'},
						{name:'similarityMeasureMsg',type:'string'}
					]
});
var mainOutputGrid= Ext.create('Ext.grid.Panel', {
		title:'Main Output Grid',
		forceFit : true,
		id : 'mainOutputGrid',
		store : mainStoreTemp,
		columns : mainColumns,
		width:200,
		height:200,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : 'mainOutputContainer',
		collapsible:true
	});






var tfColumns=[
					{
     				header : 'Key Phrase Name',
     				dataIndex : 'keyPhraseName',
     				sortable:true,
     				width:50
					},
					{
     				header : 'Count',
     				dataIndex : 'count',
     				sortable:true,
     				width:50
					},
					{
     				header : 'Frequency',
     				dataIndex : 'freq',
     				sortable:true,
     				width:50
					},{
     				header : 'Text Frequency',
     				dataIndex : 'textFreq',
     				sortable:true,
     				width:50
					}
					
];


var tfDetailsLeftArticleGridStore=Ext.create('Ext.data.ArrayStore',{
	storeId:'tfDetailsLeftArticleGridStore',
	fields: [
			{name:'keyPhraseName', type:'string'},
			{name:'count',type:'int'},
			{name:'freq',type:'int'},
			{name:'textFreq',type:'double'}
			]
});

var tfDetailsRightArticleGridStore=Ext.create('Ext.data.ArrayStore',{
	storeId:'tfDetailsRightArticleGridStore',
	fields: [
			{name:'keyPhraseName', type:'string'},
			{name:'count',type:'int'},
			{name:'freq',type:'int'},
			{name:'textFreq',type:'double'}
			]
});

var tfDetailsRightArticleGrid = Ext.create('Ext.grid.Panel', {
		title:'Text Frequency Right Article Grid',
		forceFit : true,
		id : 'tfDetailsRightArticleGrid',
		store : tfDetailsRightArticleGridStore,
		columns : tfColumns,
		width:200,
		height:200,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : 'tfDetailsRightArticleGridContainer',
		collapsible:true
	});

var tfDetailsLeftArticleGrid = Ext.create('Ext.grid.Panel', {
		title:'Text Frequency Left Article Grid',
		forceFit : true,
		id : 'tfDetailsLeftArticleGrid',
		store : tfDetailsLeftArticleGridStore,
		columns : tfColumns,
		width:200,
		height:200,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : 'tfDetailsLeftArticleGridContainer',
		collapsible:true
	});



var unionSetColumns=[
					{
     				header : 'Union Set',
     				dataIndex : 'unionSet',
     				sortable:true,
     				width:50
     			}
];


var unionSetStore=Ext.create('Ext.data.ArrayStore',{
	storeId:'unionSetStore',
	fields: [
			{name:'unionSet', type:'string'}
			]
});

var unionSetGrid = Ext.create('Ext.grid.Panel', {
		title:'Union Grid',
		forceFit : true,
		id : 'unionSetGrid',
		store : unionSetStore,
		columns : unionSetColumns,
		width:200,
		height:200,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : 'unionSetGridContainer',
		collapsible:true
	});



var intersectionColumns=[
					{
     				header : 'Intersection Set',
     				dataIndex : 'instersectionSet',
     				sortable:true,
     				width:50
     			}

];

var instersectionSetStore=Ext.create('Ext.data.ArrayStore',{
	storeId:'instersectionSetStore',
	fields: [
			{name:'instersectionSet', type:'string'}
			]
});

var intersectionSetGrid = Ext.create('Ext.grid.Panel', {
		title:'Intersection Grid',
		forceFit : true,
		id : 'intersectionSetGrid',
		store : instersectionSetStore,
		columns : intersectionColumns,
		width:200,
		height:200,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : 'intersectionSetGridContainer',
		collapsible:true
	});





var freqColumns=[
     			{
     				header : 'Frequency ID',
     				dataIndex : 'freqId',
     				sortable:true,
     				width:50
     			},
     			{
     				header : 'Article Name',
     				dataIndex : 'articleName',
     				sortable:true,
     				width    :50
     			},
     			{
     				header : 'Sentence ID',
     				dataIndex : 'sentenceId',
     				sortable:true,
     				width    :50
     			},
     			{
     				header : 'Frequency Key Phrase',
     				dataIndex : 'freqKeyPhrase',
     				sortable:true,
     				width    :100
     			},
     			{
     				header : 'Type',
     				dataIndex : 'type',
     				sortable:true,
     				width    :50
     			},
     			{
     				header : 'Frequency Phrase',
     				dataIndex : 'freqPhrase',
     				sortable:true,
     				width    :100
     			},
     			{
     				header : 'Frequency Structure3',
     				dataIndex : 'freqStructure3',
     				sortable:true,
     				width    :100
     			},
     			{
     				header : 'Frequency Adjective',
     				dataIndex : 'freqAdjective',
     				sortable:true,
     				width    :100
     			},
     			{
     				header : 'Frequency PhraseG',
     				dataIndex : 'freqPhraseG',
     				sortable:true,
     				width    :100
     			},
     			{
     				header : 'Frequency Structure1',
     				dataIndex : 'freqStructure1',
     				sortable:true,
     				width    :100
     			},
     			{
     				header : 'Frequency Structure2',
     				dataIndex : 'freqStructure2',
     				sortable:true,
     				width    :100
     			}
     			];



	var		leftArticleFreqInfoListGridStore = Ext.create('Ext.data.ArrayStore', {
				id : 'leftArticleFreqInfoListGridStore',
				name : 'leftArticleFreqInfoListGridStore',
				fields : [ 
				          {name:'articleName', mapping:'articleName',type:'string'},
				          {name:'sentenceId',mapping:'sentenceId',type:'int'},
				          {name:'freqKeyPhrase',mapping:'freqKeyPhrase',type:'int'},
				          {name:'type',mapping:'type',type:'string'},
				          {name:'freqPhrase',mapping:'freqPhrase',type:'int'},
				          {name:'freqStructure3',mapping:'freqStructure3',type:'int'},
				          {name:'freqAdjective',mapping:'freqAdjective',type:'int'},
				          {name:'freqPhraseG',mapping:'freqPhraseG',type:'int'},
				          {name:'freqStructure1',mapping:'freqStructure1',type:'int'},
				          {name:'freqStructure2',mapping:'freqStructure2',type:'int'}
				          ]
			});
			

	

	
	var		rightArticleFreqInfoListGridStore = Ext.create('Ext.data.ArrayStore', {
				id : 'rightArticleFreqInfoListGridStore',
				name : 'rightArticleFreqInfoListGridStore',
				fields : [ 
				          {name:'articleName', mapping:'articleName',type:'string'},
				          {name:'sentenceId',mapping:'sentenceId',type:'int'},
				          {name:'freqKeyPhrase',mapping:'freqKeyPhrase',type:'int'},
				          {name:'type',mapping:'type',type:'string'},
				          {name:'freqPhrase',mapping:'freqPhrase',type:'int'},
				          {name:'freqStructure3',mapping:'freqStructure3',type:'int'},
				          {name:'freqAdjective',mapping:'freqAdjective',type:'int'},
				          {name:'freqPhraseG',mapping:'freqPhraseG',type:'int'},
				          {name:'freqStructure1',mapping:'freqStructure1',type:'int'},
				          {name:'freqStructure2',mapping:'freqStructure2',type:'int'}
				          ]
			});
			
var rightArticleFreqInfoListGrid = Ext.create('Ext.grid.Panel', {
		title:'Right Article Frequency Information',
		forceFit : true,
		id : 'rightArticleFreqInfoListGrid',
		store : rightArticleFreqInfoListGridStore,
		columns : freqColumns,
		width:2000,
		height:200,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : 'rightArticleFreqInfoListGridContainer',
		collapsible:true
	});

var leftArticleFreqInfoListGrid = Ext.create('Ext.grid.Panel', {
		title:'Left Article Frequency Information',
		forceFit : true,
		id : 'leftArticleFreqInfoListGrid',
		store : leftArticleFreqInfoListGridStore,
		columns : freqColumns,
		width:2000,
		height:200,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : 'leftArticleFreqInfoListGridContainer',
		collapsible:true
	});

	
var msgListColumns=[
					{
						header : 'Message',
						dataIndex : 'msg',
						sortable:true,
						width:50
					}
					];	

var msgListStore=Ext.create('Ext.data.ArrayStore',{
	storeId:'msgListStore',
	fields: [
			{name:'msg', type:'string'}
			]
});

var msgListGrid = Ext.create('Ext.grid.Panel', {
		title:'Message Information List',
		forceFit : true,
		id : 'msgListGrid',
		store : msgListStore,
		columns : msgListColumns,
		width:2000,
		height:200,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : 'msgListGridContainer',
		collapsible:true
	});

		


dataToBeProcessed = function(dataToBeProcessed){
	
	//Main Output Grid
	var unionSum =dataToBeProcessed.unionSum;
	var intersectionSum=dataToBeProcessed.intersectionSum;
	var similarityStatus=dataToBeProcessed.similarityStatus;
	var similarityMeasureMsg=dataToBeProcessed.similarityMeasureMsg;
	
	var array = new Array();
	var dataTemp={'unionSum':unionSum,'intersectionSum':intersectionSum,'similarityStatus':similarityStatus,'similarityMeasureMsg':similarityMeasureMsg};
	array.push(dataTemp);

	var mainStoreTemp=Ext.getCmp('mainOutputGrid').getStore();
	mainStoreTemp.loadData(array);
	
	//Message List
	var msgList =dataToBeProcessed.msgList;
	var msgListLength=msgList.length;
	
	var msgListArray = new Array();
	
	for(i=0;i<msgListLength;i++){
		var dataTemp={'msg':msgList[i]}
		msgListArray.push(dataTemp);
	}
	
	var msgListStore=Ext.getCmp('msgListGrid').getStore();
	msgListStore.loadData(array);
	
	
	
					
	
	//Text Frequency Details Grid
	
	var textFreqLeftArticleList = dataToBeProcessed.textFreqLeftArticle;
	var lengthOfLeftArticles =textFreqLeftArticleList.length;
	var tfArray = new Array();
	for(i=0;i<=lengthOfLeftArticles;i++){
		
	var keyPhraseName=textFreqLeftArticleList[i].keyPhraseName;
	var count=textFreqLeftArticleList[i].count;
	var freq=textFreqLeftArticleList[i].freq;
	var textFreq=textFreqLeftArticleList[i].textFreq;
		
	var dataTemp={'keyPhraseName':keyPhraseName,'count':count,'freq':freq,'textFreq':textFreq};
	tfArray.push(dataTemp);
	}
	var tfDetailsLeftArticleGridStore=Ext.getCmp('tfDetailsLeftArticleGrid').getStore();
	tfDetailsLeftArticleGridStore.loadData(tfArray);
	
	//Right Text Frequency Details Grid
	
	var textFreqRightArticleList = dataToBeProcessed.textFreqRArticle;
	var lengthOfRightArticles =textFreqLeftArticleList.length;
	var tfArrayR = new Array();
	for(i=0;i<lengthOfRightArticles;i++){
		
	var keyPhraseName=textFreqRightArticleList[i].keyPhraseName;
	var count=textFreqRightArticleList[i].count;
	var freq=textFreqRightArticleList[i].freq;
	var textFreq=textFreqRightArticleList[i].textFreq;
		
	var dataTemp={'keyPhraseName':keyPhraseName,'count':count,'freq':freq,'textFreq':textFreq};
	tfArrayR.push(dataTemp);
	}
	var tfDetailsRightArticleGridStore=Ext.getCmp('tfDetailsRightArticleGrid').getStore();
	tfDetailsRightArticleGridStore.loadData(tfArrayR);
	
	//unionSet Store
	
	var unionSetList =dataToBeProcessed.unionSet;
	var lengthUnionSet =unionSetList.length;
	var unionSetArray = new Array();
	
	for(i=0;i<=lengthUnionSet;i++){
		var unionSet=unionSetList[i].unionSet
		var dataTemp={'unionSet':unionSet};
		unionSetArray.push(dataTemp);
	}
	
	var unionSetGridStore=Ext.getCmp('unionSetGrid').getStore();
	unionSetGridStore.loadData(unionSetArray);
	
	
	
	//Intersection Store
	
	var intersectionSetList =dataToBeProcessed.instersectionSet;
	var lengthIntersectionSet =intersectionSetList.length;
	var intersectionSetArray = new Array();
	
	for(i=0;i<=lengthIntersectionSet;i++){
		var intersectionSet=intersectionSetList[i].intersectionSet
		var dataTemp={'intersectionSet':intersectionSet};
		intersectionSetArray.push(dataTemp);
	}
	
	var intersectionSetGridStore=Ext.getCmp('intersectionSetGrid').getStore();
	intersectionSetGridStore.loadData(intersectionSetArray);
	
	//Frequency Computation Store Left Article
	var leftArticleFreqInfoList =dataToBeProcessed.leftArticleFreqInfo;
	
	var leftArticleFreqInfoListLength =leftArticleFreqInfoList.length;
	
	var leftArticleFreqInfoListArray = new Array();
	
	for(i=0;i<leftArticleFreqInfoListLength;i++){
		var freqId=leftArticleFreqInfoList[i].freqId;
		var articleName=leftArticleFreqInfoList[i].articleName;
		var sentenceId=leftArticleFreqInfoList[i].sentenceId;
		var freqKeyPhrase=leftArticleFreqInfoList[i].freqKeyPhrase;
		var type=leftArticleFreqInfoList[i].type;
		var freqPhrase=leftArticleFreqInfoList[i].freqPhrase;
		var freqStructure3=leftArticleFreqInfoList[i].freqStructure3;
		var freqAdjective=leftArticleFreqInfoList[i].freqAdjective;
		var freqPhraseG=leftArticleFreqInfoList[i].freqPhraseG;
		var freqStructure1=leftArticleFreqInfoList[i].freqStructure1;
		var freqStructure2=leftArticleFreqInfoList[i].freqStructure2;
	
		var dataTemp={'freqId':freqId,'articleName':articleName,'sentenceId':sentenceId,'freqKeyPhrase':freqKeyPhrase,
					  'type':type,'freqPhrase':freqPhrase,'freqStructure3':freqStructure3,'freqAdjective':freqAdjective,
					  'freqPhraseG':freqPhraseG,'freqStructure1':freqStructure1,'freqStructure2':freqStructure2};
		leftArticleFreqInfoListArray.push(dataTemp);
	}
	
	var leftArticleFreqInfoListGridStore=Ext.getCmp('leftArticleFreqInfoListGrid').getStore();
	leftArticleFreqInfoListGridStore.loadData(leftArticleFreqInfoListArray);
	
	
	//Frequency Computation Store Right Article
	var rightArticleFreqInfoList =dataToBeProcessed.rightArticleFreqInfo;
	
	var rightArticleFreqInfoListLength =rightArticleFreqInfoList.length;
	
	var rightArticleFreqInfoListArray = new Array();
	
	for(i=0;i<rightArticleFreqInfoListLength;i++){
		var freqId=rightArticleFreqInfoList[i].freqId;
		var articleName=rightArticleFreqInfoList[i].articleName;
		var sentenceId=rightArticleFreqInfoList[i].sentenceId;
		var freqKeyPhrase=rightArticleFreqInfoList[i].freqKeyPhrase;
		var type=rightArticleFreqInfoList[i].type;
		var freqPhrase=rightArticleFreqInfoList[i].freqPhrase;
		var freqStructure3=rightArticleFreqInfoList[i].freqStructure3;
		var freqAdjective=rightArticleFreqInfoList[i].freqAdjective;
		var freqPhraseG=rightArticleFreqInfoList[i].freqPhraseG;
		var freqStructure1=rightArticleFreqInfoList[i].freqStructure1;
		var freqStructure2=rightArticleFreqInfoList[i].freqStructure2;
	
		var dataTemp={'freqId':freqId,'articleName':articleName,'sentenceId':sentenceId,'freqKeyPhrase':freqKeyPhrase,
					  'type':type,'freqPhrase':freqPhrase,'freqStructure3':freqStructure3,'freqAdjective':freqAdjective,
					  'freqPhraseG':freqPhraseG,'freqStructure1':freqStructure1,'freqStructure2':freqStructure2};
		rightArticleFreqInfoListArray.push(dataTemp);
	}
	
	var rightArticleFreqInfoListGridStore=Ext.getCmp('rightArticleFreqInfoListGrid').getStore();
	rightArticleFreqInfoListGridStore.loadData(rightArticleFreqInfoListArray);
	
	
		
}




Ext
		.onReady(function() {
			
			Ext.define('articleModel', {
				extend : 'Ext.data.Model',
				fields : [ 
				           {name:'articleName', mapping:'articleName',type:'string'}
				         ],
				idProperty: 'articleName'
			});
			
			
			var articleModelStore = Ext.create('Ext.data.Store', {
				id : 'articleModelStore',
				name : 'articleModelStoreName',
				model : 'articleModel',
				proxy : {
					type : 'ajax',
					url :contextPath+'/review/retriveAllArticleNames.do',
					actionMethods:{
						read:'POST'
					},
					reader : {
						type :'json',
						root:'model'
					}
				},
				listeners:
				{
					'load':function(store, records){
				}			
				},
				autoLoad : true
			});
			articleModelStore.load();
			
			    
			Ext.define('typeModel', {
				extend : 'Ext.data.Model',
				fields : [ 
				           {name:'typeName', mapping:'typeName',type:'string'}
						 ],
				idProperty: 'typeName'
			});
			 

			var typeStore = Ext.create('Ext.data.Store', {
				id : 'typeStoreId',
				name : 'typeStoreName',
				model : 'typeModel',
				proxy : {
					type : 'ajax',
					url :contextPath+'/review/typeStore.do',
					actionMethods:{
						read:'POST'
					},
					reader : {
						type :'json',
						root:'model'
					}
					
				},
				listeners:
				{
					'load':function(store, records){
				}			
				},
				autoLoad : true
			});
			typeStore.load();
			
				

			 contentPanel = Ext
					.create(
							'Ext.form.Panel',
							{
								title : 'Article Similarity Input',
								width : 800,
								height : 300,
								autoScroll : true,
								renderTo:Ext.getBody(),
								collapsible:true,
								defaults : {
										padding:'0 0 0 25',
									labelAlign : 'top'
								},
								layout : {
									type : 'table',
									columns : 1
								},
								items : [
										{
											xtype : 'combo',
											labelAlign : 'top',
											width : 150,
											fieldLabel : 'Left Article Name',
											id : 'articleNameLeft',
											name : 'articleNameLeft',
											queryMode : 'local',
											displayField : 'articleName',
											valueField : 'articleName',
											triggerAction : 'all',
											store : articleModelStore,
											listeners : {	
												'select' : function(combo,records) {
											
												}
												}
										},{
											xtype : 'combo',
											labelAlign : 'top',
											width : 150,
											fieldLabel : 'Right Article Name',
											id : 'articleNameRight',
											name : 'articleNameRight',
											queryMode : 'local',
											displayField : 'articleName',
											valueField : 'articleName',
											triggerAction : 'all',
											store : articleModelStore,
											listeners : {	
												'select' : function(combo,records) {
											
												}
												}
										},{
											xtype : 'combo',
											labelAlign : 'top',
											width : 150,
											fieldLabel : 'Type',
											id : 'typeCombo',
											name : 'typeCombo',
											queryMode : 'local',
											displayField : 'typeName',
											valueField : 'typeName',
											triggerAction : 'all',
											store : typeStore,
											listeners : {	
												'select' : function(combo,records) {
											
												}
												}
										},
										{
											xtype : 'button',
											text : 'Compare Similarity',
											id : 'Save',
											disabled : false,
											padding:'0 25 25 -17',
											handler : function(store, btn, args) {

												var reviewGenFormat = generateJSONRequestForSimilarity();
												urlLink = contextPath + '/review/compareArticle.do';
												hideConfirmationMsg();
												doJSONRequestForSimilarity(reviewGenFormat,urlLink);
											}
										}]
							});
							
			
	//Now the Grid with Data 
			 
			 
			 
			 
			 
			 
			 
	
		});
		
		