Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});
Ext.tip.QuickTipManager.init();



var webColumns=[
         			{
         				header : 'Frequency ID',
         				dataIndex : 'freqId',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'Article Name',
         				dataIndex : 'articleName',
         				sortable:true,
         				width    :100
         			},
         			{
         				header : 'Sentence ID',
         				dataIndex : 'sentenceId',
         				sortable:true,
         				width    :100
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

var hideConfirmationMsg;
var showConfirmationMsg;
/* Hide the Confirmation Message */
	hideConfirmationMsg = function() {
		var confMsgDiv = Ext.get('confirmationMessage');
		confMsgDiv.dom.innerHTML = "";
		confMsgDiv.dom.style.display = 'none';
	}
	/* Show Confirmation Message */
	showConfirmationMsg = function(msg) {
		var confMsgDiv = Ext.get('confirmationMessage');
		confMsgDiv.dom.innerHTML =  msg;
		confMsgDiv.dom.style.display = 'inline-block';		
	}
	var webSiteStore;
Ext.onReady(function () {

	var loadMask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading"});
	loadMask.show();
	
	Ext.define('webModel',{
		extend : 'Ext.data.Model',
		fields : [ 
				  {name:'freqId', mapping:'freqId',type:'int'},
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

	webStore = Ext.create('Ext.data.Store', {
		id : 'webSiteStoreId',
		name : 'webSiteStoreName',
		model : 'webModel',
		proxy : {
			type : 'ajax',
			url :contextPath+'/review/viewFreq.do',
			extraParams:{
			},
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
						
				loadMask.hide();
			}
		},
		autoLoad : true
	});
	
	rightArticleFreqInfoListGrid = Ext.create('Ext.grid.Panel', {
		title:'Articles Frequency Information',
		forceFit : true,
		id : 'rightArticleFreqInfoListGrid',
		store : webStore,
		columns : webColumns,
		width:2000,
		height:1000,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : Ext.getBody(),
		collapsible:true
	});

});
	
	
	
